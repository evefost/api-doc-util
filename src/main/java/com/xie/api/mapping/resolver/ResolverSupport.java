package com.xie.api.mapping.resolver;

import org.springframework.core.LocalVariableTableParameterNameDiscoverer;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/4.
 */
public class ResolverSupport {

    public static final LocalVariableTableParameterNameDiscoverer parameterNameDiscoverer = new LocalVariableTableParameterNameDiscoverer();


    private Map<String, MappingResolver> mappingResolverMap;

    private Class descriptAnno;

    public ResolverSupport(Map<String, MappingResolver> mappingResolverMap, Class descriptAnno) {
        this.mappingResolverMap = mappingResolverMap;
        this.descriptAnno = descriptAnno;
    }

    public MappingResolver getResoler(Annotation annotation) {
        for (Map.Entry<String, MappingResolver> entry : mappingResolverMap.entrySet()) {
            MappingResolver resolver = entry.getValue();
            if (resolver.support(annotation.getClass())) {
                return resolver;
            }
        }
        return null;
    }

    /**
     * 获取方法参数名称
     * @param method
     * @return
     */
    public  String[] getParameterNames(Method method) {
        return parameterNameDiscoverer.getParameterNames(method);
    }

    public Class getDescriptAnno() {
        return descriptAnno;
    }
}
