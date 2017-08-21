package com.xie.api.mapping.resolver;

import com.xie.api.parse.ApiInfo;
import org.springframework.web.bind.annotation.GetMapping;

import java.lang.annotation.Annotation;

/**
 * Created by xieyang on 17/8/3.
 */
public class GetMappingResolver implements MappingResolver {

    @Override
    public boolean support(Class type) {
        return GetMapping.class.isAssignableFrom(type);
    }

    @Override
    public String[] getValue(Annotation annotation) {
        GetMapping mapping = (GetMapping) annotation;
        return mapping.value();
    }

    @Override
    public String[] getSupportMethods(Annotation annotation) {
        return   new String[]{"GET"};
    }


}
