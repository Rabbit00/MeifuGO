package com.zhonghua.dileber.tools;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;
import com.zhonghua.dileber.app.SApplication;

/**
 * Created by shidawei on 16/2/3.
 */
public class SFont  extends TextView {

    public SFont(Context context) {
        this(context,null,0);
    }

    public SFont(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }
    public SFont(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        Typeface typeface = SApplication.icon_font;
        if(typeface!=null){
            setTypeface(typeface);
        }

    }



}
