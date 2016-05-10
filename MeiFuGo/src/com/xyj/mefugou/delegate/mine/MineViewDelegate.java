package com.xyj.mefugou.delegate.mine;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.toolbox.NetworkImageView;
import com.xyj.mefugou.app.AppHper;
import com.xyj.mefugou.delegate.MenuViewDelegate;
import com.zhonghua.dileber.mvp.view.AppViewDelegate;
import com.xyj.mefugou.R;

/**
 * Created by  on 16/1/17.
 */
public class MineViewDelegate extends MenuViewDelegate implements IMineView{


    @Override
    public int getRootLayoutId() {
        return R.layout.activity_mine;
    }


    @Override
    public void initWidget() {
        super.initWidget();
        NetworkImageView imageView = get(R.id.user_image);
        imageView.setImageShapeType(NetworkImageView.CIRCLE_IMAGE);
        imageView.setImageUrl(AppHper.getAppUser().getUserimage(), R.drawable.icon);
        TextView textView3 = get(R.id.textView3);
        textView3.setText(AppHper.getAppUser().getUsername());
        get(R.id.textView6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlert(DIALOG_NORMAL, "DrCoSu工作室\n线永洁");
            }
        });

        setOnClickListener(this,R.id.textView5,R.id.textView6);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()){
            case R.id.textView5:
                toast("暂未开发",Toast.LENGTH_SHORT);
                break;
            case R.id.textView6:
                showAlert(DIALOG_NORMAL, "DrCoSu工作室\n线永洁");
                break;
        }
    }
}