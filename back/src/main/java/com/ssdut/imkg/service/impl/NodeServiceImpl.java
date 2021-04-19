package com.ssdut.imkg.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ssdut.imkg.pojo.Node;
import com.ssdut.imkg.mapper.NodeMapper;
import com.ssdut.imkg.pojo.User;
import com.ssdut.imkg.pojo.pub.NodeParam;
import com.ssdut.imkg.pojo.pub.RespPageBean;
import com.ssdut.imkg.pojo.pub.UserRequestParam;
import com.ssdut.imkg.service.NodeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ssdut.imkg.utils.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author fanyuanxin
 * @since 2021-04-06
 */
@Service
public class NodeServiceImpl extends ServiceImpl<NodeMapper, Node> implements NodeService {

    @Autowired
    private NodeMapper nodeMapper;


    /**
     * 根据节点名字获得节点
     * @param name
     * @return
     */
    public Node getNodeByNodeName(String name) {
        Node node = null;
        node = nodeMapper.selectOne(new QueryWrapper<Node>().eq("name",name));

        return node;
    }


    /**
     * 按条件 分页查询节点
     * @param currentPage
     * @param size
     * @param node
     * @return
     */
    @Override
    public RespPageBean selectNodes(Integer currentPage, Integer size, NodeParam node) {
        //开启分页
        Page<NodeParam> page = new Page<>(currentPage,size);
        IPage<NodeParam> nodeIPage = nodeMapper.selectNodes(page,node);
        List<NodeParam> nodes = nodeIPage.getRecords();
        for (NodeParam node1:nodes) {
            node1.setModifyUser(nodeMapper.getNodeModifyUser(node1.getId()));
        }
        RespPageBean respPageBean = new RespPageBean(nodeIPage.getTotal(),nodes);
        return respPageBean;
    }

    /**
     * 获得节点修改人的姓名
     * @param nodeId
     * @return
     */
    @Override
    public String getNodeModifyUser(Integer nodeId) {

        return nodeMapper.getNodeModifyUser(nodeId);
    }

    @Override
    public Boolean addNode(Node node) {
        node.setCreateTime(TimeUtils.nowStr());
        if(save(node))
            return true;
        return false;
    }

    /**
     * 获得节点的所有子节点
     * @param id
     * @return
     */
    @Override
    public List<Node> getChildrenNode( Integer id) {
        return nodeMapper.getChildrenNode(id);
    }

    @Override
    public List<Node> getAllNodes() {

        return nodeMapper.selectList(new QueryWrapper<Node>()
                .eq("status",1)
                .select("id,name,level"));
    }

    /**
     * 根据id获得节点信息
     * @param id
     * @return
     */
    @Override
    public Node getNodeById(Integer id) {
        return nodeMapper.selectOne(new QueryWrapper<Node>().eq("id",id));
    }

    @Override
    public List<Node> getInitNodes() {
        List<Node> nodes = nodeMapper.selectList(new QueryWrapper<Node>()
                .eq("status", 1)
                .eq("level", 1)
                .select("id,name,level"));
        Node node = getNodeByNodeName("制造业");
        node.setProperties(null);
        nodes.add(node);
        return nodes;

    }

    @Override
    public boolean existNodeByName(String name) {
        Node node = getNodeByNodeName(name);
        if(node==null){
            return false;
        }
        else {
            return true;
        }
    }

    /**
     * 获得一个节点的所有父节点
     * @param id
     * @return
     */
    @Override
    public List<Node> getParents(Integer id) {
        return nodeMapper.getParents(id);
    }

    @Override
    public List<Node> searchNodeByName(String name , Integer level) {
        return nodeMapper.searchNodeByName(name,level);
    }

    @Override
    public List<String> getAllNodeNames() {
        return nodeMapper.getAllNodeNames();
    }

    @Override
    public List<NodeParam> searchNodeByName2(String name, Integer level) {
        return nodeMapper.searchNodeByName2(name,level);
    }

    @Override
    public List<Node> searchNodeByCreateUserId(Integer id) {
        return nodeMapper.selectList(new QueryWrapper<Node>()
        .eq("create_user",id));
    }

    /***
     * 知识专家获取未审核知识
     * @param currentPage
     * @param size
     * @param node
     * @return
     */
    @Override
    public RespPageBean getExpertNodes(Integer currentPage, Integer size, NodeParam node) {

        //开启分页
        Page<NodeParam> page = new Page<>(currentPage,size);
        IPage<NodeParam> nodeIPage = nodeMapper.getExpertNodes(page,node);
        List<NodeParam> nodes = nodeIPage.getRecords();
        for (NodeParam node1:nodes) {
            node1.setModifyUser(nodeMapper.getNodeModifyUser(node1.getId()));
        }
        RespPageBean respPageBean = new RespPageBean(nodeIPage.getTotal(),nodes);
        return respPageBean;
    }

    @Override
    public Integer adoptNode(Integer id,Integer level) {

        return nodeMapper.adoptNode(id,level);
    }

    @Override
    public Integer notAdoptNode(Integer id) {
        return nodeMapper.notAdoptNode(id);
    }

    @Override
    public Node searchNodeByName3(String name) {

        return nodeMapper.selectOne(new QueryWrapper<Node>()
        .eq("name",name));
    }


}
