package com.ssdut.imkg.controller;


import com.baomidou.mybatisplus.extension.api.R;
import com.ssdut.imkg.pojo.Node;
import com.ssdut.imkg.pojo.Relation;
import com.ssdut.imkg.pojo.pub.NodeParam;
import com.ssdut.imkg.pojo.pub.RelationParam;
import com.ssdut.imkg.pojo.pub.RespBean;
import com.ssdut.imkg.pojo.pub.RespPageBean;
import com.ssdut.imkg.service.RelationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author fanyuanxinmenu_role
 * @since 2021-04-06
 */
@RestController
@Api(tags = "关系")
public class RelationController {
    @Autowired
    private RelationService relationService;

    @ApiOperation(value = "添加关系")
    @PostMapping("/manage/kb/addRelation")
    public RespBean addRelation(@RequestBody Relation relation){
        if(relationService.addRelation(relation)){
            return RespBean.success("添加关系成功！");
        }
        return RespBean.error("添加关系失败，请重试");
    }

    @ApiOperation(value = "查找一个节点的所有子关系")
    @GetMapping("/kg/graph/getRelation/{id}")
    public List<Relation> getChildrenRelation(@PathVariable Integer id){
        List<Relation> relations = relationService.getChildrenRelation(id);
        return relations;
    }

    @ApiOperation(value = "删除关系")
    @GetMapping("/manage/kb/delOneRelation/{id}")
    public RespBean deleteOneRelation(@PathVariable Integer id){
        if(relationService.removeById(id)){
            return RespBean.success("删除成功");
        }
        return RespBean.error("删除失败");
    }

    @ApiOperation(value = "删除一个节点所有关系")
    @GetMapping("/manage/kb/delRelations/{id}")
    public RespBean deleteAllRelation(@PathVariable Integer id){
        List<Relation> relations = relationService.getChildrenRelation(id);
        for (Relation r:relations) {
            relationService.removeById(r.getId());
        }
        return RespBean.error("删除成功");
    }

    @ApiOperation(value = "删除一个节点的特定关系")
    @GetMapping("/manage/kb/delRelation/{id}/{name}")
    public RespBean deleteAllRelation(@PathVariable Integer id,@PathVariable String name){
        if(relationService.removeByIdAndName(id,name))
            return RespBean.success("删除成功");
        return RespBean.error("删除失败");
    }

    @ApiOperation(value = "获得所有的关系")
    @GetMapping("/kg/graph/allRelations")
    public List<Relation> getAllRelations(){
        return relationService.getAllRelations();
    }

    @ApiOperation(value = "根据id查询关系信息")
    @GetMapping("/kg/graph/relation/{id}")
    public Relation getRelationById(@PathVariable("id")Integer id){
        return relationService.getRelationById(id);
    }

    @ApiOperation(value = "分页查询所有关系")
    @GetMapping("/manage/kb/getRelations")
    public RespPageBean getRelations(@RequestParam(defaultValue = "1")Integer currentPage,
                                 @RequestParam(defaultValue = "8")Integer size,
                                 String name){
        return relationService.selectRelation(currentPage,size,name);
    }

}
