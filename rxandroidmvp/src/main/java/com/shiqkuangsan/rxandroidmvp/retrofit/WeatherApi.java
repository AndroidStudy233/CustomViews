package com.shiqkuangsan.rxandroidmvp.retrofit;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

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
public interface WeatherApi {
    @GET("/weather_mini")
    Observable<WeatherBean> getCityWeather(@Query("city") String city);
    @GET("/weather_mini")
    Call<WeatherBean> getCityWeatherCall(@Query("city") String city);
}
