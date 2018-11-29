package com.shiqkuangsan.rxandroidmvp.RxDetail;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

import com.shiqkuangsan.rxandroidmvp.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/*************************************************
 * <p>类描述 rx的线程变换</p>
 * <p>创建人：余志伟</p>
 * <p>创建时间 : 2017/2/8</p>
 * <p>修改人：       </p>
 * <p>修改时间：   </p>
 * <p>修改备注：   </p>
 *
 * @version V3.1
 *********************************/
public class RxSchedulerActivity extends AppCompatActivity {

    @BindView(R.id.iv_rxschedule_sample)
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxscheduler);
        ButterKnife.bind(this);

        changeScheduler();
    }

    /**
     * 在不指定线程的情况下， RxJava 遵循的是线程不变的原则，即：在哪个线程调用 subscribe()，就在哪个线程生产事件；
     * 在哪个线程生产事件，就在哪个线程消费事件。如果需要切换线程，就需要用到 Scheduler （调度器）。
     * <p>
     * Schedulers.immediate(): 直接在当前线程运行，相当于不指定线程。这是默认的 Scheduler。
     * <p>
     * Schedulers.newThread(): 总是启用新线程，并在新线程执行操作。
     * <p>
     * Schedulers.io(): I/O 操作（读写文件、读写数据库、网络信息交互等）所使用的 Scheduler。
     * 行为模式和 newThread() 差不多，区别在于 io() 的内部实现是是用一个无数量上限的线程池，
     * 可以重用空闲的线程，因此多数情况下 io() 比 newThread() 更有效率。不要把计算工作放在 io() 中，可以避免创建不必要的线程。
     * <p>
     * Schedulers.computation(): 计算所使用的 Scheduler。这个计算指的是 CPU 密集型计算，即不会被 I/O 等操作限制性能的操作，
     * 例如图形的计算。这个 Scheduler 使用的固定的线程池，大小为 CPU 核数。不要把 I/O 操作放在 computation() 中，否则 I/O 操作的等待时间会浪费 CPU。
     * <p>
     * 另外， Android 还有一个专用的 AndroidSchedulers.mainThread()，它指定的操作将在 Android 主线程运行。
     * 有了这几个 Scheduler ，就可以使用 subscribeOn() 和 observeOn() 两个方法来对线程进行控制了。
     * <p>
     * subscribeOn(): 指定 subscribe() 所发生的线程，即 Observable.OnSubscribe 被激活时所处的线程。或者叫做事件产生的线程。 (我的理解是被观察者事件创建))
     * <p>
     * observeOn(): 指定 Subscriber 所运行在的线程。或者叫做事件消费的线程。(我的理解是观察者的事件触发)
     */
    @SuppressLint("CheckResult")
    public void changeScheduler() {
        Observable.just(1, 2, 3, 4)
                .subscribeOn(Schedulers.io()) // 指定 subscribe() 发生在 IO 线程
                .observeOn(AndroidSchedulers.mainThread()) // 指定 Subscriber 的回调发生在主线程()
                .subscribe(number -> {
                    Log.d("aaa", "number:" + number);
                });

        // doOnSubscribe, 就像subscriber的onstart()方法, 但是onstart不能确定是在哪儿执行的,
        // 所以会有线程问题, 然而doOnSubscribe后面可以指定线程
        // 当然相对应的还有doOnNext().
        Observable.just(1, 2, 3, 4)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> {
                    // 比如有这么个操作需要在主线程执行
                    // progressBar.setVisibility(View.VISIBLE);
                })
                .subscribeOn(AndroidSchedulers.mainThread()) // 指定doOnSubscribe的操作在主线程
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(integer -> {
                    // 比如这里又要隐藏
                    // progressBar.setVisibility(View.INVISIBLE);
                });

        Observable.create((ObservableOnSubscribe<Drawable>) emitter -> {
            try {
                Thread.sleep(10 * 1000);//这里线程由subscribeOn决定
                Drawable drawable = getResources().getDrawable(R.drawable.pic_sample);
                emitter.onNext(drawable);
                emitter.onComplete();
            } catch (InterruptedException e) {
                e.printStackTrace();
                emitter.onError(e);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Drawable>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        //这里的 Disposable 可以用来取消事件
                    }

                    @Override
                    public void onNext(Drawable t) {
                        imageView.setImageDrawable(t);
                    }

                    @Override
                    public void onError(Throwable e) {

                        Log.e("GG", e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                        Log.e("GG", "onCompleted");
                    }
                });

    }
}
