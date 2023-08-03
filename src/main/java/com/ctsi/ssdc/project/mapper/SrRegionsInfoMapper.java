package com.ctsi.ssdc.project.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ctsi.ssdc.project.domain.SrRegionsInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SrRegionsInfoMapper extends BaseMapper<SrRegionsInfo> {

    public List<SrRegionsInfo> getPage(SrRegionsInfo srRegionsInfo);

}
