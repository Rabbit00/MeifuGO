package com.xyj.mefugou.scm;
import com.android.volley.VolleyError;
import com.xyj.mefugou.app.Api;
import com.xyj.mefugou.app.Configer;
import com.xyj.mefugou.model.ArtCopyModel;
import com.xyj.mefugou.model.ArtCopyOneWrapper;
import com.xyj.mefugou.model.ArtCopyWrapper;
import com.zhonghua.dileber.http.HttpListener;
import com.zhonghua.dileber.mvp.scm.INetListener;
import com.zhonghua.dileber.mvp.scm.Scm;
import com.xyj.mefugou.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by  on 16/1/17.
 */
public class ArticleScm extends Scm implements IArticleScm {


    @Override
    public void getArticleList(String like, Integer type, final INetListener<List<ArtCopyModel>> iNetListener) {
        iNetListener.before();
        Map<String,Object> map = new HashMap<String, Object>();
        if(like!=null){
            map.put("like",like);
        }
        if(type!=null&&type!=-1){
            map.put("mType",type);
        }
        netWork(Configer.HTTP_URL + Api.FIND_ART, map, ArtCopyWrapper.class, new HttpListener<ArtCopyWrapper>() {
            @Override
            protected void onSuccess(ArtCopyWrapper response) {
                if(response!=null){
                    if(response.getState()==1){
                        if(response.getData()!=null){
                            iNetListener.success(response.getData());
                            return;
                        }
                        iNetListener.errMsg("数据为空");
                        return;
                    }
                }
                iNetListener.failed();
            }

            @Override
            protected void onFailure(VolleyError error) {
                iNetListener.failed();
            }
        },TYPE_GET);

    }

    @Override
    public void getArticle(Integer id, final INetListener<ArtCopyModel> iNetListener) {
        iNetListener.before();
        Map<String,Object> map = new HashMap<String, Object>();
        if(id!=null){
            map.put("id",id);
        }

        netWork(Configer.HTTP_URL + Api.FIND_ART_ONE, map, ArtCopyOneWrapper.class, new HttpListener<ArtCopyOneWrapper>() {
            @Override
            protected void onSuccess(ArtCopyOneWrapper response) {
                if(response!=null){
                    if(response.getState()==1){
                        if(response.getData()!=null){
                            iNetListener.success(response.getData());
                            return;
                        }
                        iNetListener.errMsg("数据为空");
                        return;
                    }
                }
                iNetListener.failed();
            }

            @Override
            protected void onFailure(VolleyError error) {
                iNetListener.failed();
            }
        },TYPE_GET);
    }
}
