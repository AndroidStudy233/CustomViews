#JNI的使用,详细步骤
	
>基础的JDK,eclipse,SDK,NDK目录这些俱全

1. 创建一个android项目,右键项目找到Android Tools,选择Add Native Support...,写个名字,最好简单点,hello这是待会要用的类库名,项目会自动跳转到C/C++界面,可以跳回JAVA,会看到多了个jni文件夹

2. 一般用C代码进行JNI,所以将jni目录下的hello.cpp改名成hello.c,同时去Android.mk文件中将字段hello.cpp改成hello.c

3. 在jni目录下新建Application.mk文件里面就写一行

		APP_ABI := armeabi x86 
这是添加模拟器的armeabi和x86支持,要是都支持就写all

4. 在MainActivity中添加本地方法

		public native String helloFromC();
并且在上面添加静态代码块,类库名就是第一步你写的名字

		static {
			System.loadLibrary("hello");
		}

5. 本例子中是在MainActivity中用点击按钮的形式调用C,得到一个返回值String作为Toast的参数

		public void click(View v){
    		Toast.makeText(this, helloFromC(), 	0).show();
    	}

6. 复制MainActivity的全类名,进入该项目路径的src文件夹下,(还有什么bin啊,res啊,gen啊什么的,记住进入src就行了),在此处打开命令行,输入: javah MainActivity全类名,编译出.h文件.回到项目刷新下,src下就会出现个.h文件打开文件,复制代码(此时这个.h文件已经没用了,报错的话可以注释,可以删除)

		JNIEXPORT jstring JNICALL Java_com_shiqkuangsan_codestring_MainActivity_helloFromC(JNIEnv *, jobject)

7. 回到jni目录下的hello.c, 这就是方法名,本例中要返回一个HelloWorld,于是完整的方法就应该是(头文件已生成),

		JNIEXPORT jstring JNICALL Java_com_shiqkuangsan_codestring_MainActivity_helloFromC
  		(JNIEnv * env, jobject thiz){
		}
此时,是报错的,因为项目不知道jni.h文件位置,右键项目,properties,C/C++General,Paths and Symbols. 点击Add,File System, 然后选中你NDK目录下的platforms/android-18/arch-arm/usr/include 文件夹,确认(确认框有个Box选项别选)

8. 项目已经不报错了,完成C中的代码,可以运行项目了.

		#include <jni.h>

		JNIEXPORT jstring JNICALL Java_com_shiqkuangsan_codestring_MainActivity_helloFromC
		  (JNIEnv * env, jobject thiz){
		
			char* cstr = "Hello From C";
			return (*env)->NewStringUTF(env, cstr);
		}

