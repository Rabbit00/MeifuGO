package com.xyj.mefugou.delegate;

import com.xyj.mefugou.R;
import com.zhonghua.dileber.mvp.view.AppViewDelegate;

/**
 * Created by  on 16/1/17.
 */
public class ChatViewDelegate extends AppViewDelegate implements IChatView{


    @Override
    public int getRootLayoutId() {
        return R.layout.activity_chat;
    }


    @Override
    public void initWidget() {
        super.initWidget();
    }

}