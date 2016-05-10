package com.xyj.mefugou.presenter;

import android.os.Bundle;
import android.view.View;
import com.zhonghua.dileber.mvp.presenter.ActivityPresenter;
import com.xyj.mefugou.R;
import com.xyj.mefugou.delegate.RegistViewDelegate;
import com.xyj.mefugou.scm.IRegistScm;
import com.xyj.mefugou.scm.RegistScm;

public class RegistActivity extends ActivityPresenter<RegistViewDelegate>  {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        IRegistScm registSrc = new RegistScm();

    }

    @Override
    protected Class<RegistViewDelegate> getDelegateClass() {
        return RegistViewDelegate.class;
    }

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
    }


    @Override
    public void onClick(View view) {

    }
}