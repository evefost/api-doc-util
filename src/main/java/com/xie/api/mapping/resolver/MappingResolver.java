package com.xie.api.mapping.resolver;


import java.lang.annotation.Annotation;

/**
 * Created by xieyang on 17/8/3.
 */
public interface MappingResolver {

    boolean support(Class type);


    String[]  getValue(Annotation annotation);


    String[] getSupportMethods(Annotation annotation);




}
