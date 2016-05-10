package com.xyj.mefugou.model;

import com.zhonghua.dileber.mvp.model.SWrapper;

/**
 * Created by shidawei on 16/3/15.
 */
public class UserWrapper extends SWrapper {

    UserModel data;

    public UserModel getData() {
        return data;
    }

    public void setData(UserModel data) {
        this.data = data;
    }
}
