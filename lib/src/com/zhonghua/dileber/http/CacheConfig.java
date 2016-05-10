package com.zhonghua.dileber.http;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;

import com.android.volley.toolbox.DiskBasedCache;

import java.io.File;

public class CacheConfig {

    public static final int DISK_MAX_SIZE = 10 * 1024 * 1024;

    public static Context sContext;

    private static String DISK_CACHE_PATH;
    private static BitmapLruCache sBitmapCache;
    private static DiskBasedCache sDiskCache;

    public static void initCacheConfig(Context context) {
        sContext = context;
        DISK_CACHE_PATH = getExternalCacheDir("disk_cache");
    }

    public static int getVersionCode() {
        PackageInfo pInfo = null;
        try {
            pInfo = sContext.getPackageManager().getPackageInfo(sContext.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {

        }
        if (pInfo == null) {
            return 1;
        }
        return pInfo.versionCode;
    }

    public static String getExternalCacheDir(String path) {
        return Environment.getExternalStorageDirectory().getPath() + "/Android/data/" + sContext.getPackageName() + path;
    }

    public static int getMemoryCacheSize() {
        // 内存的缓存大小，默认为1/8
        return 1024 * 1024 * ((ActivityManager) sContext
                .getSystemService(Context.ACTIVITY_SERVICE)).getMemoryClass() / 8;
    }

    public static File getDiskCacheDir() {
        File cacheFile = new File(DISK_CACHE_PATH);
        if (!cacheFile.exists()) {
            cacheFile.mkdirs();
        }
        return cacheFile;
    }

    public static BitmapLruCache getBitmapCache() {
        if (sBitmapCache == null) {
            sBitmapCache = new BitmapLruCache(getMemoryCacheSize());
        }
        return sBitmapCache;
    }

    public static DiskBasedCache getDiskCache() {
        if (sDiskCache == null) {
            File cacheFile = new File(DISK_CACHE_PATH);
            if (!cacheFile.exists()) {
                cacheFile.mkdirs();
            }
            sDiskCache = new DiskBasedCache(cacheFile, DISK_MAX_SIZE);
        }
        return sDiskCache;
    }

    public static void clearCache() {
        // 清理内存
        if (sBitmapCache != null) {
            sBitmapCache.closeCache();
            sBitmapCache = null;
        }

        // 关闭volley里面网络请求资源
        HttpManager.clearResource();
    }

}
