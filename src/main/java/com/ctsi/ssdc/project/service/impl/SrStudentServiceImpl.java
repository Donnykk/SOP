package com.ctsi.ssdc.project.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ctsi.ssdc.project.domain.SrStudentInfo;
import com.ctsi.ssdc.project.mapper.SrStudentInfoMapper;
import com.ctsi.ssdc.project.service.SrStudentInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SrStudentServiceImpl extends ServiceImpl<SrStudentInfoMapper,SrStudentInfo> implements SrStudentInfoService {

    @Autowired
    private SrStudentInfoMapper srStudentInfoMapper;

    @Override
    public List<SrStudentInfo> getStuPage(SrStudentInfo srStudentInfo) {
        return srStudentInfoMapper.getStuPage(srStudentInfo);
    }

    @Override
    public SrStudentInfo getExistStudent(String cardNo, String stuId) {
        return srStudentInfoMapper.getExistStudent(cardNo,stuId);
    }

    @Override
    public Integer getStudentNum(String school) {
        return srStudentInfoMapper.getStudentNum(school);
    }
}
