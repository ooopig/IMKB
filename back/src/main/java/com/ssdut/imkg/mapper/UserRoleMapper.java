package com.ssdut.imkg.mapper;

import com.ssdut.imkg.pojo.UserRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author fanyuanxin
 * @since 2021-03-14
 */
@Component
public interface UserRoleMapper extends BaseMapper<UserRole> {

    /**
     * 得到最大id
     * @return
     */
    @Select("select max(id) from user_role")
    int getMaxId();


}
