package test.com.xie.controller;


import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import test.com.xie.bean.ResponseDataVo;


@RestController
@RequestMapping("/restful")
public class RsController {


    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public ResponseDataVo addUser() {
        return ResponseDataVo.success(null, "添加用户");
    }

    //http://127.0.0.1:8080/restful/user/11
    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public ResponseDataVo getUser(@PathVariable Long id) {

        return ResponseDataVo.success(null, "获取用户");
    }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
    public ResponseDataVo deleteUser(@PathVariable Long id) {

        return ResponseDataVo.success(null, "删除用户");
    }


    @RequestMapping(value = "/user/{id}/{name}", method = RequestMethod.PUT)
    public ResponseDataVo updateUser(@PathVariable Long id, @PathVariable String name) {
        return ResponseDataVo.success(null, "更新用户");
    }

}
