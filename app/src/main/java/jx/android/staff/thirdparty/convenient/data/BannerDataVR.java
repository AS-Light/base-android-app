package jx.android.staff.thirdparty.convenient.data;

import com.bigkoo.convenientbanner.data.BannerData;

public class BannerDataVR extends BannerData {
    public final static int TYPE = 2;

    public String imageUrl;
    public String vrUrl;

    @Override
    public int getType() {
        return TYPE;
    }

    public BannerDataVR(String imageUrl, String vrUrl) {
        this.imageUrl = imageUrl;
        this.vrUrl = vrUrl;
    }
}
