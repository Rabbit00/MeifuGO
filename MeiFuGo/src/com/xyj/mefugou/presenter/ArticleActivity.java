package com.xyj.mefugou.presenter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.xyj.mefugou.model.ArtCopyModel;
import com.zhonghua.dileber.mvp.presenter.ActivityPresenter;
import com.xyj.mefugou.R;
import com.xyj.mefugou.delegate.ArticleViewDelegate;
import com.xyj.mefugou.scm.IArticleScm;
import com.xyj.mefugou.scm.ArticleScm;
import com.zhonghua.dileber.mvp.scm.INetListener;
import com.zhonghua.dileber.tools.annotation.CloseTitle;

@CloseTitle
public class ArticleActivity extends ActivityPresenter<ArticleViewDelegate>  {
    public static final String ARTICLE = "ARTICLE";
    int m;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    protected Class<ArticleViewDelegate> getDelegateClass() {
        return ArticleViewDelegate.class;
    }
    IArticleScm articleSrc;

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        Intent it = getIntent();
        m = it.getIntExtra(ARTICLE,1);
        articleSrc = new ArticleScm();
        articleSrc.getArticle(m, new INetListener<ArtCopyModel>() {
            @Override
            public void before() {
                viewDelegate.loading();
            }

            @Override
            public void success(ArtCopyModel model) {
                viewDelegate.loadDialogDismiss();
                viewDelegate.setDate(model);
            }

            @Override
            public void failed() {
                viewDelegate.loadDialogDismiss();
            }

            @Override
            public void errMsg(String msg) {
                viewDelegate.loadDialogDismiss();
            }
        });
    }


    @Override
    public void onClick(View view) {

    }
}