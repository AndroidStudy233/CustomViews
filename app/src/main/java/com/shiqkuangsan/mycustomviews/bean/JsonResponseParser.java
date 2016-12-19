package com.shiqkuangsan.mycustomviews.bean;

import com.alibaba.fastjson.JSON;

import org.xutils.common.util.ParameterizedTypeUtil;
import org.xutils.http.app.ResponseParser;
import org.xutils.http.request.UriRequest;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by wyouflf on 15/11/5.
 */

/**
 * Json解析器,如果你想自定义CallBack的泛型,需要使用这个转换
 * 如果实现 InputStreamResponseParser, 可实现自定义流数据转换.
 */
class JsonResponseParser implements ResponseParser {

    @Override
    public void checkResponse(UriRequest request) throws Throwable {
    }

    /**
     * 转换result为resultType类型的对象(需要FastJson的支持)
     *
     * @param resultType  返回值类型(可能带有泛型信息,就是你CallBack的泛型了)
     * @param resultClass 返回值类型
     * @param result      字符串json数据
     * @return 根据定义的泛型返回响应的数据
     * @throws Throwable
     */
    @Override
    public Object parse(Type resultType, Class<?> resultClass, String result) throws Throwable {
        if (resultClass == List.class)
            return JSON.parseArray(result, (Class<?>) ParameterizedTypeUtil.getParameterizedType(resultType, List.class, 0));
        else
            return JSON.parseObject(result, resultClass);
    }
}
