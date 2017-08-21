package com.xie.api.parse;


import com.xie.api.ApiScanUtils;
import com.xie.api.Class2JsonUtils;
import com.xie.api.mapping.resolver.ResolverSupport;
import org.springframework.web.bind.annotation.RequestBody;
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import static com.xie.api.ApiScanUtils.annotationClz;
import static com.xie.api.ClassHelper.isBaseClass;


/**
 * 参数信息
 */
public class ParamtersInfo implements IInfo {

    /**
     * 基本类型
     */
    public static final int BASE = 1;//1

    /**
     * 对象类型
     */
    public static final int OBJ = 2;//10

    /**
     * 泛型对象
     */
    public static final int GIC_OBJ = 4;//100

    /**
     * 其它
     */
    public static final int OTHER = 8;//1000

    public int pType = 1;


    private ResolverSupport resolverSupport;


    private Type paramsType;

    private Class rawClz;

    private Class descriptAnno;


    private Annotation[] paramAnnotations;

    public Type getParamsType() {
        return paramsType;
    }

    public void setParamsType(Type paramsType) {
        this.paramsType = paramsType;
    }

    public ParamtersInfo(ResolverSupport resolverSupport, Type paramType, Annotation[] paramAnnotations) {
        this.paramsType = paramType;
        this.resolverSupport = resolverSupport;
        this.paramAnnotations = paramAnnotations;
        if (paramType instanceof Class) {
            rawClz = (Class) paramType;
            if (isBaseClass(rawClz)) {
                pType = BASE;
            } else {
                pType = OBJ;
            }
        } else if (paramType instanceof ParameterizedTypeImpl) {
            pType = GIC_OBJ;
            rawClz = ((ParameterizedTypeImpl) paramType).getRawType();
        } else {
            pType = OTHER;
        }
        hashAnnotationRequetBody();

    }


    public boolean hashAnnotationRequetBody() {
        if(paramAnnotations == null || paramAnnotations.length==0){
            return false;
        }
        for(Annotation annotation:paramAnnotations){
            if(annotation instanceof RequestBody){
                return true;
            }
        }
        return false;
    }


    @Override
    public StringBuffer parse() {
        StringBuffer sb = new StringBuffer();
        StringBuffer rs = Class2JsonUtils.generateApiJsonForm(paramsType,annotationClz, ApiScanUtils.forJs, ApiScanUtils.withDes);
        sb.append(rs);
        return sb;
    }


}
