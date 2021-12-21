package jx.android.staff.thirdparty.convenient.holder;

import android.content.Context;
import android.view.View;

import jx.android.staff.thirdparty.convenient.data.BannerDataVR;
import com.bigkoo.convenientbanner.holder.Holder;
import jx.android.staff.R;

import jx.android.staff.utils.AppUtils;
import jx.android.staff.utils.gyroscope.GyroscopeImageView;
import jx.android.staff.utils.gyroscope.GyroscopeTransFormation;
import com.squareup.picasso.Picasso;

public class VRHolderView extends Holder<BannerDataVR> {

    private GyroscopeImageView mImageView;

    public VRHolderView(View itemView) {
        super(itemView);
    }

    @Override
    protected void initView(View itemView) {
        mImageView = itemView.findViewById(R.id.image);
    }

    @Override
    public void updateUI(BannerDataVR data) {
        Context context = mImageView.getContext();
        //获取控件大小，作为拉伸参数
        int displayHeight = AppUtils.getScreenHeight(context);
        mImageView.post(() -> Picasso.get()
                .load(data.imageUrl)
                .transform(new GyroscopeTransFormation(displayHeight / 2 - 20, displayHeight / 2 - 20))
                .into(mImageView));
    }
}
