package com.shiqkuangsan.rxandroidmvp.review1;

/**
 * Created by shiqkuangsan on 2017/1/19.
 * <p>
 * ClassName: Watcher
 * Author: shiqkuangsan
 * Description: 观察者
 */
public class Watcher implements IWatcher {

    @Override
    public void onUpdate(Object object) {
        if (object == null)
            System.out.println("onUpdate: " + "null");
        else
            System.out.println("onUpdate: " + object.toString());
    }
}
