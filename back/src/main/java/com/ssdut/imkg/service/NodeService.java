package com.ssdut.imkg.service;

import com.ssdut.imkg.pojo.Node;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ssdut.imkg.pojo.pub.NodeParam;
import com.ssdut.imkg.pojo.pub.RespPageBean;
import io.swagger.models.auth.In;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author fanyuanxin
 * @since 2021-04-06
 */
public interface NodeService extends IService<Node> {
    /**
     * 根据节点名字获得节点
     * @param name
     * @return
     */
    Node getNodeByNodeName(String name);

    /**
     * 按条件查询节点
     * @param currentPage
     * @param size
     * @param node
     * @return
     */
    RespPageBean selectNodes(Integer currentPage, Integer size, NodeParam node);


    /**
     * 获得节点修改人的姓名
     * @param nodeId
     * @return
     */
    String getNodeModifyUser(Integer nodeId);

    /**
     * 添加节点
     * @param node
     * @return
     */
    Boolean addNode(Node node);

    /**
     * 获得节点的所有子节点
     * @param id
     * @return
     */
    List<Node> getChildrenNode(Integer id);

    /**
     * 获得所有节点
     * @return
     */
    List<Node> getAllNodes();

    /**
     * 根据id获得节点信息
     * @param id
     * @return
     */
    Node getNodeById(Integer id);

    List<Node> getInitNodes();

    boolean existNodeByName(String name);

    List<Node> getParents(Integer id);

    List<Node> searchNodeByName(String name,Integer level);

    List<String> getAllNodeNames();


    List<NodeParam> searchNodeByName2(String name, Integer level);

    List<Node> searchNodeByCreateUserId(Integer id);

    RespPageBean getExpertNodes(Integer currentPage, Integer size, NodeParam node);

    Integer adoptNode(Integer id,Integer level);

    Integer notAdoptNode(Integer id);

    Node searchNodeByName3(String name);
}
