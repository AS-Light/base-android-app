package jx.android.staff.utils;

import android.bluetooth.BluetoothAdapter;

public class BluetoothUtils {

    public static boolean isBluetoothAlive() {
        BluetoothAdapter blueadapter = BluetoothAdapter.getDefaultAdapter();
        return blueadapter != null && blueadapter.isEnabled();
    }

    public static boolean aliveBluetooth() {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter != null) {
            return bluetoothAdapter.enable();
        }
        return false;
    }
}
