package com.pubo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.pubo.entity.SysDict;
import com.pubo.entity.SysDictDetail;
import com.pubo.mapper.SysDictDetailMapper;
import com.pubo.mapper.SysDictMapper;
import com.pubo.service.ISysDictService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 数据字典 服务实现类
 * </p>
 *
 * @author pubo
 * @since 2023-12-13
 */
@Service
public class SysDictServiceImpl extends ServiceImpl<SysDictMapper, SysDict> implements ISysDictService {
    @Autowired
    private SysDictMapper dictMapper;
    @Autowired
    private SysDictDetailMapper dictDetailMapper;

    @Override
    public String getLabelByDictName(String dictName,String fieldValue) {
        QueryWrapper<SysDict> sysDictQueryWrapper = new QueryWrapper<>();
        sysDictQueryWrapper.eq("name",dictName);
        SysDict sysDict = dictMapper.selectOne(sysDictQueryWrapper);
        if (sysDict != null){
            QueryWrapper<SysDictDetail> sysDictDetailQueryWrapper = new QueryWrapper<>();
            sysDictDetailQueryWrapper.eq("dict_id",sysDict.getDictId()).eq("value",fieldValue);
            String label = dictDetailMapper.selectOne(sysDictDetailQueryWrapper).getLabel();
            return label;
        }
        return null;
    }
}
