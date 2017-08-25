package test.com.xie.vo;


import test.com.xie.annotation.Descript;

@Descript("简单实体测试")
public class SimpleUser {

    private Long id;

    private String name;

    @Descript(hide = false)
    private int age;

}
