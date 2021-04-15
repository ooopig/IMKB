package com.ssdut.imkg.controller;


import com.ssdut.imkg.pojo.Menu;
import com.ssdut.imkg.pojo.Node;
import com.ssdut.imkg.service.MenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
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
        List<Menu> menus = menuService.getMenusByUserId();
//        for(int i = 0;i<menus.size();i++){
//            if(menus.get(i).getId()==200){
//                for(int j=0;j<menus.get(i).getChildren().size();j++){
//                    if(menus.get(i).getChildren().get(j).getId() == 204){
//                        //System.out.println(menus.get(i).getChildren().get(j));
//                        Menu menuById = menuService.getMenuById(2041);
//                        //System.out.println(menuById);
//                        List<Menu> menus1 = new ArrayList<>();
//                        menus1.add(menuById);
//                        menus.get(i).getChildren().get(j).setChildren(menus1);
//                        break;
//                    }
//                }
//                break;
//            }
//        }
        return menus;
    }
}
