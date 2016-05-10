package com.xyj.mefugou.delegate;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.xyj.mefugou.R;
import com.xyj.mefugou.presenter.community.CommunityActivity;
import com.xyj.mefugou.presenter.course.CourseActivity;
import com.xyj.mefugou.presenter.home.HomeActivity;
import com.xyj.mefugou.presenter.mine.MineActivity;
import com.zhonghua.dileber.mvp.view.AppViewDelegate;
import com.zhonghua.dileber.view.dialog.DialogLinstener;

public abstract class TitleViewDelegate extends AppViewDelegate implements View.OnClickListener{


    @Override
    public void initWidget() {
        super.initWidget();
        TextView title_title = get(R.id.title_title);
        String title = "";
        if(getActivity() instanceof CommunityActivity){
            title = "社区中心";
        }else if(getActivity() instanceof HomeActivity){
            title = "美肤GO";
        }else if(getActivity() instanceof MineActivity){
            title = "个人中心";
        }else if(getActivity() instanceof CourseActivity){
            title = "课程";
        }
        title_title.setText(title);
    }

    @Override
    public void onClick(View view) {
    }
}
