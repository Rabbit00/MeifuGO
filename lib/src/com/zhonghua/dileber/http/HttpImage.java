package com.zhonghua.dileber.http;

import android.widget.ImageView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;

public class HttpImage implements ImageLoader.ImageListener {

    private ImageView imageView;
    private int errorImage;
    private boolean isRound = false;

    public HttpImage(ImageView iv, int defaultImage) {
        imageView = iv;
        errorImage = defaultImage;
    }

    @Override
    public void onResponse(ImageLoader.ImageContainer imageContainer, boolean isImmediate) {
        if (imageContainer.getBitmap() != null) {
            imageView.setImageBitmap(imageContainer.getBitmap());
        } else if (errorImage != 0) {
            imageView.setImageResource(errorImage);
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        imageView.setImageResource(errorImage);
    }

}
