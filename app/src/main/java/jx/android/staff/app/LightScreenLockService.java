package jx.android.staff.app;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;

import com.dh.bluelock.imp.BlueLockPubCallBackBase;
import com.dh.bluelock.object.LEDevice;
import com.dh.bluelock.pub.BlueLockPub;
import com.dh.bluelock.util.Constants;

//亮屏开锁
public class LightScreenLockService extends Service {
	private BlueLockPub blueLockPub;
	private LockCallBack lockCallback;
	private boolean hasScannedDaHaoLock;

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		/* 注册屏幕唤醒时的广播 */
		IntentFilter mScreenOnFilter = new IntentFilter(
				"android.intent.action.SCREEN_ON");
		LightScreenLockService.this.registerReceiver(mScreenOReceiver,
				mScreenOnFilter);
		/* 注册机器锁屏时的广播 */
		IntentFilter mScreenOffFilter = new IntentFilter(
				Intent.ACTION_SCREEN_OFF);
		LightScreenLockService.this.registerReceiver(mScreenOReceiver,
				mScreenOffFilter);
		initView();
		initData();
	}

	private void initData() {
		lockCallback = new LockCallBack();
	}

	private void initView() {
		blueLockPub = BlueLockPub.bleLockInit(this);
		int result = blueLockPub.bleInit(this);
		// 0: ok -4: not support ble, -5: bluetooth not enabled.
		if (0 != result) {
			return;
		}
	}

	public static String Int2HexStr(long val, int len) {
		String result = Long.toHexString(val).toUpperCase();
		int r_len = result.length();
		if (r_len > len) {
			return result.substring(r_len - len, r_len);
		}
		if (r_len == len) {
			return result;
		}
		StringBuffer strBuff = new StringBuffer(result);
		char zerochar = '0';
		for (int i = 0; i < len - r_len; i++) {
			strBuff.insert(0, zerochar);
		}
		return strBuff.toString();
	}

	// 十进制转16进制
	public String intToHex(long n) {
		char[] ch = new char[20];
		int nIndex = 0;
		while (true) {
			long m = n / 16;
			long k = n % 16;
			if (k == 15)
				ch[nIndex] = 'F';
			else if (k == 14)
				ch[nIndex] = 'E';
			else if (k == 13)
				ch[nIndex] = 'D';
			else if (k == 12)
				ch[nIndex] = 'C';
			else if (k == 11)
				ch[nIndex] = 'B';
			else if (k == 10)
				ch[nIndex] = 'A';
			else
				ch[nIndex] = (char) ('0' + k);
			nIndex++;
			if (m == 0)
				break;
			n = m;
		}
		StringBuffer sb = new StringBuffer();
		sb.append(ch, 0, nIndex);
		sb.reverse();
		String strHex = new String("");
		strHex += sb.toString();
		return strHex;
	}

	public void onDestroy() {
		blueLockPub.setLockMode(Constants.LOCK_MODE_NONE, null, true);
		super.onDestroy();
		LightScreenLockService.this.unregisterReceiver(mScreenOReceiver);
	}

	private BroadcastReceiver mScreenOReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (action.equals("android.intent.action.SCREEN_ON")) {
				blueLockPub.addResultCallBack(lockCallback);
				blueLockPub.oneKeyOpenDeviceUserId(null, null, null, "", "");
			} else if (action.equals(Intent.ACTION_SCREEN_OFF)) {
				blueLockPub.removeResultCallBack(lockCallback);
				blueLockPub.setLockMode(Constants.LOCK_MODE_NONE, null, false);
			} else if (action.equals(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)) {

			} else if (action.equals(Intent.ACTION_USER_PRESENT)) {
				blueLockPub.removeResultCallBack(lockCallback);
			}
		}
	};

	class LockCallBack extends BlueLockPubCallBackBase {
		@Override
		public void openCloseDeviceCallBack(final int result,
				final int battery, final String... params) {
			// TODO Auto-generated method stub
			String str = "";
			if (null != params && params.length > 0) {
				str = Long.parseLong(params[0], 16) + "";
			}
			if (0 == result) {
				// showToast(hasScannedDaHaoLock + "成功" + result);

			} else {
				// showToast(hasScannedDaHaoLock + "失败" + result);
			}
			hasScannedDaHaoLock = false;
		}

		@Override
		public void disconnectDeviceCallBack(int result, int status) {
			// TODO Auto-generated method stub
		}

		@Override
		public void scanDeviceCallBack(LEDevice ledevice, int result, int rssi) {
			// TODO Auto-generated method stub
			Log.i("ledevice", "ledevice  " + ledevice.getDeviceId());
			hasScannedDaHaoLock = true;
		}
	}
}