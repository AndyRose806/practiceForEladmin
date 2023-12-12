package com.pubo.security.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UsrRoleInfo {
    @ApiModelProperty(value = "部门名称")
    private Long deptId;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "角色名称")
    private String name;

    @ApiModelProperty(value = "数据权限")
    private String dataScope;

    @ApiModelProperty(value = "对称加密后的Token")
    private String desEncryptToken;

}
