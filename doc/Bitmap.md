# Bitmap初探
Bitmap是Android系统中的图像处理的最重要类之一。用它可以获取图像文件信息，进行图像剪切、旋转、缩放等操作，并可以指定格式保存图像文件。
## Bitmap
### 内存占用
色深：表示一个像素点可以有多少种色彩来描述，它的单位是bit，拿位图而言，其支持RGB各8bit，所以说位图的色深为24bit。

Android中一张图片（Bitmap）占用的内存主要和以下几个因数有关：图片长度，图片宽度，色深。一张图片（Bitmap）占用的内存=`图片长度*图片宽度*单位像素占用的字节数`。
8位(bit)是1字节(byte)
- ARGB_8888 各占8位，总共32位是4字节，即`width*height*4`
- RGB_565 占16位是2字节即`width*height*2`

### 常用方法
- public void recycle() 　// 回收位图占用的内存空间，把位图标记为Dead
- public final boolean isRecycled() 　//判断位图内存是否已释放
- public final int getWidth()　//获取位图的宽度
- public final int getHeight()　//获取位图的高度
- public final boolean isMutable()　//图片是否可修改
- public int getScaledWidth(Canvas canvas)　//获取指定密度转换后的图像的宽度
- public int getScaledHeight(Canvas canvas)　//获取指定密度转换后的图像的高度
- public boolean compress(CompressFormat format, int quality, OutputStream stream)　//按指定的图片格式以及画质，将图片转换为输出流。
format：压缩图像的格式,如Bitmap.CompressFormat.PNG或Bitmap.CompressFormat.JPEG
quality：画质，0-100.0表示最低画质压缩，100以最高画质压缩。对于PNG等无损格式的图片，会忽略此项设置。
stream: OutputStream中写入压缩数据。
return: 是否成功压缩到指定的流。
- public static Bitmap createBitmap(Bitmap src)　 //以src为原图生成不可变得新图像
- public static Bitmap createScaledBitmap(Bitmap src, int dstWidth, int dstHeight, boolean filter)　//以src为原图，创建新的图像，指定新图像的高宽以及是否可变。
- public static Bitmap createBitmap(int width, int height, Config config)　//创建指定格式、大小的位图
- public static Bitmap createBitmap(int width, int height, Config config)　//创建指定格式、大小的位图

## BitmapFactory
### Option
#### public boolean inJustDecodeBounds
如果设置为true，不获取图片，不分配内存，但会返回图片的高度宽度信息。如果将这个值置为true，那么在解码的时候将不会返回bitmap，只会返回这个bitmap的尺寸。这个属性的目的是，如果你只想知道一个bitmap的尺寸，但又不想将其加载到内存时。这是一个非常有用的属性。
#### public int inSampleSize
图片缩放的倍数
这个值是一个int，当它小于1的时候，将会被当做1处理，如果大于1，那么就会按照比例（1 / inSampleSize）缩小bitmap的宽和高、降低分辨率，大于1时这个值将会被处置为2的倍数。例如，width=100，height=100，inSampleSize=2，那么就会将bitmap处理为，width=50，height=50，宽高降为1 / 2，像素数降为1 / 4。
#### public int outWidth
获取图片的宽度值
#### public int outHeight
获取图片的高度值
表示这个Bitmap的宽和高，一般和inJustDecodeBounds一起使用来获得Bitmap的宽高，但是不加载到内存。
#### public int inDensity
用于位图的像素压缩比
#### public int inTargetDensity
用于目标位图的像素压缩比（要生成的位图）
#### public byte[] inTempStorage
创建临时文件，将图片存储
#### public boolean inScaled
设置为true时进行图片压缩，从inDensity到inTargetDensity
#### public boolean inDither
如果为true,解码器尝试抖动解码
#### public Bitmap.Config inPreferredConfig
设置解码格式
这个值是设置色彩模式，默认值是ARGB_8888，在这个模式下，一个像素点占用4bytes空间，一般对透明度不做要求的话，一般采用RGB_565模式，这个模式下一个像素点占用2bytes。
#### public String outMimeType
设置解码图像
#### public boolean inPurgeable
当存储Pixel的内存空间在系统内存不足时是否可以被回收
#### public boolean inInputShareable
inPurgeable为true情况下才生效，是否可以共享一个InputStream
#### public boolean inPreferQualityOverSpeed
为true则优先保证Bitmap质量其次是解码速度
#### public boolean inMutable
配置Bitmap是否可以更改，比如：在Bitmap上隔几个像素加一条线段
#### public int inScreenDensity
当前屏幕的像素密度

```java
//从文件读取图片
public static Bitmap decodeFile(String pathName)
public static Bitmap decodeFile(String pathName, Options opts)

//从输入流读取图片
public static Bitmap decodeStream(InputStream is)　
public static Bitmap decodeStream(InputStream is, Rect outPadding, Options opts)

//从资源文件读取图片
public static Bitmap decodeResource(Resources res, int id)　
public static Bitmap decodeResource(Resources res, int id, Options opts)

// 从字节数组中读取图片
public static Bitmap decodeByteArray(byte[] data, int offset, int length)
public static Bitmap decodeByteArray(byte[] data, int offset, int length, Options opts)

　//从文件读取文件 与decodeFile不同的是这个直接调用JNI函数进行读取 效率比较高
public static Bitmap decodeFileDescriptor(FileDescriptor fd)
public static Bitmap decodeFileDescriptor(FileDescriptor fd, Rect outPadding, Options opts)
```

## 优化
在Android应用里，最耗费内存的就是图片资源。很容易出现OutOfMemory异常。所以，对于图片的内存优化，是Android应用开发中比较重要的内容。
#### 1. 及时回收Bitmap的内存
Bitmap类有一个方法recycle()，从方法名可以看出意思是回收。这里就有疑问了，Android系统有自己的垃圾回收机制，可以不定期的回收掉不使用的内存空间，当然也包括Bitmap的空间。那为什么还需要这个方法呢？
Bitmap类的构造方法都是私有的，所以开发者不能直接new出一个Bitmap对象，只能通过BitmapFactory类的各种静态方法来实例化一个Bitmap。仔细查看BitmapFactory的源代码可以看到，生成Bitmap对象最终都是通过JNI调用方式实现的。所以，加载Bitmap到内存里以后，是包含两部分内存区域的。简单的说，一部分是Java部分的，一部分是C部分的。这个Bitmap对象是由Java部分分配的，不用的时候系统就会自动回收了，但是那个对应的C可用的内存区域，虚拟机是不能直接回收的，这个只能调用底层的功能释放。所以需要调用recycle()方法来释放C部分的内存。从Bitmap类的源代码也可以看到，recycle()方法里也的确是调用了JNI方法了的。
那如果不调用recycle()，是否就一定存在内存泄露呢？也不是的。Android的每个应用都运行在独立的进程里，有着独立的内存，如果整个进程被应用本身或者系统杀死了，内存也就都被释放掉了，当然也包括C部分的内存。
#### 2. 捕获异常
为了避免应用在分配Bitmap内存的时候出现OutOfMemory异常以后Crash掉，需要特别注意实例化Bitmap部分的代码。通常，在实例化Bitmap的代码中，一定要对OutOfMemory异常进行捕获。
这里对初始化Bitmap对象过程中可能发生的OutOfMemory异常进行了捕获。如果发生了OutOfMemory异常，应用不会崩溃，而是得到了一个默认的Bitmap图。
**注意：很多开发者会习惯性的在代码中直接捕获Exception。但是对于OutOfMemoryError来说，这样做是捕获不到的。因为OutOfMemoryError是一种Error，而不是Exception。在此仅仅做一下提醒，避免写错代码而捕获不到OutOfMemoryError。**
#### 3. 缓存Bitmap
例如好友的头像会好友列表和黑名单列表中显示，，就可以对同一Bitmap进行缓存。如果不进行缓存，尽管看到的是同一张图片文件，但是使用BitmapFactory类的方法来实例化出来的Bitmap，是不同的Bitmap对象。缓存可以避免新建多个Bitmap对象，避免内存的浪费。
#### 4. 压缩图片
如果在一张图片大小为800x800，要在一个200x200的ImageView上显示，则可以将图片缩小，以减少载入图片过程中的内存的使用，避免异常发生。
使用BitmapFactory.Options设置inSampleSize就可以缩小图片。属性值inSampleSize表示缩略图大小为原始图片大小的几分之一。即如果这个值为2，则取出的缩略图的宽和高都是原始图片的1/2，图片的大小就为原始大小的1/4。
如果知道图片的像素过大，就可以对其进行缩小。那么如何才知道图片过大呢？
使用BitmapFactory.Options设置inJustDecodeBounds为true后，再使用decodeFile()等方法，并不会真正的分配空间，即解码出来的Bitmap为null，但是可计算出原始图片的宽度和高度，即options.outWidth和options.outHeight。通过这两个值，就可以知道图片是否过大了。
```java
BitmapFactory.Options opts = new BitmapFactory.Options();
    // 设置inJustDecodeBounds为true
    opts.inJustDecodeBounds = true;
    // 使用decodeFile方法得到图片的宽和高
    BitmapFactory.decodeFile(path, opts);
    // 打印出图片的宽和高
    Log.d("example", opts.outWidth + "," + opts.outHeight);
```
在实际项目中，可以利用上面的代码，先获取图片真实的宽度和高度，然后判断是否需要跑缩小。如果不需要缩小，设置inSampleSize的值为1。如果需要缩小，则动态计算并设置inSampleSize的值，对图片进行缩小。需要注意的是，在下次使用BitmapFactory的decodeFile()等方法实例化Bitmap对象前，别忘记将opts.inJustDecodeBound设置回false。否则获取的bitmap对象还是null。
注意：如果程序的图片的来源都是程序包中的资源，或者是自己服务器上的图片，图片的大小是开发者可以调整的，那么一般来说，就只需要注意使用的图片不要过大，并且注意代码的质量，及时回收Bitmap对象，就能避免OutOfMemory异常的发生。
如果程序的图片来自外界，这个时候就特别需要注意OutOfMemory的发生。一个是如果载入的图片比较大，就需要先缩小；另一个是一定要捕获异常，避免程序Crash。

## 杂谈
#### Bitmap复用
4.4之后推荐使用`bitmap.getAllocationByteCount`获取bitmap占用的内存大小，但有时候它和`bitmap.getByteCount`获取的大小是相同的。因为Bitmap有个复用机制，会复用存在的Bitmap对象，也就是Options的inBitmap参数

4.4之前的版本inBitmap只能够重用相同大小的Bitmap内存区域。简单而言，被重用的Bitmap需要与新的Bitmap规格完全一致，否则不能重用。

4.4之后的版本系统不再限制旧Bitmap与新Bitmap的大小，只要保证旧Bitmap的大小是大于等于新Bitmap大小即可。
除上述规则之外，旧Bitmap必须是mutable的，这点也很好理解，如果一个Bitmap不支持修改，那么其内存自然也重用不了。

Android4.4.4以后，使用该参数需要满足如下条件：
- Bitmap本身可可变的(mutable)
- 新的Bitmap的内存需要小于等于旧的Bitmap的内存
- 新申请的bitmap与旧的bitmap必须有相同的解码格式，如：使用了ARGB_8888就不能再使用RGB_565的解码模式了。

**inBitmap是在BitmapFactory中的内部类Options的一个变量，简单而言，使用该变量可以复用旧的Bitmap的内存而不用重新分配以及销毁旧Bitmap，进而改善运行效率。inBitmap变量是在Android 3.0+版本加入到系统源码当中，也就意味着inBitmap参数只有在Android 3.0+版本及以上能够正常使用，当你的app版本低于3.0的时候，还是老老实实的使用bitmap.recycle()进行Bitmap的回收操作；**

#### 抖动解码（inDither）
在Android中的BitmapFactory.Options中有一个属性值是inDither，这个值表示是否采用抖动解码，那什么叫抖动解码呢，今天我们就来详细解说一下。

Bitmap的解码是根据它所记录的节点，依照一定算法，来补充两个节点之间的数据，可以理解为补充其中像素点的颜色，那么在解码的时候肯定会和当前所采用的颜色模式有关，很直观的说，采用32位的肯定比16位的颜色要多，自然展现的图像会看起来更好。

如果一张颜色很丰富的图，用一个位数比较低的颜色模式来解码的话，那么一个直观的感觉就是颜色不够用，那么这张图解出来之后，在一些颜色渐变的区域上就会有一些很明显的断裂色带，这个很好解释，因为一些丰富的颜色在位数较低的颜色模式下并没有，那么只能用相近的填充，可能一大片都没有，那么一大片都用这一个颜色填充，就形成了断裂色带。

如果采用抖动解码，那么就会在这些色带上采用随机噪声色来填充，目的是让这张图显示效果更好，色带不那么明显。

###### 抖动解码参考
>https://blog.csdn.net/haozipi/article/details/47185535?utm_source=blogxgwz6



###### Bitmap参考
>作者：闲庭CC
>链接：https://www.jianshu.com/p/8206dd8b6d8b
>来源：简书

>Android中Bitmap的深入探讨总结
>https://my.oschina.net/u/3863980/blog/2872578