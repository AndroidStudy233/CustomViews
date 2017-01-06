package com.shiqkuangsan.mycustomviews.db;

import com.shiqkuangsan.baiducityselector.utils.MyLogUtil;

import io.realm.DynamicRealm;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmMigration;

/**
 * Created by shiqkuangsan on 2017/1/6.
 * <p>
 * ClassName: RealmManager
 * Author: shiqkuangsan
 * Description: realm数据库管理者
 */
public class RealmManager {

    private static Realm realm;

    public static Realm getInstance(String realmName, int version) {
        if (realm == null) {
            synchronized (RealmManager.class) {
                if (realm == null) {
                    realm = init(realmName,version);
                }
            }
        }
        return realm;
    }


    /**
     * 初始化realm,如果你的app不同的账号登录同一台设备需要建立不同的数据库(根据名称来区分)
     *
     * @param realmName 数据库名称
     * @param version   版本号
     * @return realm对象
     */
    private static Realm init(String realmName, int version) {
        RealmConfiguration configuration = new RealmConfiguration.Builder()
                .name(realmName)// 配置名字
                .encryptionKey(new byte[64])// 加密用字段,不是64位会报错
                .schemaVersion(version)//版本号
                .migration(new RealmMigration() {
                    @Override
                    public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {
                        MyLogUtil.d("oldVersion: " + oldVersion);
                        MyLogUtil.d("newVersion: " + newVersion);
                    }
                })  // 迁移
//                .modules()    // 结构
//                .inMemory()// 设置后会放在缓存中,数据库关闭数据就没了
                .build();
        Realm.setDefaultConfiguration(configuration);
//        Context.getFilesDir() 目录下的realmName.realm数据库
        return Realm.getDefaultInstance();
    }

}
