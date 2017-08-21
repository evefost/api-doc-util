package com.xie.api.mapping.resolver;

import org.springframework.web.bind.annotation.PostMapping;

import java.lang.annotation.Annotation;

/**
 * Created by xieyang on 17/8/3.
 */
public class PostMappingResolver implements MappingResolver{

    @Override
    public boolean support(Class type) {
        return PostMapping.class.isAssignableFrom(type);
    }

    @Override
    public String[]  getValue(Annotation annotation) {
        PostMapping mapping = (PostMapping) annotation;
        return mapping.value();
    }

    @Override
    public String[] getSupportMethods(Annotation annotation) {
        return  new String[]{"POST"};
    }


}
