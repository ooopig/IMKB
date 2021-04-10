package com.ssdut.imkg.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ssdut.imkg.pojo.File;
import com.ssdut.imkg.mapper.FileMapper;
import com.ssdut.imkg.service.FileService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author fanyuanxin
 * @since 2021-04-07
 */
@Service
public class FileServiceImpl extends ServiceImpl<FileMapper, File> implements FileService {
    @Autowired
    private FileMapper fileMapper;

    /**
     * 获得一个节点绑定的所有文件链接
     * @param id
     * @return
     */
    @Override
    public List<File> getNodeFile(String id) {
        return fileMapper.selectList(new QueryWrapper<File>().eq("node_id",id));
    }

    @Override
    public Boolean deleteNodeFile(String id) {
        List<File> files = getNodeFile(id);
        if(files != null){
            for (File file:files) {
                fileMapper.delete(new QueryWrapper<File>().eq("id",file.getId()));
            }
        }
        return true;
    }
}
