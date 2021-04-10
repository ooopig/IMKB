package com.ssdut.imkg.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ssdut.imkg.config.security.component.UserDetailsServiceImpl;
import com.ssdut.imkg.pojo.User;
import com.ssdut.imkg.pojo.UserRole;
import com.ssdut.imkg.pojo.pub.RespBean;
import com.ssdut.imkg.pojo.pub.RespPageBean;
import com.ssdut.imkg.pojo.pub.UserRequestParam;
import com.ssdut.imkg.service.UserRoleService;
import com.ssdut.imkg.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author fanyuanxin
 * @since 2021-03-14
 */
@RestController
@Api(tags = "用户")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @Autowired
    private UserRoleService userRoleService;

    @ApiOperation(value = "获得当前登录用户信息")
    @GetMapping("/user/info")
    public User getUserInfo() {
        System.out.println("进入到获取用户信息请求");
        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal==null){
            System.out.println("principal为空");
            return null;
        }
        String username = principal.getName();
        User user = (User) userDetailsService.loadUserByUsername(username);
        user.setPassword(null);
        user.setRoles(userService.getUserRoles(user.getId()));
        System.out.println("当前用户的信息为"+user.toString());
        return user;
    }

    @ApiOperation(value = "更新当前用户信息")
    @PutMapping("/user/update")
    public RespBean updateUserInfo(@RequestBody User user){
        if(userService.updateById(user)){
            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(user,null,user.getAuthorities()));
            //System.out.println("当前用户信息=="+SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
            System.out.println("更新完成");
            return RespBean.success("更新成功");
        }else{
            return RespBean.error("更新失败");
        }
    }

    @ApiOperation(value = "更新密码")
    @PutMapping("/user/pass")
    public RespBean updateUserPassword(@RequestBody Map<String,Object> info){
        String oldPass = (String)info.get("oldPass");
        String newPass = (String)info.get("pass");
        Integer id = (Integer)info.get("userId");
        System.out.println("修改密码=====old====="+oldPass+"newPass===="+newPass+"id===="+id);
        return userService.updateUserPassword(oldPass,newPass,id);
    }

    @ApiOperation(value = "分页查询所有用户")
    @GetMapping("/manage/user/getUsers")
    public RespPageBean getUser(@RequestParam(defaultValue = "1")Integer currentPage,
                                @RequestParam(defaultValue = "10")Integer size,
                                UserRequestParam user){
        return userService.getUsers(currentPage,size,user);
    }

    @ApiOperation(value = "获得所有用户信息")
    @GetMapping("/manage/user/getAllUsers")
    public RespBean getAllUsers(){
        return userService.getAllUsers();
    }

    @ApiOperation(value = "删除用户")
    @DeleteMapping("/manage/user/delUser/{id}")
    public RespBean deleteUser(@PathVariable Integer id){
        if(userService.removeById(id)){
            return RespBean.success("删除成功");
        }
        return RespBean.error("删除失败");
    }

    @ApiOperation(value = "管理员更新用户信息")
    @PutMapping("/manage/user/update/")
    public RespBean updateUser(@RequestBody User user){
        if(userService.updateById(user)){
            UserRole userRole = userRoleService.getOne(new QueryWrapper<UserRole>().eq("user_id",user.getId()));
            userRole.setRoleId( Integer.parseInt(user.getRole()));
            //System.out.println("newrole==="+userRole);
            if(userRoleService.updateById(userRole))
                return RespBean.success("更新成功");
        }
        return RespBean.error("更新失败");
    }



}
