package com.ctsi.ssdc.project.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import com.ctsi.ssdc.project.domain.SrHqfile;

import java.util.List;

/**
 * 后勤管理文件信息(SrHqfile)表数据库访问层
 *
 * @author makejava
 * @since 2023-08-01 15:09:36
 */
@Mapper
public interface SrHqfileMapper extends BaseMapper<SrHqfile> {

    public List<SrHqfile> getSrHqfileList(SrHqfile srHqfile);

    public SrHqfile getSrHqfileById(Long id);

}