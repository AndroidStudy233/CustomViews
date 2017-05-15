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
    String imgurl_1366x768_1 = "https://image.baidu.com/search/detail?ct=503316480&z=0&ipn=d&word=1366x800&step_word=&hs=0&pn=14&spn=0&di=94867580280&pi=0&rn=1&tn=baiduimagedetail&is=0%2C0&istype=0&ie=utf-8&oe=utf-8&in=&cl=2&lm=0&st=-1&cs=3932290988%2C1204729467&os=1581929088%2C4204308513&simid=3378890467%2C359889509&adpicid=0&lpn=0&ln=1992&fr=&fmq=1494810978723_R&fm=&ic=undefined&s=0&se=&sme=&tab=0&width=1366&height=800&face=undefined&ist=&jit=&cg=&bdtype=0&oriquery=&objurl=http%3A%2F%2Fimgsrc.baidu.com%2Fforum%2Fpic%2Fitem%2F3bff90ef76c6a7ef9c29c31afcfaaf51f2de66e9.jpg%3Fv%3Dtbs&fromurl=ippr_z2C%24qAzdH3FAzdH3Fptjkw_z%26e3Bkwt17_z%26e3Bv54AzdH3FrAzdH3Fdab8n8998c%3Frg%3D9&gsm=1e&rpstart=0&rpnum=0";
    /**1366x800_2*/
    String imgurl_1366x768_2 = "https://image.baidu.com/search/detail?ct=503316480&z=0&ipn=d&word=1366x800&step_word=&hs=0&pn=98&spn=0&di=255279099582&pi=0&rn=1&tn=baiduimagedetail&is=0%2C0&istype=0&ie=utf-8&oe=utf-8&in=&cl=2&lm=0&st=-1&cs=3267383354%2C828388221&os=1028320572%2C1573255889&simid=3467473597%2C161445976&adpicid=0&lpn=0&ln=1992&fr=&fmq=1494810978723_R&fm=&ic=undefined&s=0&se=&sme=&tab=0&width=1366&height=800&face=undefined&ist=&jit=&cg=&bdtype=15&oriquery=&objurl=http%3A%2F%2Fimgstore.cdn.sogou.com%2Fapp%2Fa%2F100540002%2F671165.jpg&fromurl=ippr_z2C%24qAzdH3FAzdH3Fktzit_z%26e3Bf5257_z%26e3Bv54AzdH3F1jpwtsAzdH3Ftgu5AzdH3Fm088mcAzdH3Fm088mcAzdH3F7fj6w11AzdH3F80ddcn9c&gsm=3c&rpstart=0&rpnum=0";
    /**2560x1500_1*/
    String imgurl_1920x1080_1 = "https://image.baidu.com/search/detail?ct=503316480&z=0&ipn=d&word=2560x1500&step_word=&hs=0&pn=273&spn=0&di=181553839060&pi=0&rn=1&tn=baiduimagedetail&is=0%2C0&istype=2&ie=utf-8&oe=utf-8&in=&cl=2&lm=-1&st=-1&cs=1116867511%2C3202087547&os=507692832%2C433816666&simid=3142315133%2C3713011469&adpicid=0&lpn=0&ln=1992&fr=&fmq=1494811455548_R&fm=result&ic=0&s=undefined&se=&sme=&tab=0&width=&height=&face=undefined&ist=&jit=&cg=&bdtype=0&oriquery=&objurl=http%3A%2F%2Fimg2.niutuku.com%2Fdesk%2F1208%2F2122%2Fntk-2122-38702.jpg&fromurl=ippr_z2C%24qAzdH3FAzdH3Fgt7p7h7_z%26e3Bv54AzdH3FktzitAzdH3F98dmAzdH3Fcada0a_z%26e3Bfip4s&gsm=10e&rpstart=0&rpnum=0";
    /**2560x1500_2*/
    String imgurl_1920x1080_2 = "https://image.baidu.com/search/detail?ct=503316480&z=0&ipn=d&word=2560x1500&step_word=&hs=0&pn=39&spn=0&di=118985361880&pi=0&rn=1&tn=baiduimagedetail&is=0%2C0&istype=2&ie=utf-8&oe=utf-8&in=&cl=2&lm=-1&st=-1&cs=2850562221%2C3862248058&os=2326587714%2C2762559584&simid=4111433061%2C847537810&adpicid=0&lpn=0&ln=1992&fr=&fmq=1494811455548_R&fm=result&ic=0&s=undefined&se=&sme=&tab=0&width=&height=&face=undefined&ist=&jit=&cg=&bdtype=0&oriquery=&objurl=http%3A%2F%2Fimg5.duitang.com%2Fuploads%2Fitem%2F201409%2F30%2F20140930114737_zrf8J.jpeg&fromurl=ippr_z2C%24qAzdH3FAzdH3Fooo_z%26e3B17tpwg2_z%26e3Bv54AzdH3Frj5rsjAzdH3F4ks52AzdH3Fd8nm8anlcAzdH3F1jpwtsAzdH3F&gsm=0&rpstart=0&rpnum=0";

    /**测试realm的库名*/
    String name_test_realm = "test.realm";
}
