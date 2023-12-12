package com.pubo.security.service;

import com.pubo.security.entity.SysRole;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pubo.security.entity.SysUser;
import com.pubo.security.entity.UsrRoleInfo;
import com.pubo.security.mapper.SysRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 * 角色表 服务类
 * </p>
 *
 * @author pubo
 * @since 2023-12-10
 */
public interface ISysRoleService extends IService<SysRole> {

     UsrRoleInfo getAuthInfo(SysUser user);
}
