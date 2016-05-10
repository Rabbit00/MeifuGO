package com.xyj.mefugou.presenter;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import com.xyj.mefugou.presenter.home.HomeActivity;
import com.zhonghua.dileber.mvp.presenter.ActivityPresenter;
import com.xyj.mefugou.R;
import com.xyj.mefugou.delegate.SplashViewDelegate;
import com.xyj.mefugou.scm.ISplashScm;
import com.xyj.mefugou.scm.SplashScm;
import com.zhonghua.dileber.mvp.presenter.FragmentActivityPresenter;
import com.zhonghua.dileber.tools.annotation.CloseStatusBar;
import com.zhonghua.dileber.tools.annotation.CloseTitle;

@CloseTitle
@CloseStatusBar
public class SplashActivity extends FragmentActivityPresenter<SplashViewDelegate> {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected Class<SplashViewDelegate> getDelegateClass() {
        return SplashViewDelegate.class;
    }

    ISplashScm splashSrc;
    splashhandler splashhandler;
    Handler x;

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        splashSrc = new SplashScm();
        x = new Handler();
        splashhandler = new splashhandler();
        splashSrc.splash();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (x != null && splashhandler != null) {
            x.removeCallbacks(splashhandler);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (x != null && splashhandler != null) {
            x.postDelayed(splashhandler, 1000);
        }
    }

    class splashhandler implements Runnable {

        public void run() {
            Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        }

    }


    @Override
    public void onClick(View view) {

    }
}