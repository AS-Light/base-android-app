package jx.android.staff.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Environment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import org.devio.takephoto.app.TakePhoto;
import org.devio.takephoto.compress.CompressConfig;
import org.devio.takephoto.model.CropOptions;

import jx.android.staff.R;

import java.io.File;


/**
 * 图片处理工具类
 *
 * @author mao_siyu
 */
public class PictureProcessUtil {

    /**
     * 创建图片处理工具对象
     */
    public static PictureProcessUtil newInstance(Activity context, int width, int height) {
        return new PictureProcessUtil(context, width, height);
    }

    /**
     * 传入 activity 对象
     */
    private Activity activity;
    private int width;
    private int height;

    private PictureProcessUtil(Activity context, int width, int height) {
        activity = context;
        this.width = width;
        this.height = height;
    }

    /**
     * 显示选择菜单，可选择，从相册选择图片 或 从相机选择图片
     *
     * @param view
     */
    Uri imageUri;

    /***
     * 添加照片弹出pop
     */
    PopupWindow popupWindow;
    TakePhoto mTakePhoto;

    public void showPopupMenu(View view, final TakePhoto takePhoto) {

        File file = new File(Environment.getExternalStorageDirectory(), "/temp/" + System.currentTimeMillis() + ".jpg");
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        imageUri = Uri.fromFile(file);
        mTakePhoto = takePhoto;
        configCompress(takePhoto);
        initPopWindow(view);
    }

    private void configCompress(TakePhoto takePhoto) {
        int maxSize = 2194304;
        boolean showProgressBar = true;
        CompressConfig config = new CompressConfig.Builder().setMaxPixel(maxSize).create();
        takePhoto.onEnableCompress(config, showProgressBar);
    }

    private CropOptions getCropOptions() {
        CropOptions.Builder builder = new CropOptions.Builder();
        builder.setAspectX(width).setAspectY(height);
        builder.setOutputX(width).setOutputY(height);
        builder.setWithOwnCrop(false);
        return builder.create();
    }

    private void initPopWindow(View view) {
        // 加载PopupWindow的布局文件
        View contentView = LayoutInflater.from(activity)
                .inflate(R.layout.popup_take_photo, null);
        contentView.measure(0, 0);
        // 声明一个对话框
        popupWindow = new PopupWindow(null, AppUtils.getScreenWidth(activity), contentView.getMeasuredHeight());
        // 为自定义的对话框设置自定义布局
        popupWindow.setContentView(contentView);
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        popupWindow.setBackgroundDrawable(dw);
        popupWindow.setFocusable(false);
        popupWindow.setOutsideTouchable(true);
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = 0.7f;
        activity.getWindow().setAttributes(lp);
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
        //拍照
        RelativeLayout take_picture = contentView.findViewById(R.id.take_picture);
        take_picture.setOnClickListener(new clickListene());
        //从相册选择
        RelativeLayout take_camera = contentView.findViewById(R.id.take_camera);
        take_camera.setOnClickListener(new clickListene());
        //取消
        RelativeLayout cancel = contentView.findViewById(R.id.cancel);
        cancel.setOnClickListener(new clickListene());
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
                lp.alpha = 1f;
                activity.getWindow().setAttributes(lp);
            }
        });

    }

    class clickListene implements View.OnClickListener {

        @Override
        public void onClick(View view) {

            switch (view.getId()) {
                case R.id.take_picture:
                    popupWindow.dismiss();
                    // 从相册中获取图片（裁剪）
                    mTakePhoto.onPickFromGalleryWithCrop(imageUri, getCropOptions());
                    break;
                case R.id.take_camera:
                    popupWindow.dismiss();
                    // 从相机获取图片(裁剪)
                    mTakePhoto.onPickFromCaptureWithCrop(imageUri, getCropOptions());
                    break;
                case R.id.cancel:
                    popupWindow.dismiss();
                    break;
                default:

            }

        }
    }

    private void bgAlpha(Context context, float f) {
        WindowManager.LayoutParams layoutParams = ((Activity) context).getWindow().getAttributes();
        layoutParams.alpha = f;
        ((Activity) context).getWindow().setAttributes(layoutParams);
    }
}
