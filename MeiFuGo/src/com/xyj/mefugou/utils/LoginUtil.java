package com.xyj.mefugou.utils;

import com.xyj.mefugou.model.UserModel;

public class LoginUtil {

    public static UserModel getUser(){
        UserModel userModel = new UserModel();
        userModel.setUsername("张三");
        userModel.setLoginname("234");
        userModel.setUserimage("http://7xj92l.com1.z0.glb.clouddn.com/2015-05-27_5565e62fa9d71.jpg");
        userModel.setUserpass("432");
        userModel.setId(1);
        userModel.setApptype(2);
        return userModel;
    }

}
