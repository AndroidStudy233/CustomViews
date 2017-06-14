package com.shiqkuangsan.mycustomviews.constant;

/**
 * Created by shiqkuangsan on 2016/9/24.
 */

/**
 * 常量类
 */
public interface Constant {

    /**聚合天气的温州天气预报的url*/
    String weather_wenzhou_url = "http://op.juhe.cn/onebox/weather/query?cityname=%E6%B8%A9%E5%B7%9E&key=16931ee288c3e6900bbfa04bd7fcdcad";

    /**查找所有省的url,随时可能不能用,因此在strings资源下面定义好了一个json数据备用*/
    String mlnx_province_url = "http://121.40.137.14:80/doctor/provinces/all.do";

    /**测试下载文件(图片)的url*/
    String download_file_url = "http://imgstore.cdn.sogou.com/app/a/100540002/707478.png";

    /**测试展示图片的url*/
    String display_image_url = "http://cdn.duitang.com/uploads/item/201308/19/20130819175611_yij8E.thumb.600_0.jpeg";

    /**测试展示圆形图片的url*/
    String display_circlr_image_url = "http://f.hiphotos.baidu.com/zhidao/pic/item/a08b87d6277f9e2f520b93211830e924b899f3d9.jpg";

    /**测试展示gif的url*/
    String display_gif_url = "http://img4.duitang.com/uploads/blog/201307/18/20130718160527_BBMEh.thumb.224_0.gif";


    // 图片的比例都是1.7, 这样RatioLayout设置1.7图片就完美比例
    /**800x470_1*/
    String imgurl_800x470_1 = "http://www.cphoto.net/data/attachment/portal/201610/13/091933ko0oz2mhoogb4q0g.jpg";
    /**800x470_2*/
    String imgurl_800x470_2 = "http://imgbdb3.bendibao.com/weixinbdb/201611/12/20161112133134691.jpg";
    /**1366x800_1*/
    String imgurl_1366x768_1 = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1497452060128&di=722ddeec9afe3d0c70f6c51015fd7a3c&imgtype=0&src=http%3A%2F%2Fimage.cool-de.com%2Fdata%2Fattachment%2Fforum%2F201705%2F26%2F153616ga4kgpavaktt45jj.jpg";
    /**1366x800_2*/
    String imgurl_1366x768_2 = "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=3267383354,828388221&fm=15&gp=0.jpg";
    /**2560x1500_1*/
    String imgurl_1920x1080_1 = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1497452112436&di=35ac9198b0583174d2dfbf31e5febb2c&imgtype=0&src=http%3A%2F%2Fimg2.niutuku.com%2Fdesk%2F1208%2F2122%2Fntk-2122-38702.jpg";
    /**2560x1500_2*/
    String imgurl_1920x1080_2 = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1497452123550&di=1c2e428f6132f2b7d5c3471e1114ec36&imgtype=0&src=http%3A%2F%2Fimg5.duitang.com%2Fuploads%2Fitem%2F201409%2F30%2F20140930114737_zrf8J.jpeg";

    /**测试realm的库名*/
    String name_test_realm = "test.realm";
}
