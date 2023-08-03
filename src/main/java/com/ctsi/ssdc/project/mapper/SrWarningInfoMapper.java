package com.ctsi.ssdc.project.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ctsi.ssdc.project.domain.SrWarningInfo;
import com.ctsi.ssdc.project.domain.req.SrWarningReq;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SrWarningInfoMapper extends BaseMapper<SrWarningInfo> {

    public List<SrWarningInfo> getPage(SrWarningReq req);

    public SrWarningInfo getWarningById(Long id);
}
