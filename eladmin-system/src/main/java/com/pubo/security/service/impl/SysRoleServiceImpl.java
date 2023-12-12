package com.pubo.security.service.impl;

import com.pubo.security.entity.SysRole;
import com.pubo.security.entity.SysUser;
import com.pubo.security.entity.UsrRoleInfo;
import com.pubo.security.mapper.SysRoleMapper;
import com.pubo.security.service.ISysRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author pubo
 * @since 2023-12-10
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements ISysRoleService {

    @Autowired
    private SysRoleMapper mapper;
    @Override
    public UsrRoleInfo getAuthInfo(SysUser user) {
        return mapper.getAuthInfo(user);
    }
}
