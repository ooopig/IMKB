package com.ssdut.imkg.controller;


import com.ssdut.imkg.pojo.Menu;
import com.ssdut.imkg.service.MenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author fanyuanxin
 * @since 2021-03-14
 */
@RestController
@Api(tags = "菜单")
public class MenuController {
    @Autowired
    MenuService menuService;

    @ApiOperation(value = "通过用户id查询菜单列表")
    @GetMapping("/menu")
    public List<Menu> getMenusByUserId(){
        return menuService.getMenusByUserId();
    }
}
