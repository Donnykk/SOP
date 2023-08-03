package com.ctsi.ssdc.project.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ctsi.ssdc.project.domain.SrDeviceInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SrDeviceInfoMapper extends BaseMapper<SrDeviceInfo> {

    public List<SrDeviceInfo> getDevicePage(SrDeviceInfo srDeviceInfo);
}
