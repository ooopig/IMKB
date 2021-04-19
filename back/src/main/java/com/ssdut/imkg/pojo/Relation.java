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
 * @since 2021-04-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Relation对象", description="")
public class Relation implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "关系主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "关系名字")
    private String name;

    @ApiModelProperty(value = "头节点")
    @TableField(value = "source_id")
    private Integer sourceId;

    @ApiModelProperty(value = "尾节点")
    @TableField(value = "target_id")
    private Integer targetId;

    @ApiModelProperty(value = "权重")
    private Integer weight;

    @ApiModelProperty(value = "关系属性")
    private String properties;

    @ApiModelProperty(value = "创建人")
    @TableField(value = "create_user")
    private Integer createUser;

    @ApiModelProperty(value = "创建时间")
    @TableField(value = "create_time")
    private String createTime;

    @ApiModelProperty(value = "修改人")
    @TableField(value = "modify_user")
    private Integer modifyUser;

    @ApiModelProperty(value = "修改时间")
    @TableField(value = "modify_time")
    private String modifyTime;


}
