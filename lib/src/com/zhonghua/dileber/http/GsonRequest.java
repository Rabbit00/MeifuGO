package com.zhonghua.dileber.http;

import com.android.volley.*;
import com.android.volley.toolbox.HttpHeaderParser;
import com.zhonghua.dileber.app.FrameContants;
import com.zhonghua.dileber.data.PerfManager;
import com.zhonghua.dileber.tools.HJson;
import com.zhonghua.dileber.tools.SLog;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by shidawei on 16/1/20.
 *
 */
public class GsonRequest<T> extends Request<T> {
    private final Response.Listener<T>listener;
    private Class<T> mClass;

    private Map<String, Object> mParams;

    private static Map<String, String> mHeader = new HashMap<String, String>();

    static
    {
        mHeader.put("APP-Key", "");
        mHeader.put("APP-Secret", "");
    }


    public GsonRequest(int method,String url,Map<String, Object> params,HttpListener<T> listener,Class<T> tClass){
        super(method,url,listener);
        this.listener=listener;
        mClass = tClass;
        mParams = params;
    }


    /**
     * 数据解析
     * @param response Response from thenetwork  网络请求返回数据
     * @return
     */
    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {

        try{
            String parsed = null;
            try {
                parsed = new String(response.data, HttpHeaderParser.parseCharset(response.headers));

            } catch (UnsupportedEncodingException e) {
                parsed = new String(response.data);
            }

            String he_temp = response.headers.toString();
            SLog.w("LOG","get headers in parseNetworkResponse ",he_temp);
            //使用正则表达式从reponse的头中提取cookie内容的子串
            Pattern pattern=Pattern.compile("Set-Cookie.*?;");
            Matcher m=pattern.matcher(he_temp);
            if(m.find()){
                String cookieFromResponse = m.group();
                //去掉cookie末尾的分号
                cookieFromResponse = cookieFromResponse.substring(11,cookieFromResponse.length()-1);
                SLog.w("LOG","cookie from server ", cookieFromResponse);
                PerfManager perfManager = PerfManager.getInstance();
                perfManager.putSystemPreferences(FrameContants.SYSTEM_PREFERANCE_SESSION,cookieFromResponse);

            }

            if(mClass!=null){
                if (mClass.isAssignableFrom(String.class)) {
                    return (Response<T>) Response.success(parsed, HttpHeaderParser.parseCacheHeaders(response));
                }else{
                    T data= HJson.fromJson(parsed, mClass);
                    return Response.success(data,HttpHeaderParser.parseCacheHeaders(response));
                }
            }else {
                return (Response<T>) Response.success(parsed, HttpHeaderParser.parseCacheHeaders(response));
            }

        }catch (Exception e){
            e.printStackTrace();
            return Response.error(new ParseError(e));
        }
    }

    /**
     * 数据分发
     * @param response The parsed responsereturned by
     */
    @Override
    protected void deliverResponse(T response){
        listener.onResponse(response);
    }


    private boolean session = true;

    public void setSession(boolean session) {
        this.session = session;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        //return mHeader;
        if(session){
            Map<String,String> ret = new HashMap<String, String>();
            PerfManager perfManager = PerfManager.getInstance();
            String session = (String)perfManager.getSystmPreferences(FrameContants.SYSTEM_PREFERANCE_SESSION,"");
            SLog.w(">>>>>>>>>>>>>>>session", session);
            ret.put("cookie", "JSESSIONID="+session);
            return ret;
        }
        return super.getHeaders();
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        Map<String, String> ret= CommonParams.addCommonParams(this.mParams);
        return ret;
    }
}