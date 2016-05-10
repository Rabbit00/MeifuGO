package com.xyj.mefugou.delegate.community;

import android.content.Intent;
import android.view.View;
import com.xyj.mefugou.delegate.MenuViewDelegate;
import com.xyj.mefugou.presenter.ListArtActivity;
import com.xyj.mefugou.scm.IArticleScm;
import com.zhonghua.dileber.mvp.view.AppViewDelegate;
import com.xyj.mefugou.R;

/**
 * Created by  on 16/1/17.
 */
public class CommunityViewDelegate extends MenuViewDelegate implements ICommunityView{


    @Override
    public int getRootLayoutId() {
        return R.layout.activity_community;
    }


    @Override
    public void initWidget() {
        super.initWidget();
        setOnClickListener(this,R.id.community_lvyou,R.id.community_xinyu,R.id.community_tongcheng);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        Intent it = new Intent();
        it.setClass(getActivity(), ListArtActivity.class);
        boolean jump=true;
        switch (view.getId()){
            case R.id.community_lvyou:
                it.putExtra(ListArtActivity.TYPE, -1);
                break;
            case R.id.community_xinyu:
                it.setClass(getActivity(), ListArtActivity.class);
                it.putExtra(ListArtActivity.TYPE, 2);
                break;
            case R.id.community_tongcheng:
                it.setClass(getActivity(), ListArtActivity.class);
                it.putExtra(ListArtActivity.TYPE, 3);
                break;
            default:
                jump = false;
        }
        if(jump){
            getActivity().startActivity(it);
        }

    }
}