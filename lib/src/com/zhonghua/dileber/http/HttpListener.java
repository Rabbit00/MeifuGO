package com.zhonghua.dileber.http;

import com.android.volley.Response;
import com.android.volley.VolleyError;

/**
 * Created by shidawei on 16/1/20.
 */
public abstract class HttpListener<T> implements Response.Listener<T>, Response.ErrorListener {

    protected abstract void onSuccess(T response);

    protected abstract void onFailure(VolleyError error);

    @Override
    public void onResponse(T response) {
        onSuccess(response);
    }

    @Override
    public void onErrorResponse(VolleyError error) {

        onFailure(error);
    }

}
