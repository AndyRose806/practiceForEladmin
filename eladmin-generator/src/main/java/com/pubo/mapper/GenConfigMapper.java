package com.pubo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pubo.domain.GenConfig;
import org.apache.ibatis.annotations.Param;

public interface GenConfigMapper extends BaseMapper<GenConfig> {
    GenConfig findByTableName(@Param("tableName") String tableName);
}
