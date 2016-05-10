package com.xyj.mefugou.model;

import com.zhonghua.dileber.mvp.model.SWrapper;

import java.util.List;

/**
 * Created by shidawei on 16/4/10.
 */
public class ArtCopyWrapper  extends SWrapper {

    List<ArtCopyModel> data;

    public List<ArtCopyModel> getData() {
        return data;
    }

    public void setData(List<ArtCopyModel> data) {
        this.data = data;
    }

}
