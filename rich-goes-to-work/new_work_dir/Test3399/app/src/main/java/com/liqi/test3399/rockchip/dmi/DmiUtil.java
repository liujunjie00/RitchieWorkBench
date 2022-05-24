package com.liqi.test3399.rockchip.dmi;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.SystemClock;

public class DmiUtil {

	/*static {
		System.loadLibrary("getdmi");
	}*/

	private static native Object[] getdmi();

	public static com.liqi.test3399.rockchip.dmi.DmiInfo getDmiInfo(Context context, boolean regain) {
		DmiInfo dmiInfo = null;
		
		// use the cached infos.
		if (!regain) {
			dmiInfo = DmiInfo.getInstance();
		}
		
		// try to get infos.
		if (null == dmiInfo) {
			// enable wifi to get the mac addr.
			WifiManager wifiManager = (WifiManager) context
					.getSystemService(Context.WIFI_SERVICE);
			boolean wifiEnabled = wifiManager.isWifiEnabled();
			if (!wifiEnabled) {
				wifiManager.setWifiEnabled(true);
			}
			
			SystemClock.sleep(500);
			dmiInfo = com.liqi.test3399.rockchip.dmi.DmiInfo.getInstance(getdmi());
			
			if (!wifiEnabled) {
				wifiManager.setWifiEnabled(false);
			}
		}
		return dmiInfo;
	}
}
