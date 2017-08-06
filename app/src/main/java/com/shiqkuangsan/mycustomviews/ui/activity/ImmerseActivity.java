package com.shiqkuangsan.mycustomviews.ui.activity;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.shiqkuangsan.mycustomviews.ui.activity.coodinator.DrawerEmmaInfoActivity;
import com.shiqkuangsan.mycustomviews.ui.activity.immerse.CompleteImmerseAct;
import com.shiqkuangsan.mycustomviews.ui.activity.immerse.ToolbarImmerseAct;
import com.shiqkuangsan.mycustomviews.ui.activity.immerse.TopBarImmerseAct;
import com.shiqkuangsan.mycustomviews.ui.activity.immerse.DemoImmerseAct;

/**
 * Created by shiqkuangsan on 2017/7/27. <p>
 * ClassName: ImmerseActivity <p>
 * Author: shiqkuangsan <p>
 * Description: 新的沉浸式实例页面 <p>
 * 前言: 沉浸式是Android Api19-4.4 以后出现的一种利用状态栏背景更好展示App的方式, 由于4.4的沉浸式是只能实现半透明的样式,
 * 不太好看, 这次就不兼容了. 所以5.0以下的运行效果都一样
 * 但是兼容方法想看的可以去{@link MyImmerseActivity}
 * 另外针对DrawerLayout的操作可以去看{@link DrawerEmmaInfoActivity}
 * <p>
 * 这里我们做三种沉浸式(名字自己瞎取的):
 * 1. 着色沉浸(纯色). 自定义状态栏、Toolbar/Actionbar/Topbar、导航栏的颜色
 * 2. (半)透明沉浸(背景图片). 顾名思义.
 * 3. 完全沉浸式. 基本处于全屏模式, 但是可以滑动调出状态栏&导航栏.
 */
public class ImmerseActivity extends ListActivity {

    String[] itemTexts = new String[]{
            "1. 沉浸式 -> 使用Toolbar",
            "2. 沉浸式 -> 自定义Topbar",
            "3. 沉浸式 -> 完全沉浸",
            "4. Demo沉浸式 -> 使用Toolbar",
    };

    Class[] activities = new Class[]{
            ToolbarImmerseAct.class,
            TopBarImmerseAct.class,
            CompleteImmerseAct.class,
            DemoImmerseAct.class,
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
