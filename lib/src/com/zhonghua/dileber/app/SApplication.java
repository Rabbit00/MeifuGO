package com.zhonghua.dileber.app;


import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.graphics.Typeface;
import android.view.Window;
import com.zhonghua.dileber.http.CacheConfig;
import com.zhonghua.dileber.tools.SLog;
import com.zhonghua.dileber.tools.annotation.SFontdType;

import java.util.Stack;

public class SApplication extends Application{

	public static Typeface icon_font;

	private static SApplication instance;
	private static Context context;

	//activity 栈
	static volatile Stack<Class<? extends Activity>> activityStack;
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		if (activityStack == null) {
			activityStack = new Stack<Class<? extends Activity>>();
		}
		instance = this;
		context = instance.getApplicationContext();
		CacheConfig.initCacheConfig(context);
		if (this.getClass().isAnnotationPresent(SFontdType.class)) {
			icon_font = Typeface.createFromAsset(getAssets(), this.getClass().getAnnotation(SFontdType.class).value());
		}


	}
	
	public static Context getAppContext() {
		return context;
	}

	public static SApplication getInstance() {
		return instance;
	}

	
	/**
	 * 输出log 日志
	 * 
	 * @param bug true or false
	 * @param bugName tag
	 */
	protected void buglog(boolean bug,String bugName){
		SLog.tag = bugName; // 方便调试时过滤 adb logcat 输出
		SLog.info = bug; //关闭 LogUtils.i(...) 的 adb log 输出
	}

	public void quitApp(){
		SLog.w("exit app");
	}

	/**
	 *
	 * 获取顶栈 的 activity
	 * @return
	 */
	public static Class<? extends Activity> peekTopActivity() {
		Class<? extends Activity> ref = activityStack.peek();
		return ref;
	}

	/**
	 * activity 出栈
	 * @return
	 */
	public static Class<? extends Activity> popActivity() {
		Class<? extends Activity> ref = activityStack.pop();
		return ref;
	}

	/**
	 * activity 进栈
	 * @param activity
	 */
	public static void pushActivity(Class<? extends Activity> activity) {
		if (activity != null) {
			activityStack.push(activity);
		}
		return;
	}

	/**
	 * 栈中有多少activity
	 * @return
	 */
	public static int activityInStack() {
		return activityStack.size();
	}
	
}
