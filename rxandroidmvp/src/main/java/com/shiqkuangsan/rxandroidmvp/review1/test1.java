package com.shiqkuangsan.rxandroidmvp.review1;

/**
 * Created by shiqkuangsan on 2017/1/19.
 * <p>
 * ClassName: test1
 * Author: shiqkuangsan
 * Description: 测试类.自定义观察者/被观察者
 */
public class test1 {

    public static void main(String[] args) {
//        Watched watched = new Watched();
//        Watcher watcher1 = new Watcher();
//        Watcher watcher2 = new Watcher();
//        Watcher watcher3 = new Watcher();
//
//        watched.addWatcher(watcher1);
//        watched.addWatcher(watcher2);
//        watched.addWatcher(watcher3);
//
//        watched.notify(null);
//        watched.notify("233");
        
        
        int a=10;
        int b=10;
        method1(a,b);
        System.out.print("a="+a);
        System.out.print("b="+b);
    }
    
    public static void method1(int a,int b){
        int x=100;
        int y=200;
        System.out.print("a="+x);
        System.out.print("b="+y);
        System.exit(0);
    }
}
