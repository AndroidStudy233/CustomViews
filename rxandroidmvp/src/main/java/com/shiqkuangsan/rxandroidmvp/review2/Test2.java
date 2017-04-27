package com.shiqkuangsan.rxandroidmvp.review2;

/**
 * Created by shiqkuangsan on 2017/1/19.
 * <p>
 * ClassName: Test2
 * Author: shiqkuangsan
 * Description: 测试类,使用java提供的Observable和Observer接口实现
 */
public class Test2 {

    public static void main(String[] args) {
        SimpleObservable observable = new SimpleObservable();
        SimpleObserver observer = new SimpleObserver(observable);

        for (int i = 1; i < 101; i++) {
            observable.setProgress(i);
            if (i == 100)
                observable.setFinish(true);
        }

    }

}
