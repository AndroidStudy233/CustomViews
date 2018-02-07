package com.shiqkuangsan.mycustomviews.utils;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
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
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by shiqkuangsan on 2018/1/17. <br>
 * ClassName: ChoosePicUtil <br>
 * Author: shiqkuangsan <br>
 * Description: 简单的图片选择 Util, 一键从图库和拍照, 支持裁剪. 兼容6.0运行时权限, 兼容7.0Content uri协议, 暂且只支持 activit 中回调<br>
 * 用法:
 * <p>1. 需要从图库选择的时候: 调用ChoosePicUtil.startActivityFor(ChoosePicUtil.MATCHING_CODE_GALLERY, activity);方法</p>
 * <p>2. 需要拍照获取的时候: 调用ChoosePicUtil.startActivityFor(ChoosePicUtil.MATCHING_CODE_CAMERA, activity);</p>
 * <p>3. 在你的onRequestPermissionsResult方法中调用调用ChoosePicUtil.onRequestPermissionsResult, 在你onActivityResult中调用
 * ChoosePicUtil.onActivityResult(), 该方法返回绝对路径 path 的字符串.</p>
 * <p>tip1: 如果使用裁剪(裁剪参数在 startCrop 方法中), 那么返回的是裁剪后的文件路径, 如果需要删除缓存图片, 每次 <b>用完用完用完</b> 之后手动调用 deleteTemp 方法, .
 * 默认的拍照路径为: 存储卡+当前时间+camera.jpg, 默认的裁剪图片路径为: 存储卡+当前时间+crop.jpg. 分别在 openCamera 方法和 startCrop 方法中定义写死, 要改自行修改</p>
 * <p>tip2: 注意使用 util 之前要在清单文件中配置 provider, 并且提供 provider_paths 申明</p>
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
            tempFile_camera.getParentFile().mkdirs();

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
                        if (Build.VERSION.SDK_INT >= 19) {
                            startCrop(handlerImageAfter19(activity, data), activity);
                        } else {
                            startCrop(handlerImageBefore19(activity, data), activity);
                        }
                    } else {

                        if (Build.VERSION.SDK_INT >= 19) {
                            //4.4及以上系统使用这个方法处理图片
                            return handlerImageAfter19(activity, data);
                        } else {
                            //4.4以下系统使用这个方法处理图片
                            return handlerImageBefore19(activity, data);
                        }
                    }
                }
                break;

            // 从相机返回
            case REQUEST_CODE_CAMERA:
                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                    if (needCrop) {
                        startCrop(temppath_camera, activity);
//                        startCrop(FileProvider.getUriForFile(activity, activity.getPackageName() + ".provider", new File(temppath_camera)), activity);
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

    private static String handlerImageBefore19(Activity activity, Intent data) {
        Uri uri = data.getData();
        return getImagePath(uri, activity, null);
    }

    @TargetApi(19)
    private static String handlerImageAfter19(Activity activity, Intent data) {
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
     * @param inputPath 数据标识
     * @param activity  context对象
     */
    private static void startCrop(String inputPath, Activity activity) throws IOException {
        // 定义缓存路径, 创建File对象，用于存储裁剪后的图片，避免更改原图
        temppath_crop = Environment.getExternalStorageDirectory().getAbsolutePath()
                + "/" + String.valueOf(System.currentTimeMillis()) + "crop.jpg";
        File tempFile_crop = new File(temppath_crop);
        tempFile_crop.getParentFile().mkdirs();
//        Uri outputUri = getImageContentUri(activity, tempFile_crop);

        /*
            输入图片采用 contentUri 协议传输, 输出图片采用 FileUri 协议写入.
        */
        Uri inputUri;
//        Uri outputUri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            inputUri = FileProvider.getUriForFile(activity, activity.getPackageName() + ".provider", new File(inputPath));
//            outputUri = FileProvider.getUriForFile(activity, activity.getPackageName() + ".provider", tempFile_crop);
        } else {
            inputUri = Uri.fromFile(new File(inputPath));
//            outputUri = Uri.fromFile(tempFile_crop);
        }
        Intent cropIntent = new Intent("com.android.camera.action.CROP");

        if (Build.VERSION.SDK_INT >= 24) {
            cropIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
        cropIntent.setDataAndType(inputUri, "image/*");// inputFile
//        cropIntent.setDataAndType(getImageContentUri(activity, new File(inputPath)), "image/*");// inputFile
        // 裁剪框的宽高比例
        cropIntent.putExtra("aspectX", 1);
        cropIntent.putExtra("aspectY", 1);
        cropIntent.putExtra("crop", "true");//可裁剪
        // 裁剪后输出图片的尺寸大小
        cropIntent.putExtra("outputX", 200);
        cropIntent.putExtra("outputY", 200);
        cropIntent.putExtra("scale", true);//支持缩放
        cropIntent.putExtra("return-data", false);
        cropIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile_crop));// 剪切后输出图片位置outputFile
        cropIntent.putExtra("outputFormat", "JPEG");//输出图片格式
        cropIntent.putExtra("noFaceDetection", true);//取消人脸识别
        activity.startActivityForResult(cropIntent, REQUEST_CODE_CROP);
    }

    /**
     * 从 File 对象中获取 Uri
     *
     * @param context   上下文
     * @param imageFile file
     * @return Uri
     */
    public static Uri getImageContentUri(Context context, File imageFile) {
        String filePath = imageFile.getAbsolutePath();
        Cursor cursor = context.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Images.Media._ID},
                MediaStore.Images.Media.DATA + "=? ",
                new String[]{filePath}, null);

        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor
                    .getColumnIndex(MediaStore.MediaColumns._ID));
            Uri baseUri = Uri.parse("content://media/external/images/media");
            cursor.close();
            return Uri.withAppendedPath(baseUri, "" + id);
        } else {
            if (imageFile.exists()) {
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.DATA, filePath);
                return context.getContentResolver().insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            } else {
                return null;
            }
        }
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
    @Deprecated
    public static Bitmap readBitmapFromStream(InputStream inputStream, int width, int height) {
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
        temppath_camera = "";
        return file.delete();
    }

    /**
     * 删除裁剪的缓存图片, 请用完再删, 不然onResult返回的 path 就无效了
     *
     * @return 删除是否成功
     */
    public static boolean deleteCropTemp() {
        File file = new File(temppath_crop);
        temppath_crop = "";
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
