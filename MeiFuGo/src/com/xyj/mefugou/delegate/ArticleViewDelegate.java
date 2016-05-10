package com.xyj.mefugou.delegate;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import com.android.volley.toolbox.NetworkImageView;
import com.hyphenate.easeui.EaseConstant;
import com.xyj.mefugou.model.ArtCopyModel;
import com.xyj.mefugou.model.UserModel;
import com.xyj.mefugou.presenter.ChatActivity;
import com.zhonghua.dileber.mvp.view.AppViewDelegate;
import com.xyj.mefugou.R;
import com.zhonghua.dileber.tools.UTime;

/**
 * Created by  on 16/1/17.
 */
public class ArticleViewDelegate extends AppViewDelegate implements IArticleView{


    @Override
    public int getRootLayoutId() {
        return R.layout.activity_article;
    }

    TextView article_text,name,time;
    NetworkImageView imagee;
    @Override
    public void initWidget() {
        super.initWidget();
        article_text = get(R.id.article_text);
        name = get(R.id.name);
        time = get(R.id.time);
        imagee = get(R.id.imagee);
        get(R.id.textView4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().
                startActivity(new Intent(getActivity(), ChatActivity.class).putExtra(EaseConstant.EXTRA_USER_ID, "zhangsi"));


            }
        });

    }

    public void setDate(ArtCopyModel artCopyModel){
        article_text.setText(artCopyModel.getContent());
        time.setText("2015-04-12");
        UserModel userModel = artCopyModel.getUser();
        if(userModel!=null){
            imagee.setImageShapeType(NetworkImageView.CIRCLE_IMAGE);
            imagee.setImageUrl(userModel.getUserimage(),R.drawable.icon);
            name.setText(userModel.getUsername());
        }

    }

}