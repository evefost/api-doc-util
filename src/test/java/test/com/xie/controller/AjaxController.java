package test.com.xie.controller;


import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import test.com.xie.bean.ResponseDataVo;
import test.com.xie.vo.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/ajax")
public class AjaxController {


//    @RequestMapping(value = "generPost", method = RequestMethod.POST)
//    public ResponseDataVo gicParam(ResponseDataVo<SimpleUser> user) {
//        //user.getSchool().toString();
//        return ResponseDataVo.success(user);
//    }
//
//
//        @RequestMapping(value = "formAdd2", method = RequestMethod.POST)
//    public ResponseDataVo<SimpleUser> add(SimpleUser user, String testname) {
//        //user.getSchool().toString();
//        return ResponseDataVo.success(user);
//    }
//
//    @RequestMapping(value = "postBody", method = RequestMethod.POST)
//    public ResponseDataVo postBody(@RequestBody SimpleUser user) {
//        return ResponseDataVo.success(user);
//    }

    @RequestMapping(value = "submitINfo0")
    public ResponseDataVo getRequest0(SimpleUser user) {
        return ResponseDataVo.success(user);
    }

//    @RequestMapping(value = "submitINfo",method = RequestMethod.POST)
//    public ResponseDataVo getRequest(SimpleUser user) {
//        return ResponseDataVo.success(user);
//    }
//
//    @RequestMapping(value = "submitINfo2")
//    public ResponseDataVo<ResponseDataVo<Param>> getRequest(@RequestBody SimpleUser user, String username) {
//        return ResponseDataVo.success(user);
//    }
//
//    @RequestMapping(value = "submitINfo3")
//    public ResponseDataVo getRequest3( SimpleUser user,String username) {
//        return ResponseDataVo.success(user);
//    }
//
//    @RequestMapping(value = "submitINfo5",method = {RequestMethod.POST})
//    public ResponseDataVo<SimpleUser> getRequest5( @RequestBody SimpleUser user) {
//        return ResponseDataVo.success(user);
//    }
//
//    @RequestMapping(value = "submitINfo4")
//    public ResponseDataVo getRequest4(Long id, String name,String username) {
//        return ResponseDataVo.success(id);
//    }
//
//    @RequestMapping(value = "submitINfo6")
//    public ResponseDataVo getRequest6(Param params1) {
//        return ResponseDataVo.success(params1);
//    }
//
//    @RequestMapping(value = "submitINfo7")
//    public ResponseDataVo getRequest7(@RequestBody List<Param> params1) {
//        return ResponseDataVo.success(params1);
//    }
//
//    @RequestMapping(value = "submitINfo8")
//    public GenerBean<ClassA, ClassB> getRequest8(@RequestBody List<Param> params1) {
//        return null;
//    }


//    @RequestMapping(value = "submitINfo9")
//    public Map<ClassA, ClassB> getMap(@RequestBody List<Param> params1) {
//        return null;
//    }
}
