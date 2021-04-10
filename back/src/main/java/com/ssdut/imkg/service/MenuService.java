package com.ssdut.imkg.service;

import com.ssdut.imkg.pojo.Menu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author fanyuanxin
 * @since 2021-03-14
 */
public interface MenuService extends IService<Menu> {

    /**
     * 根据角色获取菜单
     * @return
     */
    List<Menu> getMenusWithRole();

    /**
     * 通过id查询menu列表
     * @return
     */
    List<Menu> getMenusByUserId();
}
