package com.ssdut.imkg.controller;


import com.ssdut.imkg.pojo.File;
import com.ssdut.imkg.pojo.pub.FileParam;
import com.ssdut.imkg.pojo.pub.NodeParam;
import com.ssdut.imkg.pojo.pub.RespBean;
import com.ssdut.imkg.pojo.pub.RespPageBean;
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
    public boolean uploadFile(@RequestBody File file){
        file.setCreateTime(TimeUtils.nowStr());
        file.setDownTimes(0);
        boolean save = fileService.save(file);
        if(save)
            return true;
        else
            return false;
    }

    @ApiOperation("获取节点的文件")
    @GetMapping("/file/get/{id}")
    public List<File> getNodeFile(@PathVariable("id") String id){
        List<File> files = fileService.getNodeFile(id);
        return files;
    }

    @ApiOperation(value = "管理员分页查询所有文件")
    @GetMapping("/manage/kb/getFiles")
    public RespPageBean getFiles(@RequestParam(defaultValue = "1")Integer currentPage,
                                 @RequestParam(defaultValue = "10")Integer size,
                                 FileParam file){
        return fileService.getFiles(currentPage,size,file);
    }
}
