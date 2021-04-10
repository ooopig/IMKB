package com.ssdut.imkg.service;

import com.ssdut.imkg.pojo.Role;
import com.ssdut.imkg.pojo.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ssdut.imkg.pojo.pub.RespPageBean;
import com.ssdut.imkg.pojo.pub.UserRequestParam;
import com.ssdut.imkg.pojo.pub.RespBean;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author fanyuanxin
 * @since 2021-03-14
 */
public interface UserService extends IService<User> {

    /**
     * 获得所有用户信息分页
     * @param currentPage
     * @param size
     * @param user
     * @return
     */
    RespPageBean getUsers(Integer currentPage, Integer size, UserRequestParam user);

    /**
     * 根据用户名获得用户信息
     * @param username
     * @return
     */
    User getUserByUserName(String username);

    /**
     * 根据用户id，获得该用户的角色
     * @param id
     * @return
     */
    List<Role> getUserRoles(Integer id);

    RespBean login(String username, String password, String code, HttpServletRequest request);

    int getAllUsersCount();

    RespBean addUser(UserRequestParam user);

    RespBean updateUserPassword(String oldPass, String newPass, Integer id);

    RespBean getAllUsers();
}
