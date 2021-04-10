package com.ssdut.imkg.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author fanyuanxin
 * @since 2021-04-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="File对象", description="")
public class File implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "文件主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "文件名字")
    private String name;

    @ApiModelProperty(value = "文件类型")
    private String type;

    @ApiModelProperty(value = "文件地址")
    private String url;

    @ApiModelProperty(value = "节点id")
    @TableField(value = "node_id")
    private Integer nodeId;

    @ApiModelProperty(value = "创建人")
    @TableField(value = "create_user")
    private Integer createUser;

    @ApiModelProperty(value = "创建时间")
    @TableField(value = "create_time")
    private String createTime;


    @ApiModelProperty(value = "下载次数")
    @TableField(value = "down_times")
    private Integer downTimes;


}
