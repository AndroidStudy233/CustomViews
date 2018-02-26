package com.greendao.demo;

import android.app.Application;

import green.DaoMaster;
import green.DaoSession;
import green.greendaoUtils.DBManager;


/*************************************************
 * <p>创建人：余志伟</p>
 * <p>创建时间 : 2017/8/25</p>
 * <p>修改人：       </p>
 * <p>修改时间：   </p>
 * <p>修改备注：  李小米修改   2018.2.26
 *                1、修改数据库初始化方法
 *                2、把获取DaoSession的方法放在DBMaager中
 *                3、做一下数据库的升级功能
 *
 * @version V3.1
 *********************************/

public class MyApplication extends Application {

//    private DaoMaster daoMaster;
//    private DaoSession daoSession;
//    private static MyApplication myApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化数据库
        DBManager.getInstance().init(this);
//        myApplication = this;
//        initDb();
    }

//    public void initDb() {
//        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(getApplicationContext(), "lenve.db", null);
//        daoMaster = new DaoMaster(devOpenHelper.getWritableDb());
//        daoSession = daoMaster.newSession();
//    }
//
//    public DaoSession getDaoSession() {
//        return daoSession;
//    }
//
//    public static MyApplication getApplication() {
//        return myApplication;
//    }
}
