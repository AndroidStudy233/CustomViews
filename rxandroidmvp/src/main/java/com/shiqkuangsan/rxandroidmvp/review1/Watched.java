package com.shiqkuangsan.rxandroidmvp.review1;

import android.text.style.ForegroundColorSpan;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shiqkuangsan on 2017/1/19.
 * <p>
 * ClassName: Watched
 * Author: shiqkuangsan
 * Description: 被观察者
 */
public class Watched implements IWatched {

    private List<IWatcher> list = new ArrayList<>();

    @Override
    public void addWatcher(IWatcher watcher) {
        if (!list.contains(watcher))
            list.add(watcher);
    }

    @Override
    public void removeWatcher(IWatcher watcher) {
        if (list.contains(watcher))
            list.remove(watcher);
    }

    @Override
    public void notify(Object object) {
        if (list.size() > 0)
            for (IWatcher watcher : list) {
                watcher.onUpdate(object);
            }
    }
}
