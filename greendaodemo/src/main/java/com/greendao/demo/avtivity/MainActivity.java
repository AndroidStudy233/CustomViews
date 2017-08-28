package com.greendao.demo.avtivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.greendao.demo.MyApplication;
import com.greendao.demo.UserAdapter;
import com.greendao.demo.entity.User;
import com.greendaodemo.R;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

import green.DaoSession;
import green.UserDao;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView tvUserName;
    private EditText etName;
    private EditText etAge;
    private EditText etSex;
    private View btnAdd;
    private View btnDelete;
    private View btnUpdata;
    private View btnSelect;
    private View btnSelectAll;
    private String name;
    private String sex;
    private int age;
    private DaoSession daoSession;
    private UserDao userDao;
    private ListView listView;
    private UserAdapter adapter;
    private View translate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvUserName = (TextView) findViewById(R.id.tv_username);
        etName = (EditText) findViewById(R.id.et_name);
        etAge = (EditText) findViewById(R.id.et_age);
        etSex = (EditText) findViewById(R.id.et_sex);
        btnAdd = findViewById(R.id.bt_add);
        btnDelete = findViewById(R.id.bt_delete);
        btnUpdata = findViewById(R.id.bt_updata);
        btnSelect = findViewById(R.id.bt_select);
        btnSelectAll = findViewById(R.id.bt_select_all);
        listView = (ListView) findViewById(R.id.lv);
        translate = findViewById(R.id.translante);
        adapter = new UserAdapter();
        listView.setAdapter(adapter);
        btnAdd.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnUpdata.setOnClickListener(this);
        btnSelect.setOnClickListener(this);
        btnSelectAll .setOnClickListener(this);
        translate.setOnClickListener(this);
        daoSession = MyApplication.getApplication().getDaoSession();
        userDao = daoSession.getUserDao();
//        userDao.delete(); 增删改查
//        userDao.update();
//        userDao.insert();
//        userDao.queryBuilder().where().count();
        //每次进来都会重复插入,因为id自增 ,若id写死，第二次会报id不唯一的错误
//        User user = new User(null, "三比", 38, "中性");
//        userDao.insert(user);
//        User user1 = new User(null, "肥猪三1", 38, "中性");
//        User user2 = new User(null, "肥猪三2", 35, "女");
//        User user3 = new User(null, "肥猪三3", 37, "男");
//        User user4 = new User(null, "肥猪三4", 36, "未知物种");
//        List<User> users = new ArrayList<>();
//        users.add(user1);
//        users.add(user2);
//        users.add(user3);
//        users.add(user4);
//        userDao.insertInTx(users);
////        userDao.queryBuilder().where(UserDao.Properties.UserName.like("%肥猪%")).orderAsc(UserDao.Properties.Id);//like 查询 排序
//        //常用方法用的时候在找吧，貌似功能很强大
//        List<User> list = userDao.queryBuilder().where(UserDao.Properties.UserName.like("%肥猪%")).build().list();
//        //unique 当查出来的数量不止一个时，会报错
////        User unique = userDao.queryBuilder().where(UserDao.Properties.UserName.eq("三比")).build().unique();
////        tvUserName.setText(unique.getUserName()+"---"+unique.getSex()+">>>");
//
//        //多个where    or 联合查询
//        //类似效果的sql 为   select * from User where UserName like '%肥猪%' and (Sex="中性" or (Age in('35','36') and Sex ='未知物种'))
//        QueryBuilder<User> userQueryBuilder = userDao.queryBuilder();
//        //你的查询没有返回你期望的值？这里有2个静态的标识，一个是将sql语句打印出来，一个是将传入QueryBuilder的参数打印出来
//        userQueryBuilder.LOG_SQL = true;
//        userQueryBuilder.LOG_VALUES = true;
//        userQueryBuilder.where(UserDao.Properties.UserName.like("%肥猪%"));
//        userQueryBuilder.or(UserDao.Properties.Sex.eq("%中性%"), userQueryBuilder.and(UserDao.Properties.Age.in("35", "36"), UserDao.Properties.Sex.eq("未知物种")));
//        List<User> list1 = userQueryBuilder.build().list();
//      userQueryBuilder.offset().limit()分页
    }

    @Override
    public void onClick(View v) {
        name = TextUtils.isEmpty(etName.getText().toString())?null:etName.getText().toString();
        age = TextUtils.isEmpty(etAge.getText().toString()) ? 0 : Integer.parseInt(etAge.getText().toString());
        sex = TextUtils.isEmpty(etSex.getText().toString())?null:etSex.getText().toString();
        switch (v.getId()) {
            case R.id.bt_add:
                User user = new User(null, name, age, sex);
                userDao.insert(user);
                Toast.makeText(this, "插入数据库成功", Toast.LENGTH_SHORT).show();
                break;
            case R.id.bt_delete:
                QueryBuilder<User> userQueryBuilder = userDao.queryBuilder();
                if (age != 0) {
                    userQueryBuilder.where(UserDao.Properties.Age.eq(age));
                }
                if (name != null) {
                    userQueryBuilder.where(UserDao.Properties.UserName.eq(name));
                }
                if (sex != null) {
                    userQueryBuilder.where(UserDao.Properties.Sex.eq(sex));
                }
                List<User> list = userQueryBuilder.list();
                if (list != null && list.size() > 0) {
                    userDao.deleteInTx(list);
                    Toast.makeText(this, "删除" + list.size() + "条数据", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "没有匹配到要删除的数据,影响行数为0", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.bt_updata:
                //// TODO: 2017/8/25 再做几个输入框 装新数据就行 
                QueryBuilder<User> userQueryBuilder1 = userDao.queryBuilder();
                if (age != 0) {
                    userQueryBuilder1.where(UserDao.Properties.Age.eq(age));
                }
                if (name != null) {
                    userQueryBuilder1.where(UserDao.Properties.UserName.eq(name));
                }
                if (sex != null) {
                    userQueryBuilder1.where(UserDao.Properties.Sex.eq(sex));
                }
                List<User> list1 = userQueryBuilder1.list();
                if (list1 != null && list1.size() > 0) {
                    userDao.updateInTx(list1);
                    Toast.makeText(this, "影响" + list1.size() + "条数据", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "没有匹配到需要更新的数据,影响行数为0", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.bt_select:
                QueryBuilder<User> userQueryBuilder2 = userDao.queryBuilder();
                if (age != 0) {
                    userQueryBuilder2.where(UserDao.Properties.Age.eq(age));
                }
                if (name != null) {
                    userQueryBuilder2.where(UserDao.Properties.UserName.eq(name));
                }
                if (sex != null) {
                    userQueryBuilder2.where(UserDao.Properties.Sex.eq(sex));
                }
                List<User> list2 = userQueryBuilder2.list();
                adapter.setData(list2);
                break;
            case  R.id.bt_select_all:
                List<User> list3 = userDao.queryBuilder().list();
                adapter.setData(list3);
                break;
            case R.id.translante:
                Intent intent = new Intent(this,TranslateActivity.class);
                startActivity(intent);
                break;
        }
    }
}
