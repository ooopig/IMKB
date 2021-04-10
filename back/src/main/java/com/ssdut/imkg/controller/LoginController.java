package com.ssdut.imkg.controller;


import com.ssdut.imkg.pojo.pub.UserLoginParam;
import com.ssdut.imkg.pojo.pub.RespBean;
import com.ssdut.imkg.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Api(tags="登录")
@RestController
public class LoginController {
    @Autowired
    private UserService userService;
    @ApiOperation(value = "登陆后返回token")
    @PostMapping("/login")
    public RespBean login(@RequestBody UserLoginParam userLoginParam, HttpServletRequest request){
        String username = userLoginParam.getUsername();
        String password = userLoginParam.getPassword();
        String code = userLoginParam.getCode();
        System.out.println("请求登录===username====="+username+"=====password====="+password+"=====code====="+code);
        return userService.login(username,password,code, request);
    }

    @ApiOperation(value="退出登录")

    @PostMapping("/logout")
    public RespBean logout(){
        return RespBean.success("注销成功");
    }
}
