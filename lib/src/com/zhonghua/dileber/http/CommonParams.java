package com.zhonghua.dileber.http;

import com.zhonghua.dileber.app.FrameContants;
import com.zhonghua.dileber.data.PerfManager;
import com.zhonghua.dileber.tools.HDevice;
import com.zhonghua.dileber.tools.HString;
import com.zhonghua.dileber.tools.SLog;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * Created by shidawei on 16/2/23.
 */

public final class CommonParams {

    private static volatile CommonParams ins;

    private static Map<String, Object> params = null;

    public static Object get(Object arg0) {
        buildParams();
        return params.get(arg0);
    }

    public static Object put(String arg0, Object arg1) {
        buildParams();
        return params.put(arg0, arg1);
    }

    public static int size() {
        buildParams();
        return params.size();
    }

    private CommonParams() {

    }

    public static Map<String, String> addCommonParams(Map<String, Object> mp) {

        if (params == null) {
            buildParams();
        }

        Map<String, String> ret = new HashMap<String, String>();
        {
            Set<Map.Entry<String, Object>> entries = params.entrySet();
            for (Map.Entry<String, Object> entry : entries) {
                putValue(ret, entry);
            }
        }
        if (mp != null) {
            if (mp.size() > 0) {
                Set<Map.Entry<String, Object>> entries = mp.entrySet();
                for (Map.Entry<String, Object> entry : entries) {
                    putValue(ret, entry);
                }
            }
        }
        SLog.i("request param:",ret);
        return ret;
    }

    private static void putValue(Map<String, String> ret, Map.Entry<String, Object> entry) {
        final Object value = entry.getValue();
        if (List.class.isInstance(value)) {
            List m = (List) value;
            for (int i = 0, n = m.size(); i < n; ++i) {
                ret.put(HString.concatObject(entry.getKey(), "[", i, "]"), HString.getStringValue(m.get(i)));
            }
        } else if (value.getClass().isEnum()) {
            Enum e = (Enum) value;
            ret.put(entry.getKey(), String.valueOf(e.ordinal()));
        } else {
            ret.put(entry.getKey(),HString.getStringValue(value));
        }
    }

    public static void clearParam() {
        params = null;
    }

    private static void buildParams() {
        if (params == null) {
            synchronized (CommonParams.class) {
                if (params == null) {
                    HDevice hDevice = HDevice.getInstance();
                    if (params == null) {
                        params = new HashMap<String, Object>();
                    }
                    params.put(FrameContants.SYSTEM_API_UUID, hDevice.getUUID());
                    params.put(FrameContants.SYSTEM_API_DEVICE_ID, hDevice.getSerialNumber());

                }
            }
        }
    }

}