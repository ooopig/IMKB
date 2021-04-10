package com.ssdut.imkg.controller;

import com.ssdut.imkg.pojo.pub.UserRequestParam;
import com.ssdut.imkg.pojo.pub.RespBean;
import com.ssdut.imkg.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author Fan Yuanxin
 * @Date 2021/3/15 17:40
 * @Version 1.0
 */
@Api(tags = "注册")
@RestController
public class RegisterController {
    @Autowired
    private UserService userService;

    @ApiOperation("用户注册")
    @PostMapping("/register")
    public RespBean register(@RequestBody UserRequestParam user, HttpServletRequest request){
        System.out.println("注册接受的参数==========");

        System.out.println(user.toString());
        return userService.addUser(user);
    }
}
