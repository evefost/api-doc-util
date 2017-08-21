package com.xie.api.parse;


import com.xie.api.ClassHelper;
import com.xie.api.mapping.resolver.MappingResolver;
import com.xie.api.mapping.resolver.ResolverSupport;
import com.xie.api.paramter.descript.DescriptSupport;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.Set;

import static com.xie.api.ApiScanUtils.paramterSupports;
import static com.xie.api.constant.DescriptMethodEnum.VALUE;


/**
 * 接口信息
 */
public class ApiInfo implements IInfo {

    private Method method;

    private Annotation annotation;


    /**
     * 接口描述
     */
    private String description;

    /**
     * 接口路径
     */
    private String path;

    /**
     * 接口支持的请求方式
     */
    private String[] supportMethods;

    /**
     * 接口参数信息
     */
    private ParamtersInfo[] paramtersInfos;

    /**
     * 接口返回信息
     */
    private ResponeInfo responeInfo;


    public ResponeInfo getResponeInfo() {
        return responeInfo;
    }

    public void setResponeInfo(ResponeInfo responeInfo) {
        this.responeInfo = responeInfo;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Annotation getAnnotation() {
        return annotation;
    }

    public void setAnnotation(Annotation annotation) {
        this.annotation = annotation;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String[] getSupportMethods() {
        return supportMethods;
    }

    public void setSupportMethods(String[] supportMethods) {
        this.supportMethods = supportMethods;
    }

    public ParamtersInfo[] getParamtersInfos() {
        return paramtersInfos;
    }

    public void setParamtersInfos(ParamtersInfo[] paramtersInfos) {
        this.paramtersInfos = paramtersInfos;
    }

    public ResolverSupport getResolverSupport() {
        return resolverSupport;
    }

    public void setResolverSupport(ResolverSupport resolverSupport) {
        this.resolverSupport = resolverSupport;
    }

    private ResolverSupport resolverSupport;

    public ApiInfo(String path, Annotation annotation, Method method, ResolverSupport resolverSupport) {
        this.path = path;
        this.annotation = annotation;
        this.method = method;
        this.resolverSupport = resolverSupport;


    }

    @Override
    public StringBuffer parse() {

        System.out.println("\n接口路径:" + path);

        StringBuffer sb = new StringBuffer();
        sb.append("\n");

        Annotation annotation = method.getAnnotation(resolverSupport.getDescriptAnno());
        if (annotation != null) {
            description = (String) ClassHelper.getAnnotationMethodReturn(annotation,VALUE);
        } else {
            description = "未知";
        }
        sb.append("接口路径描述:" + description);
        sb.append("\n接口路径:" + path);
        MappingResolver resoler = resolverSupport.getResoler(this.annotation);
        supportMethods = resoler.getSupportMethods(this.annotation);
        sb.append("\n\n***支持的请求方式：** ");
        for (String m : supportMethods) {
            sb.append(" - " + m);
        }

        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        //开始解释参数处理泛型参数,遍历收集参数
        Type[] gTypes = method.getGenericParameterTypes();
        if (gTypes != null && gTypes.length > 0) {
            paramtersInfos = new ParamtersInfo[gTypes.length];
            int index = 0;
            ParamtersInfo pInfo = null;
            for (Type type : gTypes) {
                pInfo = new ParamtersInfo(resolverSupport, type,parameterAnnotations[index]);
                paramtersInfos[index] = pInfo;
                index++;
            }
        }
        //解释参数处理
        doParse(sb);

        //返回参数
        Type genericReturnType = method.getGenericReturnType();
        sb.append("\n");
        sb.append("返回参数格式 ");
        sb.append("\n``` \n");
        ParamtersInfo pInfo = new ParamtersInfo(resolverSupport, genericReturnType,null);
        sb.append(pInfo.parse());
        sb.append("\n``` \n");
        return sb;
    }


    /**
     * 合并参数
     */
    private void doParse(StringBuffer sb ) {
        int parseType = 0;
        int objCount = 0;
        if (paramtersInfos != null && paramtersInfos.length > 0) {
            for (ParamtersInfo pI : paramtersInfos) {
                parseType = parseType | pI.pType;
                objCount++;
            }
        }
        sb.append("\n\n");
        String[] parameterNames = resolverSupport.getParameterNames(method);
        switch (parseType) {
            case 0:
                //无参数
                sb.append("**接收参数：** ");
                sb.append("\n无参数");
                break;
            case 1: //一个或多个简单怎么数
            case 3: //简单参数对象参数的组合（只支持单层）
                sb.append("|参数名|必选|类型|说明|\n").append("|:----    |:---|:----- |-----   |\n");
                doParseDescript(sb, parameterNames);
                break;
            case 2://一个或多对象参数
                boolean hasRequestBody = false;
                for(int i=0;i<paramtersInfos.length;i++){
                    if(paramtersInfos[i].hashAnnotationRequetBody()){
                        ParamtersInfo pInfo = paramtersInfos[0];
                        hasRequestBody = true;
                        sb.append("接收参数放在body里上报");
                        sb.append("\n``` \n");
                        sb.append(pInfo.parse());
                        sb.append("\n``` \n");
                        break;
                    }
                }
                if(!hasRequestBody){
                    sb.append("|参数名|必选|类型|说明|\n").append("|:----    |:---|:----- |-----   |\n");
                    doParseDescript(sb, parameterNames);
                }
                break;
            case 4:
                //一个或多个泛形
                if(objCount == 1){
                    ParamtersInfo pInfo = paramtersInfos[0];
                    sb.append("接收参数放在body里上报");
                    sb.append("\n``` \n");
                    sb.append(pInfo.parse());
                    sb.append("\n``` \n");
                }else {

                }
                break;
            case 5:
                //一个或多个简单参数与泛形对象组合(参数可能无法解释)
                break;
            case 6:
                //对象与泛参组合(参数可能无法解释)
                break;
            case 7:
                //简单，对象，泛型组合(参数可能无法解释)
                break;
            default:
                break;
        }


        System.out.println("参数类型:" + parseType);
    }

    private void doParseDescript(StringBuffer sb, String[] parameterNames) {
        for(int i=0;i<paramtersInfos.length;i++){
            Set<Map.Entry<String, DescriptSupport>> entries = paramterSupports.entrySet();
            for(Map.Entry<String, DescriptSupport> entry:entries){
                DescriptSupport support = entry.getValue();
                if(support.support(paramtersInfos[i])){
                    sb.append(support.getDescript(paramtersInfos[i],parameterNames,i));
                    continue;
                }

            }
        }
    }


}
