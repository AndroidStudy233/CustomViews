package green.greendaoUtils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import org.greenrobot.greendao.database.Database;

import green.DaoMaster;
import green.GreenDaoBeanDao;
import green.TestBeanDao;
import green.UserDao;


/**
 * @author 李小米
 * @version v1.0
 * @date 2018/4/08 10:00
 * @detail 数据库升级
 */
public class DBOpenHelper extends DaoMaster.OpenHelper {
    private static final String TAG = DBOpenHelper.class.getSimpleName();

    public DBOpenHelper(Context context, String dbName, SQLiteDatabase.CursorFactory factory) {
        super(context, dbName, factory);
    }

    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        super.onUpgrade(db, oldVersion, newVersion);
        //这里把所有的数据库表都放进来就可以了，不用分别放
        //在方法里边写了操作
        MigrationHelper.getInstance().migrate(db, true, UserDao.class, GreenDaoBeanDao.class, TestBeanDao.class);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        super.onDowngrade(db, oldVersion, newVersion);
        //数据库降级操作
        //这里有个bug,版本高的数据库---》版本低   新的数据库表不会被删除
        MigrationHelper.getInstance().migrate(wrap(db), false, UserDao.class, GreenDaoBeanDao.class, TestBeanDao.class);
    }
}
