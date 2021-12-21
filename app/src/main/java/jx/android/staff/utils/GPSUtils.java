package jx.android.staff.utils;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.net.Uri;
import android.provider.Settings;

import jx.android.staff.dialog.CommonTitleDialog;

public class GPSUtils {

    public static void openGPS(Context context) {
        Intent GPSIntent = new Intent();
        GPSIntent.setClassName("com.android.settings",
                "com.android.settings.widget.SettingsAppWidgetProvider");
        GPSIntent.addCategory("android.intent.category.ALTERNATIVE");
        GPSIntent.setData(Uri.parse("custom:3"));
        try {
            PendingIntent.getBroadcast(context, 0, GPSIntent, 0).send();
        } catch (PendingIntent.CanceledException e) {
            e.printStackTrace();
        }
    }

    public static void openGPSSettingsIfGPSClose(Context context, OnDialogCancelListener onCancelListener) {
        CommonTitleDialog dialog = new CommonTitleDialog(context,
                "为了更精确的为您提供房源信息，请您打开GPS",
                "不允许",
                "允许",
                true,
                new CommonTitleDialog.OnActionListener() {
                    @Override
                    public void onConfirm() {
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        context.startActivity(intent);
                    }

                    @Override
                    public void onCancel() {
                        onCancelListener.onCancel();
                    }
                });
        dialog.show();
    }

    public static boolean checkGPSIsOpen(Context context) {
        boolean isOpen;
        @SuppressLint("ServiceCast") LocationManager locationHelper = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        isOpen = locationHelper.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER);
        return isOpen;
    }

    public interface OnDialogCancelListener {
        void onCancel();
    }
}
