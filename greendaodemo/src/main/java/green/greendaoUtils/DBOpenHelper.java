package green.greendaoUtils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import org.greenrobot.greendao.database.Database;

import green.DaoMaster;
import green.GreenDaoBeanDao;
import green.TestBeanDao;
import green.UserDao;


/**
 * @author 小东
 * @version v1.0
 * @date 2017/2/28 10:00
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
        MigrationHelper.getInstance().migrate(db, UserDao.class,GreenDaoBeanDao.class,TestBeanDao.class);
    }
}
