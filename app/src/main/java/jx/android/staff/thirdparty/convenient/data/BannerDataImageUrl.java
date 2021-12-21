package jx.android.staff.thirdparty.convenient.data;

import com.bigkoo.convenientbanner.data.BannerData;

public class BannerDataImageUrl extends BannerData {
    public final static int TYPE = 1;

    public String imageUrl;

    @Override
    public int getType() {
        return TYPE;
    }

    public BannerDataImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
