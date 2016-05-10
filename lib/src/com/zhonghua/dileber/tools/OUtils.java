package com.zhonghua.dileber.tools;

/**
 * Created by shidawei on 16/1/18.
 */
public final class OUtils {

    public static StackTraceElement getCallerStackTraceElement() {
        return Thread.currentThread().getStackTrace()[5];
    }

}
