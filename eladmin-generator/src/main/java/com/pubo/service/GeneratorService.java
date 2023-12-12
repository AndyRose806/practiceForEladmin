package com.pubo.service;

import com.pubo.domain.vo.TableInfo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pubo.domain.ColumnInfo;
import com.pubo.domain.GenConfig;
import com.pubo.utils.PageResult;
import org.springframework.stereotype.Service;

import java.util.List;

public interface GeneratorService extends IService<ColumnInfo> {

    /**
     * 查询数据库元数据
     *
     * @param name 表名
     * @param page 分页参数
     * @return /
     */
    PageResult<TableInfo> getTable(String name, Page<Object> page);

    /**
     * 得到数据表的元数据
     * @param name 表名
     * @return /
     */
    List<ColumnInfo> getColumns(String name);

    /**
     * 代码生成
     * @param genConfig 配置信息
     * @param columns 字段信息
     */
    void generator(GenConfig genConfig, List<ColumnInfo> columns);

}
