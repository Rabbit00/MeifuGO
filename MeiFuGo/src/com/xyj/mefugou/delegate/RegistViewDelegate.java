package com.xyj.mefugou.delegate;

import com.zhonghua.dileber.mvp.view.AppViewDelegate;
import com.xyj.mefugou.R;

/**
 * Created by  on 16/1/17.
 */
public class RegistViewDelegate extends AppViewDelegate implements IRegistView{


    @Override
    public int getRootLayoutId() {
        return R.layout.activity_regist;
    }


    @Override
    public void initWidget() {
        super.initWidget();
    }

}