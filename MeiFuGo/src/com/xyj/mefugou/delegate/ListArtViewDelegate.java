package com.xyj.mefugou.delegate;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.volley.toolbox.NetworkImageView;
import com.xyj.mefugou.model.ArtCopyModel;
import com.xyj.mefugou.presenter.ArticleActivity;
import com.xyj.mefugou.utils.UType;
import com.zhonghua.dileber.data.db.DBManager;
import com.zhonghua.dileber.mvp.view.AppViewDelegate;
import com.xyj.mefugou.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by  on 16/1/17.
 */
public class ListArtViewDelegate extends AppViewDelegate implements IListArtView{

    public static int LAYOUT_LEFT=1;
    public static int LAYOUT_RIGHT=2;
    @Override
    public int getRootLayoutId() {
        return R.layout.activity_listart;
    }

    LinearLayout leftLinear,rightLinear;

    @Override
    public void initWidget() {
        super.initWidget();
        leftLinear = get(R.id.consult_left_column);
        rightLinear = get(R.id.consult_right_column);
    }

    public void addView(List<View> list,int type) {
        if(type==LAYOUT_LEFT){
            for(View mv:list){
                leftLinear.addView(mv);
            }
        }else if(type==LAYOUT_RIGHT){
            for(View mv:list){
                rightLinear.addView(mv);
            }
        }

    }


    public List<View> getView(List<ArtCopyModel> artCopyModels,int type) {
        if(artCopyModels==null){
            return null;
        }
        List<View> list = new ArrayList<View>();
        for(int i=0;i<artCopyModels.size();i++){
            final ArtCopyModel artCopyModel = artCopyModels.get(i);
            LayoutInflater inflater = LayoutInflater.from(this.getActivity());
            final View view = inflater.inflate(R.layout.list_find, null);
            TextView textTitle = (TextView) view.findViewById(R.id.find_title);
            textTitle.setText(artCopyModel.getTitle());
            TextView list_find_type = (TextView)view.findViewById(R.id.list_find_type);
            list_find_type.setText(UType.getType(artCopyModel.getmType()));
            NetworkImageView networkImageView = (NetworkImageView)view.findViewById(R.id.find_image);
            networkImageView.setImageUrl(artCopyModel.getImage(),R.drawable.icon);
            if(type==LAYOUT_LEFT){
                view.findViewById(R.id.list_find_tm).setVisibility(View.GONE);
            }else if(type==LAYOUT_RIGHT){
                view.findViewById(R.id.list_find_tm).setVisibility(View.VISIBLE);
            }
            list.add(view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent it = new Intent();
                    it.setClass(getActivity(), ArticleActivity.class);
                    it.putExtra(ArticleActivity.ARTICLE,artCopyModel.getId());
                    getActivity().startActivity(it);
                }
            });
        }
        return list;
    }





}