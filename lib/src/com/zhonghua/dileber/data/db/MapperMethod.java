package com.zhonghua.dileber.data.db;


import com.zhonghua.dileber.data.db.annotation.*;
import com.zhonghua.dileber.data.db.sqllite.SqlCommandType;
import com.zhonghua.dileber.tools.SLog;
import java.lang.reflect.Method;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class MapperMethod {

    private final SqlCommand command;
    private final MethodSignature method;

    public MapperMethod(Method method) {
        this.command = new SqlCommand(method);
        this.method = new MethodSignature(method);
    }



    private static final Pattern p = Pattern.compile("[#][{][a-zA-Z0-9]*[}]");

    public Object execute(final DBManager dbManager, final Object[] args) {
        Object result = null;
        final String sql = command.getSql();
        final Matcher m = p.matcher(sql);



        if (SqlCommandType.INSERT == command.getType()) {

            return check(new Cons() {
                @Override
                public Object batch() throws Exception {
                    return dbManager.dataBatch(m.replaceAll("?"),listKey(sql,(List<Map<String, Object>>) args[0]),DBManager.Type.INSERT);
                }

                @Override
                public Object one() throws Exception {
                    return dbManager.insertDataBySql(m.replaceAll("?"),mapKey(sql, (Map<String, Object>)args[0]));
                }
            },args);
        }else if (SqlCommandType.SELECT == command.getType()) {
            try {

                if(args!=null&&args.length>0){

                    if(Map.class.isAssignableFrom(method.getReturnType())){
                        return dbManager.query2Map(m.replaceAll("?"),mapKey(sql, (Map<String, Object>) args[0]));
                    }else if(List.class.isAssignableFrom(method.getReturnType())){
                        String name = command.getClazz();
                        if(!name.equals("")){
                            SLog.i(name);
                            return dbManager.queryData2T(m.replaceAll("?"), mapKey(sql, (Map<String, Object>) args[0]), Class.forName(name));
                        }
                    }else{
                        List lis = dbManager.queryData2T(m.replaceAll("?"), mapKey(sql, (Map<String, Object>) args[0]), method.getReturnType());
                        if(lis!=null&&lis.size()>=1){
                            return lis.get(0);
                        }
                    }

                }else{

                    if(Map.class.isAssignableFrom(method.getReturnType())){
                        return dbManager.query2Map(sql,null);
                    }else if(List.class.isAssignableFrom(method.getReturnType())){
                        String name = command.getClazz();
                        if(!name.equals("")){
                            SLog.i(name);
                            return dbManager.queryData2T(sql, null, Class.forName(name));
                        }

                    }else{
                        List lis = dbManager.queryData2T(sql, null, method.getReturnType());
                        if(lis!=null&&lis.size()>=1){
                            return lis.get(0);
                        }
                    }

                }

                //return dbManager.
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else if (SqlCommandType.UPDATE == command.getType()) {

            return check(new Cons() {
                @Override
                 public Object batch() throws Exception {
                    return dbManager.dataBatch(m.replaceAll("?"),listKey(sql, (List<Map<String, Object>>) args[0]), DBManager.Type.INSERT);
                }

                @Override
                public Object one() throws Exception {
                    return dbManager.updateDataBySql(m.replaceAll("?"),mapKey(sql, (Map<String, Object>)args[0]));
                }
            },args);
        }else if (SqlCommandType.DELETE == command.getType()) {


            return check(new Cons() {
                @Override
                public Object batch() throws Exception {
                    return dbManager.dataBatch(m.replaceAll("?"),listKey(sql,(List<Map<String, Object>>) args[0]),DBManager.Type.INSERT);
                }

                @Override
                public Object one() throws Exception {
                    return dbManager.deleteDataBySql(m.replaceAll("?"), mapKey(sql, (Map<String, Object>) args[0]));
                }
            },args);

        }
        return result;
    }

    public List<Object[]> listKey(String sql,List<Map<String,Object>> arg) throws Exception {
        List<Object[]> list = new ArrayList<Object[]>();
        List<Map<String,Object>> lm = arg;
        for (Map<String,Object> mp : lm){
            list.add(mapKey(sql, mp));
        }
        return list;
    }

    private interface Cons{
        Object batch() throws Exception;
        Object one() throws Exception;
    }

    private Object check(Cons cons,Object[] args) {
        try {
            if(args.length==1){
                if(args[0] instanceof List){
                    Object m = cons.batch();
                    return m;
                }else if(args[0] instanceof Map){
                    Object m = cons.one();
                    return m;
                }else{
                    throw new Exception("参数有误");
                }
            }else {
                throw new Exception("参数有误");
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private Object[] mapKey(String sql,Map<String,Object> arg) throws Exception {
        Matcher m = p.matcher(sql);
        Map<String ,Object> map = arg;
        List<Object> on = new ArrayList<Object>();
        int mcount = 0;
        while (m.find()) {
            //SLog.i(m.groupCount(),m.group(0));
            String mz = m.group(0);
            String k = mz.substring(2, mz.length() - 1);
            //SLog.i(k);
            if(map.containsKey(k)){
                on.add(map.get(k));
            }else{
                throw new Exception("参数有误,map键值不能映射");
            }
            mcount++;
        }
        Object[] objects = new Object[mcount];
        for(int i=0;i<mcount;i++){
            objects[i] = on.get(i);
        }
        return objects;
    }


    public static class ParamMap<V> extends HashMap<String, V> {

        private static final long serialVersionUID = -2212268410512043556L;

        @Override
        public V get(Object key) {
            if (!super.containsKey(key)) {
                throw new NullPointerException("Parameter '" + key + "' not found. Available parameters are " + keySet());
            }
            return super.get(key);
        }

    }

    public static class SqlCommand {

        private final SqlCommandType type;

        private final String clazz;

        private final String sql;

        public SqlCommand(Method method) {

            if(method.getAnnotation(Insert.class)!=null){
                sql = method.getAnnotation(Insert.class).value();
                type = SqlCommandType.INSERT;
            }else if(method.getAnnotation(Delete.class)!=null){
                sql = method.getAnnotation(Delete.class).value();
                type = SqlCommandType.DELETE;
            }else if(method.getAnnotation(Update.class)!=null){
                sql = method.getAnnotation(Update.class).value();
                type = SqlCommandType.UPDATE;
            }else if(method.getAnnotation(Select.class)!=null){
                sql = method.getAnnotation(Select.class).value();
                type = SqlCommandType.SELECT;
            }else{
                type = SqlCommandType.UNKNOWN;
                throw new NullPointerException("绑定异常");
            }
            if(method.getAnnotation(Clazz.class)!=null){
                clazz = method.getAnnotation(Clazz.class).value();
            }else{
                clazz = "";
            }

        }

        public SqlCommandType getType() {
            return type;
        }

        public String getSql() {
            return sql;
        }

        public String getClazz() {
            return clazz;
        }
    }

    public static class MethodSignature {


        private final Class<?> returnType;


        public MethodSignature(Method method) {

            this.returnType = method.getReturnType();
        }

        public Class<?> getReturnType() {
            return returnType;
        }

    }

}
