package com.shiqkuangsan.mycustomviews.bean;

/**
 * Created by shiqkuangsan on 2016/9/24.
 */


import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;
import org.xutils.http.annotation.HttpResponse;

/**
 * 省数据的bean类,添加注解做json解析封装,便可在CallBack泛型直接返回集合
 */
@HttpResponse(parser = JsonResponseParser.class)
@Table(name = "province")
public class Province {

    @Column(name = "_id", isId = true, autoGen = true)
    public Integer _id;// 主键

    @Column(name = "id")
    public Integer id;// 省id

    @Column(name = "name")
    public String name;// 省名

    @Column(name = "weather")
    public String weather;// 省天气


    @Override
    public String toString() {
        return "[ id: " + id +
                ",  name: " + name +
                ",  weather: " + weather +
                " ]";
    }
}
