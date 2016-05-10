package com.xyj.mefugou.scm;

import com.xyj.mefugou.model.ArtCopyModel;
import com.zhonghua.dileber.mvp.scm.INetListener;

import java.util.List;

/**
 * Created by  on 16/1/17.
 */
public interface IArticleScm {

    void getArticleList(String like,Integer type,INetListener<List<ArtCopyModel>> iNetListener);

    void getArticle(Integer id,INetListener<ArtCopyModel> iNetListener);

}
