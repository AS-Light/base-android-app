package jx.android.staff.thirdparty.takephoto;

import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import org.devio.takephoto.app.TakePhoto;
import org.devio.takephoto.compress.CompressConfig;
import org.devio.takephoto.model.CropOptions;

import java.io.File;

public class TakePhotoHelper {

    private static String TAG = TakePhotoHelper.class.getName();

    public static TakePhotoHelper newInstance(TakePhoto takePhoto) {
        TakePhotoHelper helper = new TakePhotoHelper();
        helper.takePhoto = takePhoto;
        return helper;
    }

    public static TakePhotoHelper newInstance(TakePhoto takePhoto, int width, int height) {
        TakePhotoHelper helper = new TakePhotoHelper();
        helper.takePhoto = takePhoto;
        helper.width = width;
        helper.height = height;
        return helper;
    }

    private TakePhoto takePhoto;
    private int width = 1;
    private int height = 1;

    public void takeBySelect(int limit) {
        configCompress(takePhoto);
        takePhoto.onPickMultiple(limit);
    }

    public void takeBySelectWithCrop() {
        try {
            configCompress(takePhoto);
            takePhoto.onPickFromGalleryWithCrop(getTempUri(), getCropOptions());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void takeByCamera() {
        try {
            configCompress(takePhoto);
            takePhoto.onPickFromCapture(getTempUri());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void takeByCameraWithCrop() {
        try {
            configCompress(takePhoto);
            takePhoto.onPickFromCaptureWithCrop(getTempUri(), getCropOptions());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Uri getTempUri() throws Exception {
        File file = new File(Environment.getExternalStorageDirectory(), "/temp/" + System.currentTimeMillis() + ".jpg");
        if (!file.getParentFile().exists()) {
            if (!file.getParentFile().mkdirs()) {
                Log.e(TAG, "创建文件夹失败");
                throw new Exception("创建文件夹失败");
            }
        }
        try {
            return Uri.fromFile(file);
        } catch (Exception e) {
            throw e;
        }
    }

    private void configCompress(TakePhoto takePhoto) {
        int maxSize = 2194304;
        CompressConfig config = new CompressConfig.Builder().setMaxPixel(maxSize).create();
        takePhoto.onEnableCompress(config, true);
    }

    private CropOptions getCropOptions() {
        CropOptions.Builder builder = new CropOptions.Builder();
        builder.setAspectX(width).setAspectY(height);
        builder.setOutputX(width).setOutputY(height);
        builder.setWithOwnCrop(true);
        return builder.create();
    }

}
