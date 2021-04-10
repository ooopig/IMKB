package com.ssdut.imkg.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ssdut.imkg.pojo.Node;
import com.ssdut.imkg.pojo.User;
import com.ssdut.imkg.pojo.UserRole;
import com.ssdut.imkg.pojo.pub.NodeParam;
import com.ssdut.imkg.pojo.pub.RespBean;
import com.ssdut.imkg.pojo.pub.RespPageBean;
import com.ssdut.imkg.pojo.pub.UserRequestParam;
import com.ssdut.imkg.service.NodeService;
import com.ssdut.imkg.utils.TimeUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author fanyuanxin
 * @since 2021-04-06
 */
@RestController
@Api(tags = "节点")
public class NodeController {
    @Autowired
    private NodeService nodeService;

    @ApiOperation(value = "查询所有节点")
    @GetMapping("/kg/graph/allNode")
    public List<Node> getAllNodes(){
        return nodeService.getAllNodes();
    }

    @ApiOperation(value = "知识图谱初始化节点")
    @GetMapping("/kg/graph/initNode")
    public List<Node> getInitNodes(){
        return nodeService.getInitNodes();
    }

    @ApiOperation(value = "分页查询所有节点")
    @GetMapping("/manage/kb/getNodes")
    public RespPageBean getNodes(@RequestParam(defaultValue = "1")Integer currentPage,
                                @RequestParam(defaultValue = "8")Integer size,
                                NodeParam node){
        return nodeService.selectNodes(currentPage,size,node);
    }

    @ApiOperation(value = "添加节点")
    @PostMapping({"/manage/kb/addNode","/kg/graph/addNode"})
    public RespBean addNode(@RequestBody Node node){
        String name = node.getName();
        if(nodeService.existNodeByName(name)){
            return RespBean.error("添加失败，节点已经存在");
        }
        node.setCreateTime(TimeUtils.nowStr());
        if(nodeService.addNode(node)){
            return RespBean.success("添加节点成功!");
        }
        return RespBean.error("添加节点失败，请重试");
    }

    @ApiOperation(value = "根据id查询节点信息")
    @GetMapping("/kg/graph/node/{id}")
    public Node getNodeById(@PathVariable("id")Integer id){
        return nodeService.getNodeById(id);
    }

    @ApiOperation(value = "删除节点")
    @DeleteMapping("/manage/kb/delNode/{id}")
    public RespBean deleteUser(@PathVariable Integer id){
        if(nodeService.removeById(id)){
            return RespBean.success("删除成功");
        }
        return RespBean.error("删除失败");
    }


    @ApiOperation(value = "更新节点信息")
    @PutMapping("/manage/kb/updateNode/")
    public RespBean updateUser(@RequestBody Node node){
        node.setModifyTime(TimeUtils.nowStr());
        if(nodeService.updateById(node)){
            return RespBean.success("更新成功");
        }
        return RespBean.error("更新失败");
    }


    @ApiOperation(value = "查找一个节点的所有孩子")
    @GetMapping("/kg/graph/getChildren/{id}")
    public List<Node> getChildrenNode(@PathVariable Integer id){
        List<Node> nodes = nodeService.getChildrenNode(id);
        return nodes;
    }
}
