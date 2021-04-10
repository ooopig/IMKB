package com.ssdut.imkg.pojo.pub;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 用户登录实体类
 *
 * @author fanyuanxin
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "UserLogin对象",description = "")
public class UserLoginParam {
	@ApiModelProperty(value = "用户名",required = true)
	private String username;
	@ApiModelProperty(value = "密码",required = true)
	private String password;
	@ApiModelProperty(value = "验证码",required = true)
	private String code;
}