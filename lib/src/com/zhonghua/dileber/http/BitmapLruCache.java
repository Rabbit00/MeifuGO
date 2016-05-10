
package com.zhonghua.dileber.http;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.LruCache;

import com.android.volley.toolbox.ImageLoader;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class BitmapLruCache extends LruCache<String, Bitmap> implements ImageLoader.ImageCache {

    private DiskLruCache mDiskLruCache;

    public BitmapLruCache(int maxsize) {
        super(maxsize);

        // 开启图片的本地缓存
        openDiskLruCache();
    }

    private void openDiskLruCache() {
        // 开启本地的缓存
        try {
            mDiskLruCache = DiskLruCache.open(CacheConfig.getDiskCacheDir(),
                    CacheConfig.getVersionCode(), 1, CacheConfig.DISK_MAX_SIZE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected int sizeOf(String key, Bitmap bitmap) {
        return bitmap.getRowBytes() * bitmap.getHeight();
    }

    @Override
    public Bitmap getBitmap(String urlKey) {
        // 先从内存中获取
        Bitmap bitmap = get(urlKey);

        // 内存中没有，从本地获取
        if (bitmap == null && mDiskLruCache != null && !mDiskLruCache.isClosed()) {
            String key = Md5.md5(urlKey);
            try {
                if (mDiskLruCache.get(key) != null)
                    bitmap = getBitmapFromDiskLruCache(key);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return bitmap;
        // return mCache.get(url);
    }

    @Override
    public void putBitmap(String urlKey, Bitmap bitmap) {
        put(urlKey, bitmap);

        // 放入本地缓存
        String key = Md5.md5(urlKey);
        try {
            if (bitmap != null && mDiskLruCache != null && !mDiskLruCache.isClosed() && null == mDiskLruCache.get(key)) {
                putBitmapToDiskLruCache(key, bitmap);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Bitmap getBitmapFromDiskLruCache(String key) {
        try {
            DiskLruCache.Snapshot snapshot = mDiskLruCache.get(key);
            if (snapshot != null) {
                InputStream inputStream = snapshot.getInputStream(0);
                if (inputStream != null) {
                    Bitmap bmp = BitmapFactory.decodeStream(inputStream);
                    inputStream.close();
                    return bmp;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void putBitmapToDiskLruCache(String key, Bitmap bitmap) {
        try {
            DiskLruCache.Editor editor = mDiskLruCache.edit(key);
            if (editor != null) {
                OutputStream outputStream = editor.newOutputStream(0);
                // 压缩为PNG，防止丢失透明信息
                bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
                editor.commit();

                outputStream.close();

                mDiskLruCache.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void removeDiskCache(String url) {
        if (!mDiskLruCache.isClosed())
            openDiskLruCache();

        try {
            mDiskLruCache.remove(Md5.md5(url));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void clearDiskCache() {
        if (mDiskLruCache.isClosed())
            openDiskLruCache();

        try {
            mDiskLruCache.delete();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 删除后重新打开
        openDiskLruCache();
    }

    public void closeCache() {
        // 清除内存
        evictAll();

        // 关闭本地
        if (mDiskLruCache != null && !mDiskLruCache.isClosed()) {
            try {
                mDiskLruCache.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
