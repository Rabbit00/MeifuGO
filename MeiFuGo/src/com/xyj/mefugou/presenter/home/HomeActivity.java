package com.xyj.mefugou.presenter.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import com.xyj.mefugou.model.ArtCopyModel;
import com.xyj.mefugou.presenter.ArticleActivity;
import com.xyj.mefugou.presenter.ListArtActivity;
import com.xyj.mefugou.presenter.utils.CarouselFragment;
import com.xyj.mefugou.scm.ArticleScm;
import com.xyj.mefugou.scm.IArticleScm;
import com.zhonghua.dileber.mvp.presenter.ActivityPresenter;
import com.xyj.mefugou.R;
import com.xyj.mefugou.delegate.home.HomeViewDelegate;
import com.xyj.mefugou.scm.home.IHomeScm;
import com.xyj.mefugou.scm.home.HomeScm;
import com.zhonghua.dileber.mvp.presenter.FragmentActivityPresenter;
import com.zhonghua.dileber.mvp.scm.INetListener;
import com.zhonghua.dileber.tools.annotation.CloseTitle;

import java.util.List;

@CloseTitle
public class  HomeActivity extends FragmentActivityPresenter<HomeViewDelegate> {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction().replace(R.id.home_carousel, new CarouselFragment())
                .commit();

    }

    @Override
    protected Class<HomeViewDelegate> getDelegateClass() {
        return HomeViewDelegate.class;
    }

    IArticleScm articleScm;
    EditText home_search_text;
    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        articleScm = new ArticleScm();
        articleScm.getArticleList(null, 0, new INetListener<List<ArtCopyModel>>() {
            @Override
            public void before() {
                viewDelegate.loading();
            }

            @Override
            public void success(List<ArtCopyModel> model) {
                viewDelegate.loadDialogDismiss();
                viewDelegate.showList(model);
            }

            @Override
            public void failed() {
                viewDelegate.loadDialogDismiss();
            }

            @Override
            public void errMsg(String msg) {
                viewDelegate.loadDialogDismiss();
                viewDelegate.showAlert(viewDelegate.DIALOG_WARNING, msg);
            }
        });
        home_search_text = viewDelegate.get(R.id.home_search_text);
        home_search_text.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                // TODO Auto-generated method stub
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    // 先隐藏键盘
                    String like = home_search_text.getText().toString();
                    Intent it = new Intent();
                    it.setClass(HomeActivity.this, ListArtActivity.class);

                    it.putExtra(ListArtActivity.LIKE,like);
                    startActivity(it);
                    return true;
                }
                return false;
            }
        });

    }


    @Override
    public void onClick(View view) {

    }
}