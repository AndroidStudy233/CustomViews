# GreenDao的使用

## 一. 环境配置
### 1.module的build.gradle引入依赖

    compile 'org.greenrobot:greendao:3.2.0'//GreenDao


### 2.在module的build.gradle中进行配置：

    apply plugin: 'org.greenrobot.greendao'//GreenDao

### 3.project的build.gradle:

    buildscript {
        repositories {
            jcenter()

        }
        dependencies {
            classpath 'org.greenrobot:greendao-gradle-plugin:3.2.1'//GreenDao
        }
    }

然后再Make Project 你会在build/generated/source/greendao中发现新生成的内容，
build/generated/source/greendao是默认路径，可以去配置的，配置方法在接下来将会讲到。
####(这里貌似只有新建了实体类也就是一张表之后才会生成。。。。。。亲测)

## 二. Gradle Plugin的配置:
 
> In the build.gradle file of your app project:

    android {
        ...
    }

    greendao {
        schemaVersion 1
        //daoPackage 'com.greendao.gen'
        //targetGenDir 'src/main/java'
    }

* schemaVersion 当前数据库结构的版本。结构版本变化时在OpenHelpers中被使用到。当你改变实体或者数据的结构时，这个值应该增加。
* daoPackage 生成的DAO，DaoMaster和DaoSession的包名。默认是实体的包名。
* targetGenDir 生成源文件的路径。默认源文件目录是在build目录中的(build/generated/source/greendao)。
* generateTests 设置是否自动生成单元测试。
* targetGenDirTest 生成的单元测试的根目录。
    
## 三. 实体注解：
  
    @Entity(
        // 如果你有超过一个的数据库结构，可以通过这个字段来区分
        // 该实体属于哪个结构
        schema = "myschema",

        //  实体是否激活的标志，激活的实体有更新，删除和刷新的方法
        active = true,

        // 确定数据库中表的名称
        // 表名称默认是实体类的名称
        nameInDb = "AWESOME_USERS",

        // Define indexes spanning multiple columns here.
        indexes = {
            @Index(value = "name DESC", unique = true)
        },

        // DAO是否应该创建数据库表的标志(默认为true)
        // 如果你有多对一的表，将这个字段设置为false
        // 或者你已经在GreenDAO之外创建了表，也将其置为false
        createInDb = false
    )
        public class User {
            ...
        }
   
## 四. 基础注解：

    @Entity
    public class User {
        @Id(autoincrement = true)
        private Long id;
    
        @Property(nameInDb = "USERNAME")
        private String name;
    
        @NotNull
        private int repos;
    
        @Transient
        private int tempUsageCount;
    
        ...
    }

### 1.实体@Entity注解

* schema：告知GreenDao当前实体属于哪个schema
* active：标记一个实体处于活动状态，活动实体有更新、删除和刷新方法
* nameInDb：在数据中使用的别名，默认使用的是实体的类名
* indexes：定义索引，可以跨越多个列
* createInDb：标记创建数据库表
    
### 2.基础属性注解
    
* @Id :主键 Long型，可以通过@Id(autoincrement = true)设置自增长
* @Property：设置一个非默认关系映射所对应的列名，默认是的使用字段名 举例：@Property (nameInDb="name")
* @NotNul：设置数据库表当前列不能为空
* @Transient ：添加次标记之后不会生成数据库表的列

### 3.索引注解
    
* @Index：使用@Index作为一个属性来创建一个索引，通过name设置索引别名，也可以通过unique给索引添加约束
* @Unique：向数据库列添加了一个唯一的约束

### 4.关系注解
    
* @ToOne：定义与另一个实体（一个实体对象）的关系
* @ToMany：定义与多个实体对象的关系
    
## 编写帮助类

    public class GreenDaoHelper {
    
        private static DaoMaster.DevOpenHelper mHelper;
        private static SQLiteDatabase db;
        private static DaoMaster mDaoMaster;
        private static DaoSession mDaoSession;
    
        /**
         * 初始化greenDao，这个操作建议在Application初始化的时候添加；
         */
        public static void initDatabase() {
            // 通过 DaoMaster 的内部类 DevOpenHelper，你可以得到一个便利的 SQLiteOpenHelper 对象。
            // 可能你已经注意到了，你并不需要去编写「CREATE TABLE」这样的 SQL 语句，因为 greenDAO 已经帮你做了。
            // 注意：默认的 DaoMaster.DevOpenHelper 会在数据库升级时，删除所有的表，意味着这将导致数据的丢失。
            // 所以，在正式的项目中，你还应该做一层封装，来实现数据库的安全升级。
            mHelper = new DaoMaster.DevOpenHelper(MyApplication.context, "cache-db", null);
            db = mHelper.getWritableDatabase();
            // 注意：该数据库连接属于 DaoMaster，所以多个 Session 指的是相同的数据库连接。
            mDaoMaster = new DaoMaster(db);
            mDaoSession = mDaoMaster.newSession();
        }
        public static DaoSession getDaoSession() {
            return mDaoSession;
        }
        public static SQLiteDatabase getDb() {
            return db;
        }
    
    }
    
#### 增删改查

    首先获取UserDao对象：
    
    mUserDao = GreenDaoHelper.getDaoSession().getUserDao();
    
#### 增
    
    数据库的增删改查我们都将通过UserDao来进行，插入操作如下：
    
    mUser = new User((long)1,"张三");
    mUserDao.insert(mUser);//添加一个
    User的第一个参数为id，如果这里传null的话在插入的过程中id字段会自动增长（现在知道为什么id要为Long类型了吧！）
    
#### 删
    
    删除数据和修改数据的思路一样，都是要先查找到数据：
    
    List<User> userList = (List<User>) mUserDao.queryBuilder().where(UserDao.Properties.Id.le(10)).build().list();  
    for (User user : userList) {
        mUserDao.delete(user);
    }

    where表示查询条件，这里我是查询id小于等于10的数据，where中的参数可以有多个，就是说可以添加多个查询条件。最后的list表示查询结果是一个List集合，如果你只想查询一条数据，最后unique即可。当然，我们也可以根据id来删除数据：

    User user = mUserDao.queryBuilder().where(UserDao.Properties.Id.eq(16)).build().unique();
            if (user == null) {
                Toast.makeText(MainActivity.this, "用户不存在", Toast.LENGTH_SHORT).show();
            }else{
                mUserDao.deleteByKey(user.getId());
            }
    根据主键删除

    mUserDao.deleteByKey(id);
    删除所有数据

    mUserDao.deleteAll();

#### 改
            
    根据ID修改数据

    mUser = new User((long)2,"anye0803");
    mUserDao.update(mUser);
            
#### 查
            
    查询全部数据

    List<User> users = mUserDao.loadAll();
    查询一条数据

    User user = mUserDao.queryBuilder().where(UserDao.Properties.name.eq("张三")).unique();
            
### 数据库升级

    数据库的升级其实就两个步骤我们来看看

    1.修改gradle文件

    首先在module的gradle文件中修改版本号：

    //这里改为最新的版本号
    schemaVersion 2

    2.修改实体类
    @Entity
    public class User {
        @Property
        private int age;
        @Property
        private String password;
        @Id
        private Long id;
        @Property(nameInDb = "USERNAME")
        private String username;
        @Property(nameInDb = "NICKNAME")
        private String nickname;
    }
    重现编译项目运行即可。一般的数据库升级这样就可以了，特殊情况可能需要自己编写数据库迁移脚本。
            
### tips: 还有一些比较好的链接

http://www.jianshu.com/p/4986100eff90
http://www.cnblogs.com/whoislcj/p/5651396.html
http://blog.csdn.net/u012702547/article/details/52226163

### greendao 版本升级
http://blog.csdn.net/u011071427/article/details/54574171