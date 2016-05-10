package com.xyj.mefugou.delegate.course;

import com.xyj.mefugou.delegate.MenuViewDelegate;
import com.zhonghua.dileber.mvp.view.AppViewDelegate;
import com.xyj.mefugou.R;

/**
 * Created by  on 16/1/17.
 */
public class CourseViewDelegate extends MenuViewDelegate implements ICourseView{


    @Override
    public int getRootLayoutId() {
        return R.layout.activity_course;
    }


    @Override
    public void initWidget() {
        super.initWidget();
    }

}