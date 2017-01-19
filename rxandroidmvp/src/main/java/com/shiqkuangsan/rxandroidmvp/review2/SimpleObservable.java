package com.shiqkuangsan.rxandroidmvp.review2;

import java.util.Observable;

/**
 * Created by shiqkuangsan on 2017/1/19.
 * <p>
 * ClassName: SimpleObservable
 * Author: shiqkuangsan
 * Description: 使用java提供的被观察者
 */
public class SimpleObservable extends Observable {

    private int progress;
    private boolean isFinish;

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        // 数据改变并通知观察者
        this.progress = progress;
        setChanged();
        notifyObservers();
    }

    public boolean isFinish() {
        return isFinish;
    }

    public void setFinish(boolean finish) {
        isFinish = finish;
        setChanged();
        notifyObservers();
    }
}
