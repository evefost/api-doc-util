package com.xie.api.mapping.resolver;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.lang.annotation.Annotation;

/**
 * Created by xieyang on 17/8/3.
 */
public class RequestMappingResolver implements MappingResolver {

    @Override
    public boolean support(Class type) {
        return RequestMapping.class.isAssignableFrom(type);
    }

    @Override
    public String[] getValue(Annotation annotation) {
        RequestMapping mapping = (RequestMapping) annotation;
        return mapping.value();
    }

    @Override
    public String[] getSupportMethods(Annotation annotation) {
        RequestMapping mapping = (RequestMapping) annotation;
        RequestMethod[] methods = mapping.method();
        if (methods == null || methods.length == 0) {
            methods = RequestMethod.values();
        }
        String[] supportMethods = new String[methods.length];
        for (int i = 0; i < methods.length; i++) {
            supportMethods[i] = methods[i].toString();
        }
        return supportMethods;
    }


}
