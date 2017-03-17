package com.shiqkuangsan.mycustomviews.ui.custom;

import android.content.Context;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shiqkuangsan.mycustomviews.R;

/*************************************************
 * <p>版权所有：2016-深圳市赛为安全技术服务有限公司</p>
 * <p>项目名称：安全眼</p>
 * <p/>
 * <p>创建人：余志伟</p>
 * <p>创建时间 : 2017/3/17</p>
 * <p>修改人：       </p>
 * <p>修改时间：   </p>
 * <p>修改备注：   </p>
 *
 * @version V3.1
 *********************************/
public class CommentView extends LinearLayout {
    private View commentView;
    private EditText mEditteView;
    private TextView mBtnView;
    private boolean isAdd = true; //mEditteView内容 为空时视为add
    private onCommentViewClickListener onCommentViewClickListener;
    private int position;

    public CommentView(Context context) {
        super(context);
        init(context);
    }

    public CommentView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CommentView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void init(Context context) {
        commentView = LayoutInflater.from(context).inflate(R.layout.layout_custom_comment, null);
        mEditteView = (EditText) commentView.findViewById(R.id.et_comment_content);
        mBtnView = (TextView) commentView.findViewById(R.id.btn_comment_todo);
        addView(commentView);
        setGravity(Gravity.CENTER);
        initClick();
    }

    public void initClick() {
        mBtnView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //删除  添加
                if (isAdd) {
                    onCommentViewClickListener.onAdd();
                } else {
                    onCommentViewClickListener.onDelete(CommentView.this);
                }
            }
        });
        mEditteView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int length = s.length();
                if (length > 0) {
                    isAdd = false;
                } else {
                    isAdd = true;
                }
                setText(isAdd);

            }
        });
    }

    public void setText(boolean isAdd) {
        if (isAdd) {
            mBtnView.setTextColor(getResources().getColor(R.color.blue));
            mBtnView.setText("增加");
        } else {
            mBtnView.setTextColor(Color.GRAY);
            mBtnView.setText("删除");
        }
    }

    public void setOnCommentViewClickListener(onCommentViewClickListener onCommentViewClickListener) {
        this.onCommentViewClickListener = onCommentViewClickListener;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    /*************************************************
     * <p>版权所有：2016-深圳市赛为安全技术服务有限公司</p>
     * <p>项目名称：安全眼</p>
     * <p/>
     * <p>创建人：余志伟</p>
     * <p>创建时间 : 2017/3/17</p>
     * <p>修改人：       </p>
     * <p>修改时间：   </p>
     * <p>修改备注：   </p>
     *
     * @version V3.1
     *********************************/
    public interface onCommentViewClickListener {
        void onDelete(CommentView commentView);
        void onAdd();
    }
}
