package qwy.anenda.com.baselib.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.text.InputFilter;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ToolsFormat {
    //匹配手机号码
//    public static final String RULE_MOBILE = "^(13[0-9]|15[012356789]|17[3678]|18[0-9]|14[57])[0-9]{8}$";
    public static final String RULE_MOBILE = "^(1[3456789])[0-9]{9}$";
    //匹配邮箱
    public static final String RULE_EMAIL = "^(\\w)+(\\.\\w+)*@(\\w)+((\\.\\w{2,3}){1,3})$";
    //匹配身份证号码
    public static final String RULE_IDCARD = "[1-9]\\d{13,16}[a-zA-Z0-9]{1}";
    //6~20位的数字+字母
    public static final String ruleLoginPassword = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,20}$";
    //匹配url
    public static final String RULE_URL = "((HTTP|HTTPS|http|https):\\/\\/)?[\\w\\-_]+(\\.[\\w\\-_]+)+([\\w\\-\\.,@?^=%&amp;:/~\\+#]*[\\w\\-\\@?^=%&amp;/~\\+#])?";

    public static void makeText(Context context, String str) {
        Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
    }

    public static String getString(Context context, int id) {
        return context.getResources().getString(id);
    }

    public static String getVersionName(Context context) {
        String version = "";
        try {
            // 获取packagemanager的实例
            PackageManager packageManager = context.getPackageManager();
            // getPackageName()是你当前类的包名，0代表是获取版本信息
            PackageInfo packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            version = packInfo.versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return version;
    }

    public static int getVersionCode(Context context) {
        int version = 1;
        try {
            // 获取packagemanager的实例
            PackageManager packageManager = context.getPackageManager();
            // getPackageName()是你当前类的包名，0代表是获取版本信息
            PackageInfo packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            version = packInfo.versionCode;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return version;
    }

    public static void inputMethodManager(Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public static void inputMethodManager(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
//        imm.showSoftInput(view,InputMethodManager.SHOW_FORCED);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0); //强制隐藏键盘
    }

    public static float getDisplayDensity(Context context) {
        float density = 1.0f;
        if (context != null) {
            DisplayMetrics metrics = context.getResources().getDisplayMetrics();
            density = metrics.density;
        }
        return density;
    }

    public static int getwidthPixels(Context context) {
        Resources resources = context.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        return dm.widthPixels;
    }

    public static int getheightPixels(Context context) {
        Resources resources = context.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        return dm.heightPixels;
    }

    public static int getStatusBar(Context context) {
        int statusBarHeight = 0;
        //获取status_bar_height资源的ID
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            //根据资源ID获取响应的尺寸值
            statusBarHeight = context.getResources().getDimensionPixelSize(resourceId);
        }
        return statusBarHeight;
    }


    /**
     * 通过文件名获取资源id 例子：getResId("icon", R.drawable.class);
     *
     * @param variableName
     * @param c
     * @return
     */
    public static int getResId(String variableName, Class<?> c) {
        try {
            Field idField = c.getDeclaredField(variableName);
            return idField.getInt(idField);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     *  dp 转换 px
     * @param context
     * @param dip
     * @return
     */
    public static int dpToPx(Context context, float dip) {
        final float SCALE = context.getResources().getDisplayMetrics().density;
        float valueDips = dip;
        int valuePixels = (int) (valueDips * SCALE + 0.5f);
        return valuePixels;
    }

    /**
     * sp 转换 px
     * @param context
     * @param spValue
     * @return
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * 获取联系人的电话号码
     * @param fragment
     */
    public static void getContactPhoneNumber(Fragment fragment){
        Uri uri = ContactsContract.Contacts.CONTENT_URI;
        Intent intent = new Intent(Intent.ACTION_PICK,uri);
        fragment.startActivityForResult(intent,0,null);
    }


    /**
     * 获取卡的类型
     * @param typeCode
     * @return
     */
    public static String getCardType(String typeCode){
        String cardType = "储蓄卡";
        if (typeCode.equals("CreditCard")){
            cardType = "信用卡";
        }else if (typeCode.equals("CashCard")){
            cardType = "储蓄卡";
        }
        return cardType;
    }

    /**
     * 格式化显示昵称+实名
     * @param userNickName
     * @param userRealName
     * @return
     */
    public static String formatUserName(String userNickName, String userRealName){
        String realName = "";
        if (TextUtils.isEmpty(userNickName) || TextUtils.isEmpty(userRealName)){
            return realName;
        }
        if (!TextUtils.isEmpty(userRealName)) {
            switch (realName.length()) {
                //一般不可能为1
                case 1:
                    realName = "*";
                    break;
                //
                case 2:
                    realName = "*" + userRealName.substring(1, userRealName.length());
                    break;
                //
                default:
                    realName = "*" + userRealName.substring(userRealName.length() - 2, userRealName.length());
                    break;
            }
        }
        return userNickName + " (" + realName + ")";
    }

    /**
     * 格式化显示真实姓名
     * @param realName 待格式化的真实姓名
     * @return
     * 统一姓名星号格式
    1）三位中文的情况下，前一位后一位显示，中间隐藏，比如：陈小茵，则为：陈*茵
    2）两位中文的情况下，隐藏前一位，后一位显示，比如：陈浩，则为*浩
    3）多位中文的的情况下，前一位后一位显示，中间隐藏，多少个数字，就显示多个星号，比如：慕容冰雪，则为：慕**雪
     */
    public static String formatRealName(String realName){
        String tag = "*";
        String localRealName = "";
        if (TextUtils.isEmpty(realName)){
            //真实姓名长度为0, 返回空字符串
            return localRealName;
        }
        //获取字符串中包含的汉字的个数
        int count = containChinese(realName);
        if (0 != count && count != realName.length()){
            //如果字符串中包含的汉字个数不等于传入的真实姓名长度, 不进行格式化, 直接返回
            return realName;
        }
        switch (realName.length()){
            case 1:
                localRealName = realName;
                break;
            case 2:
                localRealName = realName.replace(realName.substring(0,1), tag);
                break;
            case 3:
                localRealName = (realName.substring(0,1) + tag + realName.substring(2,3));
                break;
            default:
                localRealName += realName.substring(0, 1);
                for (int i = 0; i < realName.length() - 2; i++){
                    localRealName += tag;
                }
                localRealName += realName.substring(realName.length() - 1 , realName.length());
                break;

        }
        return localRealName;
    }

    /**
     * 判断字符串是否包含中文
     * @param chinese 匹配的字符串
     * @return
     */
    public static int containChinese(String chinese){
        int count = 0;
        String regEx = "[\\u4e00-\\u9fa5]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(chinese);      //p.matcher()只适合做
        while (m.find()) { //m.matches()全部匹配为true
            //m.groupCount()用于获取正则模式中子模式匹配的组，即只有正则中含有（）分组的情况下才有用
            for (int i = 0; i <= m.groupCount(); i++) {
                count++;
            }
        }
//        return "共有 " + count + "个 ";
        return count;
    }


    /**
     * 设置edittext的最大长度
     * @param editText
     * @param maxLength
     */
    public static void setEditMaxLength(EditText editText, int maxLength){
        if (null == editText || 0 >= maxLength) return;
        editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});
    }

    /**
     * 格式化手机号码，只显示前两位和后三位
     * @param phone
     * @return
     */
    public static String formatPhone(String phone) {
        if (TextUtils.isEmpty(phone)) {
            return phone;
        }
        if (phone.trim().length() != 11 || !checkMobile(phone)) {
            return phone;
        }
        String finalPhone = phone.substring(0, 3) + "******" + phone.substring(phone.length() - 2);
        return finalPhone;
    }

    //格式化时间成字符串
    public static String formatDate(long date){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date formatData = new Date(date);
        String format = dateFormat.format(formatData);
        return format;
    }

    /**
     * 格式化银行卡号码显示格式
     * @param bancardNO
     * @return
     */
    public static String formatBankCard(String bancardNO){
        StringBuilder sb = new StringBuilder();
        sb.append(bancardNO.substring(0, 4));
        sb.append(" ");
        sb.append("****");
        sb.append(" ");
        sb.append("****");
        sb.append(" ");
        sb.append(bancardNO.substring(bancardNO.length() - 4));
        return sb.toString();
    }


    /**
     * 获取字符串的后几位
     * @param length 位数
     * @return
     */
    public static String getStrLastLength(String cardNo, int length){
        if (TextUtils.isEmpty(cardNo)){
            return "";
        }
        if (cardNo.length() <= length){
            return cardNo;
        }
        return cardNo.substring(cardNo.length() - length);
    }



    /**
     * 格式化double，保留两位小数
     * @param formatValue
     * @return
     */
    public static String formatTwoPoint(double formatValue){
        return formatDouble(formatValue, 2);
    }

    /**
     * 格式化double
     * @param formatValue  目标double
     * @param numberAPoint 保留几位小数点
     * @return
     */
    public static String formatDouble(double formatValue, int numberAPoint) {
        return String.format("%." + numberAPoint + "f", formatValue);
    }


    /**
     * 验证手机号码（支持国际格式，+86135xxxx...（中国内地），+00852137xxxx...（中国香港））
     * @param mobile 移动、联通、电信运营商的号码段
     *<p>移动的号段：134(0-8)、135、136、137、138、139、147（预计用于TD上网卡）
     *、150、151、152、157（TD专用）、158、159、187（未启用）、188（TD专用）</p>
     *<p>联通的号段：130、131、132、155、156（世界风专用）、185（未启用）、186（3g）</p>
     *<p>电信的号段：133、153、180（未启用）、189</p>
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkMobile(String mobile) {
        return Pattern.matches(RULE_MOBILE,mobile);
    }
    /**
     * 格式化身份证号
     * 只保留身份证的前面三位和后面四位
     */
    public static String formatIDCard(String idcard){
        if("".equals(idcard)){
            return "身份证号为空";
        }
        boolean isIdCard = checkIdCard(idcard);
        if(isIdCard){
            String pro3 = idcard.substring(0,3);
            String hou4 = idcard.substring(idcard.length() - 4,idcard.length());
            return pro3+"***********"+hou4;
        }
        return "身份证号格式不正确";
    }



    /**
     * 验证身份证号码
     * @param idCard 居民身份证号码15位或18位，最后一位可能是数字或字母
     * @return 验证成功返回true，验证失败返回false
     */
    public static boolean checkIdCard(String idCard) {
        return Pattern.matches(RULE_IDCARD,idCard);
    }

    /**
     * 验证邮箱
     * @param email 输入的邮箱
     * @return
     */
    public static boolean checkEmail(String email){
        Pattern pattern = Pattern.compile(RULE_EMAIL);
        Matcher matcher = pattern.matcher(email);
        return  matcher.matches();
    }





    /*
    银行卡校验过程：
    1、从卡号最后一位数字开始，逆向将奇数位(1、3、5等等)相加。
    2、从卡号最后一位数字开始，逆向将偶数位数字，先乘以2（如果乘积为两位数，将个位十位数字相加，即将其减去9），再求和。
    3、将奇数位总和加上偶数位总和，结果应该可以被10整除。
    */
    /**
     * 校验银行卡卡号
     */
    public static boolean checkBankCard(String bankCard) {
        if(bankCard.length() < 15 || bankCard.length() > 19) {
            return false;
        }
        char bit = getBankCardCheckCode(bankCard.substring(0, bankCard.length() - 1));
        if(bit == 'N'){
            return false;
        }
        return bankCard.charAt(bankCard.length() - 1) == bit;
    }

    /**
     * 从不含校验位的银行卡卡号采用 Luhm 校验算法获得校验位
     * @param nonCheckCodeBankCard
     * @return
     */
    public static char getBankCardCheckCode(String nonCheckCodeBankCard){
        if(nonCheckCodeBankCard == null || nonCheckCodeBankCard.trim().length() == 0
                || !nonCheckCodeBankCard.matches("\\d+")) {
            //如果传的不是数据返回N
            return 'N';
        }
        char[] chs = nonCheckCodeBankCard.trim().toCharArray();
        int luhmSum = 0;
        for(int i = chs.length - 1, j = 0; i >= 0; i--, j++) {
            int k = chs[i] - '0';
            if(j % 2 == 0) {
                k *= 2;
                k = k / 10 + k % 10;
            }
            luhmSum += k;
        }
        return (luhmSum % 10 == 0) ? '0' : (char)((10 - luhmSum % 10) + '0');
    }


    /**
     * 字符串用utf-8编码
     *
     * @param content
     * @return
     */
    public static String stringToUTF8(String content) {
        if (TextUtils.isEmpty(content)) {
            return content;
        } else {
            try {
                return new String(content.getBytes("UTF-8"), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    /**
     * 刷新保存的图片
     * @param context
     * @param filePath
     */
    public static void updatePhotoAlbum(Context context, String filePath){
        try{
            //文件路径为空
            if (null == context || TextUtils.isEmpty(filePath)){
                return;
            }
            File file = new File(filePath);
            //文件不存在
            if (null == file || !file.exists()){
                return;
            }

            Uri contentUri = Uri.fromFile(file);
            if (null == contentUri){
                return;
            }
            Intent mediaScanIntent = new Intent(
                    Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            mediaScanIntent.setData(contentUri);
            context.sendBroadcast(mediaScanIntent);

           /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                //大于等于4.4
                Uri contentUri = Uri.fromFile(file); //out is your output file
                Intent mediaScanIntent = new Intent(
                        Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                mediaScanIntent.setData(contentUri);
                if(null != contentUri){
                    context.sendBroadcast(mediaScanIntent);
                }else{
                    MediaScannerConnection.scanFile(context,
                            new String[]{filePath},
                            null, null);
                }
            } else {
                //小于4.4
                context.sendBroadcast(new Intent(
                        Intent.ACTION_MEDIA_MOUNTED,
                        Uri.parse("file://"
                                + Environment.getExternalStorageDirectory())));
            }*/
        }catch(Exception e){
        }
    }

    public static String formatRunTime(String runTime){
        if(runTime!=null){
            String hour = runTime.substring(0, runTime.indexOf(":"));//小时 01 21
            String min = runTime.substring(runTime.indexOf(":")+1,runTime.length() );//小时 01 21
            if(hour!=null&&min!=null){
                return hour+"小时"+min+"分钟";
            }
        }
        return "";
    }
}
