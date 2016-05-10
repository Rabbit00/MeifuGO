package com.zhonghua.dileber.mvp.presenter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import com.zhonghua.dileber.mvp.view.IViewDelegate;
import com.zhonghua.dileber.tools.annotation.CloseStatusBar;
import com.zhonghua.dileber.tools.annotation.CloseTitle;
import com.zhonghua.dileber.tools.annotation.HideKeyboard;

/**
 * Created by shidawei on 16/3/19.
 */
public abstract class FragmentActivityPresenter<T extends IViewDelegate> extends FragmentActivity implements View.OnClickListener{

    protected T viewDelegate;
    protected boolean hideKeyboardWhenTouchOutside = false;

    public FragmentActivityPresenter() {
        try {
            viewDelegate = getDelegateClass().newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException("create IViewDelegate error");
        } catch (IllegalAccessException e) {
            throw new RuntimeException("create IViewDelegate error");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (this.getClass().isAnnotationPresent(CloseTitle.class)) {
            if(this.getClass().getAnnotation(CloseTitle.class).value()){
                this.requestWindowFeature(Window.FEATURE_NO_TITLE);
            }
        }
        if (this.getClass().isAnnotationPresent(CloseStatusBar.class)) {
            if(this.getClass().getAnnotation(CloseStatusBar.class).value()){
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            }
        }
        if (this.getClass().isAnnotationPresent(HideKeyboard.class)) {
            hideKeyboardWhenTouchOutside = this.getClass().getAnnotation(HideKeyboard.class).value();
        }
        viewDelegate.create(getLayoutInflater(), null, savedInstanceState);
        setContentView(viewDelegate.getRootView());
        viewDelegate.initWidget();
        bindEvenListener();
    }

    protected void bindEvenListener() {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        viewDelegate = null;
    }

    protected abstract Class<T> getDelegateClass();

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (viewDelegate == null) {
            try {
                viewDelegate = getDelegateClass().newInstance();
            } catch (InstantiationException e) {
                throw new RuntimeException("create IViewDelegate error");
            } catch (IllegalAccessException e) {
                throw new RuntimeException("create IViewDelegate error");
            }
        }
    }


    /**
     * 点击外部隐藏软键盘的函数
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (!hideKeyboardWhenTouchOutside) {
            return super.dispatchTouchEvent(ev);
        }
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }

    public boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }


}
