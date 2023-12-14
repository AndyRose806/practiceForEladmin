package com.pubo.service;

import com.pubo.entity.SysDict;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 数据字典 服务类
 * </p>
 *
 * @author pubo
 * @since 2023-12-13
 */
public interface ISysDictService extends IService<SysDict> {
    public String getLabelByDictName(String dictName,String fieldName);
}
