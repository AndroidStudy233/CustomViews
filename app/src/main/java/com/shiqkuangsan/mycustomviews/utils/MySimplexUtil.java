package com.shiqkuangsan.mycustomviews.utils;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.shiqkuangsan.mycustomviews.R;

import org.xutils.DbManager;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.io.File;

import static org.xutils.common.util.DensityUtil.dip2px;

/**
 * Created by shiqkuangsan on 2016/9/24.
 */

/**
 * 简单的xUtils3使用工具类,让开发更快捷
 */
public class MySimplexUtil {

    /**
     * 发送一个get请求
     *
     * @param params   请求参数,通过构建者模式创建 new SimpleRequestParams.Builder().addxxx().build()获得
     * @param callback 请求回调,简单的可以直接new SimpleRequstCallBack< T >(),自己重写需要的方法,你也可以使用原来
     *                 的new Callback.CommonCallback< T >()
     * @param <T>      请求返回类型,这个很重要,一般都写String然后回拿到json数据的字符串, 然后自行解析.
     *                 但是比如说我要直接获得一个集合List< bean >,或者直接获得一个bean类,那么你的bean类就得
     *                 添加注解@HttpResponse(parser =  JsonResponseParser.class),利用fastjson返回几行代码搞定
     *                 这里一定要注意你的bean类要和json数据对应
     * @return 请求Cancelable, 可以调用cancel()方法中断请求
     */
    public static <T> Callback.Cancelable sendGet(@NonNull SimpleRequestParams params,
                                                  @NonNull Callback.CommonCallback<T> callback) {
        return x.http().get(params, callback);
    }

    /**
     * 发送一个post请求
     *
     * @param params   请求参数,通过构建者模式创建 new SimpleRequestParams.Builder().addxxx().build()获得
     * @param callback 请求回调,简单的可以直接new SimpleRequstCallBack< T >(),自己重写需要的方法,你也可以使用原来
     *                 的new Callback.CommonCallback< T >()
     * @param <T>      请求返回类型,这个很重要,一般都写String然后回拿到json数据的字符串, 然后自行解析.
     *                 但是比如说我要直接获得一个集合List< bean >,或者直接获得一个bean类,那么你的bean类就得
     *                 添加注解@HttpResponse(parser =  JsonResponseParser.class),利用fastjson返回几行代码搞定
     *                 这里一定要注意你的bean类要和json数据对应
     * @return 请求Cancelable, 可以调用cancel()方法中断请求
     */
    public static <T> Callback.Cancelable sendPost(@NonNull SimpleRequestParams params,
                                                   @NonNull Callback.CommonCallback<T> callback) {
        return x.http().post(params, callback);
    }

    /**
     * 请求参数,采用构建者设计模式生成
     */
    public static class SimpleRequestParams extends RequestParams {
        private SimpleRequestParams(String url) {
            super(url);
        }

        /**
         * 请求参数构建者
         */
        public static class Builder {

            SimpleRequestParams params;

            /**
             * 请求参数构建者
             *
             * @param url 请求地址
             */
            public Builder(String url) {
                newSimpleRequestParams(url);
            }

            void newSimpleRequestParams(String url) {
                params = new SimpleRequestParams(url);
            }

            public SimpleRequestParams build() {
                return params;
            }

            /**
             * 添加请求头参数
             *
             * @param key   参数名
             * @param value 参数值
             * @return 构建者
             */
            public Builder addHeader(String key, String value) {
                params.addHeader(key, value);
                return this;
            }

            /**
             * 添加请求体参数
             *
             * @param key   参数名
             * @param value 参数值
             * @return 构建者
             */
            public Builder addBodyParameter(String key, String value) {
                params.addBodyParameter(key, value);
                return this;
            }

            /**
             * 添加查询参数
             *
             * @param key   参数名
             * @param value 参数值
             * @return 构建者
             */
            public Builder addQueryStringParameter(String key, String value) {
                params.addQueryStringParameter(key, value);
                return this;
            }
        }
    }

    /**
     * 请求的时候使用该回调,可以自定义重写方法,需要谁重写谁
     *
     * @param <T> 返回泛型
     */
    public static class SimpleRequstCallBack<T> implements Callback.CommonCallback<T> {

        @Override
        public void onSuccess(T result) {
        }

        @Override
        public void onError(Throwable ex, boolean isOnCallback) {
        }

        @Override
        public void onCancelled(CancelledException cex) {
        }

        @Override
        public void onFinished() {
        }
    }


    /**
     * 上传一个文件的方法,请求如果需要其他参数的话,方法还是需要修改的
     *
     * @param url       上传url
     * @param paramName 请求参数名
     * @param file      请求上传的文件对象
     * @param callback  请求回调,简单的可以直接new SimpleFileCallBack< T >(),自己重写需要的方法.泛型一般File
     * @param <T>       返回泛型
     * @return Cancelable对象, 可以调用cancel()方法撤销请求
     */
    public static <T> Callback.Cancelable uploadFile(String url, String paramName, File file,
                                                     @NonNull Callback.ProgressCallback<T> callback) {
        RequestParams params = new RequestParams(url);
        params.setMultipart(true);
        params.addBodyParameter(paramName, file);
        return x.http().post(params, callback);
    }

    /**
     * 下载一个文件的方法,请求如果需要其他参数的话,方法还是需要修改的
     *
     * @param url      下载的url
     * @param filePath 文件的保存路径
     * @param callback 请求回调,简单的可以直接new SimpleFileCallBack< T >(),自己重写需要的方法.泛型一般File
     * @param <T>      返回泛型
     * @return Cancelable对象, 可以调用cancel()方法撤销请求
     */
    public static <T> Callback.Cancelable downloadFile(String url, String filePath,
                                                       @NonNull Callback.ProgressCallback<T> callback) {
        RequestParams params = new RequestParams(url);
        //设置断点续传
        params.setAutoResume(true);
        params.setSaveFilePath(filePath);
        return x.http().get(params, callback);
    }

    /**
     * 上传的时候使用该回调,可自定义重写方法
     *
     * @param <T> 返回泛型
     */
    public static abstract class SimpleFileCallBack<T> implements Callback.ProgressCallback<T> {

        @Override
        public void onWaiting() {
        }

        @Override
        public void onStarted() {
        }

        @Override
        public void onLoading(long total, long current, boolean isDownloading) {
        }

        @Override
        public void onSuccess(T result) {
        }

        @Override
        public void onError(Throwable ex, boolean isOnCallback) {
        }

        @Override
        public void onCancelled(CancelledException cex) {
        }

        @Override
        public void onFinished() {
        }
    }


    /**
     * 加载网络图片的方法,
     *
     * @param image    展示的ImageView
     * @param url      请求链接(本地图片采用"file://sdcard/copy.jpg" / "assets://test.gif"的格式)
     * @param options  加载参数
     * @param callback 请求回调,可以直接new SimpleFileCallBack< Drawable >(),泛型是规定必须Drawable
     */
    public static void loadImage(ImageView image, String url, ImageOptions options,
                                 @Nullable Callback.CommonCallback<Drawable> callback) {
        x.image().bind(image, url, options, callback);
    }

    /**
     * xUtils3图片的默认配置,使用了加载中显示图和加载失败显示图.
     *
     * @return xUtils3图片的默认配置
     */
    public static ImageOptions getSimpleImageOptions(int radius) {
        return new ImageOptions.Builder()
                .setImageScaleType(ImageView.ScaleType.FIT_CENTER)// 缩放类型,center_crop时圆角无效
//                .setCircular(true)// 是否设置为圆形
                .setRadius(dip2px(radius))// 四个圆角半径,圆形图时无效
                .setIgnoreGif(false)// 是否忽略gif格式
                .setCrop(true)// 是否对图片进行裁剪,如果ImageView的大小不是定义为wrap_content, 不要crop.
                .setPlaceholderScaleType(ImageView.ScaleType.FIT_CENTER)// 加载中/错误时显示的图片的缩放类型
                .setLoadingDrawableId(R.drawable.pic_loading)// 加载中显示图
                .setFailureDrawableId(R.drawable.pic_loading_fail)// 加载失败显示图
                .setUseMemCache(true)// 使用内存缓存
//                .setAnimation()// 顺带可以设置动画
                .build();
    }

    /**
     * xUtils3圆形图片的默认配置
     *
     * @return xUtils3圆形图片的默认配置
     */
    public static ImageOptions getSimpleCircleImageOptions() {
        return new ImageOptions.Builder()
                .setImageScaleType(ImageView.ScaleType.FIT_CENTER)// 缩放类型,center_crop时圆角无效
                .setCircular(true)// 是否设置为圆形
                .setIgnoreGif(false)// 是否忽略gif
                .setCrop(true)// 是否对图片进行裁剪,如果ImageView的大小不是定义为wrap_content, 不要crop.
                .setPlaceholderScaleType(ImageView.ScaleType.FIT_CENTER)// 加载中/错误时显示的图片的缩放类型
                .setLoadingDrawableId(R.drawable.pic_loading)// 加载中显示图
                .setFailureDrawableId(R.drawable.pic_loading_fail)// 加载失败显示图
                .setUseMemCache(true)// 使用内存缓存
                .build();
    }

    /**
     * 获取一个数据库管理者,一般将获取到的管理者单例,保证数据库操作的唯一性,升级数据库就是讲创建的管理者版本
     * 号升级,而相应的操作可以在升级监听中实现(本来打算在SimpleDbHelper中封装manager,功能由SimpleDbHelper的
     * 实例来实现,但是自由度降低,因此仅仅实现单例)
     *
     * @param dbName    数据库名称
     * @param dbPath    数据库所在目录(注释掉了,默认在app私有目录下)
     * @param dbVersion 数据库版本
     * @param listener  数据库升级监听
     * @return 数据库管理者
     */
    public static DbManager getSimpleDbManager(String dbName, File dbPath, int dbVersion, DbManager.DbUpgradeListener listener) {
        return x.getDb(getSimpleDaoConfig(dbName, dbPath, dbVersion, listener));
    }

    private static DbManager.DaoConfig getSimpleDaoConfig(String dbName, File dbPath, int dbVersion, DbManager.DbUpgradeListener listener) {
        return new DbManager.DaoConfig()
                .setDbName(dbName)  // 数据库名称(带后缀)
//                        .setDbDir(dbPath)   // 数据库目录,不设置该项默认在app私有目录下
                .setDbVersion(dbVersion)    // 数据库版本
                .setAllowTransaction(true)  // 允许开启事务
                .setDbOpenListener(new DbManager.DbOpenListener() {// 数据库打开监听
                    @Override
                    public void onDbOpened(DbManager db) {
                        // 开启WAL, 对写入加速提升巨大
                        db.getDatabase().enableWriteAheadLogging();
                        MyLogUtil.d("数据库打开...");
                    }
                })
                .setDbUpgradeListener(listener);
    }
}
