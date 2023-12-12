package com.pubo.security.mapper;

import com.pubo.security.entity.SysRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pubo.security.entity.SysUser;
import com.pubo.security.entity.UsrRoleInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 角色表 Mapper 接口
 * </p>
 *
 * @author pubo
 * @since 2023-12-10
 */
@Mapper
public interface SysRoleMapper extends BaseMapper<SysRole> {
    public UsrRoleInfo getAuthInfo(@Param("user") SysUser user);
}
