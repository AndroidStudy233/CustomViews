package com.shiqkuangsan.rxandroidmvp.review1;

/**
 * Created by shiqkuangsan on 2017/1/19.
 * <p>
 * ClassName: IWatched
 * Author: shiqkuangsan
 * Description: 被观察者接口,一个被观察者应该可以被多个观察者观察.并提供告诉观察者数据更新的方法
 */
public interface IWatched {

    void addWatcher(IWatcher watcher);

    void removeWatcher(IWatcher watcher);

    void notify(Object object);
}
