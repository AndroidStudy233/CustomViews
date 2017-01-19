package com.shiqkuangsan.rxandroidmvp.review1;

/**
 * Created by shiqkuangsan on 2017/1/19.
 * <p>
 * ClassName: IWatcher
 * Author: shiqkuangsan
 * Description: 观察者接口,需要提供观察目的方法.就是数据更新后该干嘛
 */
public interface IWatcher {

    void onUpdate(Object object);
}
