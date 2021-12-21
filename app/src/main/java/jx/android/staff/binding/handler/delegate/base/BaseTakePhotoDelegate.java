package jx.android.staff.binding.handler.delegate.base;

import jx.android.staff.api.model.local.PhotoEdit;

public interface BaseTakePhotoDelegate extends Delegate {
    int getPhotoCount();

    void deletePhoto(PhotoEdit photo);

    void preTakePhoto();
}
