package com.ssdut.imkg.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.List;

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
 * @since 2021-03-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Menu对象", description="")
public class Menu implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    @ApiModelProperty(value = "请求url")
    private String url;

    @ApiModelProperty(value = "组件路径")
    private String path;

    @ApiModelProperty(value = "组件")
    private String component;

    @ApiModelProperty(value = "菜单名")
    private String name;

    @ApiModelProperty(value = "图标")
    @TableField("iconCls")
    private String iconcls;

    @ApiModelProperty(value = "是否保持激活")
    @TableField("keep_alive")
    private String keepAlive;

    @ApiModelProperty(value = "是否要求权限")
    @TableField("require_auth")
    private String requireAuth;

    @ApiModelProperty(value = "父id")
    @TableField("parent_id")
    private Integer parentId;

    @ApiModelProperty(value = "是否启用")
    private String enabled;

    @ApiModelProperty(value = "子菜单")
    @TableField(exist = false)
    private List<Menu> children;

    //根据菜单的url查询哪些角色拥有该权限
    @ApiModelProperty(value = "角色列表")
    @TableField(exist = false)
    private List<Role> roles;

}
