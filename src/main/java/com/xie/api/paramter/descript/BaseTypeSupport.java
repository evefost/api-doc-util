package com.xie.api.paramter.descript;


import com.xie.api.Class2JsonUtils;
import com.xie.api.parse.ParamtersInfo;

import java.lang.annotation.Annotation;

import static com.xie.api.ClassHelper.getAnnotationMethodReturn;
import static com.xie.api.constant.DescriptMethodEnum.MESSAGE;
import static com.xie.api.constant.DescriptMethodEnum.VALUE;

/**
 * Created by xieyang on 17/8/20.
 */
public class BaseTypeSupport extends DescriptSupport{

    public BaseTypeSupport(Class descriptAnno) {
        super(descriptAnno);
    }

    @Override
    public boolean support(ParamtersInfo paramtersInfo) {
        return paramtersInfo.pType== ParamtersInfo.BASE;
    }

    @Override
    public StringBuffer getDescript(ParamtersInfo pInfo, String[] parameterNames, int index) {
        StringBuffer sb = new StringBuffer();
        String name = parameterNames[index];
        Class<?> clz = (Class<?>) pInfo.getParamsType();
        Annotation annotation = clz.getAnnotation(descriptAnno);

        if (annotation != null) {
            boolean require = (boolean) getAnnotationMethodReturn(annotation, "required");
            sb.append("|").append(name).append("|" + (require ? "是" : "否"));
        } else {
            sb.append("|").append(name).append("| 未知");
        }
        sb.append("|").append(clz.getSimpleName().toLowerCase());
        if (annotation != null) {
            String message = Class2JsonUtils.isEmpty((String) getAnnotationMethodReturn(annotation, VALUE)) ? (String) getAnnotationMethodReturn(annotation, MESSAGE) : (String) getAnnotationMethodReturn(annotation, VALUE);
            sb.append("|" + message+ "|\n");
        } else {
            sb.append("|暂无参数说明|\n");
        }
        return sb;
    }
}
