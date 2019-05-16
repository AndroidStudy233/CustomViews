package qwy.anenda.com.baselib.base;
 /* 
 -----------------------------------------------------------------
 * Author: yzw
 * Create: 2019/3/15
 * Descride:
 * 
 *-----------------------------------------------------------------
 */

import android.app.Application;
import android.content.Context;

import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;

import qwy.anenda.com.baselib.R;
import qwy.anenda.com.baselib.utils.PreferenceUtil;
import qwy.anenda.com.baselib.utils.ToastUtils;


public class BaseApplication extends Application {
    private static BaseApplication app;

    static {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                layout.setPrimaryColorsId(R.color.colorPrimary, android.R.color.white);//全局设置主题颜色
                return new MaterialHeader(context);//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                //指定为经典Footer，默认是 BallPulseFooter
                return new ClassicsFooter(context).setDrawableSize(20);
            }
        });
    }
    @Override
    public void onCreate() {
        super.onCreate();
        initOkGo();
        app = this;
        PreferenceUtil.init(this);
        ToastUtils.init(this);
    }

    public static BaseApplication getApp() {
        return app;
    }

    public static void setApp(BaseApplication app) {
        BaseApplication.app = app;
    }



    public void initOkGo(){
//        HttpHeaders headers = new HttpHeaders();
////        headers.put("uid", PreferenceUtil.readString(this, Constant.CONFIG,Constant.USERID)+"");    //header不支持中文，不允许有特殊字符
////        headers.put("signKey",  PreferenceUtil.readString(this,Constant.CONFIG,Constant.SIGNKEY));
////        headers.put("appName", "0");
////        headers.put("appToken",  PreferenceUtil.readString(this, Constant.CONFIG,Constant.TOKEN));
//        HttpParams params = new HttpParams();
////        params.put("commonParamsKey1", "commonParamsValue1");     //param支持中文,直接传,不要自己编码
////        params.put("commonParamsKey2", "这里支持中文参数");
////-------------------------------------------------------------------------------------//
//        OkHttpClient.Builder builder = new OkHttpClient.Builder();
//        builder.cookieJar(new CookieJarImpl(new SPCookieStore(this)));
//        OkGo.getInstance().init(this)                       //必须调用初始化
//                .setOkHttpClient(builder.build())               //建议设置OkHttpClient，不设置将使用默认的
//                .setCacheMode(CacheMode.NO_CACHE)               //全局统一缓存模式，默认不使用缓存，可以不传
//                .setCacheTime(CacheEntity.CACHE_NEVER_EXPIRE)   //全局统一缓存时间，默认永不过期，可以不传
//                .setRetryCount(3)                               //全局统一超时重连次数，默认为三次，那么最差的情况会请求4次(一次原始请求，三次重连请求)，不需要可以设置为0
//                .addCommonHeaders(headers)                      //全局公共头
//                .addCommonParams(params);
    }
}
