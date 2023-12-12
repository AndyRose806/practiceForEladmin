package com.pubo.security.mapper;

import com.pubo.security.entity.SysUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 系统用户 Mapper 接口
 * </p>
 *
 * @author pubo
 * @since 2023-12-10
 */

@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {

}
