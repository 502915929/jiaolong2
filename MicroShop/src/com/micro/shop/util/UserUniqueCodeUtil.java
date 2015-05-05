package com.micro.shop.util;

import android.content.Context;
import android.text.TextUtils;

/**
 * Created by 95 on 2015/5/4.
 */
public class UserUniqueCodeUtil {


    public static String getDeviceInfo(Context context) {
        try {
            android.telephony.TelephonyManager tm = (android.telephony.TelephonyManager) context
                    .getSystemService(Context.TELEPHONY_SERVICE);
            String device_id = tm.getDeviceId();
            android.net.wifi.WifiManager wifi = (android.net.wifi.WifiManager) context
                    .getSystemService(Context.WIFI_SERVICE);
            String mac = wifi.getConnectionInfo().getMacAddress();
            if (TextUtils.isEmpty(device_id)) {
                device_id = mac;
            }
            if (TextUtils.isEmpty(device_id)) {
                device_id = android.provider.Settings.Secure.getString(
                        context.getContentResolver(),
                        android.provider.Settings.Secure.ANDROID_ID);
            }
            return device_id;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
