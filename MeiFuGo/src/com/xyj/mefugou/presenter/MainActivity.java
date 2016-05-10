package com.xyj.mefugou.presenter;

import android.os.Bundle;
import android.view.View;
import com.zhonghua.dileber.mvp.presenter.ActivityPresenter;
import com.xyj.mefugou.R;
import com.xyj.mefugou.delegate.MainViewDelegate;
import com.xyj.mefugou.scm.IMainScm;
import com.xyj.mefugou.scm.MainScm;

public class MainActivity extends ActivityPresenter<MainViewDelegate>  {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        IMainScm mainSrc = new MainScm();

    }

    @Override
    protected Class<MainViewDelegate> getDelegateClass() {
        return MainViewDelegate.class;
    }

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
    }


    @Override
    public void onClick(View view) {

    }
}