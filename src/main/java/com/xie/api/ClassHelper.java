package com.xie.api;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;

/**
 * Created by xieyang on 17/8/4.
 */
public class ClassHelper {




    public static boolean isBaseClass(Class clz) {
        try {
            if (clz.isPrimitive()) {
                return true;
            }
            if (clz.getSimpleName().equals("String")) {
                return true;
            }
            if (clz.getSimpleName().equals("date")) {
                return true;
            }
            return ((Class) clz.getField("TYPE").get(null)).isPrimitive();
        } catch (Exception e) {
            return false;
        }
    }


    /**
     * 只支持一个泛型
     *
     * @param field
     * @return
     */
    public static Class getParameterizedClass(Field field) {
        Class<?>[] parameterizedType = getParameterizedType(field);
        if (parameterizedType != null && parameterizedType.length > 0) {
            for (Class clz : parameterizedType) {
                //System.out.println(clz.getSimpleName());
            }
            return parameterizedType[0];
        }
        return null;
    }

    public static Class<?>[] getParameterizedType(Field f) {
        // 获取f字段的通用类型
        Type fc = f.getGenericType(); // 关键的地方得到其Generic的类型
        // 如果不为空并且是泛型参数的类型
        if (fc != null && fc instanceof ParameterizedType) {
            ParameterizedType pt = (ParameterizedType) fc;
            Type[] types = pt.getActualTypeArguments();
            if (types != null && types.length > 0) {
                Class<?>[] classes = new Class<?>[types.length];
                for (int i = 0; i < classes.length; i++) {
                    classes[i] = (Class<?>) types[i];
                }
                return classes;
            }
        }
        return null;
    }

    public static boolean isList(Class clz) {
        String simpleName = clz.getSimpleName().toLowerCase();
        switch (simpleName) {
            case "list":
            case "arraylist":
            case "linklist":
            case "set":
            case "hashset":
                return true;
        }

        return false;
    }

    public static Object getDefaultValueByClassType(Class type) {
        String typeName = type.getSimpleName().toLowerCase();
        switch (typeName) {
            case "short":
            case "int":
            case "integer":
            case "long":

                return 0;
            case "double":
            case "float":
                return 0.0;
            case "boolean":
                return false;
            case "byte":
                return "0";
            case "string":
                return "\"\"";
            case "char":
            case "character":
                return "0";
            default:
                break;

        }
        return null;
    }


    public static Method getMethod(Class clz, String methodName){
        Method[] declaredMethods = clz.getDeclaredMethods();
        for(Method method:declaredMethods){
            if(methodName.equals(method.getName())){
                return method;
            }
        }
        return null;

    }

    public static Object getAnnotationMethodReturn(Annotation annotation, Object methodName) {
        Class<? extends Annotation> targetDescriptClass = annotation.getClass();
        Method method = getMethod(targetDescriptClass, methodName.toString());
        if(method != null){
            method.setAccessible(true);
            try {
                return method.invoke(annotation, null);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return null;

    }


}
