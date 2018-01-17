package com.shiqkuangsan.mycustomviews.utils;

/**
 * Created by shiqkuangsan on 2016/9/6.
 */

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by shiqkuangsan on 2018/1/17. <p>
 * ClassName: ChoosePicUtil <p>
 * Author: shiqkuangsan <p>
 * Description:
 */
public class ChoosePicUtil {

    /**
     * 打开图库选择获取图片的匹配码
     */
    public static final int MATCHING_CODE_GALLERY = 111;

    /**
     * 使用相机拍照获取图片的匹配码
     */
    public static final int MATCHING_CODE_CAMERA = 222;

    /**
     * 从图库获取的请求码
     */
    private static final int REQUEST_CODE_GALLERY = 333;

    /**
     * 从相机获取的请求码
     */
    private static final int REQUEST_CODE_CAMERA = 444;

    /**
     * 进入裁剪界面的请求码
     */
    private static final int REQUEST_CODE_CROP = 555;

    /**
     * 6.0相机权限请求码
     */
    private static final int REQUEST_PERMISSION_CAMERA_6_0 = 666;

    /**
     * 6.0读写SD卡权限请求码
     */
    private static final int REQUEST_PERMISSION_SDCARD_6_0 = 777;

    /**
     * 拍照图片暂存文件对象, 默认是外部存储卡下每次当前时间+camera.jpg
     */
    private static String temppath_camera = "";

    /**
     * 裁剪图片暂存文件对象, 默认是外部存储卡下每次当前时间+crop.jpg
     */
    private static String temppath_crop = "";

    /**
     * 裁剪后输出的图片宽度 px 值
     */
    private static int pixelWidthCrop = 200;

    /**
     * 裁剪后输出的图片高度 px 值
     */
    private static int pixelHeightCrop = 200;

    /**
     * 根据不同匹配码确定是从图库选择还是相机获取,并启动相应activity
     *
     * @param MATCHING_CODE 匹配码, MATCHING_CODE_GALLERY - 图库获取, MATCHING_CODE_CAMERA - 相机获取
     */
    public static void startActivityFor(int MATCHING_CODE, final Activity activity) {
        switch (MATCHING_CODE) {

            // 图库获取
            case MATCHING_CODE_GALLERY:
                // 6.0调用图库处理
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

            // 拍照获取
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
                Toast.makeText(activity, "匹配码异常!", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    /**
     * 打开系统图库
     *
     * @param activity activity
     */
    private static void openGallery(Activity activity) {
//        Intent galleryIntent = new Intent(Intent.ACTION_PICK);
//        galleryIntent.setType("image/*");
//        activity.startActivityForResult(galleryIntent, REQUEST_CODE_GALLERY);
        Intent pickIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        activity.startActivityForResult(pickIntent, REQUEST_CODE_GALLERY);
    }

    /**
     * 打开系统相机
     *
     * @param activity activity
     */
    private static void openCamera(Activity activity) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            // 创建资源标识,设置额外信息
            temppath_camera = Environment.getExternalStorageDirectory().getAbsolutePath()
                    + "/" + String.valueOf(System.currentTimeMillis()) + "camera.jpg";
            File tempFile_camera = new File(temppath_camera);

            /*
                FileUriExposedException
                6.0以后当你跨package域传递file://的URI时，接收者得到的将是一个无权访问的路径，
                因此，这将会触发FileUriExposedException.
                解决方法1: 使用ContentProvider传递uri(如下)
                解决方法2: 使用官方提供的FileProvider(较麻烦)
             */
//            Uri uri = Uri.fromFile(tempFile_camera); // 这里会报错
//            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
//            activity.startActivityForResult(cameraIntent, REQUEST_CODE_CAMERA);

            // 解决
            // 方式1
//            Uri uri;
//            if (Build.VERSION.SDK_INT < 24) {
//                uri = Uri.fromFile(tempFile_camera);
//            } else {
//                ContentValues contentValues = new ContentValues(1);
//                contentValues.put(MediaStore.Images.Media.DATA, tempFile_camera.getAbsolutePath());
//                uri = activity.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
//            }
//            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
//            activity.startActivityForResult(cameraIntent, REQUEST_CODE_CAMERA);

            // 方式2 (需要在清单文件中配置provider并添加相应xml文件)
            Uri uri;
            if (Build.VERSION.SDK_INT < 24) {
                uri = Uri.fromFile(tempFile_camera);
            } else {
                uri = FileProvider.getUriForFile(activity, activity.getPackageName() + ".provider", tempFile_camera);
                cameraIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            }
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            activity.startActivityForResult(cameraIntent, REQUEST_CODE_CAMERA);
        } else {
            Toast.makeText(activity, "存储设备异常", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 根据返回的结果进行不同的解析,获取文件路径
     *
     * @param requestCode 请求码
     * @param resultCode  结果码
     * @param data        返回数据(里边封装一个uri)
     * @param activity    activity
     * @param needCrop    是否需要裁剪
     * @return 文件路径的字符串, 默认的是裁剪后的图片路径, 不是原图
     */
    public static String onActivityResult(int requestCode, int resultCode, Intent data, Activity activity, boolean needCrop) throws IOException {

        if (resultCode != Activity.RESULT_OK) {
            // 模拟器裁剪的时候可能就算你点了保存返回码也不是RESULT_OK
            return "Canceled or ResultCode does not match 'RESULT_OK' !";
        }

        switch (requestCode) {
            // 从图库返回
            case REQUEST_CODE_GALLERY:
                if (data != null) {

                    if (needCrop) {
                        startCrop(data.getData(), activity);
                    } else {

                        if (Build.VERSION.SDK_INT >= 19) {
                            //4.4及以上系统使用这个方法处理图片
                            return handlerImageAfterKitKat(activity, data);
                        } else {
                            //4.4以下系统使用这个方法处理图片
                            return handlerImageBeforeKitKat(activity, data);
                        }
                    }
                }
                break;

            // 从相机返回
            case REQUEST_CODE_CAMERA:
                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                    if (needCrop) {
                        startCrop(FileProvider.getUriForFile(activity, activity.getPackageName() + ".provider", new File(temppath_camera)), activity);
                    } else {
                        return temppath_camera;
                    }
                } else {
                    Toast.makeText(activity, "存储设备异常!", Toast.LENGTH_SHORT).show();
                }
                break;

            //裁剪界面返回
            case REQUEST_CODE_CROP:
                return temppath_crop;
        }

        return "";

    }

    private static String handlerImageBeforeKitKat(Activity activity, Intent data) {
        Uri uri = data.getData();
        return getImagePath(uri, activity, null);
    }

    @TargetApi(19)
    private static String handlerImageAfterKitKat(Activity activity, Intent data) {
        String imagePath = null;
        Uri uri = data.getData();
        if (DocumentsContract.isDocumentUri(activity, uri)) {
            //如果是document类型的Uri,则通过document id处理
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1];//解析出数字格式的id
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, activity, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                imagePath = getImagePath(contentUri, activity, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            //如果是content类型的URI，则使用普通方式处理
            imagePath = getImagePath(uri, activity, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            //如果是file类型的Uri,直接获取图片路径即可
            imagePath = uri.getPath();
        }
        return imagePath;
    }

    /**
     * 剪切图片,可以自定义剪切参数
     *
     * @param uri      数据标识
     * @param activity context对象
     */
    private static void startCrop(Uri uri, Activity activity) throws IOException {
        // 创建File对象，用于存储裁剪后的图片，避免更改原图
        temppath_crop = Environment.getExternalStorageDirectory().getAbsolutePath()
                + "/" + String.valueOf(System.currentTimeMillis()) + "crop.jpg";
        File tempFile_crop = new File(temppath_crop);

        Uri outputUri = Uri.fromFile(tempFile_crop);
        Intent intent = new Intent("com.android.camera.action.CROP");
        if (Build.VERSION.SDK_INT >= 24) {
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
        intent.setDataAndType(uri, "image/*");
        // 裁剪框的宽高比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("crop", "true");//可裁剪
        // 裁剪后输出图片的尺寸大小
        intent.putExtra("outputX", pixelWidthCrop);
        intent.putExtra("outputY", pixelHeightCrop);
        intent.putExtra("scale", true);//支持缩放
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, outputUri);// 剪切后输出图片位置
        intent.putExtra("outputFormat", "JPEG");//输出图片格式
        intent.putExtra("noFaceDetection", true);//取消人脸识别
        activity.startActivityForResult(intent, REQUEST_CODE_CROP);
    }

    /**
     * 根据图片的uri返回图片的路径
     *
     * @param uri      资源标识uri
     * @param activity activity界面
     * @return 图片的路径
     */
    private static String getImagePath(Uri uri, Activity activity, String selection) {
        String[] proj = {MediaStore.Images.Media.DATA};
        // 利用内容提供者去查询图片
        ContentResolver resolver = activity.getContentResolver();
        Cursor cursor = resolver.query(uri, proj, selection, null, null);
        String path = "";
        if (cursor != null) {
            cursor.moveToFirst();
            path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            cursor.close();
        }
        return path;
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
     * 删除拍照的缓存图片, 请用完再删, 不然onResult返回的 path 就无效了
     *
     * @return 删除是否成功
     */
    public static boolean deleteCameraTemp() {
        File file = new File(temppath_camera);
        return file.delete();
    }

    /**
     * 删除裁剪的缓存图片, 请用完再删, 不然onResult返回的 path 就无效了
     *
     * @return 删除是否成功
     */
    public static boolean deleteCropTemp() {
        File file = new File(temppath_crop);
        return file.delete();
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
