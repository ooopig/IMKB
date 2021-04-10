package com.ssdut.imkg.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
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
    private Integer sourceId;

    @ApiModelProperty(value = "尾节点")
    private Integer targetId;

    @ApiModelProperty(value = "权重")
    private Integer weight;

    @ApiModelProperty(value = "关系属性")
    private String properties;

    @ApiModelProperty(value = "创建人")
    private Integer createUser;

    @ApiModelProperty(value = "创建时间")
    private String createTime;

    @ApiModelProperty(value = "修改人")
    private Integer modifyUser;

    @ApiModelProperty(value = "修改时间")
    private String modifyTime;


}
