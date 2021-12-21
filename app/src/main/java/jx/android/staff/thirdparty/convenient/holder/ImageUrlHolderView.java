package jx.android.staff.thirdparty.convenient.holder;

import android.view.View;
import android.widget.ImageView;

import jx.android.staff.thirdparty.convenient.data.BannerDataImageUrl;
import com.bigkoo.convenientbanner.holder.Holder;
import com.bumptech.glide.Glide;
import jx.android.staff.R;

public class ImageUrlHolderView extends Holder<BannerDataImageUrl> {

    private ImageView mImageView;

    public ImageUrlHolderView(View itemView) {
        super(itemView);
    }

    @Override
    protected void initView(View itemView) {
        mImageView = itemView.findViewById(R.id.image);
    }

    @Override
    public void updateUI(BannerDataImageUrl data) {
        Glide.with(mImageView.getContext())
                .load(data.imageUrl)
                .placeholder(R.drawable.svg_photo_def)
                .error(R.drawable.svg_photo_def)
                .into(mImageView);
    }
}
