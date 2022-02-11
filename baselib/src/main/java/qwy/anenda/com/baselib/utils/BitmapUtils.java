package qwy.anenda.com.baselib.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.view.View;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by LiuBin on 2020/7/6.
 * 关于Bitmap的一些操作
 */
@RequiresApi(api = Build.VERSION_CODES.KITKAT)
class BitmapUtils {

    /**
     * 获取缩放后的本地图片
     *
     * @param filePath 文件路径
     * @param width    宽
     * @param height   高
     */
    @Nullable
    public static Bitmap readBitmapFromFile(String filePath, int width, int height) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);
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

        return BitmapFactory.decodeFile(filePath, options);
    }

    /**
     * 获取缩放后的本地图片
     *
     * @param filePath 文件路径
     * @param width    宽
     * @param height   高
     */
    @Nullable
    public static Bitmap readBitmapFromFileDescriptor(@Nullable String filePath, int width, int height) {
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
        } catch (OutOfMemoryError | Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * 获取缩放后的本地图片
     *
     * @param ins    输入流
     * @param width  宽
     * @param height 高
     */
    @Nullable
    public static Bitmap readBitmapFromInputStream(@Nullable InputStream ins, int width, int height) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(ins, null, options);
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

        return BitmapFactory.decodeStream(ins, null, options);
    }


    /**
     * Res资源加载方式
     */
    @Nullable
    public static Bitmap readBitmapFromResource(Resources res, int resourcesId, int w, int h) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resourcesId, options);
        float srcWidth = options.outWidth;
        float srcHeight = options.outHeight;
        int inSampleSize = 1;

        if (srcHeight > h || srcWidth > w) {
            if (srcWidth > srcHeight) {
                inSampleSize = Math.round(srcHeight / h);
            } else {
                inSampleSize = Math.round(srcWidth / w);
            }
        }

        options.inJustDecodeBounds = false;
        options.inSampleSize = inSampleSize;

        return BitmapFactory.decodeResource(res, resourcesId, options);
    }

    /**
     * 采用decodeStream代替decodeResource更节约内存
     */
    @Nullable
    public static Bitmap readBitmapFromResource2(Resources res, int resourcesId, int w, int h) {
        InputStream ins = res.openRawResource(resourcesId);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(ins, null, options);
        float srcWidth = options.outWidth;
        float srcHeight = options.outHeight;
        int inSampleSize = 1;

        if (srcHeight > h || srcWidth > w) {
            if (srcWidth > srcHeight) {
                inSampleSize = Math.round(srcHeight / h);
            } else {
                inSampleSize = Math.round(srcWidth / w);
            }
        }

        options.inJustDecodeBounds = false;
        options.inSampleSize = inSampleSize;

        return BitmapFactory.decodeStream(ins, null, options);
    }

    /**
     * 获取缩放后的本地图片
     *
     * @param filePath 文件路径,即文件名称
     */
    @Nullable
    public static Bitmap readBitmapFromAssetsFile(Context context, String filePath) {
        Bitmap image = null;
        AssetManager am = context.getResources().getAssets();
        try {
            InputStream is = am.open(filePath);
            image = BitmapFactory.decodeStream(is);
            is.close();
        } catch (OutOfMemoryError | IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    /**
     * 从二进制数据读取图片
     */
    @Nullable
    public static Bitmap readBitmapFromByteArray(byte[] data, int width, int height) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeByteArray(data, 0, data.length, options);
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

        return BitmapFactory.decodeByteArray(data, 0, data.length, options);
    }

    /**
     * Drawable转化成Bitmap
     */
    @Nullable
    public static Bitmap drawableToBitmap(Drawable drawable) {
        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();
        Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ?
                Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565;
        Bitmap bitmap = Bitmap.createBitmap(w, h, config);
        if (bitmap == null) {
            return null;
        }
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, w, h);
        drawable.draw(canvas);
        return bitmap;
    }

    /**
     * Bitmap转换成Drawable
     */
    @NonNull
    public static Drawable bitmapToDrawable(Resources resources, Bitmap bm) {
        return new BitmapDrawable(resources, bm);
    }

    /**
     * Bitmap转换成byte[]
     */
    public byte[] bitmap2Bytes(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }

    /**
     * byte[]转换成Bitmap
     */
    @Nullable
    @Deprecated
    public Bitmap bytes2Bitmap(byte[] b) {
        return BitmapFactory.decodeByteArray(b, 0, b.length);
    }

    /**
     * InputStream转换成Bitmap
     */
    @Nullable
    @Deprecated
    public Bitmap inputStream2Bitmap(InputStream is) {
        return BitmapFactory.decodeStream(is);
    }

    /**
     * inputStream转byte[]
     * 感觉是个很危险的操作
     */
    public byte[] inputStream2Bytes(InputStream is) {
        byte[] b = new byte[1024 * 2];
        int len = 0;
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            while ((len = is.read(b, 0, b.length)) != -1) {
                baos.write(b, 0, len);
                baos.flush();
            }
            return baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将Bitmap保存为本地文件
     */
    public static void writeBitmapToFile(String filePath, Bitmap b, int quality) {
        File desFile = new File(filePath);
        try (
                FileOutputStream fos = new FileOutputStream(desFile);
                BufferedOutputStream bos = new BufferedOutputStream(fos)
        ) {
            b.compress(Bitmap.CompressFormat.JPEG, quality, bos);
            bos.flush();
        } catch (OutOfMemoryError | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 图片压缩
     */
    @Nullable
    private static Bitmap compressImage(Bitmap image) {
        if (image == null) {
            return null;
        }
        try (
                ByteArrayOutputStream baos = new ByteArrayOutputStream()
        ) {
            image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] bytes = baos.toByteArray();
            ByteArrayInputStream isBm = new ByteArrayInputStream(bytes);
            return BitmapFactory.decodeStream(isBm);
        } catch (OutOfMemoryError | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 图片缩放
     * 根据scale生成一张图片
     *
     * @param scale 等比缩放值
     */
    @Nullable
    public static Bitmap bitmapScale(Bitmap bitmap, float scale) {
        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale); // 长和宽放大缩小的比例
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        return Bitmap.createBitmap(bitmap, 0, 0, w, h, matrix, true);
    }

    /**
     * 读取照片exif信息中的旋转角度
     *
     * @param path 照片路径
     * @return 角度
     */
    private static int readPictureDegree(String path) {
        if (TextUtils.isEmpty(path)) {
            return 0;
        }
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return degree;
    }

    /**
     * 设置图片旋转角度
     */
    @Nullable
    private static Bitmap rotateBitmap(Bitmap b, float rotateDegree) {
        if (b == null) {
            return null;
        }
        Matrix matrix = new Matrix();
        matrix.postRotate(rotateDegree);
        int w = b.getWidth();
        int h = b.getHeight();
        return Bitmap.createBitmap(b, 0, 0, w, h, matrix, true);
    }

    /**
     * 通过图片id获得Bitmap
     */
    @Nullable
    @Deprecated
    public static Bitmap getBitmapById(@NonNull Context ctx, int id) {
        return BitmapFactory.decodeResource(ctx.getResources(), id);
    }

    /**
     * 有时候会返回null
     */
    @Nullable
    public static Bitmap convertViewToBitMap(View view) {
        // 打开图像缓存
        view.setDrawingCacheEnabled(true);
        // 必须调用measure和layout方法才能成功保存可视组件的截图到png图像文件
        // 测量View大小
        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        // 发送位置和尺寸到View及其所有的子View
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        // 获得可视组件的截图
        return view.getDrawingCache();
    }

    /**
     * 巧妙的使用draw函数 将view的内容绘制出来
     */
    @NonNull
    public static Bitmap getBitmapFromView(View view) {
        Bitmap returnedBitmap = Bitmap.createBitmap(
                view.getWidth(),
                view.getHeight(),
                Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(returnedBitmap);

        // 没有背景的话 这里可以设置个填充色
//        Drawable bgDrawable = view.getBackground();
//        if(bgDrawable == null){
//            canvas.drawColor(Color.WHITE);
//        }

        view.draw(canvas);
        return returnedBitmap;
    }

    /**
     * 放大缩小图片
     * todo 不确定怎么回事，建议不用
     */
    @Nullable
    @Deprecated
    public static Bitmap zoomBitmap(@Nullable Bitmap bitmap, int w, int h) {
        if (bitmap == null) {
            return null;
        }
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Matrix matrix = new Matrix();
        float scaleWidth = ((float) w / width);
        float scaleHeight = ((float) h / height);
        matrix.postScale(scaleWidth, scaleHeight);
        return Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
    }

    /**
     * 圆形图片
     */
    @Nullable
    public static Bitmap circleBitmap(@Nullable Bitmap b) {
        if (b == null) {
            return null;
        }
        int radius = Math.max(b.getWidth(), b.getHeight());
        return getRoundedCornerBitmap(b, radius);
    }

    /**
     * 圆角图片
     *
     * @param roundPx 小于0 直接返回原来的Bitmap
     */
    @Nullable
    public static Bitmap getRoundedCornerBitmap(@Nullable Bitmap bitmap, float roundPx) {
        if (bitmap == null) {
            return null;
        }

        if (roundPx <= 0) {
            return bitmap;
        }

        int w = bitmap.getWidth();
        int h = bitmap.getHeight();

        // 绘制透明背景
        Bitmap output = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, w, h);
        final RectF rectF = new RectF(rect);

        // 绘制圆角矩形
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        // 绘制图片
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }
}