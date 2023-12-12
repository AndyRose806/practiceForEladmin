package com.pubo.security.service;

import com.pubo.security.entity.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 系统用户 服务类
 * </p>
 *
 * @author pubo
 * @since 2023-12-10
 */
public interface ISysUserService extends IService<SysUser> {

    public SysUser checkByUsrNameAndPassword(String username, String password);

}
