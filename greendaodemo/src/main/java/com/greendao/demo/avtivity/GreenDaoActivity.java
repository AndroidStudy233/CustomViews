package com.greendao.demo.avtivity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.greendao.demo.entity.GreenDaoBean;
import com.greendaodemo.R;

import java.util.ArrayList;
import java.util.List;


import green.GreenDaoBeanDao;

import green.greendaoUtils.DBManager;

/**
 * 作者：dell or Xiaomi Li
 * 时间： 2018/2/26
 * 内容：数据库的记本使用和数据库升级功能
 * 最后修改：
 * 使用方法：数据库升级时，要把所有的表都写在DBOpenHelper.onUpgrade()方法中，然后去修改build文件中的数据库版本号
 * 可通过对TestBean中添加一个字段的操作来验证数据库升级结果
 * 这里数据库版本不可逆哦！！！！
 */

public class GreenDaoActivity extends AppCompatActivity {

    private android.widget.Button greendaoinsert;
    private android.widget.Button greendaodelete;
    private android.widget.Button greendaodeleteall;
    private android.widget.Button greendaoquery;
    private android.widget.Button greendaoasc;
    private android.widget.Button greendaodesc;
    private android.widget.Button greendaoqueryor;
    private android.widget.Button greendaoqueryand;
    private android.widget.Button greendaoquerywhere;
    private android.widget.TextView greendaotv;
    private android.widget.TextView versiontv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_greendao);
        initView();
    }


    private GreenDaoBeanDao getDao() {
        return DBManager.getInstance().getDaoSession().getGreenDaoBeanDao();
    }

    private void initView() {
        this.greendaotv = (TextView) findViewById(R.id.greendao_tv);
        this.versiontv = (TextView) findViewById(R.id.version_tv);
        this.greendaoqueryand = (Button) findViewById(R.id.greendao_query_and);
        this.greendaoqueryor = (Button) findViewById(R.id.greendao_query_or);
        this.greendaodesc = (Button) findViewById(R.id.greendao_desc);
        this.greendaoasc = (Button) findViewById(R.id.greendao_asc);
        this.greendaoquery = (Button) findViewById(R.id.greendao_query);
        this.greendaoquerywhere = (Button) findViewById(R.id.greendao_query_where);
        this.greendaodeleteall = (Button) findViewById(R.id.greendao_delete_all);
        this.greendaodelete = (Button) findViewById(R.id.greendao_delete);
        this.greendaoinsert = (Button) findViewById(R.id.greendao_insert);

        versiontv.setText("当前数据库版本号：" + DBManager.getInstance().getDatabase().getVersion() + "可通过对TestBean中字段的操作来验证数据库升级结果");

        greendaoinsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDao().deleteAll();//删除全部

                GreenDaoBean mGreenDao1 = new GreenDaoBean();
                mGreenDao1.setGreenDaoId(null);//主键自增，设置为null就可以了
                mGreenDao1.setGreenDaoIndex(1);
                mGreenDao1.setGreenDaoName("我是1");

                GreenDaoBean mGreenDao2 = new GreenDaoBean();
                mGreenDao2.setGreenDaoId(null);//主键自增，设置为null就可以了
                mGreenDao2.setGreenDaoIndex(2);
                mGreenDao2.setGreenDaoName("我是2");

                GreenDaoBean mGreenDao3 = new GreenDaoBean();
                mGreenDao3.setGreenDaoId(null);//主键自增，设置为null就可以了
                mGreenDao3.setGreenDaoIndex(3);
                mGreenDao3.setGreenDaoName("我是3");

                GreenDaoBean mGreenDao4 = new GreenDaoBean();
                mGreenDao4.setGreenDaoId(null);//主键自增，设置为null就可以了
                mGreenDao4.setGreenDaoIndex(4);
                mGreenDao4.setGreenDaoName("我是4");

                GreenDaoBean mGreenDao5 = new GreenDaoBean();
                mGreenDao5.setGreenDaoId(null);//主键自增，设置为null就可以了
                mGreenDao5.setGreenDaoIndex(5);
                mGreenDao5.setGreenDaoName("我是5");

                GreenDaoBean mGreenDao6 = new GreenDaoBean();
                mGreenDao6.setGreenDaoId(null);//主键自增，设置为null就可以了
                mGreenDao6.setGreenDaoIndex(6);
                mGreenDao6.setGreenDaoName("我是6");

                GreenDaoBean mGreenDao7 = new GreenDaoBean();
                mGreenDao7.setGreenDaoId(null);//主键自增，设置为null就可以了
                mGreenDao7.setGreenDaoIndex(6);
                mGreenDao7.setGreenDaoName("我是假的6");

                ArrayList<GreenDaoBean> mList = new ArrayList<>();
                mList.add(mGreenDao2);
                mList.add(mGreenDao3);
                mList.add(mGreenDao4);
                mList.add(mGreenDao5);
                mList.add(mGreenDao6);
                mList.add(mGreenDao7);
                getDao().insert(mGreenDao1);//单个插入
                getDao().insertInTx(mList);//批量插入

                greendaotv.setText("插入完成");
            }
        });


        greendaodelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GreenDaoBean unique = getDao().queryBuilder().where(GreenDaoBeanDao.Properties.GreenDaoIndex.eq(1)).unique();
                GreenDaoBean unique1 = getDao().queryBuilder().where(GreenDaoBeanDao.Properties.GreenDaoIndex.eq(3)).unique();
                if (unique != null) {
                    getDao().delete(unique);//删除某一个
                }
                if (unique1 != null) {
                    getDao().deleteByKey(unique1.getGreenDaoId());//通过主键删除
                }
                if (unique == null || unique1 == null) {
                    greendaotv.setText("没有数据，请先插入");
                } else {
                    greendaotv.setText("删除完成");
                }
            }
        });

        greendaodeleteall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDao().deleteAll();//删除全部
                greendaotv.setText("全部删除完成");
            }
        });


        greendaoquery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<GreenDaoBean> greenDaoBeans = getDao().loadAll();
                if (greenDaoBeans.size() != 0) {
                    GreenDaoBean load = getDao().load(greenDaoBeans.get(0).getGreenDaoId());
                    greendaotv.setText("查询完成全部：" + greenDaoBeans.toString() + "按主键查询：" + load.toString());
                } else {
                    greendaotv.setText("查询完成全部：" + greenDaoBeans.toString());
                }

            }
        });

        greendaoasc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<GreenDaoBean> list = getDao().queryBuilder().orderAsc(GreenDaoBeanDao.Properties.GreenDaoIndex).list();
                if (list != null) {
                    greendaotv.setText("升序查询：" + list.toString());
                } else {
                    greendaotv.setText("升序查询：没有这个对象");
                }
            }
        });


        greendaodesc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<GreenDaoBean> list = getDao().queryBuilder().orderDesc(GreenDaoBeanDao.Properties.GreenDaoIndex).list();
                if (list != null) {
                    greendaotv.setText("降序查询：" + list.toString());
                } else {
                    greendaotv.setText("降序查询：没有这个对象");
                }
            }
        });


        greendaoqueryor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<GreenDaoBean> list = getDao().queryBuilder().
                        whereOr(GreenDaoBeanDao.Properties.GreenDaoIndex.eq(6), GreenDaoBeanDao.Properties.GreenDaoIndex.eq(5)).
                        list();

                if (list != null) {
                    greendaotv.setText("and查询：" + list.toString());
                } else {
                    greendaotv.setText("and查询：没有这个对象");
                }
            }
        });


        greendaoqueryand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<GreenDaoBean> list = getDao().queryBuilder().
                        where(GreenDaoBeanDao.Properties.GreenDaoIndex.eq(6)).
                        where(GreenDaoBeanDao.Properties.GreenDaoName.eq("我是假的6")).
                        list();

                if (list != null) {
                    greendaotv.setText("and查询：" + list.toString());
                } else {
                    greendaotv.setText("and查询：没有这个对象");
                }
            }
        });


        greendaoquerywhere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GreenDaoBean unique = getDao().queryBuilder().where(GreenDaoBeanDao.Properties.GreenDaoIndex.eq(4)).unique();
                if (unique != null) {
                    greendaotv.setText("查询完成：" + unique.toString());
                } else {
                    greendaotv.setText("查询完成：没有这个对象");
                }
            }
        });
    }
}
