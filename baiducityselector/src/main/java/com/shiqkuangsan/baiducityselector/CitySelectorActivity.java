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

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.Poi;
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
 * 1. 由于该module内部的定位功能使用了百度地图,需要开发者申请百度地图的appKey(原生的定位就不加了)
 * 该key的申请主要就是SHA1值(需要用到keystore)的获取和包名(你的要运行的module包名).详参见百度地图开放平台
 * <p>
 * 2. 获取key之后,在你的应用清单文件中配置该activity(CitySelectorActivity,记得主题使用NoActionBar的
 * 因为我里面使用了Toolbar.),配置百度地图的service和meta-data,记住和activity是同级的.还有就是权限!!!
 * <service android:name="com.baidu.location.f"
 * android:enabled="true"
 * android:process=":remote"/>
 * <meta-data
 * android:name="com.baidu.lbsapi.API_KEY"
 * android:value="你申请的appkey" />
 * <p>
 * 3. 添加该module的依赖,调用的时候直接正常Intent启动过来就行了
 * <p>
 * 4. 城市的选择回调在onCitySelected()方法中,就是我吐司的地方.自行改成你需要的操作就行了.我这里的回调
 * 给的是城市名,本来是打算给City对象的.算了就这样吧.
 * 最后我想说有时候一直定位中...没找到关于定位sdkTimeout的回调接口
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
    // 百度地图相关
    public LocationClient baiduClient = null;
    public BDLocationListener myListener = new MyLocationListener();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_list);

        initToolbar();
        initData();
        initView();
        initBaiduLocation();
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
        if (baiduClient != null) {
            if (baiduClient.isStarted()) {
                baiduClient.stop();
            }
            mCityAdapter.updateLocateState(LocateState.LOCATING, null);
            baiduClient.start();
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
     * 初始化百度定位相关
     */
    private void initBaiduLocation() {
        baiduClient = new LocationClient(getApplicationContext());
        baiduClient.registerLocationListener(myListener);

        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy
        );//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        int span = 0;
        option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(true);//可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤GPS仿真结果，默认需要
        option.setTimeOut(10000);
        baiduClient.setLocOption(option);

//        baiduClient.start();
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
        if (baiduClient != null)
            baiduClient.stop();
    }


    class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {

            switch (location.getLocType()) {
                // GPS定位
                case BDLocation.TypeGpsLocation:
                    setupLocateInfo(location);
                    break;

                // WIFI定位
                case BDLocation.TypeNetWorkLocation:
                    // 可以获取运营商
                    location.getOperators();
                    setupLocateInfo(location);
                    break;

                // 离线定位
                case BDLocation.TypeOffLineLocation:
                    setupLocateInfo(location);
                    break;

                // 服务器error
                case BDLocation.TypeServerError:
                    mCityAdapter.updateLocateState(LocateState.FAILED, null);
                    break;
                // 网络异常
                case BDLocation.TypeNetWorkException:
                    mCityAdapter.updateLocateState(LocateState.FAILED, null);
                    break;
                // 无法定位(可能飞行模式)
                case BDLocation.TypeCriteriaException:
                    mCityAdapter.updateLocateState(LocateState.FAILED, null);
                    break;
            }

        }
    }

    /**
     * 设置定位信息.更新状态
     *
     * @param location BDLocation
     */
    private void setupLocateInfo(BDLocation location) {

        mCityAdapter.updateLocateState(LocateState.SUCCESS, location.getCity());

        // 地址描述
        MyLogUtil.d("百度Addr: " + location.getAddrStr());
        // 位置信息其他描述
        MyLogUtil.d("百度Describe: " + location.getLocationDescribe());
        // POI数据
        List<Poi> list = location.getPoiList();
        if (list != null) {
            for (Poi p : list) {
                String s = p.getId() + " " + p.getName() + " " + p.getRank();
                MyLogUtil.d("百度poi: " + s);
            }
        }
    }

}
