package com.xyj.mefugou.delegate.utils;

import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.android.volley.toolbox.NetworkImageView;
import com.xyj.mefugou.R;
import com.xyj.mefugou.utils.carousel.ImageCarouselPageAdapter;
import com.zhonghua.dileber.mvp.view.AppViewDelegate;
import com.zhonghua.dileber.tools.SLog;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class CarouselFragmentViewDelegate extends AppViewDelegate {

    /**
     * 请求更新显示的View。
     */
    protected static final int MSG_UPDATE_IMAGE  = 1;
    /**
     * 请求暂停轮播。
     */
    protected static final int MSG_KEEP_SILENT   = 2;
    /**
     * 请求恢复轮播。
     */
    protected static final int MSG_BREAK_SILENT  = 3;
    /**
     * 记录最新的页号，当用户手动滑动时需要记录新页号，否则会使轮播的页面出错。
     * 例如当前如果在第一页，本来准备播放的是第二页，而这时候用户滑动到了末页，
     * 则应该播放的是第一页，如果继续按照原来的第二页播放，则逻辑上有问题。
     */
    protected static final int MSG_PAGE_CHANGED  = 4;

    //轮播间隔时间
    protected static final long MSG_DELAY = 3000;
    @Override
    public int getRootLayoutId() {
        return R.layout.fragment_carousel;
    }


    ViewPager viewPager;
    private int currentItem = 0;

    private MyHandler mHandler ;
    private WeakReference<CarouselFragmentViewDelegate> weakReference;

    private LinearLayout linearLayout;
    @Override
    public void initWidget() {
        super.initWidget();
        viewPager = get(R.id.carousel_viewpager);
        linearLayout = get(R.id.carousel_point);
        weakReference = new WeakReference<CarouselFragmentViewDelegate>(this);
        mHandler = new MyHandler();
        point = new ArrayList<View>();
    }

    List<View> point = null;
    private View point() {
        View view = new View(getActivity());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            view.setBackground(getActivity().getResources().getDrawable(R.drawable.grey_solid_round));
        }else{
            view.setBackgroundDrawable(getActivity().getResources().getDrawable(R.drawable.grey_solid_round));
        }
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(8, 8);
        //view.setPadding(5,5,5,5);
        params.setMargins(3, 5, 3, 5);
        view.setLayoutParams(params);
        point.add(view);
        return view;
    }

    View temp=null;

    /**
     * 设置选中颜色
     * @param i
     */
    public void setPointBackground(int i){
        SLog.w(">>>>>>>>>>>",i);
        if(i<0||i>=point.size()){
            return;
        }
        if(temp != null){
            if(temp.equals(point.get(i))){
                return;
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                temp.setBackground(getActivity().getResources().getDrawable(R.drawable.grey_solid_round));
            }else{
                temp.setBackgroundDrawable(getActivity().getResources().getDrawable(R.drawable.grey_solid_round));
            }
        }
        temp = point.get(i);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            temp.setBackground(getActivity().getResources().getDrawable(R.drawable.red_solid_round));
        }else{
            temp.setBackgroundDrawable(getActivity().getResources().getDrawable(R.drawable.red_solid_round));
        }
    }

    class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            CarouselFragmentViewDelegate carouselFragmentViewDelegate = weakReference.get();
            if (carouselFragmentViewDelegate==null){
                //Activity已经回收，无需再处理UI了
                return ;
            }
            //检查消息队列并移除未发送的消息，这主要是避免在复杂环境下消息出现重复等问题。
            if (mHandler.hasMessages(MSG_UPDATE_IMAGE)){
                mHandler.removeMessages(MSG_UPDATE_IMAGE);
            }
            switch (msg.what) {
                case MSG_UPDATE_IMAGE:
                    currentItem++;
                    viewPager.setCurrentItem(currentItem);
                    //准备下次播放
                    mHandler.sendEmptyMessageDelayed(MSG_UPDATE_IMAGE, MSG_DELAY);
                    break;
                case MSG_KEEP_SILENT:
                    //只要不发送消息就暂停了
                    break;
                case MSG_BREAK_SILENT:
                    mHandler.sendEmptyMessageDelayed(MSG_UPDATE_IMAGE, MSG_DELAY);
                    break;
                case MSG_PAGE_CHANGED:
                    //记录当前的页号，避免播放的时候页面显示不正确。
                    currentItem = msg.arg1;
                    break;
                default:
                    break;
            }
        }
    }

    public void imageAdapter(List<String> urlm) {
        List<String> url = new ArrayList<String>();
        url.add("http://7xj92l.com1.z0.glb.clouddn.com/100200897512_207495_1010.jpg");
        url.add("http://7xj92l.com1.z0.glb.clouddn.com/1.38759944924E+128164.jpg");
        List<View> list = new ArrayList<View>();
        for(int i = 0;i<url.size();i++){
            list.add(imageViews(url.get(i)));
            linearLayout.addView(point());
        }
        viewPager.setAdapter(new ImageCarouselPageAdapter(list));
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            //配合Adapter的currentItem字段进行设置。
            @Override
            public void onPageSelected(int arg0) {
                int select = arg0 % point.size();
                SLog.w("<><><><>", arg0, select);
                setPointBackground(select);
                mHandler.sendMessage(Message.obtain(mHandler, MSG_PAGE_CHANGED, arg0, 0));
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            //覆写该方法实现轮播效果的暂停和恢复
            @Override
            public void onPageScrollStateChanged(int arg0) {
                switch (arg0) {
                    case ViewPager.SCROLL_STATE_DRAGGING:
                        mHandler.sendEmptyMessage(MSG_KEEP_SILENT);
                        break;
                    case ViewPager.SCROLL_STATE_IDLE:
                        mHandler.sendEmptyMessageDelayed(MSG_UPDATE_IMAGE, MSG_DELAY);
                        break;
                    default:
                        break;
                }
            }
        });
        viewPager.setCurrentItem(Integer.MAX_VALUE / 2);//默认在中间，使用户看不到边界
        //开始轮播效果
        mHandler.sendEmptyMessageDelayed(MSG_UPDATE_IMAGE, MSG_DELAY);

    }


    public View imageViews(String url) {
        NetworkImageView imageView = new NetworkImageView(getActivity());
        //String urln = Configer.HTTP_CONFIG+url;
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setImageUrl(url, R.drawable.icon);
        return imageView;
    }
}