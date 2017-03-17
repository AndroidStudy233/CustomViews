package com.shiqkuangsan.mycustomviews.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;

import com.shiqkuangsan.mycustomviews.R;
import com.shiqkuangsan.mycustomviews.ui.custom.CommentView;

/*************************************************
 * <p>版权所有：2016-深圳市赛为安全技术服务有限公司</p>
 * <p>项目名称：安全眼</p>
 * <p/>
 * <p>类描述：${todo}(用一句话描述该文件做什么)</p>
 * <p>创建人：余志伟</p>
 * <p>创建时间 : 2017/3/17</p>
 * <p>修改人：       </p>
 * <p>修改时间：   </p>
 * <p>修改备注：   </p>
 *
 * @version V3.1
 *********************************/
public class AddDeleteActivity extends AppCompatActivity implements CommentView.onCommentViewClickListener {
    private LinearLayout mRootView;
    private CommentView commentView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adddelete);
        mRootView = (LinearLayout) findViewById(R.id.rootview);
        commentView = (CommentView) findViewById(R.id.comment);
        commentView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                commentView.setOnCommentViewClickListener(AddDeleteActivity.this);
            }
        });
    }

    @Override
    public void onDelete(CommentView commentView) {
        mRootView.removeView(commentView);
    }

    @Override
    public void onAdd() {
        CommentView commentView = new CommentView(AddDeleteActivity.this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.topMargin = 10;
        layoutParams.gravity = Gravity.CENTER;
        commentView.setLayoutParams(layoutParams);
        commentView.setPosition(mRootView.getChildCount() + 1);
        commentView.setOnCommentViewClickListener(this);
        mRootView.addView(commentView);
    }
}