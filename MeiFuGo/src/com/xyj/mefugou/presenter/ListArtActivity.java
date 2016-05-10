package com.xyj.mefugou.presenter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.xyj.mefugou.model.ArtCopyModel;
import com.xyj.mefugou.scm.ArticleScm;
import com.xyj.mefugou.scm.IArticleScm;
import com.zhonghua.dileber.mvp.presenter.ActivityPresenter;
import com.xyj.mefugou.R;
import com.xyj.mefugou.delegate.ListArtViewDelegate;
import com.xyj.mefugou.scm.IListArtScm;
import com.xyj.mefugou.scm.ListArtScm;
import com.zhonghua.dileber.mvp.scm.INetListener;
import com.zhonghua.dileber.tools.annotation.CloseStatusBar;
import com.zhonghua.dileber.tools.annotation.CloseTitle;

import java.util.ArrayList;
import java.util.List;

@CloseTitle
public class ListArtActivity extends ActivityPresenter<ListArtViewDelegate>  {

    public static final String LIKE = "like";
    public static final String TYPE = "type";
    String like = null;
    int type = -1;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    protected Class<ListArtViewDelegate> getDelegateClass() {
        return ListArtViewDelegate.class;
    }
    IArticleScm listartSrc;
    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        Intent it = getIntent();
        like = it.getStringExtra(LIKE);
        type = it.getIntExtra(TYPE,-1);
        listartSrc = new ArticleScm();
        listartSrc.getArticleList(like, type, new INetListener<List<ArtCopyModel>>() {
            @Override
            public void before() {
                viewDelegate.loading();
            }

            @Override
            public void success(List<ArtCopyModel> model) {
                viewDelegate.loadDialogDismiss();
                List<ArtCopyModel> model_left = new ArrayList<ArtCopyModel>();
                List<ArtCopyModel> model_right = new ArrayList<ArtCopyModel>();
                for(int i=0;i<model.size();i++){
                    if(i%2==0){
                        model_left.add(model.get(i));
                    }else {
                        model_right.add(model.get(i));
                    }
                }
                viewDelegate.addView(viewDelegate.getView(model_left,viewDelegate.LAYOUT_LEFT),viewDelegate.LAYOUT_LEFT);
                viewDelegate.addView(viewDelegate.getView(model_right,viewDelegate.LAYOUT_RIGHT),viewDelegate.LAYOUT_RIGHT);
            }

            @Override
            public void failed() {
                viewDelegate.loadDialogDismiss();
            }

            @Override
            public void errMsg(String msg) {
                viewDelegate.loadDialogDismiss();
                viewDelegate.showAlert(viewDelegate.DIALOG_WARNING,msg);
            }
        });
    }


    @Override
    public void onClick(View view) {

    }
}