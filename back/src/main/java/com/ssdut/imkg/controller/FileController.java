package com.ssdut.imkg.controller;


import com.ssdut.imkg.pojo.File;
import com.ssdut.imkg.pojo.pub.RespBean;
import com.ssdut.imkg.service.FileService;
import com.ssdut.imkg.utils.TimeUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author fanyuanxin
 * @since 2021-04-07
 */
@RestController
@Api(tags = "文件")
public class FileController {

    @Autowired
    private FileService fileService;

    @ApiOperation("上传文件")
    @PostMapping("/file/upload")
    public RespBean uploadFile(@RequestBody File file){
        file.setCreateTime(TimeUtils.nowStr());
        file.setDownTimes(0);
        boolean save = fileService.save(file);
        if(save)
            return RespBean.success("上传成功");
        else
            return RespBean.error("上传失败，请重试");
    }

    @ApiOperation("获取节点的文件")
    @PostMapping("/file/get/{id}")
    public List<File> getNodeFile(@PathVariable("id") String id){
        List<File> files = fileService.getNodeFile(id);
        return files;
    }
}
