package com.shiqkuangsan.baiducityselector;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;

import com.shiqkuangsan.baiducityselector.adapter.CityListAdapter;
import com.shiqkuangsan.baiducityselector.adapter.ResultListAdapter;
import com.shiqkuangsan.baiducityselector.bean.City;
import com.shiqkuangsan.baiducityselector.bean.LocateState;
import com.shiqkuangsan.baiducityselector.custom.PermissionSettingsDialog;
import com.shiqkuangsan.baiducityselector.custom.SideLetterBar;
import com.shiqkuangsan.baiducityselector.db.DBManager;
import com.shiqkuangsan.baiducityselector.utils.MyLogUtil;
import com.shiqkuangsan.baiducityselector.utils.ToastUtil;

import java.util.List;

/**
 * 城市选择页面
 * <p>
 * 1. 由于该module内部的定位功能使用了高德地图定位,需要开发者申请高德地图的appKey(原生的定位就不加了)
 * 该key的申请主要就是SHA1值(需要用到keystore)的获取和包名(你的要运行的module包名).详参见高德地图开放平台
 * <p>
 * 2. 获取key之后,在你的应用清单文件中配置该activity(CitySelectorActivity,记得主题使用NoActionBar的
 * 因为我里面使用了Toolbar.),配置高德地图的service和meta-data,记住和activity是同级的.还有就是权限!!!
 * <service android:name="com.amap.api.location.APSService" />
 * <meta-data
 * android:name="com.amap.api.v2.apikey"
 * android:value="f43853597dc1380b67ea401072fd44e5" />
 * <p>
 * 3. 添加该module的依赖,调用的时候直接正常Intent启动过来就行了
 * <p>
 * 4. 城市的选择回调在onCitySelected()方法中,就是我吐司的地方.自行改成你需要的操作就行了.我这里的回调
 * 给的是城市名,本来是打算给City对象的.算了就这样吧.
 * <p>
 * <p>
 * 另外提供一条根据坐标查询城市的接口
 * http://gc.ditu.aliyun.com/regeocoding?l=32.6,-119.99&type=010
 */
public class CitySelectorActivity extends AppCompatActivity implements View.OnClickListener {
    /**
     * 权限请求码
     */
    public static final int CODE_PERMISSION_LOCATION = 2333;
    /**
     * 没有权限打开设置界面请求码
     */
    public static final int CODE_START_SETTINGS = 2334;

    private ListView mListView;
    private ListView mResultListView;
    private SideLetterBar mLetterBar;
    private EditText searchBox;
    private ImageView clearBtn;
    private ViewGroup emptyView;
    private CityListAdapter mCityAdapter;
    private ResultListAdapter mResultAdapter;
    private List<City> mAllCities;
    private DBManager dbManager;
    private Toolbar toolbar;
    // 高德地图相关
    private AMapLocationClient gaodeClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_list);

        initToolbar();
        initData();
        initView();
        initGaodeLocation();
    }


    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar_citypicker);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setBackgroundColor(getResources().getColor(R.color.titleblue));
        toolbar.setTitleTextColor(0xffffffff);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

    private void initData() {
        dbManager = new DBManager(this);
        dbManager.copyDBFile();
        mAllCities = dbManager.getAllCities();
        mCityAdapter = new CityListAdapter(this, mAllCities);
        mCityAdapter.setOnCityClickListener(new CityListAdapter.OnCityClickListener() {
            @Override
            public void onCityClick(String name) {
                onCitySelected(name);
            }

            @Override
            public void onLocateClick() {
                checkLocationPermission();
            }
        });

        mResultAdapter = new ResultListAdapter(this, null);
    }

    private void initView() {
        mListView = (ListView) findViewById(R.id.listview_all_city);
        mListView.setAdapter(mCityAdapter);

        TextView overlay = (TextView) findViewById(R.id.tv_letter_overlay);
        mLetterBar = (SideLetterBar) findViewById(R.id.side_letter_bar);
        mLetterBar.setOverlay(overlay);
        mLetterBar.setOnLetterChangedListener(new SideLetterBar.OnLetterChangedListener() {
            @Override
            public void onLetterChanged(String letter) {
                int position = mCityAdapter.getLetterPosition(letter);
                mListView.setSelection(position);
            }
        });

        searchBox = (EditText) findViewById(R.id.et_search);
        searchBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String keyword = s.toString();
                if (TextUtils.isEmpty(keyword)) {
                    clearBtn.setVisibility(View.GONE);
                    emptyView.setVisibility(View.GONE);
                    mResultListView.setVisibility(View.GONE);
                } else {
                    clearBtn.setVisibility(View.VISIBLE);
                    mResultListView.setVisibility(View.VISIBLE);
                    List<City> result = dbManager.searchCity(keyword);
                    if (result == null || result.size() == 0) {
                        emptyView.setVisibility(View.VISIBLE);
                    } else {
                        emptyView.setVisibility(View.GONE);
                        mResultAdapter.changeData(result);
                    }
                }
            }
        });

        emptyView = (ViewGroup) findViewById(R.id.empty_view);
        mResultListView = (ListView) findViewById(R.id.listview_search_result);
        mResultListView.setAdapter(mResultAdapter);
        mResultListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onCitySelected(mResultAdapter.getItem(position).getName());
            }
        });

        clearBtn = (ImageView) findViewById(R.id.iv_search_clear);
        clearBtn.setOnClickListener(this);
    }

    /**
     * 百度定位需要定位权限,6.0动态申请
     */
    public void checkLocationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
                    && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
                locate();
            else {
                // 该方法在用户上次拒绝后调用,因为已经拒绝了这次你还要申请授权你得给用户解释一波 在6.0之前的版本永远返回的是fasle
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                    new PermissionSettingsDialog.Builder(this, "定位功能需要您允许我们获取您的定位权限,否则将无法使用定位功能~")
                            .setTitle("权限提醒")
                            .setPositiveButton("权限设置")
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    mCityAdapter.updateLocateState(LocateState.FAILED, null);
                                }
                            })
                            .setRequestCode(CODE_START_SETTINGS)
                            .build()
                            .show();
                } else
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION}, CODE_PERMISSION_LOCATION);
            }
            // 6.0以下直接百度定位
        } else
            locate();
    }

    /**
     * 重新定位
     */
    private void locate() {
        if (gaodeClient != null) {
            if (gaodeClient.isStarted()) {
                gaodeClient.stopLocation();
            }
            mCityAdapter.updateLocateState(LocateState.LOCATING, null);
            gaodeClient.startLocation();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // 考虑到这儿涉及到两条权限的问题写了这么多判断,感觉冗余
        if (requestCode == CODE_PERMISSION_LOCATION)
            if (grantResults.length > 0) {
                if (grantResults.length > 1) {
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED
                            || grantResults[1] == PackageManager.PERMISSION_GRANTED)
                        locate();
                    if (grantResults[0] != PackageManager.PERMISSION_GRANTED
                            && grantResults[1] != PackageManager.PERMISSION_GRANTED)
                        mCityAdapter.updateLocateState(LocateState.FAILED, null);
                } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    locate();
                else
                    mCityAdapter.updateLocateState(LocateState.FAILED, null);
            } else
                mCityAdapter.updateLocateState(LocateState.FAILED, null);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CODE_START_SETTINGS) {
            // 用户从设置页面返回.
            locate();
        }
    }

    /**
     * 初始化高德地图定位
     */
    private void initGaodeLocation() {
        gaodeClient = new AMapLocationClient(this);
        AMapLocationClientOption option = new AMapLocationClientOption();
        option.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        option.setOnceLocation(true);// 设置只定位一次
        option.setNeedAddress(true);// 设置返回地址信息
        option.setHttpTimeOut(20000);// 定位超时
//        option.setInterval(20000);// 设置定位间隔,默认为2000ms，最低1000ms。
        gaodeClient.setLocationListener(new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                if (aMapLocation != null) {
                    if (aMapLocation.getErrorCode() == 0) {
                        String city = aMapLocation.getCity();
                        String district = aMapLocation.getDistrict();
                        mCityAdapter.updateLocateState(LocateState.SUCCESS, city);
                        MyLogUtil.d("高德city: " + city);
                        MyLogUtil.d("高德district: " + district);
                    } else {
                        //定位失败
                        mCityAdapter.updateLocateState(LocateState.FAILED, null);
                        MyLogUtil.e("高德Error, ErrCode:" + aMapLocation.getErrorCode()
                                + ", errInfo:" + aMapLocation.getErrorInfo());
                    }
                }
            }
        });
        gaodeClient.setLocationOption(option);

//        gaodeClient.startLocation();
        checkLocationPermission();
    }

    /**
     * 选择城市的回调
     *
     * @param city 回调的城市名
     */
    private void onCitySelected(String city) {
        /*
            改成你需要处理的操作
        */
        ToastUtil.showToast(this, "点击的城市：" + city);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.iv_search_clear) {
            searchBox.setText("");
            clearBtn.setVisibility(View.GONE);
            emptyView.setVisibility(View.GONE);
            mResultListView.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (gaodeClient != null)
            gaodeClient.stopLocation();
    }

}
