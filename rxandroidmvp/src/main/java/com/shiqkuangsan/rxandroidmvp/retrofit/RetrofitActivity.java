package com.shiqkuangsan.rxandroidmvp.retrofit;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.shiqkuangsan.rxandroidmvp.MyLogUtil;
import com.shiqkuangsan.rxandroidmvp.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/*************************************************
 * <p>类描述：${todo}(用一句话描述该文件做什么)</p>
 * <p>创建人：余志伟</p>
 * <p>创建时间 : 2017/2/9</p>
 * <p>修改人：       </p>
 * <p>修改时间：   </p>
 * <p>修改备注：   </p>
 *
 * @version V3.1
 *********************************/
public class RetrofitActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit);
        Retrofit retrofit = getRetrofit("http://wthrcdn.etouch.cn");
        rxRetrofit(retrofit);
        callRetrofit(retrofit);
        rxRetroMap(retrofit);
        rxRetrofitFlatMap(retrofit);
    }

    public static Retrofit getRetrofit(String baseUrl) {
        return new Retrofit.Builder().baseUrl(baseUrl)
                //增加返回值为String支持
                .addConverterFactory(ScalarsConverterFactory.create())
                        //增加返回值为Gson的支持（返回值是实体类）
                .addConverterFactory(GsonConverterFactory.create())
                        //增加返回值为obserable<T>支持
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    /**
     * @return
     * @desc: rxjava方式回调
     * @author: yzw
     * @modify: 2017/2/9 16:01
     * @param:
     **/
    public void rxRetrofit(Retrofit retrofit) {
        WeatherApi weatherApi = retrofit.create(WeatherApi.class);
        weatherApi.getCityWeather("深圳")//rxjava方式
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<WeatherBean>() {
                    @Override
                    public void call(WeatherBean weatherBean) {
                        MyLogUtil.e("rx回调call");
                    }
                }, new Action1<Throwable>() {   //异常必须加上，不然在无网络会崩溃
                    @Override
                    public void call(Throwable throwable) {
                        MyLogUtil.e("rx回调throwable");
                    }
                });
    }
    /**
     *@desc: rx+reftrofit  返回后在进行 map操作
     *@author: yzw
     *@modify: 2017/2/10 11:38
     *@param: 
     *@return 
     **/
    public void rxRetroMap(Retrofit retrofit){
        WeatherApi weatherApi = retrofit.create(WeatherApi.class);
        weatherApi.getCityWeather("深圳").map(new Func1<WeatherBean, WeatherBean.DataBean>() {
            @Override
            public WeatherBean.DataBean call(WeatherBean weatherBean) {
                return weatherBean.getData();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<WeatherBean.DataBean>() {
                    @Override
                    public void call(WeatherBean.DataBean dataBean) {

                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        MyLogUtil.e("rx map回调throwable");
                    }
                });
    }
    /**
     *@desc: rx+reftrofit  返回后在进行 FlatMap操作
     *@author: yzw
     *@modify: 2017/2/10 11:38
     *@param:
     *@return
     **/
    public void rxRetrofitFlatMap(Retrofit retrofit){
        WeatherApi weatherApi = retrofit.create(WeatherApi.class);
        weatherApi.getCityWeather("深圳")
                .flatMap(new Func1<WeatherBean, Observable<WeatherBean.DataBean>>() {
            @Override
            public Observable<WeatherBean.DataBean> call(WeatherBean weatherBean) {
                //这里一般返回的数据是个集合时才用flatmap
                List<WeatherBean.DataBean> list = new ArrayList<WeatherBean.DataBean>();
                list.add(weatherBean.getData());
                return Observable.from(list);
            }
        }).map(new Func1<WeatherBean.DataBean, String>() {
            @Override
            public String call(WeatherBean.DataBean dataBean) {
                return dataBean.getCity();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        MyLogUtil.e("rxFlatMap-" + s);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        MyLogUtil.e("rx flatmap回调throwable");
                    }
                });
    }
    /**
     * @return
     * @desc: callback方式回调
     * @author: yzw
     * @modify: 2017/2/9 16:00
     * @param:
     **/
    public void callRetrofit(Retrofit retrofit) {
        WeatherApi weatherApi = retrofit.create(WeatherApi.class);
        Call<WeatherBean> cityWeatherCall = weatherApi.getCityWeatherCall("深圳");//callback方式
        cityWeatherCall.enqueue(new Callback<WeatherBean>() {
            @Override
            public void onResponse(Call<WeatherBean> call, Response<WeatherBean> response) {
                MyLogUtil.e("callback-----onResponse");
            }

            @Override
            public void onFailure(Call<WeatherBean> call, Throwable t) {
                MyLogUtil.e("callback---onFailure");
            }
        });
    }
}
