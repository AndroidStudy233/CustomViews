package com.shiqkuangsan.rxandroidmvp.review3;

import android.annotation.SuppressLint;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

/**
 * Created by shiqkuangsan on 2017/2/8.
 * <p>
 * ClassName: RxTest3
 * Author: shiqkuangsan
 * Description: RxJava提供的观察者
 */
public class RxTest3 {

    static int count = 0;

    public static void main(String[] args) {
//        method1();

//        method2();

//        method3();
    }

    /**
     * 实现观察者模式    方式1: create 方式
     */
    private static void method1() {
        // 创建被观察者,这里的泛型要和下面观察者中的泛型保持一致
//        Observable<String> observable = Observable.create(new Observable.OnSubscribe<String>() {
//            @Override
//            public void call(Subscriber<? super String> subscriber) {
//                if (!subscriber.isUnsubscribed()) {
//                    // 如果还存在订阅关系的话
//                    subscriber.onNext("hello");
//                    subscriber.onNext("rxjava");
//                    if (getJsonData() == null) {
//                        subscriber.onError(new NullPointerException());
//                    } else {
//                        subscriber.onNext(getJsonData());
//                    }
//                    subscriber.onCompleted();
//                }
//            }
//        });
        // 箭头函数你好
        Observable<String> observable = Observable.create(
                (subscriber) -> {
                    if (!subscriber.isDisposed()) {
                        // 如果还存在订阅关系的话
                        subscriber.onNext("hello");
                        subscriber.onNext("rxjava");
                        if (getJsonData() == null) {
                            subscriber.onError(new NullPointerException());
                        } else {
                            subscriber.onNext(getJsonData());
                        }
                        subscriber.onComplete();
                    }
                }
        );

        // 创建观察者
        Observer<String> subscriber = new Observer<String>() {
            @Override
            public void onComplete() {
                System.out.println("已完成onCompleted");
                count = 0;
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("步入歧途onError");
                e.printStackTrace();
            }

            /*
            * 订阅时候回调
            * 得到的 Disposable可以用来取消事件
            * */
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String s) {
                System.out.println("观察到改变" + (++count) + ": " + s);
            }

            /*
                 RxJava 1的方法
                另外提一下这个方法.会在 subscribe 刚开始，而事件还未发送之前被调用，可以用于做一些准备工作
             */
//            @Override
//            public void onStart() {
//                super.onStart();
//            }
        };

        // 订阅 一旦被观察者(observable)被订阅了,其中的call方法就会被执行 从而触发观察者的回调
        observable.subscribe(subscriber);

        /*
            其实可以连起来写    Observable.create(xx).subscribe(xx);
         */
    }

    /**
     * 一个获取json数据的方法
     */
    private static String getJsonData() {
        return "I'm json data!";
//        return null;
    }

    /**
     * 实现观察者模式    方式2: just方式
     */
    @SuppressLint("CheckResult")
    private static void method2() {
        // 将会依次调用 onNext(hello) --> onNext(world) --> onNext(rxjava) --> oncomplete
        // 值得一提的是just方式的参数可以是一个个数组.会依次遍历数组执行...这就厉害啦
        Observable.just("hello", "world", "rxjava")
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        System.out.println("已完成onCompleted");
                        count = 0;
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.out.println("步入歧途onError");
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(String s) {
                        System.out.println("观察到改变" + (++count) + ": " + s);
                    }
                });

        System.out.println("=================");

        // 如果你不需要其他操作,只需要onNext中处理可以使用 Action1 类(就是相当于一个onNext的subscriber)
        Observable.just("hello2", "world2", "rxjava2")
                .subscribe(s-> {
                        System.out.println("观察到改变: " + s);
                });

        System.out.println("=================");

        // 这是上面的完整写法.Action0相当于oncomplete, Action1相当于onNext, Action1的泛型为Throwable时相当于onError
//        observable.subscribe(onNextAction);
//        observable.subscribe(onNextAction, onErrorAction);
//        observable.subscribe(onNextAction, onErrorAction, onCompletedAction);

        Observable.just("hello3", "world3", "rxjava3")
                .subscribe(
                        (String s) -> System.out.println("观察到改变: " + s),
                        (Throwable throwable) -> {
                            System.out.println("步入歧途onError");
                            throwable.printStackTrace();
                        },
                        () -> System.out.println("已完成onCompleted")
                );
    }

    /**
     * 实现观察者模式    方式3: from方式
     * from方式支持两种格式
     * <p>
     * 1.集合
     * <p>
     * 2.数组
     */
    @SuppressLint("CheckResult")
    private static void method3() {
        // 集合
        List<String> list = new ArrayList<>();
        list.add("hello");
        list.add("world");
        list.add("rxjava");
        Observable.fromIterable(list)
                .subscribe(s-> {
                        System.out.println("观察到改变: " + s);
                });

        System.out.println("=================");

        // 数组
        String[] words = {"hello2", "world2", "rxjava2"};
        Observable.fromArray(words)
                .subscribe(s-> {
                        System.out.println("观察到改变: " + s);
                });
    }

    /**
     * 其他方式:
     * 1. Observable.interval(1, 2, TimeUnit.SECONDS);
     * 延迟时间,间隔时间,时间单位...结果会每隔1s拿到一个秒值整数
     * <p>
     * 2. Observable.range(10, 3);
     * 从10开始总共3个数据...结果会拿到10,11,12
     * <p></p>
     * 操作符的使用(部分):
     * fitler: 如下fitler()
     * scan: 如下scan()
     * timer: 如下timer()
     */
    @SuppressLint("CheckResult")
    void fitler() {
        Observable.just(1, 2, 3, 4, 5)
                .filter(integer-> {
                        return integer < 3;// 小于3才返回true,于是过滤了3,4
                })
                .subscribe(integer-> {
                        System.out.println("fitler---" + integer);
                });
    }

    @SuppressLint("CheckResult")
    void scan() {
        // 第一次发射得到1，作为结果与2相加；发射得到3，作为结果与3相加
        Observable.just(1, 2, 3, 4, 5)
                .scan( (integer, integer2)-> integer + integer2)
                .subscribe(integer-> {
                        System.out.println("scan---" + integer);
                });
    }

    @SuppressLint("CheckResult")
    void timer() {
        // 3秒后输出 hello world . 该方法使用了android线程.因此不能在main中运行测试
        Observable.timer(3, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aLong-> {
                        System.out.println("timer--hello world--" + aLong);
                });
    }
}
