package com.ctsi.ssdc.project.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ctsi.ssdc.project.domain.SrCamerasInfo;
import com.ctsi.ssdc.project.domain.SrCamerasVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SrCamerasInfoMapper extends BaseMapper<SrCamerasInfo> {

    public List<SrCamerasInfo> getPage(SrCamerasInfo srCamerasInfo);

    public List<SrCamerasVo> getSrCamerasVo(String regionIndexCode);


}
