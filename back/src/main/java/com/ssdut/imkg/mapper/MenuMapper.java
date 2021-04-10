package com.ssdut.imkg.mapper;

import com.ssdut.imkg.pojo.Menu;
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
public interface MenuMapper extends BaseMapper<Menu> {

    /**
     * 根据角色获取菜单
     * @return
     */
    List<Menu> getMenusWithRole();

    /**
     * 根据用户id获取菜单
     * @param id
     * @return
     */
    List<Menu> getMenusByUserId(Integer id);
}
