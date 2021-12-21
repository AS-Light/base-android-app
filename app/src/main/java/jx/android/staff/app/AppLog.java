package jx.android.staff.app;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.util.Log;

/**
 *  Log打印工具
 *  1、通过AppInfo中的FLAG_DEBUGGABLE标志位判断是否打印Log
 *  2、对于过长的Log进行分段打印
 */
public class AppLog {

    private final static String TAG = "mmf_agent";

    public static boolean isDebugVersion = true;

    public static void init() {
        isDebugVersion = isDebugVersion(AppContext.getInstance());
    }

    public static void e(String log) {
        if (isDebugVersion) {
            int segmentSize = 3 * 1024;
            long length = log.length();
            if (length > segmentSize) {
                // 如果log长度过长，循环分段打印日志
                while (log.length() > segmentSize) {
                    String logContent = log.substring(0, segmentSize);
                    log = log.replace(logContent, "");
                    Log.e(TAG, logContent);
                }
            }
            Log.e(TAG, log);
        }
    }

    private static boolean isDebugVersion(Context context) {
        try {
            ApplicationInfo info = context.getApplicationInfo();
            return (info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
