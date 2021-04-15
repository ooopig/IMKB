package com.ssdut.imkg.service;

import com.ssdut.imkg.pojo.Node;
import com.ssdut.imkg.pojo.Relation;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ssdut.imkg.pojo.pub.RespPageBean;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author fanyuanxin
 * @since 2021-04-06
 */
public interface RelationService extends IService<Relation> {

    /**
     * 添加关系
     * @param relation
     * @return
     */
    boolean addRelation(Relation relation);

    /**
     * 获得一个节点的所有子关系
     * @param nodeId
     * @return
     */
    List<Relation> getChildrenRelation(Integer nodeId);

    boolean removeByIdAndName(Integer id, String name);

    List<Relation> getAllRelations();

    Relation getRelationById(Integer id);

    RespPageBean selectRelation(Integer currentPage, Integer size, String name);
}
