
#小知识 / 小操作 :


### 矢量图在线制作

>矢量图区别于位图(像素), 位图放大了其实就是一个一个点, 矢量图放的再大也很圆润, 不过矢量图不能表示复杂的颜色

		http://editor.method.ac/

---

* ListView中有个属性是去掉每个条目之间的分割线的

		// 设置没有分割线
		android:divider="@android:color/transparent"(也可以直接写@null)
		// 点击条目没有背景不显示颜色
        android:listSelector="@android:color/transparent" >

* 布局之中还嵌套了一个布局文件用include节点引进
 
---

## MD支持插入图片 / 链接

>要求本地图片的路径必须是相对路径, 使用div的方式可以限定图片的宽高(只写一个属性就行了,另一个属性自适应).而使用标准格式引用不能控制大小

	 使用div的方式,引用网络图片和本地图片. 

<div>	
	<img src='http://srain-github.qiniudn.com/ultra-ptr/contains-all-of-views.gif'  height="500" style='border: #67F2F7 solid 2px'/>
</div>

<div>
	<img src='/others/images/3.jpg'  width="400px" style='border: #67F2F7 solid 2px'/>
</div>

	使用 "![描述](图片路径)"的方式

![我的图片](http://srain-github.qiniudn.com/ultra-ptr/contains-all-of-views.gif)

![我的动漫图](/others/images/3.jpg)

>插入一个链接

[点击打开百度](https://www.baidu.com/)
---

## 汉字转拼音类库

		compile 'com.github.promeg:tinypinyin:1.0.0'

		/**
		 * 如果c为汉字，则返回大写拼音；如果c不是汉字，则返回String.valueOf(c)
		 */
		String Pinyin.toPinyin(char c)
		
		/**
		 * c为汉字，则返回true，否则返回false
		 */
		boolean Pinyin.isChinese(char c)

---

## xml画圆角矩形
1. 在res/drawable目录下新建xml文件, 选择shape节点, 然后在根节点shape中设置shape属性

2. 添加corner属性设置圆角, 添加solid节点设置背景颜色等等

---

### adb -s emulator-5554 install app-debug.apk    

---

## android给空包签名

	jarsigner -verbose -keystore 'keystorePath' -signedjar 'apkOut' 'apkIn' 'alias'
	也可以
	jarsigner -verbose -keystore 'keystorePath' 'apkIn' 'alias'

---
## date转格式

	public static Date parseDate(String dateStr, String pattern){
        SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.getDefault());
        try
        {
            return sdf.parse(dateStr);
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }
        return null;
    }

---
## "app_name" is not translated in "zh" (Chinese) [MissingTranslation]解决办法

Android在打release包时，可能会出现这种错误。 
解决办法就是在build.gradle里面进行配置：

	android{

	  	lintOptions {
	        disable 'InvalidPackage'
	        //只要添加下面这一句就可以了
	        disable 'MissingTranslation'
	    }

    }
---
## xml画渐变
	
还是shape节点.不过这次属性是添加gradient.startcolor / endColor


---

## json中的一个JSONObject获取key

 	Iterator it = obj.keys();
	List<String> keyListstr = new ArrayList<String>();  
    while(it.hasNext()){  
        keyListstr.add(it.next().toString());  
    }  

---

## Android修改AVD的路径

android 虚拟机的保存目录默认的是C:\Documents and Settings\用户名\.android。如想自己更改AVD的位置只需要做如下三步操作即可。

	（1）到AVD的默认文件夹下将“.android”剪切到你想放的盘片，比如“D:\Android\AVDs”目录下。
	（2）然后在系统环境变量里设置一个ANDROID_SDK_HOME，将“D:\Android\AVDs”复制到该变量下。
	（3）修改D:\Android\AVDs\.android\avd目录下的配置文件中的path项目。

例如“Android2.2.ini”修改其中的path
path=D:\Android\AVDs\.android\avd\Android2.2.avd
即可。
  注意，在修改时，要使eclipse和AVD关闭。
然后启动eclipse 进入android virtual devices manager 可以看到路径已经变化了。

---
### 获取登录设备mac地址

	/**
     * 获取登录设备mac地址
     *
     * @return
     */
    protected String getMac() {
        WifiManager wm = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        String mac = wm.getConnectionInfo().getMacAddress();
        return mac == null ? "" : mac;
    }

---
## outOfMemory:GC overHead

放开gradle.properties中的javaargs那行

---

### support-V4包中的某个class---duplicate, ctrl + N 复制包名

---

### dlopen failed.  xxx.so  has text relocations

说明编译.so文件时使用了较低版本sdk 
而project 中的配置 targetSdkVersionxxx 大于so编译时使用的sdkversion，所以只需要把功能中 
的targetSdkVersion降低即可 

	defaultConfig { 
		applicationId “com.example” 
		minSdkVersion 16 
		targetSdkVersion 22    这个调低点
		versionCode 1 
		versionName “1.0” 
	}

---
### 查看调试版和发布版的SHA1值

	// 调试版 在.android目录下(一般都是没有密码的.)
	keytool -list -v -keystore debug.keystore
	
	// 正式版 就是你的keystore路径目录下
	keytool -list -v -keystore xxxx.keystore

---
## 查看指定端口占用情况
		
		1. 查看跟该端口所有关联进程
		netstat -aon|findstr "5037"

		2. 根据不同的PID查找具体的进程
		tasklist|findstr "2360"

---

## ScrollView / ListView 控制滑动速度 
	
	自定义ScrollView然后重写fling()方法, 该方法中的参数velocityY可以控制滑动速度

	在ListView初始化之后setFriction(ViewConfiguration.getScrollFriction() * 2); 通过设置摩擦力来达到改变滑动速度的目的

---

## Gradle配置修改生成的apk名字

>和buildConfig / buildTypes方法同级

	/**修改生成的apk名字*/
    applicationVariants.all{ variant->
        variant.outputs.each { output->
            def oldFile = output.outputFile
            if(variant.buildType.name.equals('release')){
                def releaseApkName = '4G远程诊疗'+defaultConfig.versionName +"-"+getDate() + '.apk'
                output.outputFile = new File(oldFile.parent, releaseApkName)
            }
        }
    }

---

### 底部导航栏中间的按钮突出效果

>通过合理的布局当然可以实现.这里说个简便方法

	android:clipChildren = false;

相对布局中使用该属性. 底部是个LinearLayout, 其中5个按钮宽度按weight分配, 可以给中间的按钮设置 'layout_gravity' 属性为bottom, 然后指定他的高度比LinearLayout高就行

---
## Gradle配置clean操作删除build文件

>project的build.gradle文件中添加

	task clean(type: Delete) {
    	delete rootProject.buildDir
	}

---
## XML布局文件中tools:属性

根部局中加入tools命名空间,是为了区分布局中的预览和实际使用.有点迷,举个栗子

		xmlns:tools="http://schemas.android.com/tools"


3个TextView	2016-10-25 如果给的属性是如下那么加载上去的时候会先默认显示11-22-33,待到textView的逻辑数据处理完才会显示你想要的2016-10-25,而如果使用tools属性,默认首先会是 空白( - - ) 
	
		android:text="11"
		android:text="22"
		android:text="33"

		tools:text="11"
		tools:text="22"
		tools:text="33"

---

## 自定义字体,设置字体

>在activity的onCreate()或者onStart()方法中设置,有两个ttf文件在MyCustomViews项目下

	AssetManager mgr=getAssets();//得到AssetManager
    Typeface tf=Typeface.createFromAsset(mgr, "fonts/rm_albion.ttf");//根据路径得到Typeface
    tv_splash_info.setTypeface(tf);//设置字体

---
## 按钮的点击与否两种状态对应两张图片的方法

1. 如果需要两种状态显示不同背景(按钮点击与否), 在res/drawable目录下新建xml文件, 选择selector属性

2. 然后添加节点item, 在drawable属性中选择目标图片, state_pressed属性选择true/false, 另外再写个item,就选择另一种状态. 

2. 然后在布局文件中组件的背景中设置

	android:background="@drawable/selector_button_left"(/后面是selector文件名)


---

## FAT32转NTFS
>其中E盘是你要转的目标盘符

	convert E:/FS:NTFS 

---

## ButterKnife的完整依赖

* 在项目的project 的build.gredle 文件中的dependencies标签下添加。

		classpath 'com.neenbedankt.gradle.plugins:android-apt:1.8'

* 在module的build.gredle 文件中添加

		apply plugin: 'android-apt'

* 在module的build.gredle 文件中的dependencies标签中添加

		compile 'com.jakewharton:butterknife:8.4.0'
		apt 'com.jakewharton:butterknife-compiler:8.4.0'

---
### android中的百分比布局

	 compile 'com.android.support:percent:22.2.0'

	// 支持的属性

	layout_widthPercent、layout_heightPercent、 	
	layout_marginPercent、layout_marginLeftPercent、 
	layout_marginTopPercent、layout_marginRightPercent、 
	layout_marginBottomPercent、layout_marginStartPercent、layout_marginEndPercent。

	可以看到支持宽高，以及margin。

---

## 想在ListView中设置,一点进来就跳转到某一个指定的条目.或者是最后一个条目(这是在adapter是CursorAdapter的情况)
比如在短信界面. 一点进来就想看到最新的消息条目, 该操作在初始化initView()或者initData()中都是没法实现的.因为我们的LsitView在构造的时候, 设置的适配器是的参数Cursor是null,也就是说只有在changeCursor()方法调用的时候,LsitView的条目才能正常显示. 所以.该操作只能在Adapter中实现(重写changeCursor()方法)

---


## android中的动画插值器Interpolator.

<div>
	<img src='/others/images/anime_interpolator.gif'  width="400px" style='border: #67F2F7 solid 2px'/>
</div>

1. AccelerateDecelerateInterpolator 加速减速插补器（先慢后快再慢）

2. AccelerateInterpolator 加速插补器（先慢后快）

3. AnticipateInterpolator 向前插补器（先往回跑一点，再加速向前跑）

4. AnticipateOvershootInterpolator 向前向后插补器（先往回跑一点，再向后跑一点，再回到终点）

5. BounceInterpolator 反弹插补器（在动画结束的时候回弹几下，如果是竖直向下运动的话，就是玻璃球下掉弹几下的效果）

6. CycleInterpolator 循环插补器（按指定的路径以指定时间（或者是偏移量）的1/4、变速地执行一遍，再按指定的轨迹的相反反向走1/2的时间，再按指定的路径方向走完剩余的1/4的时间，最后回到原点。假如：默认是让a从原点往东跑100米。它会先往东跑100米，然后往西跑200米，再往东跑100米回到原点。可在代码中指定循环的次数）

7. DecelerateInterpolator 减速插补器（先快后慢）

8. LinearInterpolator 直线插补器（匀速）

9. OvershootInterpolator 超出插补器（向前跑直到越界一点后，再往回跑）

10. FastOutLinearInInterpolator MaterialDesign基于贝塞尔曲线的插补器 效果：依次 慢慢快

11. FastOutSlowInInterpolator MaterialDesign基于贝塞尔曲线的插补器 效果：依次 慢快慢

12. LinearOutSlowInInterpolator MaterialDesign基于贝塞尔曲线的插补器 效果：依次 快慢慢


使用方法1: 

	<?xml version="1.0" encoding="utf-8"?>
	<set xmlns:android="http://schemas.android.com/apk/res/android"
	    android:fillAfter="true"
	    android:interpolator="@android:anim/accelerate_interpolator">//对当前动画设置插补器
	    <translate
	        android:duration="2000"
	        android:fromXDelta="50%"
	        android:fromYDelta="0%"
	        android:interpolator="@android:anim/accelerate_interpolator"// 对当前节点设置插补器
	        android:toXDelta="500%"
	        android:toYDelta="0%" />
	</set>


使用方法2:

	Animation mAnimation = AnimationUtils.loadAnimation(this, R.anim.xxx);//引用动画文件
	mAnimation.setInterpolator(new AccelerateDecelerateInterpolator());//代码设置插补器
	view.startAnimation(mAnimation);

---

## Material Design风格(MD风格)设计实例

1. 设置该背景的View点击会有一个轻微的点击动画效果.MD风格的响应式

	android:background="?android:attr/selectableItemBackground"

2. 上面的效果同样可以使用ripple样式的xml实现,但是ripple节点是API21之后才有的属性,其中ripple节点的color指的是波纹的颜色(必须是8位颜色值),里边的drawable属性指的是正常状态下按钮的背景,也可以自己画shape也可以用图片

	// 给按钮设置background,直接引用这个xml

		<ripple xmlns:android="http://schemas.android.com/apk/res/android" android:color="@android:color/darker_gray">

		<!--<item android:drawable="@android:color/white"/>-->

	    <item>
	        <shape android:shape="rectangle">
	            <solid android:color="#FFFFFF" />
	            <corners android:radius="4dp" />
	        </shape>
	    </item>

	</ripple>

3. Circular Reveal动画效果,主要是ViewAnimationUtils的使用.
	
		Animator animator = ViewAnimationUtils.createCircularReveal(
	                        btn_main, 0, 0, 0, (float) Math.hypot(btn_main.getWidth(), btn_main.getHeight()));
	    animator.setInterpolator(new AccelerateInterpolator());
	    animator.setDuration(600);
	    animator.start();

		// 多个元素用
		 Pair.create(((View) iv1),"my_iv")

	参数:1. view对象; 2. 圆形动画的开始地点x坐标(基于view的); 3. y坐标; 4. 动画开始的半径; 5. 动画结束的半径. (开始为0结束值适当则是显示动画;开始值适当结束为0则是隐藏动画)
	
4. Material 主题

	* 系统自带的主题

		* @android:style/Theme.Material
		
		* @android:style/Theme.Material.Light
		
		* @android:style/Theme.Material.Light.DarkActionBar
	
	* 常用的样式属性

			 <style name="RedTheme" parent="android:Theme.Material">

			    <!-- 状态栏颜色，会被statusBarColor效果覆盖-->
			    <item name="android:colorPrimaryDark">@color/status_red</item>
			    <!-- 状态栏颜色，继承自colorPrimaryDark -->
			    <item name="android:statusBarColor">@color/status_red</item>
			    <!-- actionBar颜色 -->
			    <item name="android:colorPrimary">@color/action_red</item>
			    <!-- 背景颜色 -->
			    <item name="android:windowBackground">@color/window_bg_red</item>
			    <!-- 底部栏颜色 -->
			    <item name="android:navigationBarColor">@color/navigation_red</item>
			
			    <!-- ListView的分割线颜色，switch滑动区域色-->
			    <item name="android:colorForeground">@color/fg_red</item>
			    <!-- popMenu的背景色 -->
			    <item name="android:colorBackground">@color/bg_red</item>
			
			    <!-- 控件默认颜色 ，效果会被colorControlActivated取代  -->
			    <item name="android:colorAccent">@color/control_activated_red</item>
			
			    <!-- 控件默认时颜色  -->
			    <item name="android:colorControlNormal">@color/control_normal_red</item>
			    <!-- 控件按压时颜色，会影响水波纹效果，继承自colorAccent  -->
			    <item name="android:colorControlHighlight">@color/control_highlight_red</item>
			    <!-- 控件选中时颜色 -->
			    <item name="android:colorControlActivated">@color/control_activated_red</item>
			    <!-- Button的默认背景 -->
			    <item name="android:colorButtonNormal">@color/button_normal_red</item>
			
			    <!-- Button，textView的文字颜色  -->
			    <item name="android:textColor">@color/white_text</item>
			    <!-- RadioButton checkbox等控件的文字 -->
			    <item name="android:textColorPrimaryDisableOnly">@color/white_text</item>
			    <!-- actionBar的标题文字颜色 -->
			    <item name="android:textColorPrimary">@color/white_text</item>
			</style>
	 
5. 这个应该是5.0的新特性

	* 首先是Z轴属性
		
			android:elevation   //静态Z轴的高度。相对于父控件的高度
			android:translationZ    //Z轴偏移的高度。

	* 然后是阴影轮廓
		
			android:outlineProvider：有四个值
			none：不显示阴影
			background ：按照显示区域来绘制阴影
			bounds ：在View的边界描绘阴影
			paddedBounds： 在View边界描绘阴影，但会计算paddingTop和paddingLeft

	* 取色器

			Vibrant(充满活力的)
			Vibrant dark(充满活力的黑)
			Vibrant light(充满活力的亮)
			Muted(柔和的)
			Muted dark(柔和的黑)
			Muted lighr(柔和的亮)

			//在子线程可以使用同步操作
			 Palette p = Palette.from(bitmap).generate();
			
			 //在主线程使用异步操作
			 Palette.from(bitmap).generate(new PaletteAsyncListener() {
			     public void onGenerated(Palette p) {
			         // Use generated instance
					 Palette.Swatch vibrant = Palette.getVibrantSwatch();
					// swatch下有这么几个方法
					getPopulation(): 样本中的像素数量
					getRgb(): 颜色的RBG值
					getHsl(): 颜色的HSL值
					getBodyTextColor(): 主体文字的颜色值
					getTitleTextColor(): 标题文字的颜色值
			     }
			 });
			
			
			//gradle 添加依赖
			dependencies {  
			    compile 'com.android.support:palette-v7:24.0.0'
			}


			// 额外提一提一个方法: 获取某个像素点的颜色值
			int color=Bitmap.getPixel(int x, int y);


6. 5.0+版本Activity的共享元素和场景转换动画

	* Activity转场动画
	
	第一步: 从A跳转到B,以前一直用overridepxxx()这个方法.5.0以后不用了可以直接配置.A启动B的操作改成下面的方式.
		
		ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this);
		startActivity(new Intent(MainActivity.this, TransitionActivity.class), options.toBundle());

	第二步: 在B的theme中直接配置下面的属性/或者使用代码设置
	
		<item name="android:windowActivityTransitions" tools:targetApi="lollipop">true</item>
        <item name="android:windowEnterTransition" tools:targetApi="lollipop">@android:transition/slide_left</item>
        <item name="android:windowExitTransition" tools:targetApi="lollipop">@android:transition/slide_left</item>

		or code:
		getWindow().setEnterTransition(new Explode().setDuration(1200));  
		getWindow().setExitTransition(new Explode().setDuration(1200));  

	
	* Activity共享元素.道理一样
	
	第一步: 配置两个共享元素的transitionName属性,名称必须一样.

	第二步: 还是theme的配置.

		<item name="android:windowContentTransitions" tools:targetApi="lollipop">true</item>
        <item name="android:windowSharedElementEnterTransition" tools:targetApi="lollipop">@android:transition/move</item>
        <item name="android:windowSharedElementExitTransition" tools:targetApi="lollipop">@android:transition/move</item>

		or code:
		getWindow().setSharedElementEnterTransition(new Explode().setDuration(500));
        getWindow().setSharedElementExitTransition(new Explode().setDuration(500));

	第三步: 启动B的方法使用这个,带着共享元素的transition名字去启动

		ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this, fab_main, "transition_name_fab"); 
		startActivity(new Intent(getApplicationContext(), TransitionActivity.class), options.toBundle()); 
		// 这是只有一个fab_main View的情况,如果有多个共享元素.用Pair.create(),这是可变参数

	
		

---

### 屏幕适配中的不同分辨率dimens

![dimens](/others/images/dimens.png)

## 屏幕适配 ##

- 图片适配

	- 开启4种分辨率的模拟器
	- 在drawable的多个目录下放置内容不同但命名相同的图片
	- 运行程序,查看在不同模拟器上的显示效果
	- 常规做法: 美工只做一套1280*720的图片,放置在drawable-xhdpi的目录下, ImageView宽高指定为确定的值, 不包裹屏幕

- 布局适配

	- 针对特定分辨率,创建layout文件夹: layout-800x480, layout-land(表示横屏)
	- 800x480 和其他分辨率模拟器对比
	- 常规做法: 该方式不到万不得已,一般不用

- 尺寸(dimens)适配

	- 设备密度:

			float density = getResources().getDisplayMetrics().density;

	- dp = px / 设备密度

	- 常规设备密度: 320x240(0.75/ldpi), 480x320(1/mdpi), 800x480(1.5/hdpi), 1280x720(2/xhdpi), 1920x1080(3/xxhdpi)
	- 设置dp值, 在不同屏幕上查看显示的比例
	- 创建文件夹values-1280x720, 在dimens.xml中制定尺寸, 适配屏幕
	- 常规做法: 此方法比布局适配更常用. 美工提供像素px值, 我们使用前需要用px除以设备密度,转换成dp后,写在布局文件中

	- 案例分析: 智慧北京新手引导页小圆点处理

- 权重适配

	 	android:weightSum="3" //表示总权重数
		常规做法: 当布局有严格比例分配时, 可以使用权重来处理

- 代码适配

		int width = getWindowManager().getDefaultDisplay().getWidth();
		int height = getWindowManager().getDefaultDisplay().getHeight();
		tv1.setLayoutParams(new LayoutParams((int)(width*0.5), (int)(height*0.2)));

		常规做法: 如果是自定义的控件, 没有使用xml布局文件时, 可以在代码中动态设置宽高

		案例分析: 智慧北京侧边栏宽度处理

---
## ListView显示与输入法的调整(类似于短信/QQ聊天界面很实用)

1. ListView进入界面跳到最后一条数据显示

	listView.setSelection(adapter.getCount())

如果是CursorAdapter作为适配器, 可以直接在适配器中重写changeCursor()方法, 执行上面的操作

2. 在清单文件ListView所在的Activity中设置属性(这会使得ListView上边的组件暂时不可见), 相当于输入法把ListView顶上去了:

	android:windowSoftInputMode="stateUnspecified|adjustPan"


3. 另一个设置:可以随手滑动ListView而且输入框不回去, 上边的组件不会消失, 但是缺点是相当于让输入法盖住ListView的下面部分

	android:windowSoftInputMode="stateUnspecified|adjustResize"

4. 拓展: 初始化ListView的时候设置该属性之后,ListView的滑动效果更好..标题栏不会消失,而且ListView刷新,就会自动滑动. 比如同时设置了1, 3 , 4. 就能实现比较客观的效果了 

	lv_conversation_details.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);

5. 点击会话详细的任意一条短信, 隐藏输入框软键盘

	InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
	imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);


---

## 输入法相关

	android:windowSoftInputMode

>Activity的主窗口如何与包含屏幕上的软键盘窗口交互。这个属性的设置将会影响两件事情

   
1. 软键盘的状态——是否它是隐藏或显示——当活动(Activity)成为用户关注的焦点。

   
2. 活动的主窗口调整——是否减少活动主窗口大小以便腾出空间放软键盘或是否当活动窗口的部分被软键盘覆盖时它的内容的当前焦点是可见的。它的设置必须是下面列表中的一个值，或一个"state…"值加一个"adjust…"值的组合。在任一组设置多个值——多个"state…"值.

	<activityandroid:windowSoftInputMode="stateVisible|adjustResize">

	* "stateUnspecified": 软键盘的状态(是否它是隐藏或可见)没有被指定。系统将选择一个合适的状态或依赖于主题的设置。这个是为了软件盘行为默认的设置。

	* "stateUnchanged": 软键盘被保持无论它上次是什么状态，是否可见或隐藏，当主窗口出现在前面时。

	* "stateHidden": 当用户选择该Activity时，软键盘被隐藏——也就是，当用户确定导航到该Activity时，而不是返回到它由于离开另一个Activity。

	* "stateAlwaysHidden": 软键盘总是被隐藏的，当该Activity主窗口获取焦点时。

	* "stateVisible": 软键盘是可见的，当那个是正常合适的时(当用户导航到Activity主窗口时)。

	* "stateAlwaysVisible": 当用户选择这个Activity时，软键盘是可见的——也就是，也就是，当用户确定导航到该Activity时，而不是返回到它由于离开另一个Activity。

	* "adjustUnspecified": 它不被指定是否该Activity主窗口调整大小以便留出软键盘的空间，或是否窗口上的内容得到屏幕上当前的焦点是可见的。系统将自动选择这些模式中一种主要依赖于是否窗口的内容有任何布局视图能够滚动他们的内容。如果有这样的一个视图，这个窗口将调整大小，这样的假设可以使滚动窗口的内容在一个较小的区域中可见的。这个是主窗口默认的行为设置。

	* "adjustResize": 该Activity主窗口总是被调整屏幕的大小以便留出软键盘的空间

	* "adjustPan": 该Activity主窗口并不调整屏幕的大小以便留出软键盘的空间。相反，当前窗口的内容将自动移动以便当前焦点从不被键盘覆盖和用户能总是看到输入内容的部分。这个通常是不期望比调整大小，因为用户可能关闭软键盘以便获得与被覆盖内容的交互操作。


3.  收起输入法(传入绑定的edittext)
 
		((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).  
		hideSoftInputFromWindow(editText.getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);

	传一个activity
	View view = activity.getWindow().peekDecorView();
 	 if (view != null) {
	   (InputMethodManager) getSystemServic(Context.INPUT_METHOD_SERVICE).hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
	  }

---

## 代码设置全屏

		// 去标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 去状态
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

---
## LinearLayout等布局中的ImageButton或者CheckBox等抢占条目的焦点 / 点击侦听

1. 如果imageButton / CheckBox逻辑和条目的点击逻辑一样, 可以直接屏蔽掉他们的焦点, 在布局文件中设置属性

	android:focusable="false",android:clickable="true"

2.如果各有各的逻辑, 可以在布局文中的根节点设置属性

	android:descendantFocusability="blocksDescendants", 

其意思是子孙的焦点事件视其区块位置而定

---

### 执行了动画的组件调用setVisibility()设置隐藏等状态的的时候,需要先调用clearAnimation();
---

## 在Activity绘制完成的全局监听

        ViewTreeObserver observer = view.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                view.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                final int w = view.getMeasuredWidth();
                final int h = view.getMeasuredHeight();
            }
        });

---

## 6.0需要动态申请的权限

* CALENDAR	

		READ_CALENDAR
		WRITE_CALENDAR

* CAMERA	
	
		CAMERA

* CONTACTS	
	
		READ_CONTACTS
		WRITE_CONTACTS
		GET_ACCOUNTS

* LOCATION	
	
		ACCESS_FINE_LOCATION
		ACCESS_COARSE_LOCATION

* MICROPHONE	
	
		RECORD_AUDIO

* PHONE	
	
		READ_PHONE_STATE
		CALL_PHONE
		READ_CALL_LOG
		WRITE_CALL_LOG
		ADD_VOICEMAIL
		USE_SIP
		PROCESS_OUTGOING_CALLS

* SENSORS	
	
		BODY_SENSORS

* SMS	
	
		SEND_SMS
		RECEIVE_SMS
		READ_SMS
		RECEIVE_WAP_PUSH
		RECEIVE_MMS

* STORAGE	
	
		READ_EXTERNAL_STORAGE
		WRITE_EXTERNAL_STORAGE

---
### 如果报错Gradle需要2048M以上的内存,找到gradle.properties文件最后面加上一句
		
	org.gradle.jvmargs=-Xmx3072m

---


### 代码设置页面横竖屏

	// 设置页面默认为竖屏
	setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

---

## GifView的使用

---

## 防止多次点击按钮反复弹出吐司

		private Toast toast;
	    public void showToast(String msg) {
	        if (toast != null) {
	            toast.cancel();
	            toast = null;
	        }
	        toast = Toast.makeText(this, msg + "", Toast.LENGTH_SHORT);
	        toast.show();
	    }

---
## 线程池相关
>一般为了方便整个项目的线程管理不去new Thread(){}.start(). 而是去搞一个线程管理器(直接去我的Utils文件夹下看该类ThreadManager.java就好.)搞这么一个管理器维护一个线程池去开线程.线程数根据手机CPU核心数确定.


---

### AS有时候会出现点9图片冲刷失败crush failed,那是因为该图片存在Alpha通道,在执行app:mergeDebugResources的时候出错,那是因为点9图片不正规

1. 可以将有问题的点9图片重新画好

2. 可以直接配置忽略

		对应module的build.gradle文件，在buildToolsVersion下面加入以下两句：

		aaptOptions.cruncherEnabled = false     

		aaptOptions.useNewCruncher = false


---

## 判断进程是否运行,在后台?在前台?

	private boolean isAppRunning(Context context) {
        String packageName = context.getPackageName();
        String topActivityClassName=getTopActivityName(context);

        if (packageName!=null&&topActivityClassName!=null&&topActivityClassName.startsWith(packageName)) {
            return true;
        } else {
            return false;
        }
    }

    public  String getTopActivityName(Context context){
        String topActivityClassName=null;
        ActivityManager activityManager =
                (ActivityManager)(context.getSystemService(android.content.Context.ACTIVITY_SERVICE )) ;
        List<RunningTaskInfo> runningTaskInfos = activityManager.getRunningTasks(1) ;
        if(runningTaskInfos != null){
            ComponentName f=runningTaskInfos.get(0).topActivity;
            topActivityClassName=f.getClassName();
        }
        return topActivityClassName;
    }

	//是否在前台运行,判断是否在前台运行 就判断栈顶的是否是这个包名
	info.topActivity.getPackageName().equals("com.shiqkaungsan.hello")
	//是否在后台运行,判断是否在后台运行 就判断栈中是否有这个包名
	info.baseActivity.getPackageName().equals("com.shiqkaungsan.hello")

---
## Service里面弹对话框(吐司是可以直接的)

		AlertDialog dialog = new AlertDialog.Builder(this).setTitle("标题").setMessage("内容").setPositiveButton("确定", null).create();

		dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);

		dialog.show();

		<uses-permission android:name="android.permission.SYSTEM_ALERT_WINDOW" />
		
---

## 使用本地的gradle-3.3-all.zip

替换项目中 android/gradle/wrapper/gradle-wrapper.properties 的 distributionUrl ，
即  distributionUrl=file\:///D:/gradle/gradle-2.14.1-all.zip 

---

### 打开已有的数据库 

		SQLiteDatabase.openDatabase()

---
## ListView添加headView和footView

通过把布局文件加载成view对象, 调用ListView的addHeaderView()和addFooterView()方法, 在自定义控件的时候用到, 比如上拉加载和下拉刷新, 一般初始化的时候通过测量获取测量高度, 然后设置padding值为测量高使其隐藏, 再配合手势拉出推进实现一系列逻辑.

---

## ListView滑动到顶部/底部有蓝色阴影

	listview.setOverScrollMode(View.OVER_SCROLL_NEVER);

---

## ListView的onItemClick方法中的4个参数问题

假如有一个listview，该listview中含a,b,c,d这4个item。

如果你点了b这个item。如下：

（1）public  void onItemClick (AdapterView<?> parent,  

/* parent相当于listview 适配器的一个指针，可以通过它来获得listview里装着的一切东西*/

（2）View view,     

/* view是你点b item的view的句柄，就是你可以用这个view，来获得b里的控件的id后操作控件*/

（3） int position,   

/* position是b在适配器里的位置（生成listview时，适配器一个一个的做item，然后把他们按顺序排好队，在放到listview里，意思就是这个b是第position号做好的）*/

（4） long id     

/* id是b在listview 里的第几行的位置（很明显是第2行），在没有headerView，用户添加的view以及footerView的情况下position和id的值是一样的。*/

---
### 不要忘记	@android:drawable/ 下面有很多系统为准备的小图标哦

---
# ImageView中的几种ScaleType的意思

>ImageView的scaleType的属性有好几种，分别是matrix（默认）、center、centerCrop、centerInside、fitCenter、fitEnd、fitStart、fitXY


1. android:scaleType="center"

	保持原图的大小，显示在ImageView的中心。当原图的size大于ImageView的size，超过部分裁剪处理。


2. android:scaleType="centerCrop"

	以填满整个ImageView为目的，将原图的中心对准ImageView的中心，等比例放大原图，直到填满ImageView为止（指的是ImageView的宽和高都要填满），原图超过ImageView的部分作裁剪处理。


3. android:scaleType="centerInside"

	以原图完全显示为目的，将图片的内容完整居中显示，通过按比例缩小原图的size宽(高)等于或小于ImageView的宽(高)。如果原图的size本身就小于ImageView的size，则原图的size不作任何处理，居中显示在ImageView。


4. android:scaleType="matrix"

	不改变原图的大小，从ImageView的左上角开始绘制原图，原图超过ImageView的部分作裁剪处理。

5. android:scaleType="fitCenter"

	把原图按比例扩大或缩小到ImageView的ImageView的高度，居中显示


6. android:scaleType="fitEnd"

	把原图按比例扩大(缩小)到ImageView的高度，显示在ImageView的下部分位置


7. android:scaleType="fitStart"

	把原图按比例扩大(缩小)到ImageView的高度，显示在ImageView的上部分位置


8. android:scaleType="fitXY"
	
	把原图按照指定的大小在View中显示，拉伸显示图片，不保持原比例，填满ImageView.


---

## 修改吐司背景

	　　Toast toast = Toast.makeText(context, "Toast text", Toast.LENGTH_SHORT); 
		View view = toast.getView(); 
		view.setBackgroundResource(/*你的背景图片Drawable*/); 
		toast.setView(view); 
		toast.show(); 
	

---

## SlidingMenu简介

- 导入SlidingMenu库, 需要添加侧边栏的Activity继承自SlidingActivity
- 
-  如果还用到Fragment就继承自SlidingFragmentActivity. 直接调用setBehindContentView(R.layout......)添加侧边栏,

-   要想设置模式的话就得拿到SlidingMenu对象, SlidingMenu menu = getSlidingMenu(); 
比如想左右都有侧边栏, 可以menu.setSecondaryMenu(R.layout...), menu.setMode(SlidingMenu.LIFT_RIGHT)

- 还可以设置滑动范围setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN), 设置侧边栏宽度则可以用setBehindOffset(像素值)

- 设置侧滑菜单的背景menu.setBackgroundColor(Color.BLACK);	setBackgroundResource

- 至于侧滑
- 动画的可以看源码实现

## SlidingMenu + Actionbarsherlock

>新版的SlidingMenu使用到ActionBar, 但是他用的是开源框架Actionbarsherlock, 所以使用起来需要两个都导进来, 配合使用效果杠杠的

注意事项：

1、 SlidingMenu 的demo工程引用了ActionBarSherlock 里的Threme和style，所以要把后者的libary添加到前者的工程中；否则前者的style.xml文件和AndroidManifest.xml都会报错，大致如下：

Theme.Sherlock.Light.DarkActionBar无法找到
      No resource found that matches the given name: attr 'homeAsUpIndicator'.
      No resource found that matches the given name 'Theme.Sherlock.Light.DarkActionBar'.
	原因是它本身又引用了另外一个开源库 ActionBarSherlock ，所以首先要做的就是去下载 ActionBarSherlock，引用其中的library。

2、 这样，SlidingMenu 的示例代码就引用了两个library，这时候还不能用，项目报错：
      Jar mismatch! Fix your dependencies
      解决此问题的方法，其实就是需要两个library使用的support包是一样的。

3、 解决了上面的问题之后，还会出现下面的问题：
      可能报找不到getSupportActionBar等ActionBarSherLock的方法。原因是使用ActionBarSherLock的Activity需继承于SherlockActivity，修改SlidingMenu library中的SlidingFragmentActivity，让它继承于SherlockFragmentActivity，重新编译library导入。

---
## 有时候在查询数据的时候, 如果报错.有可能就是游标cursor需要调用moverToFirst()

		E/AndroidRuntime(2252): java.lang.RuntimeException: 
		Failure delivering result ResultInfo{who=null, request=1, result=-1, data=Intent 

---
## PopupWindow小知识
* PopupWindow默认不能获取焦点, 如果需要获取焦点得在代码中设置setFocusble(true)

* PopupWindow可以设置点击外部区域自动消失setOutsideTouchable(true), 一般也设置个空背景

* PopupWindow显示需要调用方法showAt...showAs...

* PopupWindow默认没有背景, 而没有背景是不能执行动画的, 需要它需要执行动画的时候得强行设置背景

---
### EditText有个addTextChangedListener()侦听,专门监控输入文本改变的

---

### android判断是不是今天和日期格式化
	
		DateUtils.isToday(Date date)

        if (DateUtils.isToday(sms.getDate())) {
            holder.date.setText(DateFormat.getTimeFormat(context).format(sms.getDate()));
        } else {
            holder.date.setText(DateFormat.getDateFormat(context).format(sms.getDate()));
   		}



---

## MotionEvent中getRawX、getRawY与getX、getY以及View中的getScrollX、getScrollY

1. 在编写android的自定义控件，或者判断用户手势操作时，往往需要使用MotionEvent中的getRawX()、getRawY()与getX()、getY()取得触摸点在X轴与Y轴上的距离，这四个方法都返回一个float类型的参数，单位为像素（Pixel）。在一个自定义的View中, 重写onTouchEvent, 调用getRawX()、getRawY()返回的是触摸点相对于屏幕的距离(以屏幕左上角为原点)，而getX()、getY()返回的则是触摸点相对于该View的距离(以view左上角为原点)。

2. 
	
	* left(getLeft())：View相对于父View的左边坐标。

			这个值不会随着View的属性（transitionX和X）改变。
			View在layout的时候，会调用setFrame函数来改变left的值。
			客户端也可以直接调用setLeft来改变left值。
			left值改变后，View的大小也会改变。因为View的大小是right - left。

	* x(getX()): View实际显示的左边坐标。它的值是  translationX + left

	* tanslationX(getTranslationX):View 相对于left的位移。

    		 属性动画改变的是x和translationX的值，没有改变left的值。
	
所以，这三个值的关系是：

   x = translationX + left。

---

## 项目中自行配置jni的路径
>在module的defaultConfig{}中

		sourceSets {
            main {
                jniLibs.srcDirs = ['libs']
                java.srcDirs = ['src/main/java']
            }
        }

---

### 另一款好用的json解析

fastjson

	compile 'com.alibaba:fastjson:1.2.17'

---

### 打开系统中本App的设置页面

	Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
     Uri uri = Uri.fromParts("package", context.getPackageName(), null);
    intent.setData(uri);

---
## 将汉字转化成汉语拼音

> // http://www.360doc.com/content/09/0216/20/15103_2563474.shtml#

导入jar包: pinyin4j.jar

	public String getPinYin(String inputString) {

		HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
		format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		format.setVCharType(HanyuPinyinVCharType.WITH_U_UNICODE);

		char[] input = inputString.trim().toCharArray();
		StringBuffer output = new StringBuffer("");

		try {
			for (int i = 0; i < input.length; i++) {
				if (Character.toString(input[i]).matches("[\u4E00-\u9FA5]+")) {
					String[] temp = PinyinHelper.toHanyuPinyinStringArray(
							input[i], format);
					output.append(temp[0]);
					output.append(" ");
				} else
					output.append(Character.toString(input[i]));
			}
		} catch (BadHanyuPinyinOutputFormatCombination e) {
			e.printStackTrace();
		}
		return output.toString();
	}

---
### 滚动式TextView

>这儿的自定义控件ScrollTextView其实就是直接继承自TextView然后重写isFocused方法 返回true意思是可以获取焦点, 因为android中TextView默认不能获取焦点

 	<!--
         ellipsize: none			省略前面的后面的文字
    	 ellipsize: start 		隐藏前面的文字
    	 ellipsize: middle 		隐藏中间的文字
    	 ellipsize: end 			隐藏后面的文字
    	 
    	 ellipsize: marquee	 	设置成滚动
    	 
    	 focusable				设置是否可触摸
    	 focusableInTouchMode	设置触摸获取焦点
    	 marqueeRepeatLimit		设置滚动次数限制
    -->

// 注意配合重写方法使用
    
		<com.shiqkuangsan.safer_diy.ui.custom.ScrollTextView
	        android:id="@+id/tv_main_scroll"
	        android:layout_width="match_parent"
	        android:layout_height="wrap_content"
	        android:layout_marginTop="5dp"
	        android:ellipsize="marquee"
	        android:focusableInTouchMode="true"
	        android:gravity="center_horizontal"
	        android:marqueeRepeatLimit="marquee_forever"	
	        android:singleLine="true"
	        android:text="  非常牛逼一款手机应用, 谁用谁知道, 啥功能都有, 东哥倾力设计打造~~"
	        android:textSize="17sp" />

---

## Glide简单用法

1. 引用

		repositories {
		  mavenCentral() // jcenter() works as well because it pulls from Maven Central
		}
		
		dependencies {
		  compile 'com.github.bumptech.glide:glide:3.7.0'
		  compile 'com.android.support:support-v4:19.1.0'
		}

2. 使用

	  	Glide
	    .with(myFragment)
	    .load(url)
	    .centerCrop()
	    .placeholder(R.drawable.loading_spinner)
	    .crossFade()
	    .into(myImageView);


---
## 在Activity中代码设置取消titlebar

在setContentView(R.layout.main);前加入如下两行代码：

		getWindow().setFlags(WindowManager.LayoutParam.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

		requestWindowFeature(Window.FEATURE_NO_TITLE);

---

## Toolbar简介

1. 在Activity的布局文件中使用
		
		<android.support.v7.widget.Toolbar
	        android:id="@+id/toolbar_main"
	        android:layout_width="match_parent"
	        android:layout_height="wrap_content"/>
 
2. 简单设置

 		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        toolbar.setNavigationIcon(R.mipmap.xxx);// 设置导航栏图标
        toolbar.setLogo(R.mipmap.xxx);// 设置app logo
        toolbar.setTitle("xxx");// 设置主标题
        toolbar.setSubtitle("xxx");// 设置子标题
		toolbar.inflateMenu(R.memnu.xxx)// 设置右边填充的菜单
		toolbar.setOnMenuItemClickListener()// 设置菜单点击侦听

		// 也可以使用ToolBar的属性在布局中实现(注意命名空间)
		app:navigationIcon="@mipmap/xxx"
        app:logo="@mipmap/xxx"
        app:subtitle="xxx"
        app:title="xxx"
		
		// 其中这两个属性需要注意 1. theme: 因为默认是黑色该属性可以控制返回键/菜单键等白色
		// 2. popupTheme: 下拉菜单的主题如背景色白色
		app:theme="@style/ThemeOverlay.AppCompat.Dark.ActionBar"(ThemeOverlay.AppCompat.Light就是黑色的返回键)
		app:popupTheme="@style/ThemeOverlay.AppCompat.Light"

3. 响应overflow下拉菜单: 在res/menu中建立menu布局文件. 设置item

		<menu xmlns:android="http://schemas.android.com/apk/res/android"
		    xmlns:app="http://schemas.android.com/apk/res-auto">
		
		    <item
		        android:id="@+id/menu_listh"
		        android:orderInCategory="100"
		        android:title="item1"
		        app:showAsAction="ifRoom" />
		
		    <item
		        android:id="@+id/menu_grid"
		        android:orderInCategory="100"
		        android:title="item2"
		        app:showAsAction="ifRoom" />
		
		    <item
		        android:id="@+id/menu_gridh"
		        android:orderInCategory="100"
		        android:title="item3"
		        app:showAsAction="never" />
		
		</menu>

 	其中orderInCategory属性是优先级显示(值越大越低), showAsAction属性的意思:

		ifRoom		会显示在Item中，但是如果已经有4个或者4个以上的Item时会隐藏在溢出列表中。当然	个数并不仅仅局限于4个，依据屏幕的宽窄而定
		
		never		永远不会显示。只会在溢出列表中显示，而且只显示标题，所以在定义item的时候，最好把标题都带上。
	
		always		无论是否溢出，总会显示。
	
		withText	withText值示意Action bar要显示文本标题。Action bar会尽可能的显示这个标题，但是，如果图标有效并且受到Action bar空间的限制，文本标题有可能显示不全。
	
		collapseActionView  	声明了这个操作视窗应该被折叠到一个按钮中，当用户选择这个按钮时，这个操作视窗展开。否则，这个操作视窗在默认的情况下是可见的，并且即便在用于不适用的时候，也要占据操作栏的有效空间。一般要配合ifRoom一起使用才会有效果。
		
4. 两个方法中具体实现
	
		@Override
	    public boolean onCreateOptionsMenu(Menu menu) {
	        // 加载菜单
	        getMenuInflater().inflate(R.menu.main_menu, menu);
	        return true;
	    }
	
	    @Override
	    public boolean onOptionsItemSelected(MenuItem item) {
			// 根据不同的id设置响应事件
	        switch (item.getItemId()) {
			}
			return super.onOptionsItemSelected(item);
		}

5. overflow菜单位置显示在状态栏下,但是想让它显示在ToolBar下位置

	>在Activity的style定义中加上一行,引用样式

		<item name="actionOverflowMenuStyle">@style/OverflowMenu</item>
	
		<!--创建这个主题，继承自主题Widget.AppCompat.PopupMenu-->
	    <style name="OverflowMenu" parent="Widget.AppCompat.PopupMenu.Overflow">
	        <!--兼容Api 21之前的版本 (Spinner也有该属性)-->
	        <item name="overlapAnchor">false</item>
	        <!-- Api 21 以上专用-->
	        <!--<item name="android:overlapAnchor">false</item>-->

			.. 另外还有这么几个属性
	        <item name="android:dropDownHorizontalOffset">0dp</item>
	        <!-- 溢出菜单离toolbar的距离-->
	        <item name="android:dropDownVerticalOffset">0dp</item>
	        <!-- 溢出菜单的背景色-->
	        <item name="android:popupBackground">@color/app_blue</item>
	    </style>

6. 设置返回键 / 绑定DrawerLayout(一般通过支持ActionBar实现)
		
		// 首先需要支持ActionBar
		setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

 		drawerlayout = (DrawerLayout) findViewById(R.id.drawer_coordinator_main);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerlayout, toolbar, R.string.drawer_open, R.string.drawer_close);
        toggle.syncState();
        drawerlayout.addDrawerListener(toggle);

7. 添加搜索功能

	* 首先在ToolBar上添加搜索按钮, 也就是在加载的菜单中添加一个item
		
			<!--actionViewClass最关键，实现了主要的搜索视图功能-->
	   		<item
		        android:id="@+id/menu_bgimmerse_search"
		        android:orderInCategory="80"
		        android:title="搜索"
		        		app:actionViewClass="android.support.v7.widget.SearchView"
		        app:showAsAction="ifRoom" />
	
	* 然后再xml目录下定义一个searchable文件mysearch.xml
	
		// imeOptions属性会使得键盘的enter键变成搜索键点击触发搜索事件
		<searchable xmlns:android="http://schemas.android.com/apk/res/android"
		    android:label="@string/app_name"
		    android:imeOptions="actionSearch"
		    android:hint="搜索..." />

	* 在Activity的清单文件中添加meta属性
	
		<meta-data
                android:name="android.app.searchable"
                android:resource="@xml/mysearch" />

	* 接下来的配置就分两种情况了:
	
		1. 搜索框的结果只需要在本Activity,接着在本Activity中配置过滤器
		
				<intent-filter>
	                <action android:name="android.intent.action.SEARCH" />
	            </intent-filter>
		
		
				// 加载菜单的时候初始化组件
				@Override
			    public boolean onCreateOptionsMenu(Menu menu) {
			        getMenuInflater().inflate(R.menu.menu_bgimmerse, menu);
			        // 初始化搜索框,设置侦听
			        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
			        searchView = (SearchView) menu.findItem(R.id.menu_bgimmerse_search).getActionView();
			        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
			        searchView.setOnQueryTextListener(this);
			        return true;
			    }
	
				// 实现SearchView.OnQueryTextListener接口
				重写onQueryTextChange() / onQueryTextSubmit()方法自行处理

		2. 搜索结果需要传给另一个Activity:目标Activity还需要如下配置
		
				<activity android:name="xxx.SearchResultActivity"
		            android:parentActivityName="xxx.SearchActivity">
		            <meta-data
		                android:name="android.support.PARENT_ACTIVITY"
		                android:value="xxx.SearchActivity" />
		            
		            <meta-data android:name="android.app.searchable"
		                android:resource="@xml/mysearch" />
		            <intent-filter>
		                <action android:name="android.intent.action.SEARCH" />
		            </intent-filter>
		        </activity>

			// SearchResultActivity界面中可以从启动意图中拿到搜索内容
			String content = getIntent().getStringExtra(SearchManager.QUERY);


8. 当前界面点击搜索绑定ListView根据搜索内容过滤item

	1. 通过搜索框的onQueryTextChange()方法拿到输入框的文本newText自定过滤adapter的数据,通知adapter更新

	2. 通过让adapter实现Filterable接口实现过滤.需要设置listView.setTextFilterEnabled(true);然后拿到newText
	
			adapter.getFilter().filter("newText(也就是过滤文本)");

---
 
## 沉浸式状态栏   由于沉浸式是android 19以后才支持 搞个values-v19,values-v21, v21支持手动设置状态栏颜色(19是半沉浸,21以后才是全沉浸)

>由于是4.4 V19 以后才有,所以需要搞个values-v19 / 21文件夹, 里面再搞一个styles.xml定义该样式(注意其他一致,重写immerse_theme)
首先在Activity的根布局中添加属性,并设置想沉浸的background属性颜色
		
		android:fitsSystemWindows="true"
		// 背景设置成图片可以实现图片沉浸,注意v21需要设置statusBarColor透明	
		android:background="#e0fcfc"

		v19的方法就是利用fitsSystemWindows属性配合设置windowTranslucentStatus属性直接在根部局设置背景颜色来沉浸到状态栏实现    
       
		v21的方法是通过fitsSystemWindows属性分离状态栏和你的Activity布局然后直接设置状态栏颜色实现沉浸式,两者都完美支持ActionBar和ToolBar(至于下面的导航栏沉不沉浸看需求)

		// v19的处理
		<style name="immerse_theme" parent="@style/Theme.AppCompat.Light.NoActionBar">
		    <!--半透明状态栏-->
	        <item name="android:windowTranslucentStatus">true</item>
	        <!--半透明导航栏-->
	        <item name="android:windowTranslucentNavigation">false</item>
		</style>
	
		// v21的处理(不能再使用半透明状态栏了)
		<style name="immerse_theme" parent="@style/Theme.AppCompat.Light.NoActionBar">
			<!--半透明状态栏-->
	        <item name="android:windowTranslucentStatus">false</item>
	        <!--半透明导航栏-->
	        <item name="android:windowTranslucentNavigation">false</item>
	  		<!--Android 5.x开始需要手动设置颜色，否则导航栏会呈现系统默认的浅灰色-->
	        <!--<item name="android:statusBarColor">#你的ToolBar/topView颜色一致</item>-->
	    </style>

注意: 一般由于为了v21中的沉浸样式能被所有需要沉浸式的Activity用,在代码中设置该属性(抽象方法实现),每个Activity需要沉浸什么颜色自己决定.另外可以设置boolean变量是否需要沉浸式.对于界面显示是一个图片的沉浸式则需要设置状态栏的颜色为Color.TRANSPARENT

---

### 采用ToolBar的Activity可以直接通过指定label属性(建议string引用)来定义ToolBar的title

---

## 获取开发版调试版的SHA1值

1. 调试版: 进入你的.android目录下(如果你没设置默认在c盘),打开命令行输入

	keytool -list -v -keystore debug.keystore

密码就是android

2. 开发版: 随便在哪儿打开命令行,输入

	keytool -list -v -keystore	(这后面跟的是你的keystore的全路径)

密码就是你自己设置的密码


---
# Bitmap和Base64之间的转换

		/** 
		 * bitmap转为base64 
		 * @param bitmap 
		 * @return 
		 */  
		public static String bitmapToBase64(Bitmap bitmap) {  
		  
		    String result = null;  
		    ByteArrayOutputStream baos = null;  
		    try {  
		        if (bitmap != null) {  
		            baos = new ByteArrayOutputStream();  
		            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);  
		  
		            baos.flush();  
		            baos.close();  
		  
		            byte[] bitmapBytes = baos.toByteArray();  
		            result = Base64.encodeToString(bitmapBytes, Base64.DEFAULT);  
		        }  
		    } catch (IOException e) {  
		        e.printStackTrace();  
		    } finally {  
		        try {  
		            if (baos != null) {  
		                baos.flush();  
		                baos.close();  
		            }  
		        } catch (IOException e) {  
		            e.printStackTrace();  
		        }  
		    }  
		    return result;  
		}  
		  
		/** 
		 * base64转为bitmap 
		 * @param base64Data 
		 * @return 
		 */  
		public static Bitmap base64ToBitmap(String base64Data) {  
		    byte[] bytes = Base64.decode(base64Data, Base64.DEFAULT);  
		    return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);  
		}  

---
## SQLite小知识

* 一般在上面的runQuery()方法中使用模糊查询. 也就是给的selection是这样的语句 
	
		String selection = "data1 like '%" + constraint + "%'";

	意思是data1列中只要其中包含constraint的都给我找到

* 点击下拉框中的条目,返回条目中的联系人号码(直接显示在输入框)

		@Override
		public CharSequence convertToString(Cursor cursor) {
			return cursor.getString(cursor.getColumnIndex("data1"));
		}

* 一般的为查询语句为:
	
		select name, age, hight from student where age = 24

	意思是:从student表中, 查询name,age,hight这三列, 如果所有列都查询就用 * ,查询的条件是年龄等于24岁. 如果想插多个年龄就这样

		  select name, age, hight from student where age in(24,30,40)


---
## Drawable和Bitmap转换 

1. Bitmap转Drawable

	
	Bitmap bm=xxx; //xxx根据你的情况获取
	
		BitmapDrawable bd=new BitmapDrawable(bm);

	因为BtimapDrawable是Drawable的子类，最终直接使用bd对象即可。

2. Drawable转Bitmap
	
	转成Bitmap对象后，可以将Drawable对象通过Android的SK库存成一个字节输出流，最终还可以保存成为jpg和png的文件。
	Drawable d=xxx; //xxx根据自己的情况获取drawable
	
		BitmapDrawable bd = (BitmapDrawable) d;
		Bitmap bm = bd.getBitmap();

	最终bm就是我们需要的Bitmap对象了。

### 打包时移除无用的资源文件

	buildTypes下对应的Type(一般release设置这个)下添加配置: 
	shrinkResources true

### 从资源中获取Bitmap

		public static Bitmap getBitmapFromResources(Context context, int resId){
			return context.getResources().getDrawable(resId);
		}


		public static Bitmap getBitmapFromResources(Context context, int resId) {
			Resources res = context.getResources();
			return BitmapFactory.decodeResource(res, resId);
		}


		// byte[] → Bitmap
		public static Bitmap convertBytes2Bimap(byte[] b) {
			if (b.length == 0) {
				return null;
			}
			return BitmapFactory.decodeByteArray(b, 0, b.length);
		}


		// Bitmap → byte[]
		public static byte[] convertBitmap2Bytes(Bitmap bm) {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
			return baos.toByteArray();
		}

1. Drawable → Bitmap

		public static Bitmap convertDrawable2BitmapByCanvas(Drawable drawable) {
			Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
			drawable.getIntrinsicHeight(),
			drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
			: Bitmap.Config.RGB_565);

			Canvas canvas = new Canvas(bitmap);
			// canvas.setBitmap(bitmap);
			drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
			drawable.getIntrinsicHeight());
			drawable.draw(canvas);
			return bitmap;
		}

2. Drawable → Bitmap

		public static Bitmap convertDrawable2BitmapSimple(Drawable drawable){
			BitmapDrawable bd = (BitmapDrawable) drawable;
			return bd.getBitmap();
		}

3. Bitmap → Drawable

		public static Drawable convertBitmap2Drawable(Bitmap bitmap) {
			BitmapDrawable bd = new BitmapDrawable(bitmap);
			// 因为BtimapDrawable是Drawable的子类，最终直接使用bd对象即可。
			return bd;
		}

---

## 从相册还是相机选择图片

>可以参考我的demo中的ChoosePicActivity

		// 打开系统的画廊
	 	Intent i = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		startActivityForResult(i, RequestCode_01);

		// 在onActivityResult中判断返回码是否成功,请求码是否匹配
		if (resultCode == RESULT_OK)
			if (requestCode == RequestCode_01)

		/**
		 * 返回所选图片的绝对路径
		 * @param context 上下文
		 * @param intent onActivityResult中返回的那个intent(他封装了数据)
		 * @param appPath
		 * @return 
		 */
		public static String resolvePhotoFromIntent(Context context, Intent intent,
                                                String appPath) {
	        if (context == null || intent == null || appPath == null) {
	            MyLogUtil.e("传入的参数不能为null");
	            return null;
	        }
	        Uri uri = Uri.parse(intent.toURI());
	        Cursor cursor = context.getContentResolver().query(uri, null, null,
	                null, null);
	        try {
	
	            String pathFromUri = null;
	            if (cursor != null && cursor.getCount() > 0) {
	                cursor.moveToFirst();
	                int columnIndex = cursor
	                        .getColumnIndex(MediaStore.MediaColumns.DATA);
	                // if it is a picasa image on newer devices with OS 3.0 and up
	                if (uri.toString().startsWith(
	                        "content://com.google.android.gallery3d")) {
	                    // Do this in a background thread, since we are fetching a
	                    // large image from the web
						// pathFromUri = saveBitmapToLocal(appPath, createChattingImageByUri(intent.getData()));
	                } else {
	                    // it is a regular local image file
	                    pathFromUri = cursor.getString(columnIndex);
	                }
	                cursor.close();
	                LogUtil.d("photo from resolver, path: " + pathFromUri);
	                return pathFromUri;
	            } else {
	
	                if (intent.getData() != null) {
	                    pathFromUri = intent.getData().getPath();
	                    if (new File(pathFromUri).exists()) {
	                        LogUtil.d("photo from resolver, path: "
	                                + pathFromUri);
	                        return pathFromUri;
	                    }
	                }
	
	                // some devices (OS versions return an URI of com.android
	                // instead of com.google.android
	                if ((intent.getAction() != null)
	                        && (!(intent.getAction().equals("inline-data")))) {
	                    // use the com.google provider, not the com.android
	                    // provider.
	                    // Uri.parse(intent.getData().toString().replace("com.android.gallery3d","com.google.android.gallery3d"));
	                    pathFromUri = saveBitmapToLocal(appPath, (Bitmap) intent
	                            .getExtras().get("data"));
	                    LogUtil.d("photo from resolver, path: " + pathFromUri);
	                    return pathFromUri;
	                }
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        } finally {
	            if (cursor != null) {
	                cursor.close();
	            }
	        }
	
	        LogUtil.e("resolve photo from intent failed ");
	        return null;
    	}

---
## 正则匹配电话号码
 	
		// 简单判断是不是手机号类型的,返回布尔值
	    private boolean isPhoneNumber(String input) {
			if (input == null)
            return false;
			//"^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$"
	        String regex = "1([\\d]{10})|((\\+[0-9]{2,4})?\\(?[0-9]+\\)?-?)?[0-9]{7,8}";
	        Pattern p = Pattern.compile(regex);
	        Matcher m = p.matcher(input);
	        return m.matches();
	    }

---
## 双击事件/多击事件
> 其原理如下. 最后的if条件句中的 500 就是控制多击事件的时间间隔

			final long[] hits = new long[2];
			view.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					
					System.arraycopy(hits, 1, hits, 0, hits.length -1);
					hits[hits.length -1] = SystemClock.uptimeMillis();
					if(hits[0] >= SystemClock.uptimeMillis() - 500){
						
						view.setVisibility(View.INVISIBLE);
					}
				}
			});


## 对话框不弹出输入法

对话框默认不支持输入框, 就算你的布局文件中有输入框, 点击输入框也不会弹出输入法这里进行一下设置就行了, 因为initView()中加载了布局文件, 所以这行代码的布局不会生效, 但是已经支持输入框了

        	dialog.setView(new EditText(context));

---

### 清单文件中Activity使用Theme属性配置activity切换动画
>在activity的theme属性的style中配置

	// 自定义activityAnimation样式,使用下面的属性引用
	<item name="android:windowAnimationStyle">@style/activityAnimation</item>


	// 配置animation的style
	<!-- animation 样式 -->
    <style name="activityAnimation" parent="@android:style/Animation">
        <item name="android:activityOpenEnterAnimation">@anim/slide_right_in</item>
        <item name="android:activityOpenExitAnimation">@anim/slide_left_out</item>
        <item name="android:activityCloseEnterAnimation">@anim/slide_left_in</item>
        <item name="android:activityCloseExitAnimation">@anim/slide_right_out</item>
    </style>

	// 动画slide_right_in
	<?xml version="1.0" encoding="utf-8"?>  
	<set xmlns:android="http://schemas.android.com/apk/res/android" >  
	  
	    <translate  
	        android:duration="200"  
	        android:fromXDelta="100.0%p"  
	        android:toXDelta="0.0" />  
	  
	</set>  
	
	// 动画slide_left_out
	<?xml version="1.0" encoding="utf-8"?>  
	<set xmlns:android="http://schemas.android.com/apk/res/android" >  
	  
	    <translate  
	        android:duration="200"  
	        android:fromXDelta="0.0"  
	        android:toXDelta="-100.0%p" />  
	  
	</set>  

	// 动画slide_right_out
	<?xml version="1.0" encoding="utf-8"?>
	<set xmlns:android="http://schemas.android.com/apk/res/android" >
	
	    <translate
	        android:duration="200"
	        android:fromXDelta="-100.0%p"
	        android:toXDelta="0.0" />
	
	</set>

	// 动画slide_right_in
	<?xml version="1.0" encoding="utf-8"?>  
	<set xmlns:android="http://schemas.android.com/apk/res/android" >  
	  
	    <translate  
	        android:duration="200"  
	        android:fromXDelta="0.0"  
	        android:toXDelta="100.0%p" />  
	  
	</set>  


---
## ListView滑动的时候会有黑色的背景
		
		ListView的布局文中重写属性cacheColorHint:"#fff"改成白色即可

---

### 使用占位符来设置text (比如页面指示器 1/3)
>在strings.xml中定义	viewpager_indicator,值是自己写的,

		<string name="viewpager_indicator">%1$d / %2$d</string>
		// %1$d : 第一个数	%2$d : 第二个数

		CharSequence text = myContext.getString(R.string.viewpager_indicator, 1, mPager.getAdapter().getCount());

---

## AS中代码配置java和jni路径

在defaultConfig方法下添加

		sourceSets {
            main {
                jniLibs.srcDirs = ['libs']
                java.srcDirs = ['src/main/java']
            }
        } 

---

## AndroidSDK/tools/有个hierarchyviewer

这玩意叫做视图树, 作用是观察当前App的进程的分支结构.


## xUtils的jar包报错(没有HttpClient类)

在android 6.0（API 23）中，Google已经移除了移除了Apache HttpClient相关的类推荐使用HttpUrlConnection，如果要继续使用需要Apache HttpClient，需要在eclipse下libs里添加org.apache.http.legacy.jar，android studio里在相应的module下的build.gradle中加入：

		android {
			useLibrary 'org.apache.http.legacy'
		}

和HttpClient一样，HttpResponse类也被移除了，解决方法如上所述，jar文件位置在sdk目录下\platforms\android-23\optional文件夹中

## xUtils3 简介


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


xUtils -- 让开发更简单

---

## ImageLoader的Gradle

		compile 'com.nostra13.universalimageloader:universal-image-loader:1.9.5'

---

## 6.0动态权限类库

		compile 'pub.devrel:easypermissions:0.2.0'

---

## 构建者Builder设计模式
>Builder模式的定义是用于构建复杂对象的一种模式,所构建的对象往往需要多步初始化或赋值才能完成

比如类: ClientUser, 该类在不同情况下使用的时候需要的参数个数不定,采用构建者模式让调用者自由添加

		public class ClientUser {
		    private String userName;
		    private String passWord;
		    private Integer height;
		    private Integer age;
		
		    private ClientUser(String userName, String passWord) {
		    }
		
		    public String getUserName() {
		        return userName;
		    }
		
		    public void setUserName(String userName) {
		        this.userName = userName;
		    }
		
		    public String getPassWord() {
		        return passWord;
		    }
		
		    public void setPassWord(String passWord) {
		        this.passWord = passWord;
		    }
		
		    public Integer getHeight() {
		        return height;
		    }
		
		    public void setHeight(Integer height) {
		        this.height = height;
		    }
		
		    public Integer getAge() {
		        return age;
		    }
		
		    public void setAge(Integer age) {
		        this.age = age;
		    }
		
		    public static class Builder {
		        private static ClientUser user;
		
		        public Builder(String userName, String passWord) {
		            createClientUser(userName, passWord);
		        }
		
		        private void createClientUser(String userName, String passWord) {
		            user = new ClientUser(userName, passWord);
		        }
		
		        public ClientUser build() {
		            return user;
		        }
		
		        public Builder setHeight(Integer height) {
		            user.setHeight(height);
		            return this;
		        }
		
		        public Builder setAge(Integer age) {
		            user.setAge(age);
		            return this;
		        }
		    }
		}



---

## 自定义控件在代码中获取自定义属性

1. 传统方法: 构造方法中的参数attrs已经封装了所有的属性,调用方法即可

		attrs.getAttributeBooleanValue(yourNameSpace, yourAttrsName, defaultValue);
当然也可以直接获取值, 不过拿到的都是String类型的

		attrs.getAttributeValue(nameSpace, attrsName);

2. 逼格方法: 申明的自定义属性会在R文件生成对应的值, API提供了如下方法获取
	
		TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.自定义属性的申明name);
		Boolean MyAttr = array.getBoolean(R.styleable.申明name_属性名, defaultValue);
		array.recycle(); // 回收下释放资源, 提高性能


---

## 一般数据库路径

	String PACKAGE_NAME = "com.bbdtek.guanxinbing.patient";
    String db_dir = "/data" + Environment.getDataDirectory().getAbsolutePath() + "/" + PACKAGE_NAME;
    String db_name = "patient.db";

---


## ScrollView嵌套ListView或者GridView冲突问题

解决办法：重写 ListVew或者 GridView，网上还有很多若干解决办法，但是都不好用或者很复杂。

		@Override
		
		/**   只重写该方法，达到使ListView适应ScrollView的效果   */ 
		
		protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		
			int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,MeasureSpec.AT_MOST);
		
			super.onMeasure(widthMeasureSpec, expandSpec);
		
		}

---

## NestedScrollView嵌套RecyclerView冲突问题

		// 升级support库到23.2以上

		mLinearLayoutManager.setSmoothScrollbarEnabled(true);  
        mLinearLayoutManager.setAutoMeasureEnabled(true);  
        recycler_cardslist.setLayoutManager(mLinearLayoutManager);  
        recycler_cardslist.setHasFixedSize(true);  
        recycler_cardslist.setNestedScrollingEnabled(false);  

---
## 触摸事件分发机制
dispatchTouchEvent -> onInterceptTouchEvent -> onTouchEvent 

当一个控件不希望任何父控件来拦截它的点击事件的时候重写dispatchTouchEvent()方法, 调用

		requestDisallowInterceptTouchEvent(true);

当一个控件需要拦截掉触摸事件不让子View消费重写onInterceptTouchEvent返回true
	
---
### 获取当前手机系统版本号

		int sdk = android.os.Build.VERSION.SDK_INT;

#Activity的四种启动模式
>每个应用会有一个Activity任务栈，存放已启动的Activity

>Activity的启动模式，修改任务栈的排列情况

* standard 标准启动模式
* singleTop 单一顶部模式 
	  * 如果任务栈的栈顶存在这个要开启的activity，不会重新的创建activity，而是复用已经存在的activity。保证栈顶如果存在，不会重复创建。
	* 应用场景：浏览器的书签
* singeTask 单一任务栈，在当前任务栈里面只能有一个实例存在
	* 当开启activity的时候，就去检查在任务栈里面是否有实例已经存在，如果有实例存在就复用这个已经存在的activity，并且把这个activity上面的所有的别的activity都清空，复用这个已经存在的activity。保证整个任务栈里面只有一个实例存在
	* 应用场景：浏览器的activity
	* 如果一个activity的创建需要占用大量的系统资源（cpu，内存）一般配置这个activity为singleTask的启动模式。webkit内核 c代码

* singleInstance启动模式非常特殊， activity会运行在自己的任务栈里面，并且这个任务栈里面只有一个实例存在
	* 如果你要保证一个activity在整个手机操作系统里面只有一个实例存在，使用singleInstance
	* 应用场景： 电话拨打界面

---

## Error:The number of method references in a .dex file cannot exceed 64K.

1. 在app的build.gradle中, 在 defaultConfig 中添加

    multiDexEnabled true

2. 在你的Application类中添加(如果你的minSdkVersion >= 21, 那么这一步忽略)

    (1) dependencies 中添加依赖 compile 'com.android.support:multidex:1.0.1'

    (2) 替换Manifest中的application(分为下面是那种情况)

        <1> 你没有自己的Application类, 直接替换成
            android:name="android.support.multidex.MultiDexApplication"

        <2> 你有自己的MyApplication类, 继承自MultiDexApplication即可

        <3> 你的MyApplication已经有基类, 没事如下操作即可

             @Override
             protected void attachBaseContext(Context base) {
                 super.attachBaseContext(base);
                 MultiDex.install(this) ;
             }

---

## 图片的三级缓存简介

1. 网络缓存. 其实就是去网络上下载会拿到返回的流, 使用BitmapFactory.decodeStream(is)方法获取BitMap对象, (这里其实还得注意一个图片的缩放问题,bitmap对象占用内存太大)同时写入本地缓存和内存缓存
2. 把网络获取的BitMap对象写入本地, 就是指定一个目录然后使用bitmap.compress()方法写图片写成文件(一般以url的md5值作为文件名). 提供get/set方法
3. 把网络获取的BitMapdioxide写入内存, 就是使用一个映射HashMap来保存BtiMap对象, 由于BitMap太大所以用到软引用来封装BitMap, 但是不可靠所以重新使用LruCache来替换BitMap.

总结:给ImageView设置图片的时候, 先从内存中get如果没就从本地get如果没就去网络下载, 在网络下载完成的时候同时写入本地和内存缓存


## 三级缓存中V4下的LruCache实现内存缓存, 之前使用的软引用SoftRefrence<BitMap>包装BitMap在android2.3以后作用不大了.
>Lru全称:Least Recently used  最近最少使用(一种算法), 将最近最少使用的对象回收掉

			// android分配给每个app的最大内存大小, 一般给LruCache设置1/8最大内存大小
			long maxMemory = Runtime.getRuntime().maxMemory();

			// 初始化LruCache对象, 需要指定最大值
			LruCache<String, Bitmap> mMemoryCache = new LruCache<String, Bitmap>((int) (maxMemory / 8)) {
				// 返回每个对象的大小
				@Override
				protected int sizeOf(String key, Bitmap value) {
					// int byteCount = value.getByteCount();
					int byteCount = value.getRowBytes() * value.getHeight();// 计算图片大小:每行字节数*高度
					return byteCount;
				}
			};

然后就把LruCache当HashMap来用就行了.


---
# 新见到的组件、API及其用法 :

---
## ViewPropertyAnimator

1. 一般为了做兼容, 都用的第三方支持版本9及其以下的jar包中的ViewPropertyAnimator

2. 调用静态方法直接传入要执行动画的view. 直接设置动画即可. tran
3. slationY()参数表示以自身为原点, 正数表示向下移动, 负数表示向上移动. X同理 + 右  - 左
		
		ViewPropertyAnimator.animate(ll_menu_edit).translationY(ll_menu_edit.getWidth()).setDuration(200);

---


## 登录注册页新控件TextInputLayout

>android.support.design.widget.TextInputLayout, 里边只能放一个EditText

	<android.support.design.widget.TextInputLayout
        android:id="@+id/userName"
        android:layout_width="match_parent"
        android:layout_height="wrap_content">

        <EditText
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:hint="用户名"
            android:inputType="textEmailAddress"
  	 />

    </android.support.design.widget.TextInputLayout>

    <android.support.design.widget.TextInputLayout
        android:id="@+id/email"
        android:layout_width="match_parent"
        android:layout_height="wrap_content">

        <EditText
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:hint="邮箱"
            android:inputType="textEmailAddress" />

    </android.support.design.widget.TextInputLayout>


* 使用:在activiy中find控件tl_name.getEditText().getText().toString();
* 如果检查用户名或者密码失败可以调用tl_name.setError("用户名不正确！");
* 此外,hintColor和errorColor也可以在activity的style(theme)中设置.
		
		<item name="android:textColorHint">#00ffff</item>
        <item name="textColorError">#ff0000</item>

注意: 在点击登录按钮的操作中要自己收起输入法

	private void hideKeyboard() {
	    View view = getCurrentFocus();
	    if (view != null) {
	        ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).
	            hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
	    }
	}

---


## SwipeRefreshLayout简单使用

         1、setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener listener):设置手势滑动监听器。

         2、setProgressBackgroundColor(int colorRes):设置进度圈的背景色。

         3、setColorSchemeResources(int… colorResIds):设置进度动画的颜色。

         4、setRefreshing(Boolean refreshing):设置组件的刷洗状态。

         5、setSize(int size):设置进度圈的大小，只有两个值：DEFAULT、LARGE


---

## CardView相关属性

		app:cardBackgroundColor				这是设置背景颜色 

		app:cardCornerRadius				这是设置圆角大小
 
		app:cardElevation					这是设置z轴的阴影
 
		app:cardMaxElevation				这是设置z轴的最大高度值 

		app:cardUseCompatPadding			是否使用CompatPadding 

		app:cardPreventCornerOverlap		是否使用PreventCornerOverlap 

		app:contentPadding 					设置内容的padding 

		app:contentPaddingLeft 				设置内容的左padding 

		app:contentPaddingTop 				设置内容的上padding 

		app:contentPaddingRight 			设置内容的右padding 

		app:contentPaddingBottom 			设置内容的底padding

---
## 一款好用的下拉刷新组件

>在项目的Gradle中添加依赖	compile 'in.srain.cube:ultra-ptr:1.0.11'

有6个参数可配置:

1. 阻尼系数	默认: 1.7f，越大，感觉下拉时越吃力。

2. 触发刷新时移动的位置比例	 默认，1.2f，移动达到头部高度1.2倍时可触发刷新操作。
 
3. 回弹延时	 默认 200ms，回弹到刷新高度所用时间
   
4. 头部回弹时间	 默认1000ms
   
5. 刷新时保持头部	 默认值 true
   
6. 下拉刷新 / 释放刷新	 默认为false释放刷新
   
>使用

1. 使用的时候包裹ListView即可

		<in.srain.cube.views.ptr.PtrClassicFrameLayout 
			xmlns:cube_ptr="http://schemas.android.com/apk/res-auto"
            android:id="@+id/fl_ptr_zaoying_list"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
           
			cube_ptr:ptr_resistance="1.7"
		    cube_ptr:ptr_ratio_of_header_height_to_refresh="1.2"
		    cube_ptr:ptr_duration_to_close="300"
		    cube_ptr:ptr_duration_to_close_header="1000"
		    cube_ptr:ptr_keep_header_when_refresh="true"
		    cube_ptr:ptr_pull_to_fresh="false" >

            <ListView
                android:id="@+id/lv_zaoying_list"
                android:layout_width="match_parent"
                android:layout_height="match_parent"
                android:layout_marginLeft="20dp"
                android:layout_marginRight="20dp"
                android:divider="@color/gray_bg_color"
                android:dividerHeight="1dp"/>

        </in.srain.cube.views.ptr.PtrClassicFrameLayout>

2. 代码中使用只要初始化控件, 然后设置下就行了

		@ViewInject(R.id.fl_ptr_zaoying_list)
    	private PtrClassicFrameLayout ptr_zaoyingList;// 下拉刷新容器
		/**
	     * 初始化下拉刷新控件的方法
	     */
	    private void initPtrFrameLayout() {
	        // 设置支持自动刷新
	        ptr_zaoyingList.postDelayed(new Runnable() {
	            @Override
	            public void run() {
	                ptr_zaoyingList.autoRefresh(true);
	            }
	        }, 100);
	
	        ptr_zaoyingList.setPtrHandler(new PtrHandler() {
	            @Override
	            public void onRefreshBegin(PtrFrameLayout frame) {
	                // 刷新时需要处理的逻辑(自己的需求)
	                refreshYouLogic();
	            }
	
	            @Override
	            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
	                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
	            }
	        });
	    }

>更换头部控件 

1. 在布局中使用
	
		<in.srain.cube.views.ptr.PtrFrameLayout 
			xmlns:cube_ptr="http://schemas.android.com/apk/res-auto"
	        android:id="@+id/ptr_frame"
	        android:layout_width="match_parent"
	        android:layout_height="match_parent"
	        cube_ptr:ptr_duration_to_close="300"
	        cube_ptr:ptr_duration_to_close_header="1000"
	        cube_ptr:ptr_keep_header_when_refresh="true"
	        cube_ptr:ptr_pull_to_fresh="false"
	        cube_ptr:ptr_ratio_of_header_height_to_refresh="1.2"
	        cube_ptr:ptr_resistance="1.7">
	
	        <ListView
	            android:id="@+id/lv_ptrdemo"
	            android:layout_width="match_parent"
	            android:layout_height="wrap_content"
	            android:layout_marginLeft="20dp"
	            android:divider="@android:color/transparent"
	            android:layout_marginRight="20dp"
	            android:dividerHeight="10dp" />
   		</in.srain.cube.views.ptr.PtrFrameLayout>

2. 在代码中初始化
		
		mPtrFrameLayout = (PtrFrameLayout) findViewById(R.id.ptr_frame);
		// header, 使用MD风格的
        final MaterialHeader header = new MaterialHeader(this);
        int[] colors = {Color.RED, Color.BLUE, Color.YELLOW, Color.GREEN};
        header.setColorSchemeColors(colors);
        header.setLayoutParams(new PtrFrameLayout.LayoutParams(-1, -2));
        header.setPadding(0, DPPXUtil.dip2qx(this, 15), 0, DPPXUtil.dip2qx(this, 10));
        header.setPtrFrameLayout(mPtrFrameLayout);

        mPtrFrameLayout.setLoadingMinTime(1000);
        // mPtrFrameLayout.setDurationToCloseHeader(1500);
        mPtrFrameLayout.setHeaderView(header);
        mPtrFrameLayout.addPtrUIHandler(header);
        mPtrFrameLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                mPtrFrameLayout.autoRefresh(false);
            }
        }, 0);

        mPtrFrameLayout.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }

            @Override
            public void onRefreshBegin(final PtrFrameLayout frame) {
                frame.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dataList.add("刷新数据" + dataList.size());
                        frame.refreshComplete();
                        adapter.notifyDataSetChanged();
                    }
                }, 1500);
            }
        });



---
### TabLayout的两个需要注意的属性

		Android Material Design 中的TabLayout有两个比较有用的属性 app:tabMode、app:tabGravity，
		（1）app:tabMode有两个值：fixed和scrollable。
		（2）app:tabGravity有两个值：fill和center。
		比较常用的是app:tabMode设置值scrollable，以及app:tabGravity设置值center。
		比如，当app:tabMode设置值scrollable表示此TabLayout中当子view超出屏幕边界时候，将提供滑动以便滑出不可见的那些子view。
		而app:tabGravity设置值center，在有些情况下，比如TabLayout中子view较少需要居中显示时候的情景。
		

---

## android自带的底部上拉容器.

>在Api23以后,supportLibrary中添加了一款位于Activity底部的上拉容器----BottomSheet.其实之前的CoordinatorLayout的艾玛沃森下面的就是引用了BottomSheet.

效果展示: 

<div>
	<img src='/others/images/BottomSheet1.png'  width="300px" style='border: #67F2F7 solid 2px'/>
</div>

<div>
	<img src='/others/images/BottomSheet2.png'  height="500px" style='border: #67F2F7 solid 2px'/>
</div>


* Step1: 前提,BottomSheet的父布局必须是CoordinatorLayout.

* Step2: 定义好你要的底部布局,在父布局中使用 'include' 节点引用.当然你也可以直接定义在父布局中.随意. 重点是底部布局的根节点需要注意下面三个属性.如果前两个属性不定义,默认是全部显示并且不可被隐藏

		// 是否可以被隐藏
		app:behavior_hideable="true"
		// 一开始展露出来的高度
	    app:behavior_peekHeight="60dp"
		// 行为标识.CoordinatorLayout通过该属性识别BottomSheet.
	    app:layout_behavior="android.support.design.widget.BottomSheetBehavior"

* Step3: 在Activity中获取对象.加载底部布局的View.比如你是LinearLayout就通过指定的id找到他,或者你是另写的底部布局文件也可以加载这个Layout.不管是1/2你都会活得一个LinearLayout/View对象.调用BottomSheetBehavior.from(View view)方法获取BottomSheetBehavior对象,这种类型的可以加个泛型(随意)

* 主要方法:

		behavior.setBottomSheetCallback(); 

	在这个CallBack中.onStateChanged()方法和onSlide()方法将会别回调,你可以拿到底部当前状态等数据.在onStateChanged方法中你将拿到当前更新后的newState和bottomSheet对象.可以根据当前状态做操作.
		
		behavior.setState();// 设置当前状态,BottomSheet会自动滑到相应状态下的位置
		behavior.setHideable();// 设置是否可被隐藏
		behavior.setPeekHeight();// 设置初始展示的高度


* 状态说明

	* STATE_HIDDEN: 默认禁用是的(使用app:behavior_hideable属性来启用 ), 如果这个启用，用户可以在 bottom sheet中下滑以完全隐藏bottom sheet。
	
	* STATE_COLLAPSED: 这是折叠状态 ，也是默认的状态。只是在底部边沿显示布局的一部分。其高度可以使用 app:behavior_peekHeight 属性来控制（默认是0）。　
	
	* STATE_DRAGGING: 这是中间状态，此时用户直接上下拖动 bottom sheet。
	
	* STATE_SETTLING: 视图被释放之后到达最终位置之间的瞬间。
	
	* STATE_EXPANDED: bottom sheet完全展开的状态。 整个bottom sheet都是可见的（如果它的高度小于包含它的CoordinatorLayout）或者整个CoordinatorLayout都是填满的。


---

## BottomNavigationView

>在CoordinatorLayout中解释并展示了NavigationView的使用.其实BottomNavigationView就是底部的导航标签.SupportLibrary 25增加(MD设计老早就有).

1. 定义Navigation的菜单 menu .(一般3~5个导航条目)

2. 布局文件中定义BottomNavigationView,默认的icon选中和Text选中都是colorAccent的值.另外默认高度是56dp.

	<android.support.design.widget.BottomNavigationView
        android:id="@+id/navigation_bottom"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_alignParentBottom="true"
        app:itemBackground="@android:color/black"
        app:itemIconTint="@android:color/white"
        app:itemTextColor="@android:color/white"
        app:menu="@menu/navigation_bottom"/>

3. 获取对象, mNavigationView.setOnNavigationItemSelectedListener().回调中会拿到MenuItem对象.其封装了菜单中的item的所有相关信息.

---

## CoordinatorLayout + AppBarLayout / CollapsingToolbarLayout  

###分析一下整个布局中CoordinatorLayout的作用:

1. 因为CoordinatorLayout是一个超级的FrameLayout,所以我们只要设置android:layout_gravity="bottom|end"这个属性即可将FloatingActionBar放置在底部靠右的位置;

2. 如果我们想要在手指向上滑动的时候Toolbar隐藏,就需要给Toolbar设置一个属性,app:layout_scrollFlags="scroll|enterAlways",来确定滚动出屏幕时候的动作,我们现在来解释一些这些参数:

	* scroll: 所有想滚动出屏幕的view**都需要**设置这个flag， 没有设置这个flag的view将被固定在屏幕顶部。例如，TabLayout 没有设置这个值，将会停留在屏幕顶部。

	* enterAlways: 配合scroll使用,设置这个flag时，任意向下的滚动都会导致该view变为可见，当向上滑的时候Toolbar就隐藏，下滑的时候显示，启用快速“返回模式”。

	* enterAlwaysCollapsed: 这个flag定义的是何时进入（已经消失之后何时再次显示）,配合scroll使用,当你的视图已经设置minHeight属性又使用此标志时，那么view将在到达这个最小高度的时候开始显示，并且从这个时候开始慢慢展开，当滚动到顶部的时候完全展开。

	* exitUntilCollapsed: 这个flag是定义何时退出，当你定义了一个minHeight，这个view将在滚动到达这个最小高度的时候消失。

	* 注意:这些flag的模式一般是前两个一起使用或者 scroll与enterAlwaysCollapsed 一起使用,而最后一个flag只有在CollapsingToolbarLayout中才有用,所以这些flag的使用场景,一般都是固定的;

3. 当然,为了使Toolbar可以滚动,还需要一个条件,就是CoordinatorLayout布局下需要包裹一个可以滑动的布局，比如 RecyclerView或者NestedScrollView(ListView及ScrollView不支持),CoordinatorLayout包含的子布局中带有滚动属性的View需要设置app:layout_behavior属性。示例中Viewpager设置了此属性:app:layout_behavior="@string/appbar_scrolling_view_behavior",这样一来AppBarLayout就能响应RecyclerView中的滚动事件,CoordinatorLayout在接受到滑动时会通知AppBarLayout 中可滑动的Toolbar可以滑出屏幕了;

总结:如果想让Toolbar划出屏幕,需要做到以下4点

1. CoordinatorLayout作为顶层的父布局

2. 需要给Toolbar也就是想要划出屏幕的view设置flag值,app:layout_scrollFlags=”scroll|enterAlways”

3. 需要给可滑动的组件设置一个layout_behavior的属性,示例中为viewpager,app:layout_behavior="@string/appbar_scrolling_view_behavior"

4. 可滑动的组件目前经测试支持RecyclerView,NestedScrollView,示例中viewpager中包含的就是一个RecyclerView


###CollapsingToolbarLayout–可折叠的Toolbar

我们知道如果在某些详情页面,我们只是在AppbarLayout中使用了可隐藏/展示的Toolbar的话, 只能固定到屏幕顶端并且不能做出好的动画效果,而且无法控制不同元素如何响应collapsing(折叠)的细节，所以为了达到此目的，CollapsingToolbarLayout就应运而生.

CollapsingToolbarLayout一般都是需要包括一个Toolbar,这样就可以实现一个可折叠的Toolbar,一般都是作为AppbarLayout的子view使用,CollapsingToolbarLayout的子视图类似于LinearLayout垂直方向排放。

CollapsingToobarLayout的属性及用法:

1. Collapsing title：ToolBar的标题，CollapsingToolbarLayout和Toolbar在一起使用的时候，title会在展开的时候自动变得比较大，而在折叠的时候让字体变小过渡到默认值。注意，你必须在CollapsingToolbarLayout上调用setTitle()，而不是在Toolbar上进行设置。

2. Content scrim：ToolBar被折叠到顶部固定时候的背景，你可以调用setContentScrim(Drawable)方法改变背景或者 在属性中使用app:contentScrim=”?attr/colorPrimary”来改变背景。

3. Status bar scrim：状态栏的背景，调用方法setStatusBarScrim(Drawable)。

4. Parallax scrolling children：CollapsingToolbarLayout滑动时，子视图的视觉因子，可以通过属性app:layout_collapseParallaxMultiplier=”0.7”来改变。值的范围[0.0,1.0]，值越大视差越大。

5. CollapseMode ：子视图的折叠模式，需要在子视图设置; 
“pin”：固定模式，在折叠的时候最后固定在顶端；
“parallax”：视差模式，在折叠的时候会有个视差折叠的效果。我们可以在布局中使用属性app:layout_collapseMode=”parallax”来改变。

6. layout_anchor : 这个是CoordinatorLayout提供的属性,与layout_anchorGravity 一起使用,可以用来放置与其他视图关联在一起的悬浮视图（如 FloatingActionButton）或者头像

---

## 新布局Constraintlayout

---
## AutoCompleteTextView

>带有下拉框的一种EditText, 多用于查询搜索来回显的EditText

* 这玩意也需要一个Adapter,一般如果去查询数据库的话就用CursorAdapter, 此时设置完adapter了还要调用下面这个方法, 因为开始的话cursor一般都没有数据:

		 AutoCompleteTVAdapter mAdapter = new AutoCompleteTVAdapter(context, null);

         mAutoCompleteTV.setAdapter(mAdapter);

		 mAdapter.setFilterQueryProvider(new FilterQueryProvider() {

			// 该方法就是执行查询的.逻辑由调用者实现,constraint就是输入框的内容
			// 不过该方法是输入框至少有个2字节才调用.想1个就调用就去布局文件中该组件属性添加
					// android:completionThreshold="1"
			@Override
			public Cursor runQuery(CharSequence constraint) {
				String[] projection = {"你需要查询的字段"};
                String selection = "你需要查询的条件" 
                Cursor cursor = getContentResolver().query("你需要查询的Uri", projection, selection, null, "你设置的排序方式");

                // 当查询到数据之后,cursor携带好数据返回,上面原本给null的构造就完整了.adapter就会准备显示内容了
                return cursor;
			}

		});


* 还提供了设置下拉列表的背景和竖直偏移(与EditText的距离) 写不写看需求了

	        mAutoCompleteTV.setDropDownBackgroundResource(R.drawable.bg_btn_normal);
	        mAutoCompleteTV.setDropDownVerticalOffset(5);

---
## ViewPager

> ViewPager可以平滑地切换界面, 使用Adapter设置布局条目. 默认如果上一个或者下一个界面有数据的话, 会在内存中加载上一个和下一个, 也就是说总共3个页面. 要是想多加载就设置setOffScreenPagerLimit(2). 这样左右就各存2个, 总共5个pager

### 1. 使用(继承自FragmentPagerAdapter): 

1. 一般为了做兼容都使用v4包下ViewPager, 布局文件中要写上全类名

2. 和ListView一样也要设置Adapter,专门有个PagerAdapter(用于显示Fragment的时候使用其子类FragmentPagerAdapter), 一般其内容都是由不同的Fragment来填充并切换, 所以一般Adapter的构造需要传入V4包下的FragmentManager和一个封装了Fragment的list集合

		public class MyPagerAdapter extends FragmentPagerAdapter {

		    private List<Fragment> list;
		
		    public MyPagerAdapter(FragmentManager manager, List<Fragment> list) {
		        super(manager);
		        this.list = list;
		    }
		
		
		    @Override
		    public Fragment getItem(int position) {
		        return list.get(position);
		    }
		
		    @Override
		    public int getCount() {
		        return list.size();
		    }
		}


3. 侦听的话一般用道的就是addOnPageChangeListener(), 然后另外有setCurrentItem()方法可以直接在点击事件中切换到目标ViewPager的界面

		pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            // ViewPager滑动过程中不断调用该方法.参数2:偏移百分比	参数3:偏移像素
            // 特别注意:   滑动过程中出现2个界面,position的值是前一个界面的索引(和下面的position不同)
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
			
			}

            // 切换完成后调用,position是切换之后的界面索引
            @Override
            public void onPageSelected(int position) {

            }
			
			// 滑动状态改变的时候该方法调用
            @Override
            public void onPageScrollStateChanged(int state) {

			}
        });



### 2. 直接使用PagerAdapter
>本例子中list集合中放的是几个imageview

		public class MyPagerAdapter extends PagerAdapter {
		
		    private List<View> list;
		
		    public MyPagerAdapter(List<View> list) {
		        this.list = list;
		    }
		
		    @Override
		    public int getCount() {
		        return list.size();
		    }
		
		    @Override
		    public boolean isViewFromObject(View view, Object object) {
		        // 当滑到新的条目又返回来的时候view是否可以被复用
		        return view == object;
		    }
		
		    // 返回的对象就是要显示的条目的内容
		    @Override
		    public Object instantiateItem(ViewGroup container, int position) {
		
		        // 必须要手动将显示的条目添加到容器中,然后将条目返回
		        ImageView view = (ImageView) list.get(position);
		        container.addView(view);
		
		        return view;
		    }
		
		    // 销毁条目
		    @Override
		    public void destroyItem(ViewGroup container, int position, Object object) {
		        // 这儿的object就是上面返回的条目(其实是个ImageView)
		        container.removeView((View)object);
		    }
		}


* ViewPager设置跳转页面除去滑动动画
		
			pager.setCurrentItem(1, false)

* 解决ViewPager默认加载3个页面数据
	
	可以给ViewPager设置addOnPageChangeListener(),在onPageSelected()方法中加载数据,由于第一个页面默认不会调用该方法,需要手动设置进行加载数据的功能

---

# 可扩展的listview---ExpandableListView

>该组件可实现分组显示等效果, 父条目和自条目均可以自定义显示

* 所用适配器BaseExpandableListAdapter
* 可以自行设置子条目的是否可点击, 其长按侦听无法区分点的是父/子, 可以在适配器中返回view对象的时候给父子分别设置setTag(int key, Object tag), 然后在长按侦听回调中判断使用

	// 展开指定的组
	expandableListView.expandGroup(groupPosition);	
	// 关闭指定的组
	expandableListView.collaspGroup(groupPosition);



---

## android中的是三种动画: Frame Animation--帧动画 、Tween Animation--补间动画 Property Animation--属性动画

见android中的三种动画.md


---

## 控件RadioGroup

		<RadioGroup
		    android:id="@+id/rg_main_group"
		    android:layout_width="match_parent"
		    android:layout_height="wrap_content"
		    android:orientation="horizontal" >
	
			<RadioButton
	            android:id="@+id/btn_rg_search"
	            android:layout_width="0dp"
	            android:layout_height="wrap_content"
	            android:layout_weight="1"
	            android:button="@null"
	            android:drawablePadding="@dimen/little_space"
	            android:drawableTop="@drawable/selector_main_index_search"
	            android:gravity="center"
	            android:scaleX="0.8"
	            android:scaleY="0.8"
	            android:text="首页"
	            android:textColor="@drawable/selector_main_index_textcolor" />
	
	        <RadioButton
	            android:id="@+id/btn_rg_mine"
	            android:layout_width="0dp"
	            android:layout_height="wrap_content"
	            android:layout_weight="1"
	            android:button="@null"
	            android:drawablePadding="@dimen/little_space"
	            android:drawableTop="@drawable/selector_main_index_mime"
	            android:gravity="center"
	            android:scaleX="0.8"
	            android:scaleY="0.8"
	            android:text="我的"
	            android:textColor="@drawable/selector_main_index_textcolor" />

  	 	</RadioGroup>

---

## 开源框架ViewPagerIndicator, 根据example的界面查找布局文件中的和class的用法

1. 首先还是导入开源框架 以智慧北京的TabIndicator为例子, 首先在viewpager布局文件上使用人家的自定义控件

		<com.viewpagerindicator.TabPageIndicator
	        android:id="@+id/viewpager_indicator"
	        android:layout_width="match_parent"
	        android:layout_height="match_parent"/>


2. 初始化的时候初始化该控件(这里用的是TabPageIndicator), 在viewpager设置适配器的时候给指示器设置目标viewpager

		TabPageIndicator indicator = new TabPageIndicator(context);

3. 由于用的是TabPageIndicator, 所以需要在viewpager的适配器中重写getPageTitle()方法, 返回对应的标题应该显示的文字

4. 在清单文件中找到使用了该viewpager和指示器的Activity配置以下样式

		android:theme="@style/Theme.PageIndicatorDefaults"

5. 一般而言运行后界面会变黑色背景, 自己给Activity的布局文件改成白色(你需要的颜色)即可, 更重要的是你会发现显示的字体和颜色以及下边条条的样式不是你想要的, 找到刚刚Activity中定义的样式, 戳进去找到样式, 继续戳, 然后自己设置background/textsize/textcolor...之类的, 不过有一点需要注意, 你在库文件中是拿不到本app的资源文件的, 只能复制过去


---

## 消息推送相关及原理
>极光推送

1. 长连接: 最基础最底层的保障(socket:TCP/UDP)


---

## WebView及其用法

1. 在布局文件中使用WebView组件, 设置好id

2. 在所在Activity中拿到该组件对象, 使用起来很简单. 

		WebView webview = (WebView) findViewById(R.id.web_news_detail);
		
3. 主要是一些设置的使用

	 		webview.loadUrl("你要加载的网页的网址");
	        // 拿到网页设置对象,里面封装者各种对WebView的设置
	        WebSettings settings = webview.getSettings();
	        // 有的网页就是mei这些功能的,但是你得都给打开
	        settings.setBuiltInZoomControls(true);  // 显示缩放按钮
	        settings.setUseWideViewPort(true);  // 实现双击缩放
	        settings.setJavaScriptEnabled(true);    // 支持js功能
	
	        // 设置跳转的网页还是在此组件中加载, 不要使用别的浏览器
	        webview.setWebViewClient(new WebViewClient() {
	            // 开始加载网页
	            @Override
	            public void onPageStarted(WebView view, String url, Bitmap favicon) {
	                super.onPageStarted(view, url, favicon);
	                pb_loading.setVisibility(View.VISIBLE);
	            }
	
	            // 网页加载结束
	            @Override
	            public void onPageFinished(WebView view, String url) {
	                super.onPageFinished(view, url);
	                pb_loading.setVisibility(View.INVISIBLE);
	            }
	
	            // 所有链接跳转都会走这儿
	            @Override
	            public boolean shouldOverrideUrlLoading(WebView view, String url) {
	                view.loadUrl(url);
				//  return super.shouldOverrideUrlLoading(view, url);
	                return true;
	            }
	        });
	
	        // webview还有2个方法, 返回上一个网页和下一个网页
			// webview.goBack();       webview.goForward();
			
				// 限制访问网页在自己的webView中而不是去打开手机中的浏览器
				mWebView.setWebViewClient(new WebViewClient(){
			
			      @Override
			      public boolean shouldOverrideUrlLoading(WebView view, String url) {
			        // TODO Auto-generated method stub
			        view.loadUrl(url);
			        return true;
			      }
			    });

	
	        webview.setWebChromeClient(new WebChromeClient() {

	            // 该方法可以不断拿到当前加载进度
	            @Override
	            public void onProgressChanged(WebView view, int newProgress) {
	                super.onProgressChanged(view, newProgress);
	                System.out.println("当前进度: " + newProgress);
	                pb_loading.setProgress(newProgress);
	            }
	
	            // 该方法在接收到网页标题时调用
	            @Override
	            public void onReceivedTitle(WebView view, String title) {
	                super.onReceivedTitle(view, title);
	                System.out.println("当前网页标题: " + title);
	            }
	        });

4. 界面销毁的时候

			vip_web.removeAllViews();
            vip_web.destroy();

---





# 配置 / Error :
---
1. Eclipse环境下查看V4包等第三方包的源码	
	
	如:在libs文件夹下有个android-support-v4.jar, 创建android-support-v4.jar.properties 文件, 里面写上该包的源码路径重启Eclipse即可

		src = D:\\AndroidDevelopment\\AndroidStudioSDK\\extras\\android\\support\\v4\\src

	也可以将V4jar包BuildPath到 reference 

2. Couldn't load vi_voslib from loader dalvik.system.PathClassLoader

	在使用jni的时候,报这么个错 需要把armeabi下的.so文件新建个armeabi-v7a复制进去

