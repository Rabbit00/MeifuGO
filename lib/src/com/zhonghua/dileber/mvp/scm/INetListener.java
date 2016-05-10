package com.zhonghua.dileber.mvp.scm;


/**
 * Created by shidawei on 16/1/22.
 */
public interface INetListener <T> {

    void before();

    void success(T model);

    void failed();

    void errMsg(String msg);

}
