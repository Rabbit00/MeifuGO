package com.xyj.mefugou.presenter.course;

import android.os.Bundle;
import android.view.View;
import com.zhonghua.dileber.mvp.presenter.ActivityPresenter;
import com.xyj.mefugou.R;
import com.xyj.mefugou.delegate.course.CourseViewDelegate;
import com.xyj.mefugou.scm.course.ICourseScm;
import com.xyj.mefugou.scm.course.CourseScm;
import com.zhonghua.dileber.tools.annotation.CloseTitle;

@CloseTitle
public class CourseActivity extends ActivityPresenter<CourseViewDelegate>  {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ICourseScm courseSrc = new CourseScm();

    }

    @Override
    protected Class<CourseViewDelegate> getDelegateClass() {
        return CourseViewDelegate.class;
    }

    @Override
    protected void bindEvenListener() {
        super.bindEvenListener();
    }


    @Override
    public void onClick(View view) {

    }
}