package com.shiqkuangsan.rxandroidmvp.review3;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;

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
     * 实现被观察者    方式1: create 方式
     */
    private static void method1() {
        // 创建被观察者,这里的泛型要和下面观察者中的泛型保持一致
        Observable<String> observable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                if (!subscriber.isUnsubscribed()) {
                    // 如果还存在订阅关系的话
                    subscriber.onNext("hello");
                    subscriber.onNext("rxjava");
                    if (getJsonData() == null) {
                        subscriber.onError(new NullPointerException());
                    } else {
                        subscriber.onNext(getJsonData());
                    }
                    subscriber.onCompleted();
                }
            }
        });

        // 创建观察者
        Subscriber<String> subscriber = new Subscriber<String>() {
            @Override
            public void onCompleted() {
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

            /*
                另外提一下这个方法.会在 subscribe 刚开始，而事件还未发送之前被调用，可以用于做一些准备工作
             */
            @Override
            public void onStart() {
                super.onStart();
            }
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
     * 实现被观察者    方式2: just方式
     */
    private static void method2() {
        // 将会依次调用 onNext(hello) --> onNext(world) --> onNext(rxjava) --> oncomplete
        // 值得一提的是just方式的参数可以是一个个数组.会依次遍历数组执行...这就厉害啦
        Observable.just("hello", "world", "rxjava")
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
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
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        System.out.println("观察到改变: " + s);
                    }
                });

        System.out.println("=================");

        // 这是上面的完整写法.Action0相当于oncomplete, Action1相当于onNext, Action1的泛型为Throwable时相当于onError
//        observable.subscribe(onNextAction);
//        observable.subscribe(onNextAction, onErrorAction);
//        observable.subscribe(onNextAction, onErrorAction, onCompletedAction);
        Observable.just("hello3", "world3", "rxjava3")
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        System.out.println("观察到改变: " + s);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        System.out.println("步入歧途onError");
                        throwable.printStackTrace();
                    }
                }, new Action0() {
                    @Override
                    public void call() {
                        System.out.println("已完成onCompleted");
                    }
                });
    }

    /**
     * 实现被观察者    方式3: from方式
     * from方式支持两种格式
     * <p>
     * 1. 集合
     * <p>
     * 2.数组
     */
    private static void method3() {
        // 集合
        List<String> list = new ArrayList<>();
        list.add("hello");
        list.add("world");
        list.add("rxjava");
        Observable.from(list)
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        System.out.println("观察到改变: " + s);
                    }
                });

        System.out.println("=================");

        // 数组
        String[] words = {"hello2", "world2", "rxjava2"};
        Observable.from(words)
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        System.out.println("观察到改变: " + s);
                    }
                });
    }

    /**
     * 其他方式:
     * 1. Observable.interval(1, 3, TimeUnit.SECONDS);
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
    void fitler() {
        Observable.just(1, 2, 3, 4)
                .filter(new Func1<Integer, Boolean>() {
                    @Override
                    public Boolean call(Integer integer) {
                        return integer < 3;// 小于3才返回true,于是过滤了3,4
                    }
                })
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        System.out.println("fitler---" + integer);
                    }
                });
    }

    void scan() {
        // 第一次发射得到1，作为结果与2相加；发射得到3，作为结果与3相加
        Observable.just(1, 2, 3, 4, 5)
                .scan(new Func2<Integer, Integer, Integer>() {
                    @Override
                    public Integer call(Integer integer, Integer integer2) {
                        return integer + integer2;
                    }
                })
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        System.out.println("scan---" + integer);
                    }
                });
    }

    void timer() {
        // 3秒后输出 hello world . 该方法使用了android线程.因此不能在main中运行测试
        Observable.timer(3, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        System.out.println("timer--hello world--" + aLong);
                    }
                });
    }
}
