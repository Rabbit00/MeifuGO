package com.zhonghua.dileber.mvp.scm;

import android.widget.ImageView;
import com.android.volley.Request;
import com.android.volley.toolbox.NetworkImageView;
import com.zhonghua.dileber.http.HttpListener;
import com.zhonghua.dileber.http.HttpManager;
import com.zhonghua.dileber.http.INetWork;

import java.util.Map;

/**
 * Created by shidawei on 16/1/17.
 */
public class Scm implements INetWork {

    protected final static Integer TYPE_GET = 0;

    protected final static Integer TYPE_POST = 1;

    HttpManager httpManager = HttpManager.getInstance();

    @Override
    public <T> void netWork(String url, Map<String, Object> map, Class<T> clazz, HttpListener<T> listener, Integer type) {
        int method = Request.Method.GET;
        if(type==null||type.equals(TYPE_GET)){
            method =  Request.Method.GET;
        }else if(type.equals(TYPE_POST)){
            method =  Request.Method.POST;
        }
        httpManager.requestGson(method, url, map, listener, clazz);
    }

    /**
     *  已经有networkImageView支持～～这个要废弃掉了
     * @param imageView
     * @param url
     * @param errorImage
     */
    @Deprecated
    @Override
    public void netWorkForImage(ImageView imageView, String url, int errorImage) {
        httpManager.requestImage(imageView, url, errorImage);
    }

}
