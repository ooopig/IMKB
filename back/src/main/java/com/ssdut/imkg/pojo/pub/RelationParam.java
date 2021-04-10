package com.ssdut.imkg.pojo.pub;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * @Author Fan Yuanxin
 * @Date 2021/4/9 8:57
 * @Version 1.0
 */
@Data
@Setter
@Getter
@ToString
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "关系交互对象",description = "")
public class RelationParam {
    @ApiModelProperty(value = "关系主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "关系名字")
    private String name;

    @ApiModelProperty(value = "头节点id")
    private Integer sourceId;

    @ApiModelProperty(value = "尾节点id")
    private Integer targetId;

    @ApiModelProperty(value = "头节点名字")
    private String sourceName;

    @ApiModelProperty(value = "尾节点名字")
    private String targetName;

    @ApiModelProperty(value = "权重")
    private Integer weight;

    @ApiModelProperty(value = "关系属性")
    private String properties;

    @ApiModelProperty(value = "创建人id")
    private Integer createUserId;

    @ApiModelProperty(value = "创建人名字")
    private String createUserName;

    @ApiModelProperty(value = "创建时间")
    private String createTime;

    @ApiModelProperty(value = "修改人id")
    private Integer modifyUserId;

    @ApiModelProperty(value = "修改人名字")
    private String modifyUserName;

    @ApiModelProperty(value = "修改时间")
    private String modifyTime;
}
