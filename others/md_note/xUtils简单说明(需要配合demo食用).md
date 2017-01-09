## xUtils3 简介
>Createed by shiqkuangsan

### 导入

1. 方式1: 使用Gradle  compile 'org.xutils:xutils:3.3.36'

2. 方式2: 导入一个module类库(github上有类库下载),必然报错(插件找不到).将下面这句代码添加Project的build.gradle中(dependencies方法中如果已存在直接添加里面的代码就行了)
			
			dependencies {
		        classpath 'com.android.tools.build:gradle:2.0.0'
		        classpath 'com.jfrog.bintray.gradle:gradle-bintray-plugin:1.2'
		        classpath 'com.github.dcendents:android-maven-gradle-plugin:1.3'
		        // NOTE: Do not place your application dependencies here; they belong
		        // in the individual module build.gradle files
    		}

3. 打开Project Structure 添加依赖

### 初始化

1. 在项目入口处初始化,新建类MyApplication继承自Application

			  x.Ext.init(this);
			//x.Ext.setDebug(BuildConfig.DEBUG);

2. 不要忘记给上面的MyApplication配置name(在清单文件的Application节点中)

			android:name=".MyApplication"

### 使用
>简单的使用, xUtls分为四大模块:注解模块(常用的是View注解和onClick注解)、网络模块(网络请求和上传下载)、图片模块(这个就是设置option加载图片)、数据库模块

1. View注解模块

	简单明了的注解绑定id

		@ViewInject(R.id.btn_get)  
	    Button btn_get;  
	    @ViewInject(R.id.btn_post)  
	    Button btn_post;  

	记得在activity的onCreate()方法中注入事件     
		
		x.view().inject(this); 

	事件的注解,方法必须private修饰,方法名随意,不设置type默认是点击
		
		// @Event(value={R.id.btn_get,R.id.btn_post},type=View.OnClickListener.class)  
		// 上面的是完整模式,默认是设置OnClickListener
	    @Event(value={R.id.btn_get,R.id.btn_post})  
	    private void processOnclick(View view){  
	        switch(view.getId()){  
	        case R.id.btn_get:  
	            break;  
	        case R.id.btn_post:  
	            break;  
	        }  
	    }


2. 网络模块


	(1) 网络请求get: 调用SimplexUtil的sendGet()方法即可,参数参见方法说明,值的一提的是CallBack的泛型.正常使用String,那么拿到json数据自行解析使用.但是如果想更简单一点呢?直接返回bean或者List<bean\>.这就需要给你的bean添加一个注解,在类名上面.

		@HttpResponse(parser = JsonResponseParser.class)

	其中JsonResponseParser需要实现ResponseParser,重写checkResponse()和parse()方法,只管parse()方法.利用fastjson解析直接将json转换成bean的操作在parse()中返回那么就可以在CallBack的泛型上使用了

		compile 'com.alibaba:fastjson:1.2.17'

		@Override
	    public Object parse(Type resultType, Class<?> resultClass, String result) throws Throwable {
	        if (resultClass == List.class)
	            return JSON.parseArray(result, (Class<?>) ParameterizedTypeUtil.getParameterizedType(resultType, List.class, 0));
	        else
	            return JSON.parseObject(result, resultClass);
	    }
	
	(2) 网络请求post: 和get一样的使用方法,调用SimplexUtil的sendPost().

	(3) 上传文件: 调用SimplexUtil的uploadFile(),参数见方法说明

	(4) 下载文件: 调用SimplexUtil的downloadFile(),参数见方法说明.此时CallBack的泛型可以设置为File.

3. 图片模块

	这个比较简单,就是直接调用SimplexUtil.loadImage()方法,其中的参数ImageOptions工具类中提供了两个getSimpleImageOption(),getSimpleCircleImageOption(),前者加载一般图片和gif,后者加载圆形图.具体参数见方法说明

4. 数据库模块
>这个内容太多了,这里只介绍一些简单的使用

	(1) xUtils使用通过传入DaoConfig初始化DbManager来操作数据库.使用者只需要调用SimplexUtil的getSimpleDbManager()通过传入相应的配置即可获取对应数据库的DbManager从而实现管理,建议参考SimpleDbHelper实现manager的单例,当然做法你随意.如果有多个数据库可以建立不同的DbHelper,从而提供不同的manager管理对应的数据库.而在使用manager的时候,xUtils会先去看看这张表存不存在,不存在就创建,存在就执行相应操作.不需要单独创表

	(2) xUtils操作数据通过注解结合bean技术,通过使用对象才实现数据的操作.你需要在你的bean类类名上面添加注解.是需要在数据库操作的字段同样要用注解绑定.其中的属性必须有一个主键.其isId属性为true,autoGen一般都为true,自增长的意思
		
		@Table(name = "province")

		@Column(name = "_id", isId = true, autoGen = true)
	    public Integer _id;// 主键
	
	    @Column(name = "id")
	    public Integer id;// 省id
	
	    @Column(name = "name")
	    public String name;// 省名

	(3) 简单提一提几个方法

		manager.save(Object obj)	存入一个对象

		查找id=30的省,也可以用 > < 号,也可以findAll()
		manager.selector(Province.class).where("id", "=", "30").findFirst();
			
		// 1.更新一个数据,首先你得找到这个数据,然后设置属性,完了你告诉我哪些属性名你变动了
		manager.update(Object obj, String...updateColumnName);
		// 2:你想改谁?键值对(可传多个)告诉我怎么改
		manager.update(Province.class, WhereBuilder.b("id", "=", "30"), new KeyValue("name", "日本省"));

		// 删除某个数据 / 一张表的所有数据
		manager.delete(Object obj);
		manager.delete(Class<?> bean);
		
		除了以上几个简单的方法,xUtils还单独提供通过主键id进行操作的方法.还有执行sql语句进行操作的方法.想入xUtils坑就试试,国产良心大作不谢


#xUtils -- 让开发更简单,谁用谁知道.