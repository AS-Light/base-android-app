package jx.android.staff.utils;

import com.amap.api.maps.model.LatLng;

/**
 * Created by Administrator on 2015/12/2.
 */
public class DistanceMethod {

    private final static double PI = 3.14159265358979323; // 圆周率
    private final static double R = 6371229; // 地球的半径

    public static long getDistance(LatLng start, LatLng end) {
        return getDistance(start.longitude, start.latitude, end.longitude, end.latitude);
    }

    public static long getDistance(double longt1, double lat1, double longt2, double lat2) {
        double x, y;
        long distance;
        x = (longt2 - longt1) * PI * R
                * Math.cos(((lat1 + lat2) / 2) * PI / 180) / 180;
        y = (lat2 - lat1) * PI * R / 180;
        distance = (long) Math.hypot(x, y);
        return distance;
    }

}
