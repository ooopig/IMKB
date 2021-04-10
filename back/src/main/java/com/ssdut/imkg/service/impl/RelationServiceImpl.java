package com.ssdut.imkg.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ssdut.imkg.mapper.UserMapper;
import com.ssdut.imkg.pojo.Node;
import com.ssdut.imkg.pojo.Relation;
import com.ssdut.imkg.mapper.RelationMapper;
import com.ssdut.imkg.pojo.User;
import com.ssdut.imkg.pojo.pub.NodeParam;
import com.ssdut.imkg.pojo.pub.RelationParam;
import com.ssdut.imkg.pojo.pub.RespPageBean;
import com.ssdut.imkg.service.RelationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ssdut.imkg.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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
public class RelationServiceImpl extends ServiceImpl<RelationMapper, Relation> implements RelationService {

    @Autowired
    private RelationMapper relationMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public boolean addRelation(Relation relation) {
        if(save(relation))
            return true;
        return false;
    }

    /**
     * 获得一个节点的所有子关系
     * @param nodeId
     * @return
     */
    @Override
    public List<Relation> getChildrenRelation(Integer nodeId) {
        return relationMapper.getChildrenRelation(nodeId);
    }

    @Override
    public boolean removeByIdAndName(Integer id, String name) {
        if(relationMapper.removeByIdAndName(id,name)>0)
            return true;
        return false;
    }

    @Override
    public List<Relation> getAllRelations() {
        //return relationMapper.selectList(new QueryWrapper<Relation>().select("id,name,source_id,target_id,weight"));
        return relationMapper.getAllRelations();
    }

    @Override
    public Relation getRelationById(Integer id) {

        return relationMapper.selectOne(new QueryWrapper<Relation>().eq("id",id));
    }

    /**
     * 分页查询所有节点
     * @param currentPage
     * @param size
     * @param name
     * @return
     */
    @Override
    public RespPageBean selectRelation(Integer currentPage, Integer size, String name) {

        //开启分页
        Page<RelationParam> page = new Page<>(currentPage,size);
        IPage<RelationParam> relationIPage = relationMapper.selectRelations(page,name);
        List<RelationParam> relations = relationIPage.getRecords();
        for (RelationParam relationParam:relations) {
            if(relationParam.getModifyTime()!=null){
                String nn = userMapper.selectOne(new QueryWrapper<User>().eq("id",relationParam.getModifyUserId())).getName();
                relationParam.setModifyUserName(nn);
            }
        }
        RespPageBean respPageBean = new RespPageBean(relationIPage.getTotal(),relations);
        return respPageBean;
    }
}
