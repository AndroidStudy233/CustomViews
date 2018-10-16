# android中的三种动画
>在android3.0之前只有两种动画, 分别是帧动画和补间动画, 3.0之后加入属性动画

## 一、 Frame Animation：（帧动画）

>这个很好理解，一帧帧的播放图片，利用人眼视觉残留原理，给我们带来动画的感觉。它的原理的GIF图片、电影播放原理一样。

1. 定义逐帧动画比较简单，在drawable目录下定义xml文件，子节点为animation-list，在这里定义要显示的图片和每张图片的显示时长
		
		<animation-list xmlns:android="http://schemas.android.com/apk/res/android" android:oneshot="false">
		    <item android:drawable="@drawable/g1" android:duration="200" />
		    <item android:drawable="@drawable/g2" android:duration="200" />
		    <item android:drawable="@drawable/g3" android:duration="200" />
		</animation-list>

	(1) 根节点属性: android:oneshot="" 设置是否仅播放一次
	
	(2) 子节点属性: android:drawable 设置每一帧图片
	
	(3) 子节点属性:android:duration 设置图片间切换间隔

 
2. 习惯上把AnimationDrawable设置为ImageView的背景 / 或者是在代码中动态设置

		(1) android:background=@anim/frame_anim
		(2)	ImageView iv = (ImageView) findViewById(R.id.iv);
			//把动画文件设置为imageView的背景
       	 	iv.setBackgroundResource(R.drawable.animations);

	然后我们就可以在java代码中获取AnimationDrawable对象了

		AnimationDrawable anim = (AnimationDrawable)yourImageView.getBackground();
		anim,start();

	需要注意的是，AnimationDrawable默认是不播放的，调用其start()方法开始播放，stop停止播放

3．上面的动画文件是通过xml文件来配置的，如果你喜欢，也可以通过在java代码中创建AnimationDrawable对象，然后通过addFrame(Drawable frame, int duration)方法向动画添加帧，然后start()。。。



---

## 二、Tween Animation：（补间动画）

>补间动画就是我们只需指定开始、结束的“关键帧“，而变化中的其他帧由系统来计算，不必自己一帧帧的去定义。

1. 无需逐一定义每一帧，只要定义开始、结束的帧，和指定动画持续时间。补间动画有4种（均为Animation抽象类子类）：

	(1) AlphaAnimation（透明度，0~1）

	(2) ScaleAnimation（大小缩放，X、Y轴缩放，还包括缩放中心pivotX、pivotY）

	(3) TranslationAnimation（位移，X、Y轴位移）

	(4) RotateAnimation（旋转，包括缩放中心pivotX、pivotY）

		// 位移动画
		public void move(View view){
		    //定义一个位移补间动画，X轴从0变化到100，Y轴不变
		    TranslateAnimation animation = new TranslateAnimation(0, 200, 0, 0);
		    //设置动画持续时间
		    animation.setDuration(1000);
		    //设置动画结束后效果保留
		    animation.setFillAfter(true);
		    //控制动画先慢后快
		    animation.setInterpolator(new AccelerateInterpolator());
		    //找到对象，开启动画
		    view.startAnimation(animation);
		}


	指定3个信息后，动画是匀速的，效果同逐帧动画。上例中还有一个属性，可以控制速度，即为Interpolator（插值器），有以下几种（Interpolator的实现类): 
	LinearInterpolator(匀速）
	
	AccelerateInterpolator（先慢后快）
	
	AccelerateDecelerateInterpolator（先慢中快后慢）
	
	DecelerateInterpolator（先快后慢）
	
	CycleInterpolator（循环播放，速度为正弦曲线）
	
	AnticipateInterpolator（先回撤，再匀速向前）
	
	OvershootInterpolator（超过，拉回）
	
	BounceInterpolator(回弹）


2. 不仅可以在代码中创建Animation对象，很多情况下，是采用动画资源文件来定义补间动画。资源目录：res/anim/myanim.xml

		<?xml version="1.0" encoding="utf-8"?>
		<set xmlns:android="http://schemas.android.com/apk/res/android">

		    <!-- 定义缩放动画 -->
		    <scale android:fromXScale="1.o"
		        android:fromYScale="1.0"
		        android:toXScale="0.01"
		        android:toYScale="0.01"
		        android:pivotX="50%"
		        android:pivotY="50%"
		        android:fillAfter="true"
		        android:duration="1000"/>

		    <!-- 定义透明度动画 -->
		    <alpha android:fromAlpha="1"
		        android:toAlpha="0.05"
		        android:duration="3000"/>

		    <!-- 定义旋转动画 -->
		    <rotate android:fromDegrees="0"
		        android:toDegrees="1800"
		        android:pivotX="50%"
		        android:pivotY="50%"
		        android:duration="2000"/>
		</set>
	然后在代码中使用AnimationUtils工具类加载动画资源，返回一个Animation对象

		Animation animation = AnimationUtils.loadAnimation(this, R.anim.myanim);

4. 补间动画一起飞: AnimationSet

		//创建动画集合, 是否共享插值器
		AnimationSet set = new AnimationSet(false);
		//往集合中添加动画
		set.addAnimation(aa);
		set.addAnimation(sa);
		set.addAnimation(ra);
		iv.startAnimation(set);


---

## 三、Property Animation：（属性动画）

>属性动画，这个是在Android 3.0中才引进的，它可以直接更改我们对象的属性。在上面提到的Tween Animation中，只是更改View的绘画效果而View的真实属性是不改变的。假设你用Tween动画将一个Button从左边移到右边，无论你怎么点击移动后的Button，他都没有反应。而当你点击移动前Button的位置时才有反应，因为Button的位置属性木有改变。而Property Animation则可以直接改变View对象的属性值，这样可以让我们少做一些处理工作，提高效率与代码的可读性。

1. (常用)ValueAnimator：包含Property Animation动画的所有核心功能，如动画时间，开始、结束属性值，相应时间属性值计算方法等。应用ValueAnimator有两个步骤

	(1) 计算属性值。

	(2) 根据属性值执行相应的动作，如改变对象的某一属性。我们的主是第二步，需要实现ValueAnimator.onUpdateListener接口，这个接口只有一个函数onAnimationUpdate()，将要改变View对象属性的事情在该接口中做。


 		ValueAnimator animator = ValueAnimator.ofInt(startInt, endInt);
        // 先在上面设置要变化的属性的起始值, 然后设置侦听, 侦听中不断回调更新方法
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
				// 可以不断拿到起始中间的值 
                Integer newHight = (Integer) valueAnimator.getAnimatedValue();
				// 做你想做的事
            }
        });
        animator.setDuration(200);
        animator.start();

2. ObjectAnimator：继承自ValueAnimator，要指定一个对象及该对象的一个属性，当属性值计算完成时自动设置为该对象的相应属性，即完成了Property Animation的全部两步操作。实际应用中一般都会用ObjectAnimator来改变某一对象的某一属性，但用ObjectAnimator有一定的限制，要想使用ObjectAnimator，传入的对象要有get() set()方法,起始值和中间值可以不给, 只给结束值：
		
		ObjectAnimator oa = ObjectAnimator.ofFloat(对象, 对象的一个属性, 起始值, 中间值0,中间值1..., 结束值);
		oa.setDuration(3000);
		oa.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                
            }

            @Override
            public void onAnimationEnd(Animator animator) {

            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });

		oa.start();

	一般使用的是View对象的话可以用这些属性值:
	translationX,translationY: View相对于原始位置的偏移量
	
	rotation,rotationX,rotationY: 旋转，rotation用于2D旋转角度，3D中用到后两个
	
	scaleX,scaleY: 缩放比
	
	x,y: View的最终坐标，是View的left，top位置加上translationX，translationY
	
	alpha: 透明度


3. 属性动画一起飞: AnimatorSet (注意和补间动画的不一样)

		AnimatorSet bouncer = new AnimatorSet();
		bouncer.play(animator1).before(animator2);
		bouncer.play(animator2).with(animator3);
		bouncer.play(animator2).with(animator4)
		bouncer.play(animator5).after(animator2);
		animatorSet.start();

	首先播放anim1；同时播放anim2,anim3,anim4；最后播放anim5。

属性动画也有插值器:

	TimeInterplator：与Tween中的interpolator类似。有以下几种

	AccelerateInterpolator　　　　　 加速，开始时慢中间加速

	DecelerateInterpolator　　　 　　 减速，开始时快然后减速

	AccelerateDecelerateInterolator　 先加速后减速，开始结束时慢，中间加速

	AnticipateInterpolator　　　　　　 反向 ，先向相反方向改变一段再加速播放

	AnticipateOvershootInterpolator　 反向加回弹，先向相反方向改变，再加速播放，会超出目的值然后缓慢移动至目的值

	BounceInterpolator　　　　　　　 跳跃，快到目的值时值会跳跃，如目的值100，后面的值可能依次为85，77，70，80，90，100

	CycleIinterpolator　　　　　　　　 循环，动画循环一定次数，值的改变为一正弦函数：Math.sin(2 * mCycles * Math.PI * input)

	LinearInterpolator　　　　　　　　 线性，线性均匀改变

	OvershottInterpolator　　　　　　 回弹，最后超出目的值然后缓慢改变到目的值

	TimeInterpolator　　　　　　　　 一个接口，允许你自定义interpolator，以上几个都是实现了这个接口