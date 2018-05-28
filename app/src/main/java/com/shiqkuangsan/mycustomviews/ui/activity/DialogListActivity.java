package com.shiqkuangsan.mycustomviews.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.shiqkuangsan.mycustomviews.R;
import com.shiqkuangsan.mycustomviews.base.BaseActivity;
import com.shiqkuangsan.mycustomviews.bean.DialogTestBean;
import com.shiqkuangsan.mycustomviews.utils.dialogList.MiDialogList;


import java.util.ArrayList;

/**
 * 作者：dell or Xiaomi Li
 * 时间： 2018/5/28
 * 内容：一个弹出选择框，可单选多选，入参可为String/任意Bean(需实现MiListInterface接口)
 * 最后修改：
 */
public class DialogListActivity extends BaseActivity implements View.OnClickListener {

    private android.widget.Button but1;
    private android.widget.Button but2;
    private android.widget.Button but3;
    private android.widget.Button but4;
    private android.widget.TextView tv1;

    ArrayList<DialogTestBean> mList = new ArrayList<>();
    ArrayList<String> mStringList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_list);
        this.tv1 = (TextView) findViewById(R.id.tv1);
        this.but4 = (Button) findViewById(R.id.but4);
        this.but3 = (Button) findViewById(R.id.but3);
        this.but2 = (Button) findViewById(R.id.but2);
        this.but1 = (Button) findViewById(R.id.but1);

        but4.setOnClickListener(this);
        but3.setOnClickListener(this);
        but2.setOnClickListener(this);
        but1.setOnClickListener(this);

        initData();
    }

    private void initData() {
        for (int i = 0; i < 10; i++) {
            mStringList.add("我是小米" + i);
            mList.add(new DialogTestBean("DialogTestBean" + i, "男"));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.but1:
                new MiDialogList<String>(DialogListActivity.this, mStringList)
                        .builder()
                        .setTitle("请选择")
                        .setGravity(MiDialogList.MILIST_DIALOG_CENTER)
                        .setReturnType(MiDialogList.MILIST_RETURN_SINGLE)
                        .setCallBack(new MiDialogList.OnDialogListCallback() {
                            @Override
                            public void onListCallback(ArrayList<Integer> dataList) {
                                tv1.setText("结果：" + new Gson().toJson(mStringList.get(dataList.get(0))));
                            }
                        })
                        .show();
                break;
            case R.id.but2:
                new MiDialogList<String>(DialogListActivity.this, mStringList)
                        .builder()
                        .setTitle("请选择")
                        .setGravity(MiDialogList.MILIST_DIALOG_BOTTOM)
                        .setReturnType(MiDialogList.MILIST_RETURN_MULTIPLE)
                        .setCallBack(new MiDialogList.OnDialogListCallback() {
                            @Override
                            public void onListCallback(ArrayList<Integer> dataList) {
                                String a = "多选  底部--结果：";
                                int size = dataList.size();
                                for (int i = 0; i < size; i++) {
                                    a = a + new Gson().toJson(mStringList.get(dataList.get(i)));
                                }
                                tv1.setText(a);
                            }
                        })
                        .show();
                break;
            case R.id.but3:
                new MiDialogList<String>(DialogListActivity.this, mStringList)
                        .builder()
                        .setTitle("请选择")
                        .setGravity(MiDialogList.MILIST_DIALOG_CENTER)
                        .setReturnType(MiDialogList.MILIST_RETURN_MULTIPLE)
                        .setCallBack(new MiDialogList.OnDialogListCallback() {
                            @Override
                            public void onListCallback(ArrayList<Integer> dataList) {
                                String a = "多选  中间--结果：";
                                int size = dataList.size();
                                for (int i = 0; i < size; i++) {
                                    a = a + new Gson().toJson(mStringList.get(dataList.get(i)));
                                }
                                tv1.setText(a);
                            }
                        })
                        .show();
                break;
            case R.id.but4:
                new MiDialogList<DialogTestBean>(DialogListActivity.this, mList)
                        .builder()
                        .setTitle("请选择")
                        .setGravity(MiDialogList.MILIST_DIALOG_CENTER)
                        .setReturnType(MiDialogList.MILIST_RETURN_MULTIPLE)
                        .setCallBack(new MiDialogList.OnDialogListCallback() {
                            @Override
                            public void onListCallback(ArrayList<Integer> dataList) {
                                String a = "Object--结果：";
                                int size = dataList.size();
                                for (int i = 0; i < size; i++) {
                                    a = a + new Gson().toJson(mList.get(dataList.get(i)));
                                }
                                tv1.setText(a);
                            }
                        })
                        .show();
                break;
            default:
                break;
        }
    }
}
