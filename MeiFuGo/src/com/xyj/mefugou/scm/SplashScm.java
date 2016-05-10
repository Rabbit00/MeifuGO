package com.xyj.mefugou.scm;
import com.xyj.mefugou.app.AppHper;
import com.xyj.mefugou.model.UserModel;
import com.xyj.mefugou.utils.HuanXinUtil;
import com.zhonghua.dileber.mvp.scm.Scm;
import com.xyj.mefugou.R;
import com.zhonghua.dileber.tools.SLog;

/**
 * Created by  on 16/1/17.
 */
public class SplashScm extends Scm implements ISplashScm {

    @Override
    public void splash() {
        SLog.w("Ok");
        UserModel userModel = AppHper.getAppUser();
        if(userModel!=null){
            HuanXinUtil.login(userModel, null);
        }
    }

}
