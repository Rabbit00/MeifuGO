package com.zhonghua.dileber.mvp.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.SparseArray;
import android.view.*;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;
import cn.pedant.SweetAlert.SweetAlertDialog;
import com.zhonghua.dileber.R;
import com.zhonghua.dileber.tools.annotation.HideKeyboard;
import com.zhonghua.dileber.view.dialog.DialogLinstener;

/**
 * Created by shidawei on 16/1/10.
 */
public abstract class AppViewDelegate implements IViewDelegate{

    protected final SparseArray<View> mViews = new SparseArray<View>();

    protected View rootView;


    public abstract int getRootLayoutId();

    Dialog loadalert = null;
    Dialog alert = null;
    @Override
    public void create(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        int rootLayoutId = getRootLayoutId();
        rootView = inflater.inflate(rootLayoutId, container, false);
        alert = new SweetAlertDialog(getActivity());
        loadalert = new SweetAlertDialog(getActivity());
    }

    @Override
    public View getRootView() {
        return rootView;
    }

    @Override
    public void initWidget() {

    }

    public <T extends View> T bindView(int id) {
        T view = (T) mViews.get(id);
        if (view == null) {
            view = (T) rootView.findViewById(id);
            mViews.put(id, view);
        }
        return view;
    }

    public <T extends View> T get(int id) {
        return (T) bindView(id);
    }

    public void setOnClickListener(View.OnClickListener listener, int... ids) {
        if (ids == null) {
            return;
        }
        for (int id : ids) {
            get(id).setOnClickListener(listener);
        }
    }

    public void setOnClickListener(View.OnClickListener listener, View... views) {
        if (views == null) {
            return;
        }
        for (View view : views) {
            view.setOnClickListener(listener);
        }
    }

    public void toast(String msg,int duration) {
        //Toast.makeText(rootView.getContext(), msg, Toast.LENGTH_SHORT).show();
        View layout = getActivity().getLayoutInflater().inflate(R.layout.deleber_toast, null);
        TextView txt = (TextView) layout.findViewById(R.id.main_toast_text);
        txt.setText(msg);
        Toast toast = new Toast(getActivity());
        //设置Toast的位置
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(duration);
        //让Toast显示为我们自定义的样子
        toast.setView(layout);
        toast.show();

    }

    public <T extends Activity> T getActivity() {
        return (T) rootView.getContext();
    }

    public static final int DIALOG_ERROR = 1;
    public static final int DIALOG_SUCCESS = 2;
    public static final int DIALOG_NORMAL = 3;
    public static final int DIALOG_WARNING = 4;



    public void showAlert(Integer type,String message){
        SweetAlertDialog alertDialog = new SweetAlertDialog(getActivity());
        if(type==null){
            type = DIALOG_NORMAL;
        }
        switch (type){
            case DIALOG_ERROR:
                alertDialog.changeAlertType(SweetAlertDialog.ERROR_TYPE);
                alertDialog.setTitleText(getActivity().getResources().getString(R.string.dialog_error));
                break;
            case DIALOG_SUCCESS:
                alertDialog.changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                alertDialog.setTitleText(getActivity().getResources().getString(R.string.dialog_success));
                break;
            case DIALOG_NORMAL:
                alertDialog.changeAlertType(SweetAlertDialog.NORMAL_TYPE);
                alertDialog.setTitleText(getActivity().getResources().getString(R.string.dialog_success));
                break;
            case DIALOG_WARNING:
                alertDialog.changeAlertType(SweetAlertDialog.WARNING_TYPE);
                alertDialog.setTitleText(getActivity().getResources().getString(R.string.dialog_success));
                break;
        }

        alertDialog.setTitleText(message);
        alertDialog.show();
    }

    public void loading(){
        ((SweetAlertDialog)loadalert).changeAlertType(SweetAlertDialog.PROGRESS_TYPE);
        ((SweetAlertDialog)loadalert).setTitleText(getActivity().getResources().getString(R.string.LOADING));
        loadalert.show();
        loadalert.setCancelable(false);
        ((SweetAlertDialog)loadalert).getProgressHelper().setBarColor(getActivity().getResources().getColor(R.color.blue_btn_bg_color));
    }

    public void loadDialogDismiss(){
        loadalert.dismiss();
    }

    public void dialogOk(String content, final DialogLinstener dialogLinstener){
        ((SweetAlertDialog)alert).changeAlertType(SweetAlertDialog.WARNING_TYPE);
        ((SweetAlertDialog)alert).setTitleText(getActivity().getResources().getString(R.string.dialog_warning));
        ((SweetAlertDialog)alert).setContentText(content);
        ((SweetAlertDialog)alert).setCancelText(getActivity().getResources().getString(R.string.dialog_cancel));
        ((SweetAlertDialog)alert).setConfirmText(getActivity().getResources().getString(R.string.dialog_ok));
        ((SweetAlertDialog)alert).showCancelButton(true);
        ((SweetAlertDialog)alert).setCancelable(false);
        ((SweetAlertDialog)alert).setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                dialogLinstener.cancel(sweetAlertDialog);
            }
        });
        ((SweetAlertDialog)alert).setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                dialogLinstener.confirm(sweetAlertDialog);
            }
        });
        alert.show();
    }




}
