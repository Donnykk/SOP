package com.ctsi.ssdc.project.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ctsi.ssdc.project.domain.SrDeviceInfo;

import java.util.List;

public interface SrDeviceInfoService extends IService<SrDeviceInfo> {

    List<SrDeviceInfo> getDevicePage(SrDeviceInfo srDeviceInfo);
}
