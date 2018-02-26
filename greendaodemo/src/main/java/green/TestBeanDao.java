package green;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.greendao.demo.entity.TestBean;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "TEST_BEAN".
*/
public class TestBeanDao extends AbstractDao<TestBean, Long> {

    public static final String TABLENAME = "TEST_BEAN";

    /**
     * Properties of entity TestBean.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property TestId = new Property(0, Long.class, "TestId", true, "_id");
        public final static Property UserName = new Property(1, String.class, "UserName", false, "USER_NAME");
    }


    public TestBeanDao(DaoConfig config) {
        super(config);
    }
    
    public TestBeanDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"TEST_BEAN\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ," + // 0: TestId
                "\"USER_NAME\" TEXT);"); // 1: UserName
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"TEST_BEAN\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, TestBean entity) {
        stmt.clearBindings();
 
        Long TestId = entity.getTestId();
        if (TestId != null) {
            stmt.bindLong(1, TestId);
        }
 
        String UserName = entity.getUserName();
        if (UserName != null) {
            stmt.bindString(2, UserName);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, TestBean entity) {
        stmt.clearBindings();
 
        Long TestId = entity.getTestId();
        if (TestId != null) {
            stmt.bindLong(1, TestId);
        }
 
        String UserName = entity.getUserName();
        if (UserName != null) {
            stmt.bindString(2, UserName);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public TestBean readEntity(Cursor cursor, int offset) {
        TestBean entity = new TestBean( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // TestId
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1) // UserName
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, TestBean entity, int offset) {
        entity.setTestId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setUserName(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(TestBean entity, long rowId) {
        entity.setTestId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(TestBean entity) {
        if(entity != null) {
            return entity.getTestId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(TestBean entity) {
        return entity.getTestId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
