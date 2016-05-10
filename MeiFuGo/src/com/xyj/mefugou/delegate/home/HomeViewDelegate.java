package com.xyj.mefugou.delegate.home;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.android.volley.toolbox.NetworkImageView;
import com.xyj.mefugou.delegate.MenuViewDelegate;
import com.xyj.mefugou.model.ArtCopyModel;
import com.xyj.mefugou.model.UserModel;
import com.xyj.mefugou.presenter.ArticleActivity;
import com.xyj.mefugou.scm.ArticleScm;
import com.xyj.mefugou.scm.IArticleScm;
import com.xyj.mefugou.utils.UType;
import com.zhonghua.dileber.mvp.scm.INetListener;
import com.zhonghua.dileber.mvp.view.AppViewDelegate;
import com.xyj.mefugou.R;
import com.zhonghua.dileber.tools.SFont;
import com.zhonghua.dileber.tools.UTime;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by  on 16/1/17.
 */
public class HomeViewDelegate extends MenuViewDelegate implements IHomeView{


    @Override
    public int getRootLayoutId() {
        return R.layout.activity_home;
    }


    HomeListAdapter homeListAdapter;

    ListView home_list;

    @Override
    public void initWidget() {
        super.initWidget();
        home_list = get(R.id.home_list);
        homeListAdapter = new HomeListAdapter(getActivity());
        home_list.setAdapter(homeListAdapter);
        home_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent it = new Intent();
                it.setClass(getActivity(), ArticleActivity.class);
                it.putExtra(ArticleActivity.ARTICLE, artModels_.get(i).getId());
                getActivity().startActivity(it);
            }
        });
        setOnClickListener(this,R.id.home_myshop,R.id.home_mycourse);
    }

    List<ArtCopyModel> artModels_ = new ArrayList<ArtCopyModel>();

    public void showList(List<ArtCopyModel> artModels){

        homeListAdapter.setArtModels(artModels);
        homeListAdapter.notifyDataSetChanged();
    }

    public class HomeListAdapter extends BaseAdapter {
        private LayoutInflater mInflater;
        class CellHolder{
            TextView list_home_title;
            NetworkImageView list_home_image;
            TextView list_home_type;
        }


        public HomeListAdapter(Context context){
            this.mInflater = LayoutInflater.from(context);
        }

        public void setArtModels(List<ArtCopyModel> artModels) {
            artModels_ = artModels;
        }


        public void addArtModel(List<ArtCopyModel> artModels){

            artModels_.addAll(artModels);

        }


        @Override
        public int getCount() {
            return artModels_.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            CellHolder _cellHolder = null;

            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.list_home, null);
                _cellHolder = new CellHolder();
                _cellHolder.list_home_type = (TextView) convertView.findViewById(R.id.list_home_type);
                _cellHolder.list_home_title = (TextView) convertView.findViewById(R.id.list_home_title);
                _cellHolder.list_home_image = (NetworkImageView) convertView.findViewById(R.id.list_home_image);
                _cellHolder.list_home_image.setImageShapeType(NetworkImageView.CIRCLE_IMAGE);
                convertView.setTag(_cellHolder);
            } else {
                _cellHolder = (CellHolder) convertView.getTag();
            }
            ArtCopyModel artModel = artModels_.get(position);
            _cellHolder.list_home_type.setText(UType.getType(artModel.getmType()));
            _cellHolder.list_home_title.setText(artModel.getTitle());
            UserModel userModel = artModel.getUser();
            if(userModel!=null){
                _cellHolder.list_home_image.setImageUrl(userModel.getUserimage(), R.drawable.icon);
            }

            return convertView;
        }
    }

    TextView text_temp = null;
    SFont font_temp = null;
    //LinearLayout linearLayout_temp = null;
    @Override
    public void onClick(View view) {
        super.onClick(view);
        TextView text = null;
        SFont font = null;
        //LinearLayout linearLayout = null;
        switch (view.getId()){
            case R.id.home_myshop:
                text = get(R.id.home_myshop_text);
                font= get(R.id.home_myshop_font);
                //linearLayout = get(R.id.home_myshop);
                getData(0);
                break;
            case R.id.home_mycourse:
                text = get(R.id.home_mycourse_text);
                font= get(R.id.home_mycourse_font);
                //linearLayout = get(R.id.home_mycourse);
                getData(0);
                break;
        }
        if(text!=null&&font!=null) {
            if (text_temp != null && font_temp != null) {
                if (text_temp.equals(text)) {
                    return;
                }
                text_temp.setTextColor(getActivity().getResources().getColor(R.color.black));
                font_temp.setTextColor(getActivity().getResources().getColor(R.color.black));
                //linearLayout_temp.setBackgroundColor(Color.TRANSPARENT);
            }
            text_temp = text;
            font_temp = font;
            text.setTextColor(getActivity().getResources().getColor(R.color.menu_text_color_two));
            font.setTextColor(getActivity().getResources().getColor(R.color.menu_text_color_two));
            //linearLayout.setBackgroundColor(getActivity().getResources().getColor(R.color.gray_deep));
        }


    }
    public void getData(Integer type){
        if(type==null){
            return;
        }
        get(R.id.home_hot).setVisibility(View.GONE);

    }

}