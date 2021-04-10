package com.ssdut.imkg.config.security.component;

import com.ssdut.imkg.pojo.Role;
import com.ssdut.imkg.pojo.User;
import com.ssdut.imkg.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author Fan Yuanxin
 * @Date 2021/3/14 15:46
 * @Version 1.0
 */
@Component
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserService userService;

    //返回一个UserDetails对象，这里的user继承了该对象
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.getUserByUserName(username);

        //List<GrantedAuthority> grantedAuthorities = new ArrayList<>();

        List<Role>roles = userService.getUserRoles(user.getId());


        if (null!=user){
            user.setRoles(roles);

            //System.out.println("当前用户为===="+user.toString());
            return user;
        }
        return user;
    }
}
