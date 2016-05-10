package com.zhonghua.dileber.tools;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.TextUtils;
import com.zhonghua.dileber.app.FrameContants;
import com.zhonghua.dileber.app.SApplication;
import com.zhonghua.dileber.data.PerfManager;

import java.util.UUID;

/**
 * Created by shidawei on 16/2/23.
 */
public class HDevice {

    private static volatile HDevice instance;

    private String mUUIDString;
    private PerfManager perfManager;
    private String macAddress;
    private WifiManager wifi;

    public static HDevice getInstance() {
        if (instance == null) {
            synchronized (HDevice.class) {
                instance = new HDevice();
            }
        }
        return instance;
    }

    public HDevice(){
        perfManager = PerfManager.getInstance();
        wifi = (WifiManager) SApplication.getAppContext().getSystemService(Context.WIFI_SERVICE);
    }

    public String getSerialNumber() {
        String serialNumber = "";
        serialNumber = SSystem.getOnlyId();
        if (serialNumber==null||serialNumber.equals("")) {
            serialNumber = "?";
        }
        return serialNumber;
    }

    public String getMacAddress() {
        if (macAddress == null) {
            WifiInfo info = wifi.getConnectionInfo();
            if (info != null) {
                macAddress = info.getMacAddress();
            }
        }
        return macAddress;

    }

    public String getUUID() {
        if (mUUIDString != null) {
            return mUUIDString;
        }

        String uuidString = (String)perfManager.getSystmPreferences(FrameContants.SYSTEM_PREFERANCE_UUID,null);
        if (uuidString != null) {
            mUUIDString = uuidString;
        } else {
            String mac = this.getMacAddress();
            UUID uuid;
            if (TextUtils.isEmpty(mac)) {
                uuid = UUID.randomUUID();
            } else {
                uuid = UUID.nameUUIDFromBytes(mac.getBytes());
            }
            mUUIDString = uuid.toString();
            perfManager.getSystmPreferences(FrameContants.SYSTEM_PREFERANCE_UUID, mUUIDString);
        }

        return mUUIDString;
    }



}
