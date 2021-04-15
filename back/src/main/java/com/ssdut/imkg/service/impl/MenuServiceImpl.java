package com.ssdut.imkg.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.ssdut.imkg.pojo.Menu;
import com.ssdut.imkg.mapper.MenuMapper;
import com.ssdut.imkg.pojo.Role;
import com.ssdut.imkg.pojo.User;
import com.ssdut.imkg.service.MenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author fanyuanxin
 * @since 2021-03-14
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    @Autowired
    private MenuMapper menuMapper;
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 根据角色获取菜单
     * @return
     */
    @Override
    public List<Menu> getMenusWithRole() {
        List<Menu>menus = menuMapper.getMenusWithRole();
        for (Menu menu:menus
             ) {
            //System.out.println("url======"+menu.getUrl());
            for(Role role:menu.getRoles()){
                System.out.println(role.toString());
            }
        }
        return menus;
    }

    /**
     * 通过id获取菜单列表
     * @return
     */
    @Override
    public List<Menu> getMenusByUserId() {
        Integer userId = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        ValueOperations<String,Object> valueOperations = redisTemplate.opsForValue();

        //从redis中取
        List<Menu> menus = (List) valueOperations.get("menu" + userId);
        if(CollectionUtils.isEmpty(menus)){
            menus = menuMapper.getMenusByUserId(userId);
            System.out.println("redis中不存在menu");
            //将数据存储到redis中
            valueOperations.set("menu-"+userId,menus);
        }
        return menus;
    }

    @Override
    public Menu getMenuById(Integer id) {
        return menuMapper.selectOne(new QueryWrapper<Menu>().eq("id",id));
    }
}
