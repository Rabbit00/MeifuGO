package com.xyj.mefugou.presenter.community;

import android.os.Bundle;
import android.view.View;
import com.zhonghua.dileber.mvp.presenter.ActivityPresenter;
import com.xyj.mefugou.R;
import com.xyj.mefugou.delegate.community.CommunityViewDelegate;
import com.xyj.mefugou.scm.community.ICommunityScm;
import com.xyj.mefugou.scm.community.CommunityScm;
import com.zhonghua.dileber.tools.annotation.CloseTitle;

@CloseTitle
public class CommunityActivity extends ActivityPresenter<CommunityViewDelegate>  {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ICommunityScm communitySrc = new CommunityScm();

    }

    @Override
    protected Class<CommunityViewDelegate> getDelegateClass() {
        return CommunityViewDelegate.class;
    }

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
    }


    @Override
    public void onClick(View view) {

    }
}