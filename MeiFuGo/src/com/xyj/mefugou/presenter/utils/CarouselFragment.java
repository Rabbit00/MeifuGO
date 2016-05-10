package com.xyj.mefugou.presenter.utils;

import android.view.View;
import com.zhonghua.dileber.mvp.presenter.FragmentPresenter;
import com.xyj.mefugou.delegate.utils.CarouselFragmentViewDelegate;
import com.xyj.mefugou.R;
import com.xyj.mefugou.scm.utils.ICarouselFragmentScm;
import com.xyj.mefugou.scm.utils.CarouselFragmentScm;

public class CarouselFragment extends FragmentPresenter<CarouselFragmentViewDelegate> {
    @Override
    protected Class<CarouselFragmentViewDelegate> getDelegateClass() {
        return CarouselFragmentViewDelegate.class;
    }

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
        viewDelegate.imageAdapter(null);
    }
    @Override
    public void onClick(View v) {
    }
}
