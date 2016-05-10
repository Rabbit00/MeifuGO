package com.xyj.mefugou.presenter;

import android.os.Bundle;
import android.view.View;
import com.zhonghua.dileber.mvp.presenter.ActivityPresenter;
import com.xyj.mefugou.R;
import com.xyj.mefugou.delegate.LoginViewDelegate;
import com.xyj.mefugou.scm.ILoginScm;
import com.xyj.mefugou.scm.LoginScm;

public class LoginActivity extends ActivityPresenter<LoginViewDelegate>  {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ILoginScm loginSrc = new LoginScm();

    }

    @Override
    protected Class<LoginViewDelegate> getDelegateClass() {
        return LoginViewDelegate.class;
    }

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
    }


    @Override
    public void onClick(View view) {

    }
}