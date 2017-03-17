package com.shiqkuangsan.rxandroidmvp.retrofit;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/*************************************************
 * <p>版权所有：2016-深圳市赛为安全技术服务有限公司</p>
 * <p>项目名称：安全眼</p>
 * <p/>
 * <p>创建人：余志伟</p>
 * <p>创建时间 : 2017/2/9</p>
 * <p>修改人：       </p>
 * <p>修改时间：   </p>
 * <p>修改备注：   </p>
 *
 * @version V3.1
 *********************************/
public class RetrofitTest1 {
    public static  void main(String[] args){
        Retrofit retrofit = getRetrofit("http://wthrcdn.etouch.cn");
        WeatherApi weatherApi = retrofit.create(WeatherApi.class);
        weatherApi.getCityWeather("深圳")
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.computation())
                .subscribe(new Action1<WeatherBean>() {
                    @Override
                    public void call(WeatherBean weatherBean) {
                        System.out.print("onNext");
                    }
                });
        

    }
    public static  Retrofit getRetrofit(String baseUrl){
      return  new Retrofit.Builder().baseUrl(baseUrl)
              //增加返回值为String支持
              .addConverterFactory(ScalarsConverterFactory.create())
              //增加返回值为Gson的支持（返回值是实体类）
              .addConverterFactory(GsonConverterFactory.create())
              //增加返回值为obserable<T>支持
              .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
              .build();
    }
}
