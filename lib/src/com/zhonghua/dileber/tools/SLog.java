package com.zhonghua.dileber.tools;

import android.util.Log;

/**
 * Created by shidawei on 16/1/18.
 * 日志类
 *
 */
public final class SLog {

    public static String tag = "tag";
    public static final String SEPARATOR = ",";


    public static boolean debug = true;
    public static boolean verbose = true;
    public static boolean error = true;
    public static boolean info = true;
    public static boolean warn = true;
    public static boolean wtf = true;


    public static void v(Object... content) {
        if (!verbose) return;
        Log.e(tag,  getInfo(content));
    }

    public static void e(Object... content) {
        if (!error) return;
        Log.e(tag,  getInfo(content));
    }

    public static void w(Object... content) {
        if (!warn) return;
        Log.w(tag,  getInfo(content));
    }

    public static void d(Object... content) {
        if (!debug) return;
        Log.d(tag,  getInfo(content));
    }

    public static void i(Object... content) {
        if (!info) return;
        Log.i(tag,  getInfo(content));
    }


    public static void wtf(Object... content) {
        if (!wtf) return;
        Log.wtf(tag, getInfo(content));
    }

    private static String getInfo(Object... content){
        String msg = HString.concatObject(" ", content);
        StackTraceElement caller = OUtils.getCallerStackTraceElement();
        String info = getLogInfo(caller);
        return HString.concat("  ",msg,info);
    }


    //系列函数来输出一个日志.在输出日志的同时，它会把此处代码此时的执行路径(调用栈)打印出来。

    /**
     * 输出日志所包含的信息
     */
    public static String getLogInfo(StackTraceElement stackTraceElement) {
        StringBuilder logInfoStringBuilder = new StringBuilder();
        // 获取线程名
        String threadName = Thread.currentThread().getName();
        // 获取线程ID
        long threadID = Thread.currentThread().getId();
        // 获取文件名.即xxx.java
        String fileName = stackTraceElement.getFileName();
        // 获取类名.即包名+类名
        String className = stackTraceElement.getClassName();
        // 获取方法名称
        String methodName = stackTraceElement.getMethodName();
        // 获取输出行数
        int lineNumber = stackTraceElement.getLineNumber();

        logInfoStringBuilder.append("[ ");
        logInfoStringBuilder.append("threadID=" + threadID).append(SEPARATOR);
        logInfoStringBuilder.append("threadName=" + threadName).append(SEPARATOR);
        logInfoStringBuilder.append("fileName=" + fileName).append(SEPARATOR);
        logInfoStringBuilder.append("className=" + className).append(SEPARATOR);
        logInfoStringBuilder.append("methodName=" + methodName).append(SEPARATOR);
        logInfoStringBuilder.append("lineNumber=" + lineNumber);
        logInfoStringBuilder.append(" ] ");
        return logInfoStringBuilder.toString();
    }



}
