package com.ctsi.ssdc.project.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ctsi.ssdc.project.domain.SrDeviceInfo;
import com.ctsi.ssdc.project.mapper.SrDeviceInfoMapper;
import com.ctsi.ssdc.project.service.SrDeviceInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SrDeviceInfoServiceImpl extends ServiceImpl<SrDeviceInfoMapper, SrDeviceInfo> implements SrDeviceInfoService {

    @Autowired
    SrDeviceInfoMapper srDeviceInfoMapper;

    @Override
    public List<SrDeviceInfo> getDevicePage(SrDeviceInfo srDeviceInfo) {
        return srDeviceInfoMapper.getDevicePage(srDeviceInfo);
    }
}
