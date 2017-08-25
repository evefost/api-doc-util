package test.com.xie.vo;

import test.com.xie.annotation.Descript;

@Descript("泛型测试类")
public class GenerBean<A,B> {

    @Descript()
    private A classA;

    private B classB;
}
