package com.ssdut.imkg.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ssdut.imkg.mapper.UserMapper;
import com.ssdut.imkg.pojo.Node;
import com.ssdut.imkg.pojo.User;
import com.ssdut.imkg.pojo.UserRole;
import com.ssdut.imkg.pojo.pub.NodeParam;
import com.ssdut.imkg.pojo.pub.RespBean;
import com.ssdut.imkg.pojo.pub.RespPageBean;
import com.ssdut.imkg.pojo.pub.UserRequestParam;
import com.ssdut.imkg.service.NodeService;
import com.ssdut.imkg.service.UserService;
import com.ssdut.imkg.utils.TimeUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

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

    @ApiOperation(value = "管理员分页查询所有节点")
    @GetMapping("/manage/kb/getNodes")
    public RespPageBean getNodes(@RequestParam(defaultValue = "1")Integer currentPage,
                                @RequestParam(defaultValue = "8")Integer size,
                                NodeParam node){
        return nodeService.selectNodes(currentPage,size,node);
    }

    @ApiOperation(value = "专家分页查询所有节点")
    @GetMapping("/expert/kb/getNodes")
    public RespPageBean getExpertNodes(@RequestParam(defaultValue = "1")Integer currentPage,
                                 @RequestParam(defaultValue = "8")Integer size,
                                 NodeParam node){
        return nodeService.getExpertNodes(currentPage,size,node);
    }

    @ApiOperation(value = "添加节点")
    @PostMapping({"/manage/kb/addNode","/kg/graph/addNode"})
    public RespBean addNode(@RequestBody Node node){
        String name = node.getName();
        if(nodeService.existNodeByName(name)){
            return RespBean.error("提交失败，节点已经存在");
        }
        node.setCreateTime(TimeUtils.nowStr());
        if(nodeService.addNode(node)){
            //创建者增加贡献度
            User user = userMapper.selectOne(
                    new QueryWrapper<User>()
                            .eq("id", node.getCreateUser())
            );
            Integer contribution = user.getContribution();
            user.setContribution(contribution+1);
            userService.updateById(user);
            return RespBean.success("提交成功!，将由知识专家进行审核",nodeService.getNodeByNodeName(name));
        }
        return RespBean.error("提交失败，请重试");
    }

    @ApiOperation(value = "根据id查询节点信息")
    @GetMapping("/kg/graph/node/{id}")
    public Node getNodeById(@PathVariable("id")Integer id){
        return nodeService.getNodeById(id);
    }

    @ApiOperation(value = "图谱按名字和节点等级搜索节点")
    @GetMapping("/kg/graph/search")
    public List<Node> searchNodeByName(String name,
                                       Integer level){
        if((name.equals("")||name==null) && level == null){
            return null;
        }
        System.out.println("name="+name);
        System.out.println("level="+level);
        return nodeService.searchNodeByName(name,level);
    }

    @ApiOperation(value = "搜索界面按名字和节点等级搜索节点")
    @GetMapping("/kg/graph/search2")
    public List<NodeParam> searchNodeByName2(String name,
                                       Integer level){
        if((name.equals("")||name==null) && level == null){
            return null;
        }
        return nodeService.searchNodeByName2(name,level);
    }

    @ApiOperation(value = "按名字精准搜索节点")
    @GetMapping("/kg/graph/search4")
    public Node searchNodeByName3(String name){
        if(name.equals("")||name==null){
            return null;
        }
        Node node = nodeService.searchNodeByName3(name);
        node.setProperties(null);
        return node;
    }

    @ApiOperation(value = "按创建者id搜索节点")
    @GetMapping("/kg/graph/search3")
    public List<Node> searchNodeByCreateUserId(Integer id){
        return nodeService.searchNodeByCreateUserId(id);
    }

    @ApiOperation(value = "查询所有节点名字")
    @GetMapping("/kg/graph/search/names")
    public List<String> getAllNodeNames(){
        return nodeService.getAllNodeNames();
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

    @ApiOperation(value = "节点审核通过")
    @GetMapping("/expert/kb/adoptNode/{id}/{level}")
    public Integer adoptNode(@PathVariable("id")Integer id,
                             @PathVariable("level")Integer level){
        return nodeService.adoptNode(id,level);
    }

    @ApiOperation(value = "节点审核不通过")
    @GetMapping("/expert/kb/notAdoptNode/{id}")
    public Integer notAdoptNode(@PathVariable("id")Integer id){
        return nodeService.notAdoptNode(id);
    }

    @ApiOperation(value = "查找一个节点的所有孩子")
    @GetMapping("/kg/graph/getChildren/{id}")
    public List<Node> getChildrenNode(@PathVariable Integer id){
        List<Node> nodes = nodeService.getChildrenNode(id);
        return nodes;
    }

    @ApiOperation(value = "查找一个节点的所有父亲")
    @GetMapping("/kg/graph/getParents/{id}")
    public List<Node> getParents(@PathVariable Integer id){
        List<Node> nodes = nodeService.getParents(id);
        return nodes;
    }
}
