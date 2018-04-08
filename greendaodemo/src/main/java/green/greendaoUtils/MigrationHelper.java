package green.greendaoUtils;

import android.database.Cursor;
import android.text.TextUtils;
import android.util.Log;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.internal.DaoConfig;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import green.DaoMaster;

/**
 * @author 小米
 * @version v1.0
 * @date 2018/4/08 10:00
 * @detail 具体实现
 */
public class MigrationHelper {

    private static final String CONVERSION_CLASS_NOT_FOUND_EXCEPTION = "MIGRATION HELPER - CLASS DOESN'T MATCH WITH THE CURRENT PARAMETERS";
    private static MigrationHelper instance;

    public static MigrationHelper getInstance() {
        if (instance == null) {
            instance = new MigrationHelper();
        }
        return instance;
    }

    public void migrate(Database db, boolean isUp, Class<? extends AbstractDao<?, ?>>... daoClasses) {
        if (isUp) {
            Log.e("###", "####################################数据库升级操作####################################");
        } else {
            Log.e("###", "####################################数据库降级####################################");
        }

        generateTempTables(db, isUp, daoClasses);//创建出临时表，并且把数据转存到临时表中
        DaoMaster.dropAllTables(db, true);//如果存在这个表就删除表
        DaoMaster.createAllTables(db, false);//创建新的数据库
        restoreData(db, isUp, daoClasses);//然后把临时表的数据转存到新的数据库中
    }


    /**
     * 生成临时列表
     * 把原来表中有的字段复制到临时表，把原来表中没有的就略过，在新生成的表里边，新字段都是空的
     *
     * @param db
     * @param daoClasses
     */
    private void generateTempTables(Database db, boolean isUp, Class<? extends AbstractDao<?, ?>>... daoClasses) {
        for (int i = 0; i < daoClasses.length; i++) {
            DaoConfig daoConfig = new DaoConfig(db, daoClasses[i]);//这里都出来的不是原来的，是新的实体类里边的属性
            String divider = "";
            String tableName = daoConfig.tablename;//拿到表名
            String tempTableName = daoConfig.tablename.concat("_TEMP");//临时表名
            ArrayList<String> properties = new ArrayList<>();//要复制的字段
            StringBuilder createTableStringBuilder = new StringBuilder();
            createTableStringBuilder.append("CREATE TABLE ").append(tempTableName).append(" (");

            for (int j = 0; j < daoConfig.properties.length; j++) {//遍历每一个字段，就是每一列的列名
                String columnName = daoConfig.properties[j].columnName;//取到列名
                //如果数据库表中的列名包含这个bean里边的字段，就把数据复制过去，下边的循环就是去拼接sql的
                //就是说如果这个字段是新添加的字段，就不用复制了，如果是原来的字段，就复制
                if (getColumns(db, tableName).contains(columnName)) {
                    properties.add(columnName);
                    String type = null;
                    try {
                        type = getTypeByClass(daoConfig.properties[j].type);//获取字段类型
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                    createTableStringBuilder.append(divider).append(columnName).append(" ").append(type);//拼接sql
                    if (daoConfig.properties[j].primaryKey) {//如果是主键，就设置上主键
                        createTableStringBuilder.append(" PRIMARY KEY");
                    }
                    divider = ",";
                }
            }

            if (isUp && properties.size() == 0) {//如果是升级，要检查是不是遍历所有的
                //这里发现没有要复制的字段，就略过这个表，进行下一个循环
                continue;
            }
            //如果有要复制的字段，就继续拼接sql，并执行语句，把数据复制到临时表中
            createTableStringBuilder.append(");");
            db.execSQL(createTableStringBuilder.toString()); //执行sql,到这里创建出临时表
            //这里把数据库里边的数据转存到临时表中
            StringBuilder insertTableStringBuilder = new StringBuilder();
            insertTableStringBuilder.append("INSERT INTO ").append(tempTableName).append(" (");
            insertTableStringBuilder.append(TextUtils.join(",", properties));
            insertTableStringBuilder.append(") SELECT ");
            insertTableStringBuilder.append(TextUtils.join(",", properties));
            insertTableStringBuilder.append(" FROM ").append(tableName).append(";");
            db.execSQL(insertTableStringBuilder.toString());
        }
    }

    /**
     * 存储新的数据库表 以及数据
     *
     * @param db
     * @param daoClasses
     */
    private void restoreData(Database db, boolean isUp, Class<? extends AbstractDao<?, ?>>... daoClasses) {
        //循环每一个表
        for (int i = 0; i < daoClasses.length; i++) {
            DaoConfig daoConfig = new DaoConfig(db, daoClasses[i]);//取出字段
            String tableName = daoConfig.tablename;//拿到表明
            String tempTableName = daoConfig.tablename.concat("_TEMP");//临时表明
            ArrayList<String> properties = new ArrayList();//要复制的字段集合
            for (int j = 0; j < daoConfig.properties.length; j++) {
                String columnName = daoConfig.properties[j].columnName;
                if (getColumns(db, tempTableName).contains(columnName)) {
                    properties.add(columnName);
                }
            }

            if (isUp && properties.size() == 0) {//如果是升级，要检查是不是遍历所有的
                //这里发现没有要复制的字段，就略过这个表，进行下一个循环
                continue;
            }

            //存到新表里边
            StringBuilder insertTableStringBuilder = new StringBuilder();
            insertTableStringBuilder.append("INSERT INTO ").append(tableName).append(" (");
            insertTableStringBuilder.append(TextUtils.join(",", properties));
            insertTableStringBuilder.append(") SELECT ");
            insertTableStringBuilder.append(TextUtils.join(",", properties));
            insertTableStringBuilder.append(" FROM ").append(tempTableName).append(";");
            //删除临时表
            StringBuilder dropTableStringBuilder = new StringBuilder();
            dropTableStringBuilder.append("DROP TABLE ").append(tempTableName);
            db.execSQL(insertTableStringBuilder.toString());
            db.execSQL(dropTableStringBuilder.toString());
        }
    }

    private String getTypeByClass(Class<?> type) throws Exception {
        if (type.equals(String.class)) {
            return "TEXT";
        }
        if (type.equals(Long.class) || type.equals(Integer.class) || type.equals(long.class)) {
            return "INTEGER";
        }
        if (type.equals(Boolean.class)) {
            return "BOOLEAN";
        }

        Exception exception = new Exception(CONVERSION_CLASS_NOT_FOUND_EXCEPTION.concat(" - Class: ").concat(type.toString()));
        exception.printStackTrace();
        throw exception;
    }

    /**
     * 根据数据库表，拿到所有的列名
     *
     * @param db
     * @param tableName
     * @return
     */
    private List<String> getColumns(Database db, String tableName) {
        List<String> columns = new ArrayList<>();
        Cursor cursor = null;
        try {
            //取表里边的第一条数据
            cursor = db.rawQuery("SELECT * FROM " + tableName + " limit 1", null);
            if (cursor != null) {//如果是空的，就拿到所有的列名，然后转换成数组
                columns = new ArrayList<>(Arrays.asList(cursor.getColumnNames()));
            }
        } catch (Exception e) {
            Log.v(tableName, e.getMessage(), e);
            e.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return columns;
    }
}