package com.xyj.mefugou.presenter.mine;

import android.os.Bundle;
import android.view.View;
import com.zhonghua.dileber.mvp.presenter.ActivityPresenter;
import com.xyj.mefugou.R;
import com.xyj.mefugou.delegate.mine.MineViewDelegate;
import com.xyj.mefugou.scm.mine.IMineScm;
import com.xyj.mefugou.scm.mine.MineScm;
import com.zhonghua.dileber.tools.annotation.CloseTitle;

@CloseTitle
public class MineActivity extends ActivityPresenter<MineViewDelegate>  {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        IMineScm mineSrc = new MineScm();

    }

    @Override
    protected Class<MineViewDelegate> getDelegateClass() {
        return MineViewDelegate.class;
    }

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
    }


    @Override
    public void onClick(View view) {

    }
}