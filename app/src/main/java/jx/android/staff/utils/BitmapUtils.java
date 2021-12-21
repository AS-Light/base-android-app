package jx.android.staff.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.util.Log;

import jx.android.staff.app.Config;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class BitmapUtils {

    private final static String TAG = "BitmapUtils";

    /**
     * 根据文件名,从Assets中取图片
     *
     * @param context
     * @param fileName 例如:game_bg.png
     * @return
     */
    public static Bitmap getBitmapFromAsset(Context context, String fileName) {
        Bitmap bmp = null;
        if (StringUtils.isEmpty(fileName)) {
            return null;
        }
        AssetManager asm = context.getAssets();
        if (asm == null) {
            return bmp;
        }
        InputStream is = null;
        try {
            is = asm.open(fileName);
            bmp = BitmapFactory.decodeStream(is);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bmp;
    }

    public static Bitmap getBitmapFromRes(Context context, int vectorDrawableId) {
        Bitmap bitmap = null;
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            Drawable vectorDrawable = context.getDrawable(vectorDrawableId);
            bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(),
                    vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            vectorDrawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            vectorDrawable.draw(canvas);
        } else {
            bitmap = BitmapFactory.decodeResource(context.getResources(), vectorDrawableId);
        }
        return bitmap;
    }

    public static Bitmap drawable2Bitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        } else if (drawable instanceof NinePatchDrawable) {
            Bitmap bitmap = Bitmap
                    .createBitmap(
                            drawable.getIntrinsicWidth(),
                            drawable.getIntrinsicHeight(),
                            drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                                    : Bitmap.Config.RGB_565);
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
                    drawable.getIntrinsicHeight());
            drawable.draw(canvas);
            return bitmap;
        } else {
            return null;
        }
    }

    /**
     * 根据路径，以流的形式，在sdcard中读取图片
     *
     * @param filename
     * @return
     */
    public static Bitmap getBitmapFromSdcard(String filename) {
        if (StringUtils.isEmpty(filename)) {
            return null;
        }

        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeFile(filename);
        } catch (OutOfMemoryError e) {
            if (bitmap != null) {
                if (!bitmap.isRecycled()) {
                    bitmap.recycle();
                }
                bitmap = null;
            }
        } catch (Exception e) {
            if (bitmap != null) {
                if (!bitmap.isRecycled()) {
                    bitmap.recycle();
                }
                bitmap = null;
            }
        }
        return bitmap;
    }

    /**
     * 更改bitmap的大小，按照指定的宽度
     *
     * @param bmp
     * @param targetWidth
     * @return
     */
    public static Bitmap resizeBitmapByWidth(Bitmap bmp, int targetWidth) {

        if (bmp == null || bmp.isRecycled()) {
            return null;
        }
        Bitmap bmResult = null;
        try {
            // 原始图片宽度
            float width = bmp.getWidth();
            // 原始图片高度
            float height = bmp.getHeight();

            Matrix m1 = new Matrix();

            float targetHeight = height * targetWidth / width;
            // 这里指的是目标区域（不是目标图片）
            m1.postScale(targetWidth / width, targetHeight / height);
            // 声明位图
            bmResult = Bitmap.createBitmap(bmp, 0, 0, (int) width,
                    (int) height, m1, true);

        } catch (Exception e) {
            if (bmResult != null) {
                if (!bmResult.isRecycled()) {
                    bmResult.recycle();
                    bmResult = null;
                }
            }
            bmResult = bmp;
        }
        return bmResult;
    }

    /**
     * 更改bitmap的宽,高
     *
     * @param bmp
     * @param targetWidth
     * @param targetHeight
     * @return
     */
    public static Bitmap resizeBitmap(Bitmap bmp, int targetWidth,
                                      int targetHeight) {

        if (bmp == null || bmp.isRecycled()) {
            return null;
        }
        Bitmap bmResult = null;
        try {
            // 原始图片宽度
            float width = bmp.getWidth();
            // 原始图片高度
            float height = bmp.getHeight();

            Matrix m1 = new Matrix();
            // 这里指的是目标区域（不是目标图片）
            m1.postScale(targetWidth / width, targetHeight / height);
            // 声明位图
            bmResult = Bitmap.createBitmap(bmp, 0, 0, (int) width,
                    (int) height, m1, true);

        } catch (Exception e) {
            if (bmResult != null) {
                if (!bmResult.isRecycled()) {
                    bmResult.recycle();
                    bmResult = null;
                }
            }
            bmResult = bmp;
        }
        return bmResult;
    }

    /**
     * 按某个比例缩放图片
     *
     * @param bmp
     * @param ratio
     * @return
     */
    public static Bitmap resizeBitmap(Bitmap bmp, float ratio) {

        if (bmp == null || bmp.isRecycled()) {
            return null;
        }
        Bitmap bmResult = null;

        try {
            // 图片宽度
            float width = bmp.getWidth();
            // 图片高度
            float height = bmp.getHeight();

            Matrix m1 = new Matrix();
            m1.postScale(ratio, ratio);
            // 声明位图
            bmResult = Bitmap.createBitmap(bmp, 0, 0, (int) width,
                    (int) height, m1, true);

        } catch (Exception e) {
            if (bmResult != null) {
                if (!bmResult.isRecycled()) {
                    bmResult.recycle();
                    bmResult = null;
                }
            }
            bmResult = bmp;
        }

        return bmResult;
    }

    public static Bitmap resizeBitmapByOldRatio(Bitmap bitmap, int maxWidth, int maxHeight) {

        int originWidth = bitmap.getWidth();
        int originHeight = bitmap.getHeight();

        // no need to resize
        if (originWidth < maxWidth && originHeight < maxHeight) {
            return bitmap;
        }

        int width = originWidth;
        int height = originHeight;

        // 若图片过宽, 则保持长宽比缩放图片
        if (originWidth > maxWidth) {
            width = maxWidth;

            double i = originWidth * 1.0 / maxWidth;
            height = (int) Math.floor(originHeight / i);

            bitmap = Bitmap.createScaledBitmap(bitmap, width, height, false);
        }

        // 若图片过长, 则从上端截取
        if (height > maxHeight) {
            height = maxHeight;
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height);
        }

//        Log.i(TAG, width + " width");
//        Log.i(TAG, height + " height");

        return bitmap;
    }

    /**
     * 将图片存入Sdcard中指定路径
     *
     * @param bitmap
     * @param path
     * @return
     */
    public static boolean saveBitmapToSdcard(Bitmap bitmap, String path) {
        if (bitmap == null || StringUtils.isEmpty(path)) {
            return false;
        }
        File file = null;
        FileOutputStream fos = null;
        try {
            file = new File(path);
            if (file.exists()) {
                file.delete();
            }
            //
            fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            return true;
        } catch (Exception e) {
            //
        } finally {
            if (null != fos) {
                try {
                    fos.close();
                } catch (Exception e) {
                }
            }
        }
        return false;
    }

    /**
     * 处理头像（填充圆角）
     *
     * @param bitmap
     * @param roundPx
     * @return
     */
    public static Bitmap getCornerBitmap(Bitmap bitmap, float roundPx) {
        if (bitmap == null) {
            return null;
        }
        Bitmap output = null;

        try {
            output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(),
                    Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(output);
            final Rect rect = new Rect(0, 0, bitmap.getWidth(),
                    bitmap.getHeight());
            final RectF rectF = new RectF(rect);

            final Paint paint = new Paint();
            paint.setAntiAlias(true);
            canvas.drawARGB(0, 0, 0, 0);
            canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            canvas.drawBitmap(bitmap, rect, rect, paint);

            return output;
        } catch (OutOfMemoryError e) {
            if (output != null) {
                if (!output.isRecycled()) {
                    output.recycle();
                    output = null;
                }
            }
            output = bitmap;
            //
            e.printStackTrace();

        }
        return null;
    }

    /**
     * 镜像效果mirror
     *
     * @param originalImage
     * @return
     */
    public static Bitmap getMirrorImage(Bitmap originalImage) {
        if (originalImage == null) {
            return null;
        }

        Bitmap bitmapWithReflection = null;
        try {

            final int reflectionGap = 2;
            int width = originalImage.getWidth();
            int height = originalImage.getHeight();

            Matrix matrix = new Matrix();

            matrix.preScale(1, -1);

            Bitmap reflectionImage = Bitmap.createBitmap(originalImage, 0,
                    height / 2, width, height / 2, matrix, false);

            bitmapWithReflection = Bitmap.createBitmap(width,
                    (height + height / 6), Bitmap.Config.ARGB_8888);

            Canvas canvas = new Canvas(bitmapWithReflection);

            canvas.drawBitmap(originalImage, 0, 0, null);

            canvas.drawBitmap(reflectionImage, 0, height + reflectionGap, null);

            Paint paint = new Paint();

            LinearGradient shader = new LinearGradient(0, height, 0,
                    bitmapWithReflection.getHeight() + reflectionGap + 20,
                    0xffffffff, 0x00ffffff, Shader.TileMode.MIRROR);

            paint.setShader(shader);
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));

            canvas.drawRect(0, height, width, bitmapWithReflection.getHeight()
                    + reflectionGap, paint);
        } catch (OutOfMemoryError e) {
            if (bitmapWithReflection != null) {
                if (!bitmapWithReflection.isRecycled()) {
                    bitmapWithReflection.recycle();
                    bitmapWithReflection = null;
                }
            }
            bitmapWithReflection = originalImage;
            //
            e.printStackTrace();

        }
        return bitmapWithReflection;
    }

    public static boolean mosaic(final Bitmap srcBitmap, final int radius, Bitmap dstBitmap) {
        try {
            Log.d(TAG, "mosaic srcBitmap = " + srcBitmap + ", radius = " + radius + ", dstBitmap = " + dstBitmap);
            int w = srcBitmap.getWidth();
            int h = srcBitmap.getHeight();
            int[] srcData = new int[w * h];
            int[] dstData = new int[w * h];
            int midRadius = radius >> 1;
            int srcX, srcY;
//          int[] srcData = srcBitmap.mBuffer;

            Log.d(TAG, "mosaic:w= " + w + ",h=" + h);
            long startTime = System.currentTimeMillis();
            srcBitmap.getPixels(srcData, 0, w, 0, 0, w, h);

            for (int j = 0; j < h; j++) {
                for (int i = 0; i < w; i++) {
                    srcX = i - i % radius + midRadius;
                    srcY = j - j % radius + midRadius;
                    if (srcX >= w) {
                        srcX = w - 1;
                    }
                    if (srcY >= h) {
                        srcY = h - 1;
                    }
                    dstData[j * w + i] = srcData[srcY * w + srcX];
                }
            }
            Log.d(TAG, "exe time = " + (System.currentTimeMillis() - startTime));
            dstBitmap.setPixels(dstData, 0, w, 0, 0, w, h);
            Log.d(TAG, "setPixels time = " + (System.currentTimeMillis() - startTime));

        } catch (ArrayIndexOutOfBoundsException e) {
            // TODO: handle exception
            Log.e(TAG, "ArrayIndexOutOfBoundsException = " + e);
            return false;
        } catch (IllegalStateException e) {
            Log.e(TAG, "IllegalStateException = " + e);
            return false;
        } catch (Exception e) {
            Log.e(TAG, "Exception = " + e);
            return false;
        }

        return true;
    }

    public static boolean mosaic2(final Bitmap srcBitmap, final int radius, Bitmap dstBitmap) {
        try {
            Log.d(TAG, "mosaic srcBitmap = " + srcBitmap + ", radius = " + radius + ", dstBitmap = " + dstBitmap);
            int w = srcBitmap.getWidth();
            int h = srcBitmap.getHeight();
            int srcData = 0;
//          int dstData = 0;
            int midRadius = radius >> 1;
            int srcX, srcY;

            Log.d(TAG, "mosaic:w= " + w + ",h=" + h);
            long startTime = System.currentTimeMillis();
//          srcBitmap.getPixels(srcData, 0, w, 0, 0, w, h);


            for (int j = 0; j < h; j++) {
                for (int i = 0; i < w; i++) {
                    srcX = i - i % radius + midRadius;
                    srcY = j - j % radius + midRadius;
                    if (srcX >= w) {
                        srcX = w - 1;
                    }
                    if (srcY >= h) {
                        srcY = h - 1;
                    }
                    srcData = srcBitmap.getPixel(srcX, srcY);
                    dstBitmap.setPixel(i, j, srcData);
//                  dstData[j*w + i] = srcData[srcY * w + srcX];
                }
            }
            Log.d(TAG, "exe time = " + (System.currentTimeMillis() - startTime));
//          dstBitmap.setPixels(dstData, 0, w, 0, 0, w, h);
//          Log.d(TAG, "setPixels time = " + (System.currentTimeMillis() - startTime));

        } catch (ArrayIndexOutOfBoundsException e) {
            // TODO: handle exception
            Log.e(TAG, "ArrayIndexOutOfBoundsException = " + e);
            return false;
        } catch (IllegalStateException e) {
            Log.e(TAG, "IllegalStateException = " + e);
            return false;
        } catch (Exception e) {
            Log.e(TAG, "Exception = " + e);
            return false;
        }

        return true;
    }

    /**
     * 获取Bitmap二进制
     */
    public static byte[] getBitmapByte(Bitmap bitmap) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
        try {
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return out.toByteArray();
    }

    /**
     * 将图片保存到相册
     *
     * @param bmp     获取的bitmap数据
     * @param picName 自定义的图片名
     */
    public static void saveBmp2Gallery(Context context, Bitmap bmp, String picName, Bitmap.CompressFormat format) {
        String fileName = null;
        // 声明文件对象
        File file = null;
        // 声明输出流
        FileOutputStream outStream = null;

        file = new File(Config.getLocalGalleryPath(picName));
        try {
            if (file.exists()) {
                file.delete();
                MediaUtils.removeImageFromLib(context, file.getAbsolutePath());
                Log.e("mmf", "图片已存在，删除");
            }

            // 如果有目标文件，直接获得文件对象，否则创建一个以filename为名称的文件
            // 获得文件相对路径
            fileName = file.toString();
            // 获得输出流，如果文件中有内容，追加内容
            outStream = new FileOutputStream(fileName);
            if (null != outStream) {
                bmp.compress(format, 90, outStream);
            }

        } catch (Exception e) {
            e.getStackTrace();
        } finally {
            try {
                if (outStream != null) {
                    outStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //通知相册更新
        MediaUtils.sendScanFileBroadcast(context, file.getAbsolutePath());
    }

    public static Bitmap getBitmapFromGallery(Context context, String picName) {
        try {
            File file = new File(Config.getLocalGalleryPath(picName));
            Uri uri = Uri.fromFile(file);
            if (Objects.requireNonNull(uri.getScheme()).compareTo("content") == 0) {
                ContentResolver cr = context.getContentResolver();
                Cursor cursor = cr.query(uri, null, null, null, null);// 根据Uri从数据库中找
                if (cursor != null) {
                    cursor.moveToFirst();
                    String filePath = cursor.getString(cursor.getColumnIndex("_data"));// 获取图片路径
                    cursor.close();
                    if (filePath != null) {
                        return BitmapFactory.decodeFile(filePath);
                    }
                }
            } else if (uri.getScheme().compareTo("file") == 0) {
                return BitmapFactory.decodeFile(uri.toString().replace("file://", ""));
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    public static void updateFileFromDatabase(Context context, String fileName) {
        String where = MediaStore.Audio.Media.DATA + " like \"" + fileName + "%\"";
        int i = context.getContentResolver().delete(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, where, null);
        if (i > 0) {
            Log.e(TAG, "媒体库更新成功！");
        }
    }
}