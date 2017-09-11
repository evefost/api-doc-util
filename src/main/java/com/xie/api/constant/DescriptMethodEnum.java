package com.xie.api.constant;

/**
 * 接口描述注解定所需定义的方法
 */
public enum DescriptMethodEnum {

    VALUE("value", "描述默认值"),
    MESSAGE("message", "描述"),
    HIDE("hide", "是否隐藏该字段，不生成接口文档参数"),
    REQUIRED("required", "是否为必须");

    private String methodName;

    private String descript;

    DescriptMethodEnum(String methodName, String descript) {
        this.methodName = methodName;
        this.descript = descript;
    }



    @Override
    public String toString() {
        return methodName;
    }
}
