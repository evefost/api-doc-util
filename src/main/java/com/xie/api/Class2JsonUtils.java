package com.xie.api;

//import com.xie.java.common.annotation.Annotation;

import org.springframework.web.bind.annotation.RequestBody;
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;
import sun.reflect.generics.reflectiveObjects.TypeVariableImpl;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.HashMap;
import java.util.Map;

import static com.xie.api.ClassHelper.*;
import static com.xie.api.constant.DescriptMethodEnum.*;


/**
 * Created by xieyang on 17/7/31.
 */
public class Class2JsonUtils {

//    private static final String VALUE = "value";
//
//    private static final String REQUIRED = "required";
//
//    private static final String MESSAGE = "message";

    private static Class annotationClz;


    public static void main(String[] args) throws Exception {

//        System.out.println(isBase(int.class));
//        System.out.println(isBase(char.class));
//        System.out.println(isBase(long.class));
//        System.out.println(isBase(boolean.class));
//        System.out.println(isBase(Short.class));
//        System.out.println(isBase(Integer.class));
//        System.out.println(isBase(Long.class));
//        System.out.println(isBase(Float.class));
//        System.out.println(isBase(Double.class));
//        System.out.println(isBase(Boolean.class));
//        System.out.println(isBase(Character.class));
//        System.out.println(isBase(Byte.class));
//        System.out.println(isBase(String.class));
//        System.out.println(isBase(User.class));
//        System.out.println(new String[2].getClass().isArray());
        // System.out.println(isWrapClass(User.class));

//        User s= new User();
//        Student student = new Student();
//        student.setStudentCources("aaaa");
//        student.setStudentName("xieang");
//        Student student2 = new Student();
//        student2.setStudentCources("aaaa222");
//        student2.setStudentName("xieang2222");
//        Set<Student> students = new HashSet<>();
//        students.add(student);
//        students.add(student2);
//        s.setStudents(students);

        //Annotation annotation = User.class.getAnnotation(annotationClz);


//        StringBuffer sb = generateApiJsonForm(Inner.class, true, true);
//        System.out.println(sb);
//        StringBuffer stringBuffer = generateApiParamDescript(User.class);
//        System.out.println(stringBuffer.toString());

//        Field[] declaredFields = User.class.getDeclaredFields();
//        for (Field f : declaredFields) {
//            f.setAccessible(true);
//            Class<?>[] parameterizedType = getParameterizedType(f);
//            if (parameterizedType != null && parameterizedType.length > 0) {
//                for (Class clz : parameterizedType) {
//                    System.out.println(clz.getSimpleName());
//                }
//            }
//        }


    }

    public static StringBuffer generateApiJsonForm(Type type, Class descript) {
        StringBuffer sb = generateApiJsonForm(type, descript, false, true);
        return sb;
    }

    public static StringBuffer generateApiJsonForm(Type type, Class descript, boolean forJs, boolean withDescript) {
        annotationClz = descript;
        String classDes = "";
        if (type instanceof ParameterizedTypeImpl) {
            Class<?> rawType = ((ParameterizedTypeImpl) type).getRawType();
            Annotation annotation = rawType.getAnnotation(annotationClz);
            if (annotation != null) {
                classDes = appendDescript(withDescript, annotation, rawType, "").toString();
            }
        } else {
            Class<?> clz = (Class) type;
            Annotation annotation = clz.getAnnotation(annotationClz);
            if (annotation != null) {
                classDes = appendDescript(withDescript, annotation, clz, "").toString();
            }
        }
        StringBuffer sb = dogenerateJsonFormat(type, 0, forJs, withDescript, classDes);
        return sb;
    }

    private static StringBuffer dogenerateJsonFormat(Type srcType, int loop, boolean forJs, boolean withDescript, String objDes) {

        loop++;
        StringBuffer sb = new StringBuffer();
        for (int i = 1; i < loop; i++) {
            sb.append("\t");
        }
        Class<?> clz = null;
        Map<String, Type> typeHashMap = new HashMap<>();
        if (srcType instanceof ParameterizedTypeImpl) {
            clz = ((ParameterizedTypeImpl) srcType).getRawType();
            Type[] actualTypeArguments = ((ParameterizedTypeImpl) srcType).getActualTypeArguments();
            TypeVariable<? extends Class<?>>[] typeParameters = clz.getTypeParameters();
            for (int i = 0; i < actualTypeArguments.length; i++) {
                typeHashMap.put(typeParameters[i].getName(), actualTypeArguments[i]);
            }

        } else {
            clz = (Class<?>) srcType;
            if (isBaseClass(clz)) {
                return sb;
            }
        }
        if (isList(clz)) {
            TypeVariable<? extends Class<?>>[] typeParameters = clz.getTypeParameters();
            String typeName = typeParameters[0].getName();
            Type tType = typeHashMap.get(typeName);
            Class pClass = null;
            if (tType instanceof TypeVariableImpl) {
                //获取实参(实参里面也可能有泛型)

            } else if (tType instanceof ParameterizedTypeImpl) {
                // String typeName = ((ParameterizedTypeImpl) genericType).getActualTypeArguments()[0].toString();
                // pClass = (Class)tType;
            } else {
                pClass = (Class) tType;
            }
            sb.append("[");
            if (pClass == null) {
                sb.append(" //数组没有泛型参数,没法解释到实际参数型 ],");
            } else {
                if (isBaseClass(pClass)) {
                    sb.append(getDefaultValueByClassType(pClass)).append(',').append(getDefaultValueByClassType(pClass));
                    sb.append("]");
                    String paramterClass = pClass.getSimpleName().toLowerCase();
                    sb.append(appendDescript(withDescript, clz.getAnnotation(annotationClz), clz, paramterClass));
                } else {
                    String paramterClass = pClass.getSimpleName().toLowerCase();
                    sb.append(appendDescript(withDescript, clz.getAnnotation(annotationClz), pClass, paramterClass));
                    sb.append("\n");
                    Annotation annotation = pClass.getAnnotation(annotationClz);
                    objDes = "类型" + paramterClass;
                    if (annotation != null) {
                        objDes = (isEmpty((String) getAnnotationMethodReturn(annotation, VALUE)) ? (String) getAnnotationMethodReturn(annotation, MESSAGE) : (String) getAnnotationMethodReturn(annotation, VALUE)) + objDes;
                    }
                    StringBuffer json = dogenerateJsonFormat(pClass, loop, forJs, withDescript, " // " + objDes);
                    sb.append(json);
                    sb.append("\n]");
                }
            }

        } else {
            sb.append("{").append(withDescript ? objDes : "").append("\n");
            Class superclass = clz.getSuperclass();
            Field[] supperFields = new Field[]{};
            if (superclass != null) {
                supperFields = superclass.getDeclaredFields();
            }

            Field[] childFields = clz.getDeclaredFields();

            Field[] fields = new Field[supperFields.length + childFields.length];
            for (int n = 0; n < fields.length; n++) {
                if (n < supperFields.length) {
                    fields[n] = supperFields[n];
                } else {
                    fields[n] = childFields[n - supperFields.length];
                }
            }
            int c = 0;
            for (Field field : fields) {
                field.setAccessible(true);
                Annotation fieldAnno = field.getAnnotation(annotationClz);
                if(fieldAnno != null){
                    boolean hide = (boolean) getAnnotationMethodReturn(fieldAnno, HIDE);
                    if(hide){
                        continue;
                    }
                }
                c++;
                String name = field.getName();
                Class<?> type = field.getType();
                for (int i = 0; i < loop; i++) {
                    sb.append("\t");
                }
                if (forJs) {
                    sb.append(name);
                } else {
                    sb.append("\"").append(name).append("\"");
                }
                sb.append(':');
                if (isBaseClass(type)) {
                    if (forJs) {
                        sb.append("undefined");
                    } else {
                        sb.append(getDefaultValueByClassType(type));
                    }
                    if (c < fields.length) {
                        sb.append(",");
                    }
                    sb.append(appendDescript(withDescript, field.getAnnotation(annotationClz), type, ""));
                    sb.append("\n");
                } else {
                    if (isList(type)) {
                        Class pClass = null;
                        Type genericType = field.getGenericType();
                        if (genericType instanceof TypeVariableImpl) {
                            //获取实参(实参里面也可能有泛型)
                            Type tGenerType = typeHashMap.get(((TypeVariableImpl) genericType).getName());
                            if (tGenerType instanceof ParameterizedTypeImpl) {
                                //todo

                            } else {
                                //不再是泛型实参变量
                                pClass = (Class) tGenerType;
                            }
                        } else if (genericType instanceof ParameterizedTypeImpl) {
                            String typeName = ((ParameterizedTypeImpl) genericType).getActualTypeArguments()[0].toString();
                            pClass = (Class) typeHashMap.get(typeName);
                        }
                        if (pClass == null) {
                            pClass = getParameterizedClass(field);
                        }
                        sb.append("[");
                        if (pClass == null) {
                            sb.append(" //数组没有泛型参数,没法解释到实际参数型 ],");
                        } else {
                            if (isBaseClass(pClass)) {
                                sb.append(getDefaultValueByClassType(pClass)).append(',').append(getDefaultValueByClassType(pClass));
                                sb.append("]");
                                String paramterClass = pClass.getSimpleName().toLowerCase();
                                sb.append(appendDescript(withDescript, field.getAnnotation(annotationClz), type, paramterClass));
                            } else {
                                String paramterClass = pClass.getSimpleName().toLowerCase();
                                sb.append(appendDescript(withDescript, field.getAnnotation(annotationClz), type, paramterClass));
                                sb.append("\n");
                                StringBuffer json = dogenerateJsonFormat(pClass, loop, forJs, withDescript, "");
                                sb.append(json);
                                sb.append("]");
                            }
                        }
                    } else {
                        String tObjdes = appendDescript(withDescript, field.getAnnotation(annotationClz), type, "").toString();
                        StringBuffer json = null;
                        Type genericType = field.getGenericType();
                        if (genericType instanceof TypeVariableImpl) {
                            //获取实参(实参里面也可能有泛型)
                            Type tGenerType = typeHashMap.get(((TypeVariableImpl) genericType).getName());
                            if (tGenerType == null) {
                                System.out.println("参数变量没有赋实例(" + clz.getSimpleName() + ")");
                                sb.append("{" + (withDescript ? " // 参数变量没有赋值,实际数据格式不可知" : "") + "},\n");
                                continue;
                            } else {
                                if (tGenerType instanceof ParameterizedTypeImpl) {
                                    json = dogenerateJsonFormat(tGenerType, loop, forJs, withDescript, tObjdes);
                                } else {
                                    //不再是泛型实参变量
                                    Class aClass = (Class) tGenerType;
                                    if (aClass != null) {
                                        tObjdes = appendDescript(withDescript, field.getAnnotation(annotationClz), aClass, "").toString();
                                        json = dogenerateJsonFormat(aClass, loop, forJs, withDescript, tObjdes);
                                    } else {
                                        json = dogenerateJsonFormat(aClass, loop, forJs, withDescript, tObjdes);
                                    }
                                }
                            }
                        } else if (genericType instanceof ParameterizedTypeImpl) {
                            json = dogenerateJsonFormat(genericType, loop, forJs, withDescript, tObjdes);
                        } else {
                            json = dogenerateJsonFormat(type, loop, forJs, withDescript, tObjdes);
                        }
                        sb.append(json);

                    }
                    if (c < fields.length) {
                        sb.append(",");
                    }
                    sb.append("\n");
                }
            }
            for (int i = 1; i < loop; i++) {
                sb.append("\t");
            }
            sb.append("}");
        }

        loop--;
        return sb;

    }

    private static StringBuffer appendDescript(boolean withDescript, Annotation annotation, Class type, String paramterClass) {
        StringBuffer sb = new StringBuffer();
        if (!withDescript) {
            return sb;
        }
        sb.append(" //");
        if (annotation != null) {
            String message = isEmpty((String) getAnnotationMethodReturn(annotation, VALUE)) ? (String) getAnnotationMethodReturn(annotation, MESSAGE) : (String) getAnnotationMethodReturn(annotation, VALUE);
            sb.append(message);
        }
        sb.append(" 类型:").append(type.getSimpleName().toLowerCase());
        if (!isEmpty(paramterClass)) {
            sb.append("<" + paramterClass + ">");
        }
        if (annotation != null) {
            boolean require = (boolean) getAnnotationMethodReturn(annotation, REQUIRED);
            sb.append(" 必选(" + (require ? "是)" : "否)"));
        } else {
            sb.append(" 必选(否)");
        }
        return sb;

    }


    public static boolean isEmpty(String str) {
        if (str == null || str.trim().length() == 0 || "".equals(str)) {
            return true;
        }
        return false;
    }


    //    |参数名|必选|类型|说明|
//            |:----    |:---|:----- |-----   |
//            |searchKey |否  |string | 原密码    |
//            |roleId |否  |long | 角色id    |
//            |positionId |否  |long | 职位id    |
//            |departId |否 |long | 部门id    |
    public static StringBuffer generateApiParamDescript(Class clz, Class annoClz) {

        boolean isbody = clz.isAnnotationPresent(RequestBody.class);
        StringBuffer sb = new StringBuffer();
        sb.append("|参数名|必选|类型|说明|\n").append("|:----    |:---|:----- |-----   |\n");
        parseFieldsDescript(clz, annotationClz, sb, isbody);
        return sb;

    }


    public static void parseFieldsDescript(Class clz, Class annoClz, StringBuffer sb, boolean isbody) {

        Class superclass = clz.getSuperclass();
        Field[] supperFields = new Field[]{};
        if (superclass != null) {
            supperFields = superclass.getDeclaredFields();
        }
        Field[] childFields = clz.getDeclaredFields();
        Field[] fields = new Field[supperFields.length + childFields.length];
        for (int n = 0; n < fields.length; n++) {
            if (n < supperFields.length) {
                fields[n] = supperFields[n];
            } else {
                fields[n] = childFields[n - supperFields.length];
            }
        }
        for (Field field : fields) {
            parseFieldDescript(field,annoClz,sb,isbody);
        }

    }

    private static void parseFieldDescript(Field field, Class annotationClz, StringBuffer sb, boolean isbody) {
        field.setAccessible(true);
        Class<?> type = field.getType();
        String name = field.getName();
        Annotation annotation = field.getAnnotation(annotationClz);
        String warn = "|\n";
        if (!isBaseClass(type) && !isbody) {
            warn = "(注意，非基本数据类型，form或get可能无法接收整个参数,尝试用post body方式)|\n";
        }
        if (annotation != null) {
            boolean require = (boolean) getAnnotationMethodReturn(annotation, REQUIRED);
            sb.append("|").append(name).append("|" + (require ? "是" : "否"));
        } else {
            sb.append("|").append(name).append("| 否");
        }
        sb.append("|").append(type.getSimpleName().toLowerCase());
        if (annotation != null) {
            String message = isEmpty((String) getAnnotationMethodReturn(annotation, VALUE)) ? (String) getAnnotationMethodReturn(annotation, MESSAGE) : (String) getAnnotationMethodReturn(annotation, VALUE);
            sb.append("|" + message);

        } else {
            sb.append("|暂无参数说明|");
        }
        sb.append(warn);


    }


}



