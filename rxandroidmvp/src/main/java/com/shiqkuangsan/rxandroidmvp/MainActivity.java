package com.shiqkuangsan.rxandroidmvp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.shiqkuangsan.rxandroidmvp.retrofit.RetrofitActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.functions.Action0;
import rx.functions.Action1;

/**
 * Created by shiqkuangsan on 2017/1/18.
 * <p>
 * ClassName: MainActivity
 * Author: shiqkuangsan
 * Description: rx首页面
 */
public class MainActivity extends AppCompatActivity {

    @BindView(R.id.tv_main_hello)
    TextView tv_main_hello;
    @BindView(R.id.btn_main_change)
    public Button btn_main_change;
    @BindView(R.id.btn_main_rxretro)
    Button btn_main_rxretro;

    private Observable observable;
    private Observer observer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        baseImp();//  这些全都是在同一个线程的观察者 ，本篇概念看完  能了解基本用法，下一遍异步观察~ RxSchedulerActivity

        btn_main_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RxSchedulerActivity.class));
            }
        });
        btn_main_rxretro.setOnClickListener(
                (View view) -> startActivity(new Intent(MainActivity.this, RetrofitActivity.class))
        );
    }

    // 建议使用ButterKnife来定义点击事件
//    @OnClick({R.id.btn_main_change, R.id.btn_main_rxretro})
//    public void onClick(View view) {
//        switch (view.getId()){
//            case R.id.btn_main_change:
//                break;
//
//            case R.id.btn_main_rxretro:
//                break;
//        }
//    }

    /*** rx 概念:
     *   Observable (可观察者，即被观察者)、
     *   Observer (观察者)、 subscribe (订阅)、事件。
     *   Observable 和 Observer 通过 subscribe() 方法实现订阅关系，从而 Observable 可以在需要的时候发出事件来通知 Observer。
     * */

    /**
     * @return
     * @desc: 基本实现方法
     * @author: yzw
     * @modify: 2017/2/8 9:52
     * @param:
     **/
    public void baseImp() {
        /**
         * 1)创建 Observer   它决定事件触发的时候将有怎样的行为  (观察者)   
         * 2)创建 Observable（被观察者）
         * 3)Subscribe (订阅)
         *
         * */
        //1
        Observer observer = createObserver();
        //2  
        Observable observable = createObservable();
        //3
        subscriberImp();

    }

    /**
     * 1)创建 Observer   它决定事件触发的时候将有怎样的行为  (观察者)
     * 1.1)除了 Observer 接口之外，RxJava 还内置了一个实现了 Observer 的抽象类：
     * Subscriber。 Subscriber 对 Observer 接口进行了一些扩展
     * 但他们的基本使用方式是完全一样的
     * <p>
     * 2)创建 Observable（被观察者）
     * 3)Subscribe (订阅)
     */
    public Observer createObserver() {
        //1
        observer = new Observer<String>() {
            @Override
            public void onCompleted() {
                MyLogUtil.i("完成时调用");
            }

            @Override
            public void onError(Throwable e) {
                MyLogUtil.i("异常时调用");
            }

            @Override
            public void onNext(String s) {
                tv_main_hello.setText(s);
                MyLogUtil.i("被观察者发生改变，观察者接下来的动作>>>" + s);
            }
        };
        //1.1 
//        observer = new Subscriber<String>() {
//            @Override
//            public void onCompleted() {
//                MyLogUtil.i("完成时调用");
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                MyLogUtil.i("异常时调用");
//            }
//
//            @Override
//            public void onNext(String s) {
//                MyLogUtil.i("被观察者发生改变，观察者接下来的动作");
//            }
//        };
        return observer;
    }

    /**
     * observable(被观察者)
     * create() 方法是 RxJava 最基本的创造事件序列的方法。基于这个方法， RxJava 还提供了一些方法用来快捷创建事件队列，例如：
     * just(T...): 将传入的参数依次发送出来。
     * from(T[]) / from(Iterable<? extends T>) : 将传入的数组或 Iterable 拆分成具体对象后，依次发送出来。
     * just(T...) 的例子和 from(T[]) 的例子，都和之前的 create(OnSubscribe) 的例子是等价的。
     **/
    public Observable createObservable() {
//        ob1 = Observable.just("1","2","3");

//        String [] ss = {"1","2","3"};
//        ob1 = Observable.from(ss);

        //上面的两种方式和Observable.create等价

        observable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                try {
                    //这里不用try catch  只是为了展示何时调用onError
                    subscriber.onNext("1");
                    subscriber.onNext("2");
                    subscriber.onNext("3");
                    subscriber.onCompleted();
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }
        });
        return observable;
    }

    /**
     * 除了 subscribe(Observer) 和 subscribe(Subscriber) ，
     * subscribe() 还支持不完整定义的回调，RxJava 会自动根据定义创建出 Subscriber 。形式如下：
     */
    public void subscriberImp() {
        /**
         * 简单解释一下这段代码中出现的 Action1 和 Action0。 Action0 是 RxJava 的一个接口，它只有一个方法 call()，
         * 这个方法是无参无返回值的；由于 onCompleted() 方法也是无参无返回值的，因此 Action0 可以被当成一个包装对象，
         * 将 onCompleted() 的内容打包起来将自己作为一个参数传入 subscribe() 以实现不完整定义的回调。这样其实也可以
         * 看做将 onCompleted() 方法作为参数传进了 subscribe()，相当于其他某些语言中的『闭包』。 Action1 也是一个接口，
         * 它同样只有一个方法 call(T param)，这个方法也无返回值，但有一个参数；与 Action0 同理，由于 onNext(T obj) 和
         * onError(Throwable error) 也是单参数无返回值的，因此 Action1 可以将 onNext(obj) 和 onError(error) 打包起来传入 
         * subscribe() 以实现不完整定义的回调。事实上，虽然 Action0 和 Action1 在 API 中使用最广泛，
         * 但 RxJava 是提供了多个 ActionX 形式的接口 (例如 Action2, Action3) 的，它们可以被用以包装不同的无返回值的方法
         * */
        Action1<String> action1 = new Action1<String>() {
            @Override
            public void call(String s) {
                MyLogUtil.e("onNext" + s);
            }
        };
        Action1<Throwable> error = new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                MyLogUtil.e("onError");
            }
        };
        Action0 complete = new Action0() {
            @Override
            public void call() {
                MyLogUtil.e("onComplete");
            }
        };
        observable.subscribe(action1);    //onNext
        observable.subscribe(action1, error);//onNext  onError
        observable.subscribe(action1, error, complete);//onNext  onError  complete

        //或者直接 observable.subscribe(observer);
    }
}
