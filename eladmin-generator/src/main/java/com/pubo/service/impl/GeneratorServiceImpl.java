package com.pubo.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.pubo.exception.BadRequestException;
import com.pubo.utils.*;
import com.pubo.domain.vo.TableInfo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pubo.domain.ColumnInfo;
import com.pubo.domain.GenConfig;
import com.pubo.mapper.ColumnInfoMapper;
import com.pubo.service.GeneratorService;
import com.pubo.utils.PageResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Service
public class GeneratorServiceImpl extends ServiceImpl<ColumnInfoMapper, ColumnInfo> implements GeneratorService {
    private final ColumnInfoMapper columnInfoMapper;
    private final String CONFIG_MESSAGE = "请先配置生成器";

    @Override
    public PageResult<TableInfo> getTable(String name, Page<Object> page) {
        return PageUtils.toPage(columnInfoMapper.getTables(name, page));
    }

    @Override
    public List<ColumnInfo> getColumns(String tableName) {
        List<ColumnInfo> columnInfos = columnInfoMapper.findByTableNameOrderByIdAsc(tableName);
        if (CollectionUtil.isNotEmpty(columnInfos)) {
            return columnInfos;
        }else {
            return null;
        }

    }

    @Override
    public void generator(GenConfig genConfig, List<ColumnInfo> columns) {
        if (genConfig.getId() == null) {
            throw new BadRequestException(CONFIG_MESSAGE);
        }
        try{
            GenUtil.generatorCode(columns, genConfig);
        } catch (IOException e) {
            throw new BadRequestException("生成失败，请手动处理已生成的文件");
        }
    }
}
