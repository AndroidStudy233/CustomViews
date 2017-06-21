package com.shiqkuangsan.mycustomviews.utils;

/**
 * Created by shiqkuangsan on 2016/9/6.
 */

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * Created by shiqkuangsan on 2016/11/6.
 *
 * @author shiqkuangsan
 * @summary: 开发中经常会遇到让用户从自己图库还是拍照获取图片的需求, 每次写写写...因此写了个选择图片的工具类
 * <p>
 * 使用:
 * step1. 在你的两个选择按钮分别调用startActivityFor()方法传入不同参数选择获取方式进入对应界面
 * <p>
 * 只是需要选择图片--step2          需要加载选择的图片到ImageView--step3
 * <p>
 * step2. 如果你只是需要选择图片拿到图片的bitmap对象/图片的绝对路径,那你需要在你的onActivityResult()方法处调用:
 * choice1、getBitmapFromResult()方法,将会获取到选择的图片的bitmap对象.(注意直接加载到ImageView容易OOM)
 * choice2、如果调用getPathFromResult()方法,将会获取到图片的路径,如果你需要路径的话.(有了路径你就可以用图片加载框架加载拉)
 * (choice2不支持裁剪且从相册获取使用过之后,使用过之后,使用过之后(重要的事情)若想删除缓存需要手动调用deleteTemp())
 * <p>
 * step3. 如果你是需要选择完图片之后加载到ImageView上,我也为你提供了两个方法并且解决了OOM的问题,同样也是在onActivityResult()处调用
 * 1> String loadImageView()不支持裁剪但是可以返回图片的绝对路径,使用完,使用完,使用完(重要的事情),若想删除缓存需要手动调用deleteTemp()
 * 2> void loadImageViewWithCrop()支持裁剪,支持自动删除缓存图片.
 * <p>
 * 关于android6.0动态权限申请的问题,图库选择图片的逻辑已经处理了,拍照获取大多定制机目前不需要处理,暂且是注释状态
 * 另外需要提的裁剪框的大小你如果不满意自己可以调,在crop()方法中
 */
public class ChoosePicUtil {

    /**
     * 从图库获取的匹配码
     */
    public static final int MATCHING_CODE_GALLERY = 0;

    /**
     * 从相机获取的匹配码
     */
    public static final int MATCHING_CODE_CAMERA = 1;

    /**
     * 从图库获取的请求码
     */
    private static final int REQUEST_CODE_GALLERY = 100;

    /**
     * 从相机获取的请求码
     */
    private static final int REQUEST_CODE_CAMERA = 200;

    /**
     * 进入裁剪界面的请求码
     */
    private static final int REQUEST_CODE_CROP = 300;

    /**
     * 临时存储的文件对象
     */
    private static File tempFile;

    /**
     * 6.0相机权限请求码
     */
    private static final int REQUEST_PERMISSION_CAMERA_6_0 = 400;

    /**
     * 6.0读写SD卡权限请求码
     */
    private static final int REQUEST_PERMISSION_SDCARD_6_0 = 500;

    /**
     * 根据不同匹配码确定是从图库选择还是相机获取,并启动相应activity
     *
     * @param MATCHING_CODE 匹配码, MATCHING_CODE_GALLERY - 图库获取, MATCHING_CODE_CAMERA - 相机获取
     */
    public static void startActivityFor(int MATCHING_CODE, final Activity activity) {
        switch (MATCHING_CODE) {
            case MATCHING_CODE_GALLERY:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

                        openGallery(activity);
                    } else {
                        // 该方法在用户上次拒绝后调用,因为已经拒绝了这次你还要申请授权你得给用户解释一波
                        // 一般建议弹个对话框告诉用户 该方法在6.0之前的版本永远返回的是fasle
                        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                            AlertDialog.Builder dialog = new AlertDialog.Builder(activity);
                            dialog.setTitle("权限提醒")
                                    .setMessage("获取图片功能需要您允许我们获取您的数据读取权限,否则将无法查看您手机上的图片~")
                                    .setPositiveButton("权限设置", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                            Uri uri = Uri.fromParts("package", activity.getPackageName(), null);
                                            intent.setData(uri);
                                            activity.startActivity(intent);
                                        }
                                    })
                                    .setNegativeButton("取消", null)
                                    .create()
                                    .show();
                        } else {
                            // 申请授权
                            ActivityCompat.requestPermissions(activity,
                                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_PERMISSION_SDCARD_6_0);
                        }
                    }
                    return;
                }

                // 6.0之前的版本直接走这里
                openGallery(activity);
                break;

            case MATCHING_CODE_CAMERA:
                // 6.0调用相机的处理
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {

                        openCamera(activity);
                    } else {
                        // 该方法在用户上次拒绝后调用,因为已经拒绝了这次你还要申请授权你得给用户解释一波
                        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.CAMERA)) {
                            AlertDialog.Builder dialog = new AlertDialog.Builder(activity);
                            dialog.setTitle("权限提醒")
                                    .setMessage("请您允许我们使用您的摄像头功能,否则将无法拍照~")
                                    .setPositiveButton("权限设置", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                            Uri uri = Uri.fromParts("package", activity.getPackageName(), null);
                                            intent.setData(uri);
                                            activity.startActivity(intent);
                                        }
                                    })
                                    .setNegativeButton("取消", null)
                                    .create()
                                    .show();
                        } else {
                            // 申请授权
                            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CAMERA}, REQUEST_PERMISSION_CAMERA_6_0);
                        }
                    }
                    return;
                }

                // 6.0之前的版本直接走这里
                openCamera(activity);
                break;
            default:
                Toast.makeText(activity, "匹配码有误!", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 打开系统图库
     *
     * @param activity activity
     */
    private static void openGallery(Activity activity) {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK);
        galleryIntent.setType("image/*");
        activity.startActivityForResult(galleryIntent, REQUEST_CODE_GALLERY);
    }


    /**
     * 打开系统相机
     *
     * @param activity activity
     */
    private static void openCamera(Activity activity) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
//            Intent cameraIntent = new Intent("android.media.action.IMAGE_CAPTURE");
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (tempFile != null)
                tempFile = null;
            // 创建资源标识,设置额外信息
            tempFile = new File(Environment.getExternalStorageDirectory(), System.currentTimeMillis() + ".jpg");

            /*
                FileUriExposedException
                6.0以后当你跨package域传递file://的URI时，接收者得到的将是一个无权访问的路径，
                因此，这将会触发FileUriExposedException.
                解决方法1: 使用ContentProvider传递uri(如下)
                解决方法2: 使用官方提供的FileProvider(较麻烦)
             */
//            Uri uri = Uri.fromFile(tempFile);
//            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
//            activity.startActivityForResult(cameraIntent, REQUEST_CODE_CAMERA);

            ContentValues contentValues = new ContentValues(1);
            contentValues.put(MediaStore.Images.Media.DATA, tempFile.getAbsolutePath());
            Uri uri = activity.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,contentValues);
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            activity.startActivityForResult(cameraIntent, REQUEST_CODE_CAMERA);
        } else {
            Toast.makeText(activity, "未检测到储存卡", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 根据返回的结果进行不同的解析,获取bitmap对象
     *
     * @param requestCode    请求码
     * @param resultCode     结果码
     * @param data           返回数据(里边封装这一个uri)
     * @param activity       当前activity
     * @param needCrop       获取完之后是否需要裁剪? true - 进入裁剪界面
     * @param needDeleteTemp 如果是拍照获取,获取完之后是否需要删除缓存照片? true - 删除
     * @return Bitmap对象, null - 解析失败(注意了返回的bitmap对象很容易OOM的)
     */
    public static Bitmap getBitmapFromResult(int requestCode, int resultCode, Intent data, Activity activity,
                                             int imageViewWidth, int imageViewHeight, boolean needCrop, boolean needDeleteTemp) {
        if (resultCode != Activity.RESULT_OK)
            return null;

        Bitmap bitmap = null;
        ContentResolver resolver = activity.getContentResolver();
        switch (requestCode) {
            // 从图库返回
            case REQUEST_CODE_GALLERY:
                if (data != null) {
                    // 根据返回的数据利用内容解决者解析出bitmap对象
                    Uri uri = data.getData();
                    if (needCrop)
                        crop(uri, activity);

                    else
                        try {
                            InputStream stream = resolver.openInputStream(uri);
                            bitmap = readBitmapFromStream(stream, imageViewWidth, imageViewHeight);
//                            bitmap = BitmapFactory.decodeStream(resolver.openInputStream(uri));
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }

                }
                break;

            // 从相机返回
            case REQUEST_CODE_CAMERA:
                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                    if (needCrop)
                        crop(Uri.fromFile(tempFile), activity);

                    else
                        try {
                            InputStream stream = resolver.openInputStream(Uri.fromFile(tempFile));
                            bitmap = readBitmapFromStream(stream, imageViewWidth, imageViewHeight);
//                            bitmap = BitmapFactory.decodeStream(resolver.openInputStream(Uri.fromFile(tempFile)));
                            if (needDeleteTemp)
                                try {
                                    tempFile.delete();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }

                }
                break;

            // 从剪切界面返回
            case REQUEST_CODE_CROP:
                if (data != null) {
                    bitmap = data.getParcelableExtra("data");
                }
                if (needDeleteTemp)
                    try {
                        tempFile.delete();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                break;

        }

        return bitmap;

    }


    /**
     * 剪切图片,可以自定义剪切参数
     *
     * @param uri 数据标识
     */
    private static void crop(Uri uri, Activity activity) {
        // 裁剪图片意图
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // 裁剪框的比例，pic1：pic1
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // 裁剪后输出图片的尺寸大小
        intent.putExtra("outputX", 250);
        intent.putExtra("outputY", 250);
        intent.putExtra("outputFormat", "JPEG");// 图片格式
        intent.putExtra("noFaceDetection", true);// 取消人脸识别
        intent.putExtra("return-data", true);
        activity.startActivityForResult(intent, REQUEST_CODE_CROP);
    }


    /**
     * 根据返回的结果进行不同的解析,获取文件路径
     * (如果调用此方法,拍照获取路径后需要手动调用deleteTemp方法删除缓存照片)
     *
     * @param requestCode 请求码
     * @param resultCode  结果码
     * @param data        返回数据(里边封装这一个uri)
     * @param activity    activity
     * @return 文件路径的字符串, "" / null - 获取失败
     */
    public static String getPathFromResult(int requestCode, int resultCode, Intent data, Activity activity) {

        if (resultCode != Activity.RESULT_OK)
            return null;

        String imgPath = null;
        switch (requestCode) {
            // 从图库返回
            case REQUEST_CODE_GALLERY:
                if (data != null) {
                    // 根据返回的数据利用内容解决者解析出文件路径
                    Uri uri = data.getData();
                    imgPath = getImagePath(uri, activity);
                }
                break;

            // 从相机返回
            case REQUEST_CODE_CAMERA:
                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
                    imgPath = tempFile.getPath();
                break;
        }
        // 这里如果删除了临时文件,返回的imgPath也没有意义了,因为文件已经没了,所以提供方法
        return imgPath;

    }

    /**
     * 根据图片的uri返回图片的路径
     *
     * @param uri      资源标识uri
     * @param activity activity界面
     * @return 图片的路径
     */
    private static String getImagePath(Uri uri, Activity activity) {
        String[] proj = {MediaStore.Images.Media.DATA};
        // 利用内容提供者去查询图片
        ContentResolver resolver = activity.getContentResolver();
        Cursor cursor = resolver.query(uri, proj, null, null, null);
        String path = "";
        if (cursor != null) {
            cursor.moveToFirst();
            path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            cursor.close();
        }
        return path;
    }

    /**
     * 带有裁剪功能的加载图片方法,如果你需要知道图片路径请调用另一个方法
     *
     * @param requestCode    请求码
     * @param resultCode     结果码
     * @param data           data数据
     * @param activity       Activity
     * @param view           需要加载的imageView
     * @param needCrop       是否需要裁剪
     * @param needDeleteTemp 是否需要删除缓存
     */
    public static void loadImageViewWithCrop(int requestCode, int resultCode, Intent data, Activity activity,
                                             ImageView view, boolean needCrop, boolean needDeleteTemp) {
        Bitmap bitmap = getBitmapFromResult(requestCode, resultCode, data, activity, view.getWidth(), view.getHeight(), needCrop, needDeleteTemp);
        if (bitmap != null)
            view.setImageBitmap(bitmap);
    }

    /**
     * 加载图片但是不支持裁剪功能,不过可以返回图片的路径
     *
     * @param requestCode 请求码
     * @param resultCode  结果吗
     * @param data        data数据
     * @param activity    Activity
     * @param view        需要加载的imageView
     * @return 图片的绝对路径
     */
    public static String loadImageView(int requestCode, int resultCode, Intent data, Activity activity, ImageView view) {
        String path = getPathFromResult(requestCode, resultCode, data, activity);
        if (path != null) {
            Bitmap bitmap = readBitmapFromFilePath(path, view.getWidth(), view.getHeight());
            view.setImageBitmap(bitmap);
            return path;
        } else
            return null;
    }

    /**
     * 获取缩放后的本地图片
     *
     * @param filePath 文件路径
     * @param width    要加载的imageView的宽
     * @param height   要加载的imageView的高
     * @return 缩放后的bitmap对象
     */
    private static Bitmap readBitmapFromFilePath(String filePath, int width, int height) {
        try {
            FileInputStream fis = new FileInputStream(filePath);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFileDescriptor(fis.getFD(), null, options);
            float srcWidth = options.outWidth;
            float srcHeight = options.outHeight;
            int inSampleSize = 1;

            if (srcHeight > height || srcWidth > width) {
                if (srcWidth > srcHeight) {
                    inSampleSize = Math.round(srcHeight / height);
                } else {
                    inSampleSize = Math.round(srcWidth / width);
                }
            }

            options.inJustDecodeBounds = false;
            options.inSampleSize = inSampleSize;

            return BitmapFactory.decodeFileDescriptor(fis.getFD(), null, options);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * 获取缩放后的本地图片
     *
     * @param inputStream 携带图片数据的流
     * @param width       要加载的imageView的宽
     * @param height      要加载的imageView的高
     * @return 缩放后的bitmap对象
     */
    private static Bitmap readBitmapFromStream(InputStream inputStream, int width, int height) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(inputStream, null, options);
        float srcWidth = options.outWidth;
        float srcHeight = options.outHeight;
        int inSampleSize = 1;

        if (srcHeight > height || srcWidth > width) {
            if (srcWidth > srcHeight) {
                inSampleSize = Math.round(srcHeight / height);
            } else {
                inSampleSize = Math.round(srcWidth / width);
            }
        }

        options.inJustDecodeBounds = false;
        options.inSampleSize = inSampleSize;

        return BitmapFactory.decodeStream(inputStream, null, options);
    }

    /**
     * 如果是拍照获取的图片,调用此方法删除缓存照片
     *
     * @return 删除是否成功
     */
    public static boolean deleteTemp() {
        boolean result = false;
        if (tempFile != null)
            result = tempFile.delete();
        return result;
    }

    /**
     * 申请权限返回结果时调用,用户是否同意
     *
     * @param requestCode  之前申请权限的请求码
     * @param permissions  申请的权限
     * @param grantResults 依次申请的结果
     * @param activity     activity
     */
    public static void onActRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                                     @NonNull int[] grantResults, Activity activity) {
        switch (requestCode) {
            // 如果是请求打开相机的请求码
            case REQUEST_PERMISSION_CAMERA_6_0:
                // 因为你可以一次申请多个权限, 所以返回结果的是一个数组
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    openCamera(activity);
                else
                    Toast.makeText(activity, "请先允许拍照权限 !", Toast.LENGTH_SHORT).show();
                break;

            case REQUEST_PERMISSION_SDCARD_6_0:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    openGallery(activity);
                else
                    Toast.makeText(activity, "请先允许读取内存权限 !", Toast.LENGTH_SHORT).show();
                break;
        }

    }

}
