package com.zhonghua.dileber.data.db;

import com.zhonghua.dileber.tools.SLog;

import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * Created by shidawei on 16/2/9.
 */
public class MapperProxy <T> implements InvocationHandler, Serializable {

    private static final long serialVersionUID = 433421454313421L;

    private final Class<T> mapperInterface;
    private final Map<Method, MapperMethod> methodCache;
    private final DBManager dbManager;

    public MapperProxy(Class<T> mapperInterface,Map<Method, MapperMethod> methodCache,DBManager dbManager) {
        this.mapperInterface = mapperInterface;
        this.methodCache = methodCache;
        this.dbManager = dbManager;
    }

    @Override
    public Object invoke(Object o, Method method, Object[] objects) throws Throwable {

        if (Object.class.equals(method.getDeclaringClass())) {
            try {
                //SLog.e("mmmmmmmm");
                return method.invoke(this, objects);
            } catch (Throwable t) {
                //SLog.e("dddddddddd");
            }
        }

        //SLog.e("--before method " + method.getName());

        //SLog.e("xxxxxxxxx");

        final MapperMethod mapperMethod = cachedMapperMethod(method);
        return mapperMethod.execute(dbManager, objects);

    }

    private MapperMethod cachedMapperMethod(Method method) {
        MapperMethod mapperMethod = methodCache.get(method);
        if (mapperMethod == null) {
            mapperMethod = new MapperMethod(method);
            methodCache.put(method, mapperMethod);
        }
        return mapperMethod;
    }


}
