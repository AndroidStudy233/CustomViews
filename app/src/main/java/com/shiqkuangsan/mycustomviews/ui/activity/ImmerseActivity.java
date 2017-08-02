package com.shiqkuangsan.mycustomviews.ui.activity;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.shiqkuangsan.mycustomviews.ui.activity.immerse.ColorImmerse1Act;
import com.shiqkuangsan.mycustomviews.ui.activity.immerse.ColorImmerse2Act;
import com.shiqkuangsan.mycustomviews.ui.activity.immerse.ImageImmerse1Act;
import com.shiqkuangsan.mycustomviews.ui.activity.immerse.ImageImmerse2Act;

/**
 * Created by shiqkuangsan on 2017/7/27. <p>
 * ClassName: ImmerseActivity <p>
 * Author: shiqkuangsan <p>
 * Description: 新的沉浸式实例页面 <p>
 * 前言: 沉浸式是Android Api19-4.4 以后出现的一种利用状态栏背景更好展示App的方式, 由于4.4的沉浸式是只能实现半透明的样式,
 * 不太好看, 这次就不兼容了. 所以5.0以下的运行效果都一样
 * 但是兼容方法想看的可以去{@link com.shiqkuangsan.mycustomviews.ui.activity.MyImmerseActivity}
 * <p>
 * 这里我们做三种沉浸式(名字自己瞎取的):
 * 1. 颜色沉浸式. 状态栏背景颜色和Toolbar/Actionbar/CustomBar颜色保持一致的效果
 * 2. 图片沉浸式. 状态栏背景包括下面的Toolbar/Actionbar/AppLayout/CustomBar一体采用图片的形式展现
 * 3. 完全沉浸式. 基本处于全屏模式, 但是可以滑动调出状态栏&导航栏.
 */
public class ImmerseActivity extends ListActivity {

    String[] itemTexts = new String[]{
            "1. 着色沉浸式 -> 使用Toolbar",
            "2. 着色沉浸式 -> 自定义Topbar",
            "3. 图片沉浸式 -> 使用Toolbar",
            "4. 图片沉浸式 -> 自定义Topbar",
    };

    Class[] activities = new Class[]{
            ColorImmerse1Act.class,
            ColorImmerse2Act.class,
            ImageImmerse1Act.class,
            ImageImmerse2Act.class,
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setListAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, itemTexts));
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        startActivity(new Intent(this, activities[position]));
    }
}
