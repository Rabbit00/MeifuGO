package com.zhonghua.dileber.http;

import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.ByteArrayPool;
import com.android.volley.toolbox.HttpStack;

/**
 * Created by shidawei on 16/1/21.
 */
public class SBaseNetWork extends BasicNetwork{

    public SBaseNetWork(HttpStack httpStack) {
        super(httpStack);
    }

    public SBaseNetWork(HttpStack httpStack, ByteArrayPool pool) {
        super(httpStack, pool);
    }
}
