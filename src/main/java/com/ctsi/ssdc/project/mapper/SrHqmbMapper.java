package com.ctsi.ssdc.project.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.ctsi.ssdc.project.domain.SrHqmb;

import java.util.List;

/**
 * 后勤管理-模版(SrHqmb)表数据库访问层
 *
 * @author makejava
 * @since 2023-08-01 11:37:04
 */
@Mapper
public interface SrHqmbMapper extends BaseMapper<SrHqmb> {

    public List<SrHqmb> getSrHqmbList(SrHqmb srHqmb);

}