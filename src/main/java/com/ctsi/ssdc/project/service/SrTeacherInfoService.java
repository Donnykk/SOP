package com.ctsi.ssdc.project.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ctsi.ssdc.project.domain.SrTeacherInfo;

public interface SrTeacherInfoService extends IService<SrTeacherInfo> {

    public Integer getTeacherNum(String school);
}
