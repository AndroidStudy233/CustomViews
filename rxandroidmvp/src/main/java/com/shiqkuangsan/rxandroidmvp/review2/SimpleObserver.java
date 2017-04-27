package com.shiqkuangsan.rxandroidmvp.review2;

import android.icu.util.IslamicCalendar;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by shiqkuangsan on 2017/1/19.
 * <p>
 * ClassName: SimpleObserver
 * Author: shiqkuangsan
 * Description: java提供的观察者
 */
public class SimpleObserver implements Observer {

    // 暂且只想观察SimpleObservable对象
    public SimpleObserver(SimpleObservable observable) {
        if (observable != null)
            observable.addObserver(this);
    }

    @Override
    public void update(Observable o, Object arg) {
        // 只观察SimpleObservable
        SimpleObservable observable = (SimpleObservable) o;
        if (observable.isFinish())
            System.out.println("滴滴: " + observable.getProgress() + "% --- " + observable.isFinish());
        else
            System.out.println(observable.getProgress() + "%");
    }
}
