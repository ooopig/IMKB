package com.ssdut.imkg.mapper;

import com.ssdut.imkg.pojo.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
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
public interface RoleMapper extends BaseMapper<Role> {

    /**
     * 根据用户id获取权限列表
     * @param id
     * @return
     */
    List<Role> getUserRoles(Integer id);
}
