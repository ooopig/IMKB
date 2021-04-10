package com.ssdut.imkg.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ssdut.imkg.pojo.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ssdut.imkg.pojo.pub.RespBean;
import com.ssdut.imkg.pojo.pub.UserRequestParam;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author fanyuanxin
 * @since 2021-03-14
 */
@Component
public interface UserMapper extends BaseMapper<User> {


    @Select("select count(*) from user")
    int getAllUsersCount();

    /**
     * 添加用户
     * @param user
     * @return
     */
    int addUser(UserRequestParam user);

    /**
     * 得到最大id
     * @return
     */
    @Select("select max(id) from user")
    int getMaxId();

    IPage<UserRequestParam> getUsers(Page<UserRequestParam> page, @Param("user") UserRequestParam user);

    List<UserRequestParam> getAllUsers();
}
