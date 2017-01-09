# Realm 

>Realm于2014 年7月发布, 是一个跨平台的移动数据库引擎, 专门为移动应用的数据持久化而生. 其目的是要取代Core Data和SQLite. 本文只是简单的介绍下Realm, 具体文档请戳

[Realm学习文档](https://realm.io/docs/java/latest/)

1. 配置Realm插件

	1) 在project的build.gradle下添加classpath依赖

		buildscript {
		    repositories {
		        jcenter()
		    }
		    dependencies {
		        classpath "io.realm:realm-gradle-plugin:2.2.1"
		    }
		}

	2) 在 app 的 build.gradle 文件中应用 realm-android 插件。(最上面那行)

		apply plugin: 'realm-android'

	3) 也可以使用maven配置(还是用build配置习惯一点)

		buildscript {
		    repositories {
		        maven {
		            url 'http://oss.jfrog.org/artifactory/oss-snapshot-local'
		        }
		    }
		    dependencies {
		        classpath "io.realm:realm-gradle-plugin:<version>-SNAPSHOT"
		    }
		}
		
		repositories {
		    maven {
		        url 'http://oss.jfrog.org/artifactory/oss-snapshot-local'
		    }
		}

2. 配置好就可以直接使用了

	1) 新建bean类,必须继承自RealmObject

		public class Contact extends RealmObject {
		    /**联系人账号*/
		    private String contactId;
		    /**联系人昵称*/
		    private String username;
			
			get()/set()
		}

	2) 初始化Realm

		private Realm realm;
		/**
	     * 初始化Realm
	     */
	    private void initRealm() {
	        RealmConfiguration configuration = new RealmConfiguration.Builder(this)
	                .name("test.realm")//配置名字
	                .encryptionKey(new byte[64])//加密用字段,不是64位会报错
	                .schemaVersion(1)//版本号
	                //.migration()
	                //.modules()
	                .inMemory()//设置后会放在缓存中
	                .build();
	        Realm.setDefaultConfiguration(configuration);
	        realm = Realm.getDefaultInstance();
	    }

	3) 已经可以进行操作了

		// 增 
		private void create() {
	        realm.executeTransaction(new Realm.Transaction() {
	            @Override
	            public void execute(Realm realm) {
	                Contact contact = realm.createObject(Contact.class);
	                contact.setContactId("123456");
	                contact.setUsername("王连生");
	
	                Contact contact2 = realm.createObject(Contact.class);
	                contact2.setContactId("666666");
	                contact2.setUsername("李春坚");
	            }
	        });
	    }

		// 查
		private void query() {
	        RealmResults<Contact> contacts = realm.where(Contact.class).equalTo("contactId", "666666").findAll();
	        MyLogUtil.d("李春坚: " + contacts.get(0).getUsername());
	    }
		
		Book book = realm.where(Book.class)  
                .equalTo("name","史记")//相当于where name='史记'  
                .or()//或，连接查询条件；没有这个方法时，默认是隐式地被逻辑和(&)组合  
                .equalTo("author","司马迁")//相当于 author='司马迁'  
                .findFirst();  


		// 改
		private void update() {
	        realm.executeTransaction(new Realm.Transaction() {
	            @Override
	            public void execute(Realm realm) {
	                Contact contact = realm.where(Contact.class).findFirst();
	                contact.setUsername("修改后的王连生");
	                contact.setContactId("233233");
	
	                RealmResults<Contact> contacts = realm.where(Contact.class).equalTo("contactId", "233233").findAll();
	                MyLogUtil.d("修改后: " + contacts.get(0).getUsername());
	            }
	        });
    	}

		// 删
		 private void delete() {
	        realm.executeTransaction(new Realm.Transaction() {
	            @Override
	            public void execute(Realm realm) {
					// realm.delete(Contact.class);// 删除所有
	                realm.where(Contact.class).equalTo("contactId", "233233").findAll().deleteAllFromRealm();
	
	                RealmResults<Contact> contacts = realm.where(Contact.class).findAll();
	                MyLogUtil.d("只剩李春坚: " + contacts.get(0).getUsername());
	            }
	        });
   		 }



### 使用Realm的注意点

* 存储字段类型
	
	boolean、byte、short、int、long、float、double、String、Date和byte[]

	Boolean、Byte、Short、Integer、Long、Float和Double。通过使用包装类型，可以使这些属性存取空值（null）。(有时候这些类存null没意义可以使用注解@Required , 告诉 Realm 强制禁止空值（null）被存储)

* Realm 支持以下查询条件：

		  realm.where(UserInfo.class)	// 跟查询条件
		  .equalTo("name","张三")	// 等于条件

		//.or()                      或者

        //.beginsWith()              以xxx开头

        //.endsWith()                以xxx结尾

        //.greaterThan()             大于

        //.greaterThanOrEqualTo()    大于或等于

        //.lessThan()                小于

        //.lessThanOrEqualTo()       小于或等于

        //.equalTo()                 等于

        //.notEqualTo()              不等于

        //.findAll()                 查询所有

        //.average()                 平均值

        //.beginGroup()              开始分组

        //.endGroup()                结束分组

        //.between()                 在a和b之间

        //.contains()                包含xxx

        //.count()                   统计数量

        //.distinct()                去除重复

        //.findFirst()               返回结果集的第一行记录

        //.isNotEmpty()              非空串

        //.isEmpty()                 为空串

        //.isNotNull()               非空对象

        //.isNull()                  为空对象

        //.max()                     最大值

        //.maximumDate()             最大日期

        //.min()                     最小值

        //.minimumDate()             最小日期

        //.sum()                     求和

* 查询排序 / 查完之后排序

		// 根据createdTime降序查询
		realm.where(IMessage.class).equalTo("sessionId", sessionId).findAllSorted("createdTime", Sort.DESCENDING);
		
		// 查完之后拿到结果再自行排序
		result = result.sort("age"); // Sort ascending
		result = result.sort("age", Sort.DESCENDING);

* 链式查询

		你可以查询找出所有年龄在 13 和 20 之间的 Person 并且他至少拥有一个 1 岁的 Dog：

		RealmResults<Person> teensWithPups = realm.where(Person.class).between("age", 13, 20).equalTo("dogs.age", 1).findAll();
	
* 主键字段和必写使用注解标注
	
		@PrimaryKey
   		private Integer _id;

		@Required
    	private String userName;//用户姓名,必填字段

* 根据一个对象来直接更新

		如果存在这个数据就会更新如果不存在就会增加。
		realm.copyToRealmOrUpdate()