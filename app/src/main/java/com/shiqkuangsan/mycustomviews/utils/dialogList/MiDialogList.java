package com.shiqkuangsan.mycustomviews.utils.dialogList;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.shiqkuangsan.mycustomviews.R;
import com.shiqkuangsan.mycustomviews.utils.ScreenUtils;

import java.util.ArrayList;

/**
 * 作者：dell or Xiaomi Li
 * 时间： 2018/4/13
 * 内容：一个选择控件  单选和多选
 * 最后修改：
 */

public class MiDialogList<T> {

    public static final int MILIST_DIALOG_BOTTOM = Gravity.BOTTOM;
    public static final int MILIST_DIALOG_CENTER = Gravity.CENTER;
    public static final int MILIST_RETURN_SINGLE = 0x1;
    public static final int MILIST_RETURN_MULTIPLE = 0x2;

    private static Dialog dialog;
    private static TextView titleTv;
    private static RecyclerView mRecyclerView;
    private static LinearLayout bottomLy;
    private static TextView okTv;
    private static TextView cannleTv;
    private static TextView okTopTv;
    private static TextView cannleTopTv;

    private static Context mContext;//上下文
    private static int mReturnType = MILIST_RETURN_SINGLE;//选择类型  多选或者单选
    private static int mGravity = MILIST_DIALOG_BOTTOM;//显示位置
    private ArrayList<T> mDataList = new ArrayList<>();//数据源
    private static OnDialogListCallback mOnDialogListCallback;//回调

    private static boolean mShowTitle = false;
    private static ArrayList<Integer> returnData = new ArrayList<>();//返回值

    /**
     * @param context  上下文
     * @param dataList 数据源：允许参数类型  String类型/实现了MiListInterface接口的bean对象
     */
    public MiDialogList(Context context, ArrayList<T> dataList) {
        mContext = context;
        mDataList.clear();
        returnData.clear();
        mDataList.addAll(dataList);
    }


    public MiDialogList builder() {
        View dialogView = LayoutInflater.from(mContext)
                .inflate(R.layout.dialog_choose_list, null);

        titleTv = (TextView) dialogView.findViewById(R.id.dialog_list_title);
        mRecyclerView = (RecyclerView) dialogView.findViewById(R.id.dialog_list_recycler);
        bottomLy = (LinearLayout) dialogView.findViewById(R.id.dialog_list_ly);
        okTv = (TextView) dialogView.findViewById(R.id.dialog_list_ok);
        cannleTv = (TextView) dialogView.findViewById(R.id.dialog_list_cannle);
        okTopTv = (TextView) dialogView.findViewById(R.id.dialog_list_top_ok);
        cannleTopTv = (TextView) dialogView.findViewById(R.id.dialog_list_top_cannle);

        dialog = new Dialog(mContext, R.style.DialogList);
        dialog.setContentView(dialogView);

        Window window = dialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        //设置对话框的宽度
        lp.width = ScreenUtils.getScreenWidth(mContext);
        //设置对话框的高度
        lp.height = mDataList.size() < 7 ? LinearLayout.LayoutParams.WRAP_CONTENT : (ScreenUtils.getScreenHeight(mContext) / 2);
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.getWindow().setAttributes(lp);
        return this;
    }

    /**
     * 设置标题
     *
     * @param title
     * @return
     */
    public MiDialogList setTitle(String title) {
        mShowTitle = true;
        if ("".equals(title)) {
            titleTv.setText("请选择");
        } else {
            titleTv.setText(title);
        }
        return this;
    }

    /**
     * 设置返回值类型
     *
     * @param returntype MILIST_RETURN_SINGLE：返回一个  MILIST_RETURN_MULTIPLE：返回多个
     * @return
     */
    public MiDialogList setReturnType(int returntype) {
        mReturnType = returntype;
        return this;
    }

    /**
     * 设置显示的位置
     *
     * @param gravity
     * @return
     */
    public MiDialogList setGravity(int gravity) {
        mGravity = gravity;
        return this;
    }

    /**
     * 设置回调
     *
     * @param onDialogListCallback
     * @return
     */
    public MiDialogList setCallBack(OnDialogListCallback onDialogListCallback) {
        mOnDialogListCallback = onDialogListCallback;
        return this;
    }

    public void show() {
        setLayout();
        dialog.show();
    }

    private void setLayout() {
        Window window = dialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        //设置对话框的宽度
        lp.width = mGravity == Gravity.BOTTOM ? ScreenUtils.getScreenWidth(mContext) : ScreenUtils.getScreenWidth(mContext) / 5 * 4;
        //设置对话框的高度
        lp.height = mDataList.size() < 7 ? LinearLayout.LayoutParams.WRAP_CONTENT : (ScreenUtils.getScreenHeight(mContext) / 2);
        dialog.getWindow().setGravity(mGravity);
        dialog.getWindow().setAttributes(lp);

        titleTv.setVisibility(mShowTitle ? View.VISIBLE : View.GONE);
        if(mReturnType == MiDialogList.MILIST_RETURN_MULTIPLE&&mGravity==MILIST_DIALOG_CENTER ){
            bottomLy.setVisibility(View.VISIBLE);
        }else{
            bottomLy.setVisibility(View.GONE);
        }

        if(mReturnType == MiDialogList.MILIST_RETURN_MULTIPLE&&mGravity==MILIST_DIALOG_BOTTOM ){
            okTopTv.setVisibility(View.VISIBLE);
            cannleTopTv.setVisibility(View.VISIBLE);
        }else{
            okTopTv.setVisibility(View.GONE);
            cannleTopTv.setVisibility(View.GONE);
        }


        //设置数据
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
        DialogListAdapter dialogListAdapter = new DialogListAdapter(mContext);
        dialogListAdapter.setList(mDataList, mReturnType);
        mRecyclerView.setAdapter(dialogListAdapter);
        dialogListAdapter.setOnItemClickListener(new DialogListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (mReturnType == MiDialogList.MILIST_RETURN_SINGLE) {
                    returnData.clear();
                    returnData.add(position);
                    if (mOnDialogListCallback != null) {
                        mOnDialogListCallback.onListCallback(returnData);
                    }
                    dialog.dismiss();
                } else {
                    returnData.add(position);
                }
            }
        });

        okTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnDialogListCallback != null) {
                    mOnDialogListCallback.onListCallback(returnData);
                }
                dialog.dismiss();
            }
        });

        cannleTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        okTopTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnDialogListCallback != null) {
                    mOnDialogListCallback.onListCallback(returnData);
                }
                dialog.dismiss();
            }
        });

        cannleTopTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }


    public interface OnDialogListCallback {
        void onListCallback(ArrayList<Integer> dataList);
    }
}
