package com.ctsi.ssdc.project.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ctsi.ssdc.common.domain.Constants;
import com.ctsi.ssdc.project.domain.SrTeacherInfo;
import com.ctsi.ssdc.project.mapper.SrTeacherInfoMapper;
import com.ctsi.ssdc.project.service.SrTeacherInfoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SrTeacherInfoServiceImpl   extends ServiceImpl<SrTeacherInfoMapper,SrTeacherInfo> implements SrTeacherInfoService  {
   @Autowired
    private SrTeacherInfoMapper srTeacherInfoMapper;

    @Override
    public Integer getTeacherNum(String school) {

        return srTeacherInfoMapper.getTeacherNum(school);
    }
}
