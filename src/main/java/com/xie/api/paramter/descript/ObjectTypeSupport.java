package com.xie.api.paramter.descript;


import com.xie.api.parse.ParamtersInfo;

import static com.xie.api.Class2JsonUtils.parseFieldsDescript;

/**
 * Created by xieyang on 17/8/20.
 */
public class ObjectTypeSupport extends DescriptSupport{


    public ObjectTypeSupport(Class descriptAnno) {
        super(descriptAnno);
    }

    @Override
    public StringBuffer getDescript(ParamtersInfo pInfo, String[] parameterNames, int index) {
        StringBuffer sb = new StringBuffer();
        Class clz = (Class) pInfo.getParamsType();
        parseFieldsDescript(clz,descriptAnno,sb,pInfo.hashAnnotationRequetBody());
        return sb;
    }

    @Override
    public boolean support(ParamtersInfo paramtersInfo) {
        return paramtersInfo.pType==ParamtersInfo.OBJ;
    }


}
