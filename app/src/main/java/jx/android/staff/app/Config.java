package jx.android.staff.app;

import android.os.Environment;

import jx.android.staff.utils.DateUtils;

import java.io.File;

public class Config {
    // todo: 测试地址 目前是11
//        public final static String API_URL = "http://192.168.0.117:8201/";
//    public final static String API_URL = "http://192.168.0.11:8001/api/v1/agentapp/";
//        public final static String API_URL = "http://192.168.0.52:8201/";
    public final static String API_URL = "http://192.168.8.111:8080/";
//        public final static String API_URL = "http://192.168.0.11:28001/api/v1/agentapp/";

    //    public final static String API_URL = "http://192.168.0.51:8001/api/v1/agentapp/";

    // todo: 正式地址
//     public final static String API_URL = "https://www.maifang.com/api/v2/agentapp/";
//    public final static String API_URL = "https://www.miaomaifang.com/api/v4/agentapp/";
    //public final static String API_URL = "https://www.miaomaifang.com/api/v5/agentapp/";
    public final static String API_STATIC_URL = "https://www.miaomaifang.com/h5/";

    // todo: 房源模板web路径
    public final static String HOUSE_TEMPLATE_URL = API_URL + "share/tmpl/housesourcetemplate";
    public final static String HOUSE_TEMPLATE_MUL_URL = API_URL + "share/tmpl/housesourcetemplatemultiple";
    //腾讯地图 WebService
    public final static String TX_API_URL = "https://apis.map.qq.com/";

    //后台服务器（支付相关地址可能为其他项目地址）
    public final static String PAY_URL = API_URL;


    public static String getAbsoluteH5Path(String partPath) {
        return API_STATIC_URL + partPath;
    }

    public static String getAbsoluteImagePath(String partPath) {
        return "https://snailhome.oss-cn-huhehaote.aliyuncs.com" + partPath + "@!noResize";
    }

    public static String getAbsoluteFullLengthPortraitImagePath(String partPath) {
        return "https://snailhome.oss-cn-huhehaote.aliyuncs.com" + partPath + "@!noResize";
    }

    public final static String QR_CODE = "qr";

    public static String getLocalQrImagePath(String name) {
        return getLocalQrImagePath() + name;
    }

    public static String getLocalQrImagePath() {
        String path = getLocalImagePath() + QR_CODE + "/";
        if (!new File(path).exists()) {
            new File(path).mkdirs();
        }
        return path;
    }

    public static String getLocalImagePath() {
        String basePath = Environment.getExternalStorageDirectory().getAbsolutePath();
        basePath += "/mmf/image/";
        if (!new File(basePath).exists()) {
            new File(basePath).mkdirs();
        }
        return basePath;
    }

    public static String getLocalGalleryPath(String name) {
        return getLocalGalleryPath() + name;
    }

    public static String getLocalGalleryPath() {
        String basePath = Environment.getExternalStorageDirectory()
                + File.separator + "mmf"
                + File.separator + "image" + File.separator;
        if (!new File(basePath).exists()) {
            new File(basePath).mkdirs();
        }
        return basePath;
    }

    /**
     * Broadcast
     */
    public static final String FORCE_MAIN_PAGE_CHANGE = "jx.android.staff.force_main_page_change";
    public static final String FORCE_MAIN_NUM_CHANGE = "jx.android.staff.force_main_num_change";
    public static final String FORCE_MAIN_HOUSE_SEARCH_LOCK = "jx.android.staff.force_main_house_search_lock";
}
