package com.ssdut.imkg.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ssdut.imkg.pojo.Relation;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ssdut.imkg.pojo.pub.RelationParam;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author fanyuanxin
 * @since 2021-04-06
 */
@Component
public interface RelationMapper extends BaseMapper<Relation> {

    /**
     * 获得一个节点的所有子关系
     * @param id
     * @return
     */
    List<Relation> getChildrenRelation(@Param("id")Integer id);

    int removeByIdAndName(@Param("id") Integer id, @Param("name")String name);

    IPage<RelationParam> selectRelations(Page<RelationParam> page, @Param("name")String name);

    List<Relation> getAllRelations();
}
