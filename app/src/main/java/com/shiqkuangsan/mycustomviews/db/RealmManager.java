package com.shiqkuangsan.mycustomviews.db;

import com.shiqkuangsan.mycustomviews.utils.MyLogUtil;

import java.util.Set;

import io.realm.DynamicRealm;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmMigration;
import io.realm.RealmObjectSchema;

/**
 * Created by shiqkuangsan on 2017/1/6.
 * <p>
 * ClassName: RealmManager
 * Author: shiqkuangsan
 * Description: realm数据库管理者,记得先去Application初始化
 */
public class RealmManager {

    private static RealmManager manager;

    public static RealmManager getInstance(String realmName, int version) {
        if (manager == null) {
            synchronized (RealmManager.class) {
                if (manager == null) {
                    manager = new RealmManager();
                    init(realmName, version);
                }
            }
        }
        return manager;
    }

    private Realm realm;

    /**
     * 初始化realm,比如说如果你的app在不同的账号登录同一台设备就要需要建立不同的数据库(根据名称来区分)
     *
     * @param realmName 数据库名称
     * @param version   版本号
     */
    private static void init(String realmName, int version) {
        RealmConfiguration configuration = new RealmConfiguration.Builder()
                .name(realmName)// 配置名字
                .encryptionKey(new byte[64])// 加密用字段,不是64位会报错
                .schemaVersion(version)//版本号
                // 数据bean结构变化时原本的realm数据库文件还存在时调用.
                .migration(new RealmMigration() {
                    @Override
                    public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {
                        MyLogUtil.debug("oldVersion: " + oldVersion);
                        MyLogUtil.debug("newVersion: " + newVersion);
                        RealmObjectSchema schema = realm.getSchema().get("Doctor");
                        Set<String> names = schema.getFieldNames();
                        if (names.contains("id"))
                            schema.removeField("id");
                        if (names.contains("name") && !schema.getPrimaryKey().equals("name")){
                            schema.removePrimaryKey();
                            schema.addPrimaryKey("name");
                        }

                    }
                })  // 迁移
//                .modules()    // 结构
//                .inMemory()// 设置后会放在缓存中,数据库关闭数据就没了
                .build();
        Realm.setDefaultConfiguration(configuration);
//        Context.getFilesDir() 目录下的realmName.realm数据库
    }

    public Realm getRealm() {
        if (realm == null)
            realm = Realm.getDefaultInstance();
        return realm;
    }
}
