package com.zhonghua.dileber.http;

import android.content.SharedPreferences;
import android.util.Patterns;
import android.widget.ImageView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.zhonghua.dileber.data.PerfManager;
import okhttp3.OkHttpClient;

import java.util.Map;

/**
 * Created by shidawei on 16/1/17.
 */
public class HttpManager {

    private static volatile HttpManager mInstance;

    public static HttpManager getInstance() {
        if (mInstance == null) {
            synchronized (HttpManager.class) {
                if (mInstance == null)
                    mInstance = new HttpManager();
            }
        }
        return mInstance;
    }

    public static synchronized void clearResource() {
        if (mInstance != null) {
            synchronized (HttpManager.class) {
                if (mInstance != null)
                    mInstance.clearVolleyResource();
                    mInstance = null;
            }
        }
    }

    public void clearVolleyResource() {
        // 关闭队列
        if (mRequestQueue != null)
            mRequestQueue.stop();
    }

    /**
     * 检查网址是否是真
     * @param url
     * @return
     */
    public static boolean isValidUrl(String url) {
//        try {
//            return Patterns.WEB_URL.matcher(url).matches();
//        } catch (Exception e) {
//            // 解析错误，非正常Url
//            e.printStackTrace();
//        }
        return true;
    }

    private RequestQueue mRequestQueue;

    private HttpManager() {

        //mRequestQueue = Volley.newRequestQueue(SApplication.getAppContext());
        //mRequestQueue = new RequestQueue(CacheConfig.getDiskCache(), new SBaseNetWork(new OkHttpStack(new OkHttpClient())));
        mRequestQueue = new RequestQueue(CacheConfig.getDiskCache(), new SBaseNetWork(new HurlStack()));

        mRequestQueue.start();

        mNetworkImageLoader = new ImageLoader(mRequestQueue, CacheConfig.getBitmapCache());
    }

    public<T>void addRequestQueue(Request<T> request) {

        mRequestQueue.add(request);

    }


    public<T> void requestGson(int method,final String url,Map<String, Object> map, final HttpListener<T> httpListener,Class<T> clazz) {
        // url判断
        if (!isValidUrl(url)) {
            httpListener.onFailure(new VolleyError("ERROR: URL IS NOT EXITENT"));
            return;
        }
        GsonRequest<T> gsonRequest = new GsonRequest<T>(method,url,map,httpListener,clazz);
        addRequestQueue(gsonRequest);
    }

    private ImageLoader mNetworkImageLoader;

    public ImageLoader getNetworkImageLoader() {
        return mNetworkImageLoader;
    }

    // 获取图片
    public void requestImage(final ImageView imageView, final String url, final int errorImage) {
        if (!isValidUrl(url)) {
            imageView.setImageResource(errorImage);
            return;
        }
        getNetworkImageLoader().get(url, new HttpImage(imageView, errorImage));
    }

//    public boolean getCachedImages(NetworkImageView imageView, String url) {
//        if (!isValidUrl(url)) {
//            return false;
//        }
//
//        imageView.setImageUrl(url, getNetworkImageLoader());
//        return true;
//    }

    public boolean containImageCache(String url) {
        return getNetworkImageLoader().isCached(url, 0, 0);
    }


    public String putConstantKey(String key, String value) {
        PerfManager perfManager = PerfManager.getInstance();
        String token = (String)perfManager.getSystmPreferences(key,"");
        perfManager.putSystemPreferences(key, value);
        CommonParams.put(key, value);
        return token;
    }

}
