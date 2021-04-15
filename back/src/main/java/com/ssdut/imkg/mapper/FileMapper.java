package com.ssdut.imkg.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ssdut.imkg.pojo.File;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ssdut.imkg.pojo.pub.FileParam;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author fanyuanxin
 * @since 2021-04-14
 */
public interface FileMapper extends BaseMapper<File> {

    IPage<FileParam> getFiles(Page<FileParam> page, FileParam file);
}
