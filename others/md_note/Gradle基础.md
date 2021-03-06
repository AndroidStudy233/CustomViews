# Gradle入门与基础

>Gradle是一个基于Apache Ant和Apache Maven概念的项目自动化建构工具。它使用一种基于Groovy的特定领域语言来声明项目设置，而不是传统的XML。当前其支持的语言限于Java、Groovy和Scala，计划未来将支持更多的语言。

Gradle的特点: 
>1. 一种可切换的，像maven一样的基于约定的构建框架，却又从不锁住你（约定优于配置）
2. 强大的支持多工程的构建
3. 强大的依赖管理（基于Apache Ivy），提供最大的便利去构建你的工程
4. 全力支持已有的Maven或者Ivy仓库基础建设
5. 支持传递性依赖管理，在不需要远程仓库和pom.xml和ivy配置文件的前提下
6. 基于groovy脚本构建，其build脚本使用groovy语言编写
7. 具有广泛的领域模型支持你的构建

## 除下所述, 很多东西直接写在了build.gradle中一目了然

## 1. module类型的区分

在module的build.gradle中:

* Android application module:

		apply plugin: 'com.android.application'

* Android library module:

		apply plugin: 'com.android.library'
* Java library module:

		apply plugin: 'java'


## 2+3版本号和依赖管理
>其实下面的2和3可以放在一起简单定义,在根build.gradle文件中定义方法

	ext {
	    android = [
            compileSdkVersion: 25,
            buildToolsVersion: "25.0.0",
            minSdkVersion: 15,
            targetSdkVersion: 25
	    ]
		
		 dependencies = [
            supportv4: "com.android.support:support-v4:25.0.1",
            appcompatv7: "com.android.support:appcompat-v7:25.0.1",
            design: "com.android.support:design:25.0.1"
    	]
	}

	// 引用
	compileSdkVersion rootProject.ext.android.compileSdkVersion
	buildToolsVersion rootProject.ext.android.buildToolsVersion

	compile rootProject.ext.dependencies.appcompatv7
    compile rootProject.ext.dependencies.design

## 2. 版本号管理
>在root里申明全局变量，可以在单独的gradle里（比如新建一个dependency.gradle）申明然后apply from引用进来，或者直接定义在root的build.gradle中。

	project.ext {
	    applicationId = "com.xxx"
	    buildToolsVersion = "23.0.2"
	    compileSdkVersion = 23
	    minSdkVersion = 14
	    targetSdkVersion = 23
	    versionCode = 1
	    versionName = "1.0.0"
	    abortOnLintError = false
	    checkLintRelease = false
	    useJack = false
	    abortOnLintError = false
	
	    javaVersion = JavaVersion.VERSION_1_8
	    ...
	}


在子module里面则使用rootProject.ext去进行引用:

	android {
	    compileSdkVersion rootProject.ext.compileSdkVersion
	    buildToolsVersion rootProject.ext.buildToolsVersion
	
	    defaultConfig {
	        applicationId rootProject.ext.applicationId
	        minSdkVersion rootProject.ext.minSdkVersion
	        targetSdkVersion rootProject.ext.targetSdkVersion
	        versionCode rootProject.ext.versionCode
	        versionName rootProject.ext.versionName
	        multiDexEnabled true
	    }
	
	    compileOptions {
	        sourceCompatibility rootProject.ext.javaVersion
	        sourceCompatibility rootProject.ext.javaVersion
	    }
	
	    packagingOptions {
	        exclude 'LICENSE.txt'
	        exclude 'META-INF/DEPENDENCIES'
	        exclude 'META-INF/ASL2.0'
	        exclude 'META-INF/NOTICE'
	        exclude 'META-INF/LICENSE'
	    }
	
	    lintOptions {
	        abortOnError rootProject.ext.abortOnLintError
	        checkReleaseBuilds rootProject.ext.checkLintRelease
	        quiet true
	        ignoreWarnings true
	        // Some libraries have issues with this.
	        disable 'InvalidPackage'
	        // Lint gives this warning but SDK 20 would be Android L Beta.
	        disable 'OldTargetApi'
	    }
	    ...
	}


## 3. 依赖管理
>一些共用的类库的依赖可以定义在根gradle中

	def daggerVersion = "2.0.2"
	def retrofitVersion = "2.0.0-beta4"
	def supportVersion = "23.2.1"
	def rxBindingVersion = '0.4.0'
	
	def leakCanaryVersion = "1.3.1"
	def blockCanaryVersion = '1.1.4'
	
	project.ext {
	    ...
	    libSupportAppcompat = "com.android.support:appcompat-v7:${supportVersion}"
	    libSupportDesign = "com.android.support:design:${supportVersion}"
	
	    libEventBus = "org.greenrobot:eventbus:3.0.0"
	    libJavaxAnnotation = "javax.annotation:jsr250-api:1.0"
	
	    libDaggerCompiler = "com.google.dagger:dagger-compiler:${daggerVersion}"
	
	    libGlide = "com.github.bumptech.glide:glide:3.7.0"
	
	    libRealm = "io.realm:realm-android:0.87.5"
	
	    debugDependencies = [
	            leakCanary: "com.squareup.leakcanary:leakcanary-android:${leakCanaryVersion}",
	            blockcanary: "com.github.moduth:blockcanary-ui:${blockCanaryVersion}",
	    ]
	
	    releaseDependencies = [
	            leakCanary: "com.squareup.leakcanary:leakcanary-android-no-op:${leakCanaryVersion}",
	            blockcanary: "com.github.moduth:blockcanary-no-op:${blockCanaryVersion}",
	    ]
	}

module中使用:
	
	dependencies {
	    compile fileTree(include: ['*.jar'], dir: 'libs')
	    ...
	    apt rootProject.ext.libDaggerCompiler
	    compile rootProject.ext.libDagger
	    compile rootProject.ext.libRxJava
	    compile rootProject.ext.libRxAndroid
	    compile rootProject.ext.libRxBinding
	    compile rootProject.ext.libGlide
	    provided rootProject.ext.libJavaxAnnotation
	    compile rootProject.ext.libSupportAppcompat
	    compile rootProject.ext.libSupportDesign
	    compile rootProject.ext.libSupportRecyclerview
	    compile rootProject.ext.libSupportV4
	    debugCompile rootProject.ext.debugDependencies.leakCanary
	    releaseCompile rootProject.ext.releaseDependencies.leakCanary
	    debugCompile rootProject.ext.debugDependencies.blockCanary
	    releaseCompile rootProject.ext.releaseDependencies.blockCanary
	}

## 4. 签名管理
>签名是一个很敏感的东西，只要有了签名文件和对应的密码信息，就能轻易反编译修改源码然后再签名进行发布，因此如何保存这些敏感信息是很重要的。

* local.properties定义keystore信息文件路径

		keystore.props.file=../../keystore.properties

* keystore.properties保存keystore信息

		alias=xxx
		pass=xxx
		store=../buildsystem/release.jks
		storePass=xxx

* buildsystem下保存了

	$ ls
	ci.gradle
	debug.keystore
	release.jks

* application module的signingConfigs

	signingConfigs {

	    def Properties localProps = new Properties()
	    localProps.load(new FileInputStream(file('../local.properties')))
	    def Properties keyProps = new Properties()
	
	    // 如果读取不到'keystore.props.file'属性，就使用debug keystore
	
	    if (localProps['keystore.props.file']) {
	        keyProps.load(new FileInputStream(file(localProps['keystore.props.file'])))
	    } else {
	        keyProps["store"] = '../buildsystem/debug.keystore'
	        keyProps["alias"] = 'android'
	        keyProps["storePass"] = 'androiddebugkey'
	        keyProps["pass"] = 'android'
	    }
	
	    debug {
	        storeFile file(keyProps["store"])
	        keyAlias keyProps["alias"]
	        storePassword keyProps["storePass"]
	        keyPassword keyProps["pass"]
	    }
	
	    release {
	        // release版本使用assert确保存在该属性否则报错，避免错误打包
	        assert localProps['keystore.props.file'];
	        storeFile file(keyProps["store"])
	        keyAlias keyProps["alias"]
	        storePassword keyProps["storePass"]
	        keyPassword keyProps["pass"]
	    }
	}


## 5. Java8支持
>对Android的module

	apply plugin: 'me.tatarka.retrolambda'
	
	android {
	    compileOptions {
 			// sourceCompatibility rootProject.ext.javaVersion
        	// sourceCompatibility rootProject.ext.javaVersion
	        sourceCompatibility rootProject.ext.android.javaVersion
	        sourceCompatibility rootProject.ext.androidjavaVersion
	    }
	}


## 6. Split APK
>可以根据脚本的配置，将apk以abi、density进行分包。再也不用为了缩小包的体积而专门去只留下一个arm的jni文件夹了，想怎么分怎么分，搞不定哪天就要传一个x86的包了，而且有的模拟器也只支持x86。

	splits {
	    abi {
	        enable true
	        reset()
	        include 'armeabi', 'x86' //, 'x86', 'armeabi-v7a', 'mips'
	        universalApk false
	    }
	}


## 7. Module aar依赖
>怎么能在使用aar依赖提升编译速度的同时，又能兼顾灵活性，随时可以修改源码呢---module式aar依赖。

在你需要依赖aar的module的gradle文件根中定义

	repositories {
	    flatDir {
	        dirs 'libs'
	    }
	}

这样你就直接可以用放在该module\libs下的aar文件了,在dependencies中直接添加.其中xxx是文件名

	 compile (name:'xxxxxxxxx',ext:'aar')

## Gradle性能提升

1. 放开该行->由于gradle运行在Java虚拟机上的,这个是指定java虚拟机初始化堆内存

    org.gradle.jvmargs=-Xmx2048m -XX:MaxPermSize=512m -XX:+HeapDumpOnOutOfMemoryError -Dfile.encoding=UTF-8

2. 放开该行->设置gradle可并发处理多个任务

    org.gradle.parallel=true

3. 添加该行->设置守护进程,加速gradle编译

    org.gradle.daemon=true

### 一般 编译/运行 项目失败的时候, 如果不知道到底是什么错了可以采用下面的方法查看

    gradlew taskName --xxx
    第二个参数: 运行报错会有what went wrong, 会有个':名字:具体task', 这儿就填报错的task
    第三个参数: 你要查看的方式(有三种): --stacktrace    /   --info  /   --debug
    
    Mac 下一般使用 ./gradlew 命令而不是 gradlew


## Gradle之gradle依赖和wrapper初步解析
>当AS中设置为use default gradle wrapper. 那么编译的时候就会去该路径下查找:
{project.dir}\gradle\wrapper\gradle-wrapper.properties.就是该文件

>当AS中设置为use local gradle distribution时, 根据配置的路径初始化gradle, 如果没有则去下载, 下载配置如下

>当AS中设置为Offline work时, 会去配置的service path中获取缓存的马偕库文件, 任何一个没有则直接fail

1. Thu May 04 23:08:44 CST 2017

	无需多说, 时间

2. distributionBase=GRADLE\_USER\_HOME

	####distributionBase: gradle的zip包下载解压后目标基础目录, *GRADLE\_USER\_HOME* 和 PROJECT两个取值, 一般不会用后者. 

	* *GRADLE\_USER\_HOME*: 不配置则默认windows下为: %USERPROFILE%/.gradle. 例如 C:\Users\shiqkuangsan\\.gradle\; 
	
		* 当然你也可以在环境变量中配置GRADLE\_USER\_HOME并赋值;
		* 而且还可以在项目根目录下的gradle.properties中配置, gradle.user.home=D:/xx/xx
	
	* PROJECT: 当前工程目录(gradlew所在同级目录), 一般不会把gradle的zip包等文件放在工程下吧


3. distributionPath=wrapper/dists

	distributionPath: gradle的zip包下载后 解压 解压 解压的目录延伸, 即上面目录下的wrapper/dists

	distributionBase和distributionPath组合在一起, 是对应版本gradle的zip包解压之后所存放的位置
	
4. zipStoreBase=GRADLE\_USER\_HOME

	相对于上边的distributionBase就是gradle的zip包存放的目录

5. zipStorePath=wrapper/dists

	相对于上边的distributionPath就是gradle的zip包存放的目录延伸

	zipStoreBase和zipStorePath组合在一起, 是对应版本gradle的zip所存放的位置

6. distributionUrl=https\://services.gradle.org/distributions/gradle-3.3-all.zip

	要下载的gradle的地址, 如果前面配置的目录下没找到对应的zip包, 就去该地址下载. 其实该地址换成本地路径下的zip下载地址分分钟解决问题(比如文件存在于D:\DevSoft\Gradle/package/gradle-3.3-all.zip, 那么直接拖进浏览器, 然后复制下载地址填写在distributionUrl. 相当于直接下载好了)
	
	此外提一下gradle文件的三种格式:

		* gradle-xx-all.zip是完整版，包含了各种二进制文件，源代码文件，和离线的文档。例如，https://services.gradle.org/distributions/gradle-3.3-all.zip
		
		* gradle-xx-bin.zip是二进制版，只包含了二进制文件（可执行文件），没有文档和源代码, 但是可以用于编译。例如，https://services.gradle.org/distributions/gradle-3.3-bin.zip
		
		* gradle-xx-src.zip是源码版，只包含了Gradle源代码，不能用来编译你的工程。例如，https://services.gradle.org/distributions/gradle-3.3-src.zip  

>##小结: 

>1. 设置use default gradle wrapper, 如果配置了path变量GRADLE_USER_HOME并且正确, 则直接使用gradle-wrapper.properties下配置的distributionBase + distributionPath + distributionUrl的版本号 得到对应的gradle-version-all进行编译. 如果path没有则去distributionUrl去下载并根据zipStoreBase和zipStorePath进行存放解压然后进行编译. 编译下载的jar包等缓存文件存储于distributionBase下的caches中

>2. 设置use local gradle distribution, 如果配置正确, 则直接使用配置的路径进行编译. 如果不正确会回到第一步

>tips: 所以配置gradle环境变量的时候, <1> GRADLE_HOME: 一般在Studio安装目录下gradle里面用比较新的版本(当前是3.3或者4.1), 就是gradle-3.3/4.1-all.zip解压后的目录, 然后path中引用并配到bin下. <2> GRADLE_USER_HOME: 此目录可配可不配, 不配默认为C下面的user\.gradle. 如果要配置的话建议单独弄个比如D:\File\Gradle. 一般里面的目录为caches、daemon、native、wrapper这几层


[Gradle官方文档](https://docs.gradle.org/current/dsl/org.gradle.api.tasks.wrapper.Wrapper.html#N27330)
