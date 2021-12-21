package jx.android.staff.binding.handler.base;

import android.content.Context;

import jx.android.staff.binding.vm.base.BaseViewModel;
import com.library.alertview.AlertView;
import jx.android.staff.api.model.local.PhotoEdit;
import jx.android.staff.binding.handler.delegate.base.BaseTakePhotoDelegate;
import jx.android.staff.thirdparty.takephoto.TakePhotoHelper;

import org.devio.takephoto.app.TakePhoto;

public class BaseTakePhotoHandler<VM extends BaseViewModel, D extends BaseTakePhotoDelegate> extends BaseHandler<VM, D> {

    private TakePhotoHelper mTakePhotoHelper;
    private boolean withCrop;
    private int total;

    public BaseTakePhotoHandler(Context context) {
        super(context);
    }

    public BaseTakePhotoHandler(Context context, VM viewModel) {
        super(context, viewModel);
    }

    public BaseTakePhotoHandler(Context context, VM viewModel, D delegate) {
        super(context, viewModel, delegate);
    }

    public void init(TakePhoto takePhoto, int total) {
        this.withCrop = false;
        this.total = total;
        mTakePhotoHelper = TakePhotoHelper.newInstance(takePhoto);
    }

    public void init(TakePhoto takePhoto, int width, int height) {
        withCrop = true;
        mTakePhotoHelper = TakePhotoHelper.newInstance(takePhoto, width, height);
    }

    public void addPhoto() {
        // todo: 通过TakePhoto选择照片
        new AlertView(null, null, "取消", null, new String[]{"拍照", "相册"}, mContext, AlertView.Style.ActionSheet,
                (o, position) -> {
                    switch (position) {
                        case 0:
                            if (!withCrop) {
                                mTakePhotoHelper.takeByCamera();
                            } else {
                                mTakePhotoHelper.takeByCameraWithCrop();
                            }
                            break;
                        case 1:
                            if (!withCrop) {
                                mTakePhotoHelper.takeBySelect(total - mDelegate.getPhotoCount());
                            } else {
                                mTakePhotoHelper.takeBySelectWithCrop();
                            }
                            break;
                    }
                    mDelegate.preTakePhoto();
                }).show();
    }

    public void showPhoto(PhotoEdit photo) {

    }

    public void deletePhoto(PhotoEdit photo) {
        // todo: 移除一张照片
        mDelegate.deletePhoto(photo);
    }
}
