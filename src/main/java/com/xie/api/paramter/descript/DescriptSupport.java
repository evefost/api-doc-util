package com.xie.api.paramter.descript;


import com.xie.api.parse.ParamtersInfo;

/**
 * Created by xieyang on 17/8/20.
 */
public abstract class DescriptSupport {

    protected Class descriptAnno;

    public DescriptSupport(Class descriptAnno) {
        this.descriptAnno = descriptAnno;
    }

    public abstract boolean support(ParamtersInfo pInfo);

    public abstract StringBuffer getDescript(ParamtersInfo pInfo, String[] parameterNames, int index);
}
