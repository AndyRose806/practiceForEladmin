package com.pubo.security.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.pubo.security.entity.SysUser;
import com.pubo.security.mapper.SysUserMapper;
import com.pubo.security.service.ISysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 系统用户 服务实现类
 * </p>
 *
 * @author pubo
 * @since 2023-12-10
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {
    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public SysUser checkByUsrNameAndPassword(String username, String password) {
        QueryWrapper<SysUser> sysUserQuery = new QueryWrapper<>();
        QueryWrapper<SysUser> eq = sysUserQuery.eq("username", username).eq("password", password);
        SysUser sysUser = sysUserMapper.selectOne(eq);
        return sysUser;
    }
}
