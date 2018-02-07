package com.shiqkuangsan.mycustomviews.ui.activity;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.shiqkuangsan.mycustomviews.R;
import com.shiqkuangsan.mycustomviews.base.BaseActivity;
import com.shiqkuangsan.mycustomviews.bean.Province;
import com.shiqkuangsan.mycustomviews.constant.Constant;
import com.shiqkuangsan.mycustomviews.db.SimpleDbHelper;
import com.shiqkuangsan.mycustomviews.utils.MyLogUtil;
import com.shiqkuangsan.mycustomviews.utils.MySimplexUtil;
import com.shiqkuangsan.mycustomviews.utils.MySimplexUtil.SimpleRequestParams;

import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.shiqkuangsan.mycustomviews.R.id.btn_xutils_db;

/**
 * 学习使用xUtils的界面,配合笔记里面的说明食用味道更佳
 */
@ContentView(R.layout.activity_xutils)  // UI注解
public class XUtilsActivity extends BaseActivity {

    // View注解,需要在Activity的onCreate()方法中注入
    @ViewInject(R.id.btn_xutils_sendget)
    Button btn_request_get;
    @ViewInject(R.id.btn_xutils_sendpost)
    Button btn_request_post;
    @ViewInject(R.id.btn_xutils_upload)
    Button btn_xutils_upload;
    @ViewInject(R.id.btn_xutils_download)
    Button btn_xutils_download;
    @ViewInject(R.id.tv_xutils_progress)
    TextView tv_xutils_progress;
    @ViewInject(R.id.iv_xutils_display)
    ImageView iv_xutils_display;
    @ViewInject(R.id.tv_xutils_tips)
    TextView tv_xutils_tips;
    @ViewInject(R.id.btn_xutils_db)
    Button btn_xutils_init;

    public DbManager manager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_xutils);
        x.view().inject(this);

        btn_xutils_init.setText("init");
        tv_xutils_tips.setText("先get,再init,再增删改查");
    }

    boolean isDbInited = false;
    private List<Province> list;

    // 事件注解,记住方法修饰必须为private, 默认type为OnClickListener,你可以自定义
//    @Event(value = {R.id.btn_xutils_sendget, R.id.btn_xutils_sendpost}, type = View.OnClickListener.class)
    @Event(value = {R.id.btn_xutils_sendget, R.id.btn_xutils_sendpost, R.id.btn_xutils_upload,
            R.id.btn_xutils_download, R.id.btn_xutils_image, R.id.btn_xutils_circle, R.id.btn_xutils_gif,
            btn_xutils_db, R.id.btn_xutils_dbadd, R.id.btn_xutils_dbupdate, R.id.btn_xutils_dbdelete,
            R.id.btn_xutils_dbquery})
    private void processOnclick(View view) {
        switch (view.getId()) {
            // xUtils发送get请求
            case R.id.btn_xutils_sendget:
                sendGetRequest();
                break;

            case R.id.btn_xutils_sendpost:
                showToast("和get请求使用起来都一样");
                break;

            // 上传文件
            case R.id.btn_xutils_upload:
                upploadFile();
                break;

            // 下载文件
            case R.id.btn_xutils_download:
                downloadFile();
                break;

            // 展示图片
            case R.id.btn_xutils_image:
                MySimplexUtil.loadImage(iv_xutils_display, Constant.display_image_url, MySimplexUtil.getSimpleImageOptions(8), null);
//                MySimplexUtil.loadImage(iv_xutils_display, "file://sdcard/copy.jpg",
//                        MySimplexUtil.getSimpleImageOptions(), null);// 加载本地图片,记得前缀file://,最好用Enviroment.get表示sdcard
                break;

            // 展示圆形图片
            case R.id.btn_xutils_circle:
                MySimplexUtil.loadImage(iv_xutils_display, Constant.display_circlr_image_url, MySimplexUtil.getSimpleCircleImageOptions(), null);
                break;

            // 展示gif图片
            case R.id.btn_xutils_gif:
                MySimplexUtil.loadImage(iv_xutils_display, Constant.display_gif_url, MySimplexUtil.getSimpleImageOptions(8), null);
                break;

            // 创建数据库
            case btn_xutils_db:
                manager = SimpleDbHelper.getInstance(this, "xutils.db");
                if (list == null)
                    showToast("请先get一波数据");
                else
                    isDbInited = true;
                break;

            case R.id.btn_xutils_dbadd:
                if (isDbInited)
                    x.task().post(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                // 先删除所有再存入
                                manager.delete(Province.class);
                                for (int i = 0; i < list.size(); i++) {
                                    Province province = list.get(i);
                                    manager.save(province);
                                    MyLogUtil.debug("存入省: " + province.name);
                                }
                                showToast("你重新存入了" + list.size() + "个省");
                            } catch (DbException e1) {
                                e1.printStackTrace();
                            }
                        }
                    });
                break;

            case R.id.btn_xutils_dbupdate:
                if (isDbInited) {
                    try {
                        Province last = manager.selector(Province.class).where("id", "=", "30").findFirst();
                        MyLogUtil.debug("原名: " + last.name);
                        // 方式1:找到bean类设置新属性,然后告诉manager你改了那些属性
                        last.name = "日本省";
                        manager.update(last, "name");

                        // 方式2:你想改谁?键值对告诉我怎么改
//                        manager.update(Province.class, WhereBuilder.b("id", "=", "30"), new KeyValue("name", "日本省"));

                        Province now = manager.selector(Province.class).where("id", "=", "30").findFirst();
                        MyLogUtil.debug("改完后的名字: " + now.name);
                        showToast("成功改掉了一个省的名字");
                    } catch (DbException e1) {
                        e1.printStackTrace();
                    }
                }
                break;

            case R.id.btn_xutils_dbquery:
                if (isDbInited) {
                    x.task().post(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                List<Province> provinces = manager.selector(Province.class).where("id", ">=", 30).findAll();
                                showToast("你查到了" + provinces.size() + "个省");
                                for (int i = 0; i < provinces.size(); i++) {
                                    Province province = provinces.get(i);
                                    MyLogUtil.debug(province.toString());
                                }
                            } catch (DbException e1) {
                                e1.printStackTrace();
                            }
                        }
                    });
                }
                break;

            case R.id.btn_xutils_dbdelete:
                if (isDbInited)
                    try {
                        manager.delete(Province.class);
                        showToast("全部删干净了,重新初始化吧");
                        // 删完数据为了防止瞎几把按finish
                        finish();
                    } catch (DbException e1) {
                        e1.printStackTrace();
                    }
                break;

        }
    }


    /**
     * 发送get请求
     */
    private void sendGetRequest() {
        MySimplexUtil.sendGet(new MySimplexUtil.SimpleRequestParams.Builder(Constant.mlnx_province_url)
                        .addHeader("", "")// 为了演示用法
                        .addQueryStringParameter("", "")
                        .addBodyParameter("", "")
                        .build(),

                new MySimplexUtil.SimpleRequstCallBack<List<Province>>() {
                    @Override
                    public void onSuccess(List<Province> result) {
                        if (!isDbInited)
                            list = result;
                        if (result != null && result.size() > 0)
                            showToast(result.get(new Random().nextInt(result.size() - 1)).toString());
                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                        super.onError(ex, isOnCallback);
                    }
                });
    }

    /**
     * 演示
     * 如果一个接口需要传一个对象或者一个数组. 就得把它转成json数据, 然后用setBodyContent
     */
    private void sendGetUseArr() {
        SimpleRequestParams params = new SimpleRequestParams.Builder(Constant.mlnx_province_url).build();
        params.setAsJsonContent(true);
        String jsonString = JSON.toJSONString(new Province());
        String jString1 = JSON.toJSONString(new ArrayList<Province>());
        String jString2 = JSON.toJSONString(new String[]{"233"});

        params.setBodyContent(jsonString);
        MySimplexUtil.sendGet(params, new MySimplexUtil.SimpleRequstCallBack<List<Province>>() {
            @Override
            public void onSuccess(List<Province> result) {
                if (!isDbInited)
                    list = result;
                if (result != null && result.size() > 0)
                    showToast(result.get(new Random().nextInt(result.size() - 1)).toString());
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                super.onError(ex, isOnCallback);
            }
        });
    }

    /**
     * 上传文件
     */
    private void upploadFile() {
        showToast("功能写了,没有接口,没法测试");
//        RequestParams params = new RequestParams(MlnxConfigDataFactory.getMlnxUrlRoot() + "api/file/");
//        List<KeyValue> list = new ArrayList<>();
//        for (PhotoInfo info : photoInfos) {
//            File file = new File(info.getPhotoPath());
//            list.add(new KeyValue("file", file));
//        }
//        MultipartBody body = new MultipartBody(list, "UTF-8");
//        params.setRequestBody(body);
//        params.setMultipart(true);
    }

    /**
     * 下载文件
     */
    private void downloadFile() {
        String savePath = Environment.getExternalStorageDirectory() + "/kuangsan.jpg";
        File file = new File(savePath);
        if (file.exists())
            if (file.delete())// 为了演示,下载了点按钮删除再下载
                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
                    MySimplexUtil.downloadFile(Constant.download_file_url, savePath, new MySimplexUtil.SimpleFileCallBack<File>() {
                        @Override
                        public void onStarted() {
                            tv_xutils_progress.setVisibility(View.VISIBLE);
                            tv_xutils_progress.setText("0%");
                        }

                        @Override
                        public void onLoading(long total, long current, boolean isDownloading) {
                            super.onLoading(total, current, isDownloading);
                            tv_xutils_progress.setText(current / total * 100 + "%");
                        }

                        @Override
                        public void onSuccess(File result) {
                            MyLogUtil.debug("onSuccess");
                            showToast("下载完成");
                        }

                        @Override
                        public void onError(Throwable ex, boolean isOnCallback) {
                            MyLogUtil.debug("onError");
                            showToast("下载失败");
                        }

                    });
                else
                    showToast("内存设备异常");
    }

    protected Toast toast;

    protected void showToast(String msg) {
        if (toast != null) {
            toast.cancel();
            toast = null;
        }
        toast = Toast.makeText(this, msg + "", Toast.LENGTH_SHORT);
        toast.show();
    }

}
