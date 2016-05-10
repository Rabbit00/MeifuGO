package com.xyj.mefugou.utils;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.easeui.controller.EaseUI;
import com.hyphenate.exceptions.HyphenateException;
import com.xyj.mefugou.app.MApplication;
import com.xyj.mefugou.model.UserModel;
import com.zhonghua.dileber.tools.SLog;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class HuanXinUtil {

    public interface IHuanxing{
        void success();
        void falue();
    }

    public static void init(){
        EMOptions options = new EMOptions();
// 默认添加好友时，是不需要验证的，改成需要验证
        //options.setAcceptInvitationAlways(false);
//初始化/
        options.setAutoLogin(false);
        //EMClient.getInstance().init(MApplication.getAppContext(), options);
//在做打包混淆时，关闭debug模式，避免消耗不必要的资源
        //EMClient.getInstance().setDebugMode(true);

        EaseUI.getInstance().init(MApplication.getAppContext(),options);

    }

    /**
     * 环信登陆
     * @param userModel
     */
    public static void login(UserModel userModel, final IHuanxing huanxing){
        final String loginname = userModel.getLoginname();
        final String pass = userModel.getUserpass();
        //logOut(new IHuanxing() {
         //   @Override
         //   public void success() {
                SLog.d("<><><><><><>", "登录开始", loginname, pass);
                EMClient.getInstance().login(loginname, pass, new EMCallBack() {
                    @Override
                    public void onSuccess() {
                        SLog.d("<><><><><><>", "登录成功", loginname, pass);
                        EMClient.getInstance().groupManager().loadAllGroups();
                        EMClient.getInstance().chatManager().loadAllConversations();
                        if(huanxing!=null){
                            huanxing.success();
                        }

                    }

                    @Override
                    public void onProgress(int progress, String status) {
                        SLog.d("login: onProgress");
                    }

                    @Override
                    public void onError(final int code, final String message) {
                        SLog.d("login: onError: " + code);

                        if(huanxing!=null){
                            huanxing.falue();
                        }
                    }
                });
  //          }

//            @Override
//            public void falue() {
//
//            }
//        });
    }

    /**
     * 环信注册
     * @param userModel
     */
    public static void huanXinRx(final UserModel userModel){
        Observable<String> observable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                /***注册环信***/
                subscriber.onNext(registHuanxin(userModel.getLoginname(), userModel.getUserpass(), subscriber));
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        observable.subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {
                login(userModel,null);
            }

            @Override
            public void onError(Throwable e) {
                SLog.e(e.getMessage());
            }

            @Override
            public void onNext(String s) {
                SLog.w(s);
            }
        });
    }


    private static String registHuanxin(String username,String pass, Subscriber<? super String> subscriber){
        try {
            EMClient.getInstance().createAccount(username, pass);
        } catch (HyphenateException e) {
            e.printStackTrace();
            subscriber.onError(e);
        }
        return "环信注册成功";
    }

    /**
     * 环信登出
     */
    public static void logOut(final IHuanxing huanxing){
        SLog.d("<><><><><><>", "退出登录开始");
        EMClient.getInstance().logout(true, new EMCallBack() {

            @Override
            public void onSuccess() {
                // TODO Auto-generated method stub
                SLog.d("<><><><><><>", "退出登录成功");
                if(huanxing!=null){
                    huanxing.success();
                }

            }

            @Override
            public void onProgress(int progress, String status) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onError(int code, String message) {
                // TODO Auto-generated method stub
                SLog.d("<><><><><><>", "退出登录失败");
                if(huanxing!=null){
                    huanxing.falue();
                }
            }
        });
    }

    public static void okFriend(final String user){
        new Thread(new Runnable() {
            public void run() {
                SLog.d("<><><><><><>", "同意朋友",user);
                try {
                    EMClient.getInstance().contactManager().acceptInvitation(user);
                } catch (HyphenateException e) {
                    e.printStackTrace();
                    SLog.d("<><><><><><>", "同意朋友失败", user);
                }
            }
        }).start();

    }

    public static void addFriend(final String user){
        new Thread(new Runnable() {
            public void run() {
                SLog.d("<><><><><><>", "添加朋友", user);
                try {
                    EMClient.getInstance().contactManager().addContact(user, "我要加你为好友");
                } catch (HyphenateException e) {
                    e.printStackTrace();
                    SLog.d("<><><><><><>", "添加朋友失败", user);
                }
            }
        }).start();

    }



}
