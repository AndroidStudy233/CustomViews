package com.shiqkuangsan.rxandroidmvp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;

/**
 * Created by shiqkuangsan on 2017/1/18.
 * <p>
 * ClassName: MainActivity
 * Author: shiqkuangsan
 * Description: rx首页面
 */
public class MainActivity extends AppCompatActivity {
    private TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv= (TextView) findViewById(R.id.tv);
        baseImp();
    }
    /*** rx 概念:
     *   Observable (可观察者，即被观察者)、
     *   Observer (观察者)、 subscribe (订阅)、事件。
     *   Observable 和 Observer 通过 subscribe() 方法实现订阅关系，从而 Observable 可以在需要的时候发出事件来通知 Observer。
     * */

    /**
     *@desc: 基本实现方法
     *@author: yzw
     *@modify: 2017/2/8 9:52
     *@param:
     *@return
     **/
    public void baseImp(){
        /**
         * 1)创建 Observer   它决定事件触发的时候将有怎样的行为  (观察者)   
         * 1.1)除了 Observer 接口之外，RxJava 还内置了一个实现了 Observer 的抽象类：
         * Subscriber。 Subscriber 对 Observer 接口进行了一些扩展
         * 但他们的基本使用方式是完全一样的
         *
         * 2)创建 Observable（被观察者）
         * 3)Subscribe (订阅)
         *
         * */
        //1
        Observer<String> observer = new Observer<String>() {
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
                tv.setText(s);
                MyLogUtil.i("被观察者发生改变，观察者接下来的动作>>>"+s);
            }
        };
        //1.1 
        Subscriber<String> subscriber = new Subscriber<String>() {
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
                MyLogUtil.i("被观察者发生改变，观察者接下来的动作");
            }
        };
        //2  
        Observable observable = createObservable();
        //3
        observable.subscribe(observer);
        //or    observable.subscribe(subscriber);
       
    }
    /**  
     *  observable(被观察者)
     *  create() 方法是 RxJava 最基本的创造事件序列的方法。基于这个方法， RxJava 还提供了一些方法用来快捷创建事件队列，例如：
     *  just(T...): 将传入的参数依次发送出来。
     *  from(T[]) / from(Iterable<? extends T>) : 将传入的数组或 Iterable 拆分成具体对象后，依次发送出来。
     *  just(T...) 的例子和 from(T[]) 的例子，都和之前的 create(OnSubscribe) 的例子是等价的。
     **/
    public Observable createObservable(){
        Observable ob1 =null;
//        ob1 = Observable.just("1","2","3");
//        String [] ss = {"1","2","3"};
//        ob1 = Observable.from(ss);
        
        //上面的两种方式和Observable.create等价
       
        ob1 =Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                try{
                    //这里不用try catch  只是为了展示何时调用onError
                    subscriber.onNext("1");
                    subscriber.onNext("2");
                    subscriber.onNext("3");
                    subscriber.onCompleted();
                }
                catch (Exception e){
                    subscriber.onError(e);
                }
            }
        });
        return ob1;
    }
}
