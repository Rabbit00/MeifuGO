package com.xyj.mefugou.delegate;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import com.xyj.mefugou.R;
import com.xyj.mefugou.presenter.community.CommunityActivity;
import com.xyj.mefugou.presenter.course.CourseActivity;
import com.xyj.mefugou.presenter.home.HomeActivity;
import com.xyj.mefugou.presenter.mine.MineActivity;
import com.zhonghua.dileber.tools.SFont;


public abstract class MenuViewDelegate extends TitleViewDelegate {

    @Override
    public void initWidget() {
        super.initWidget();
        initMenu();
        setOnClickListener(this, R.id.all_community, R.id.all_course, R.id.all_index, R.id.all_me);
    }

    private void initMenu(){
        SFont font = null;
        TextView text = null;
        if(getActivity() instanceof CommunityActivity){
            font = get(R.id.community_font);
            text = get(R.id.community_text);
        }else if(getActivity() instanceof HomeActivity){
            font = get(R.id.index_font);
            text = get(R.id.index_text);
        }else if(getActivity() instanceof MineActivity){
            font = get(R.id.me_font);
            text = get(R.id.me_text);
        }else if(getActivity() instanceof CourseActivity){
            font = get(R.id.course_font);
            text = get(R.id.course_text);
        }
        if(font!=null&&text!=null){
            font.setTextColor(getActivity().getResources().getColor(R.color.menu_text_color_two));
            text.setTextColor(getActivity().getResources().getColor(R.color.menu_text_color_two));
        }

    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        Intent it = new Intent();
        boolean jump = true;
        switch (view.getId()){
            case R.id.all_course:
                it.setClass(getActivity(), CourseActivity.class);
                break;
            case R.id.all_community:
                it.setClass(getActivity(), CommunityActivity.class);
                break;
            case R.id.all_index:
                it.setClass(getActivity(), HomeActivity.class);
                break;
            case R.id.all_me:
                it.setClass(getActivity(), MineActivity.class);
                break;
            default:
                jump = false;
        }
        if(jump){
            getActivity().startActivity(it);
            getActivity().overridePendingTransition(0, 0);
            if(getActivity().getComponentName().compareTo(it.getComponent())!=0){
                getActivity().finish();
            }
        }


    }

}
