package com.ssdut.imkg.service;

import com.ssdut.imkg.pojo.File;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ssdut.imkg.pojo.pub.FileParam;
import com.ssdut.imkg.pojo.pub.RespPageBean;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author fanyuanxin
 * @since 2021-04-07
 */
public interface FileService extends IService<File> {
    /**
     * 获取一个节点的所有文件
     * @param id
     * @return
     */
    List<File> getNodeFile(String id);

    /**
     * 删除一个节点的所有文件
     * @param id
     * @return
     */
    Boolean deleteNodeFile(String id);

    RespPageBean getFiles(Integer currentPage, Integer size, FileParam file);
}
