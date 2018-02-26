package green;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.greendao.demo.entity.GreenDaoBean;
import com.greendao.demo.entity.TestBean;
import com.greendao.demo.entity.User;

import green.GreenDaoBeanDao;
import green.TestBeanDao;
import green.UserDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig greenDaoBeanDaoConfig;
    private final DaoConfig testBeanDaoConfig;
    private final DaoConfig userDaoConfig;

    private final GreenDaoBeanDao greenDaoBeanDao;
    private final TestBeanDao testBeanDao;
    private final UserDao userDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        greenDaoBeanDaoConfig = daoConfigMap.get(GreenDaoBeanDao.class).clone();
        greenDaoBeanDaoConfig.initIdentityScope(type);

        testBeanDaoConfig = daoConfigMap.get(TestBeanDao.class).clone();
        testBeanDaoConfig.initIdentityScope(type);

        userDaoConfig = daoConfigMap.get(UserDao.class).clone();
        userDaoConfig.initIdentityScope(type);

        greenDaoBeanDao = new GreenDaoBeanDao(greenDaoBeanDaoConfig, this);
        testBeanDao = new TestBeanDao(testBeanDaoConfig, this);
        userDao = new UserDao(userDaoConfig, this);

        registerDao(GreenDaoBean.class, greenDaoBeanDao);
        registerDao(TestBean.class, testBeanDao);
        registerDao(User.class, userDao);
    }
    
    public void clear() {
        greenDaoBeanDaoConfig.clearIdentityScope();
        testBeanDaoConfig.clearIdentityScope();
        userDaoConfig.clearIdentityScope();
    }

    public GreenDaoBeanDao getGreenDaoBeanDao() {
        return greenDaoBeanDao;
    }

    public TestBeanDao getTestBeanDao() {
        return testBeanDao;
    }

    public UserDao getUserDao() {
        return userDao;
    }

}
