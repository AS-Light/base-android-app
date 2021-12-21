package jx.android.staff.thirdparty.convenient.data;

public class BannerDataRoomTypeImageUrl extends BannerDataImageUrl {
    public final static int TYPE = 3;

    public BannerDataRoomTypeImageUrl(String imageUrl) {
        super(imageUrl);
    }

    @Override
    public int getType() {
        return TYPE;
    }
}
