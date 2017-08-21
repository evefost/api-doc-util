package com.xie.api.mapping.resolver;

import org.springframework.web.bind.annotation.PutMapping;

import java.lang.annotation.Annotation;

/**
 * Created by xieyang on 17/8/3.
 */
public class PutMappingResolver implements MappingResolver{

    @Override
    public boolean support(Class type) {
        return PutMapping.class.isAssignableFrom(type);
    }

    @Override
    public String[]  getValue(Annotation annotation) {
        PutMapping mapping = (PutMapping) annotation;
        return mapping.value();
    }

    @Override
    public String[] getSupportMethods(Annotation annotation) {
        return  new String[]{"PUT"};
    }


}
