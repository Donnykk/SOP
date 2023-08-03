package com.ctsi.ssdc.project.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ctsi.ssdc.project.domain.ClassReport;
import com.ctsi.ssdc.project.domain.SrClassInfo;
import com.ctsi.ssdc.project.domain.SrStudentInfo;
import com.ctsi.ssdc.project.mapper.SrClassInfoMapper;
import com.ctsi.ssdc.project.service.SrClassInfoService;
import com.ctsi.ssdc.project.service.SrStudentInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SrClassInfoServiceImpl extends ServiceImpl<SrClassInfoMapper, SrClassInfo> implements SrClassInfoService {

    @Autowired
    private SrClassInfoMapper srClassInfoMapper;
    @Autowired
    private SrStudentInfoService srStudentInfoService;

    /**
     * 根据学校筛选班级信息
     * @param school
     * @return
     */
    @Override
    public List<SrClassInfo> getClassBySchool(String school) {
        return srClassInfoMapper.getSrClassBySchool(school);
    }

    /**
     * 根据年级筛选班级信息
     * @param gradeId
     * @return
     */
    @Override
    public List<SrClassInfo> getClassByGrade(Long gradeId) {
        return srClassInfoMapper.getSrClassByGradeId(gradeId);
    }

    /**
     * 获取班级分页数据
     * @param srClassInfo
     * @return
     */
    @Override
    public List<SrClassInfo> getPage(SrClassInfo srClassInfo) {
        return srClassInfoMapper.getPage(srClassInfo);
    }

    /**
     * 查询当前班级是否存在
     * @param school
     * @param grade
     * @param className
     * @return
     */
    @Override
    public SrClassInfo getExistClassInfo(String school, String grade, String className) {
        return srClassInfoMapper.getExistClassInfo(school,grade,className);
    }

    @Override
    public Integer getClassNum(String school) {
        return srClassInfoMapper.getClassNUm(school);
    }

    @Override
    public List<ClassReport> getClassReport(Long gradeId) {
        //获取班级列表
        List<SrClassInfo> classInfos = srClassInfoMapper.selectList(new LambdaQueryWrapper<SrClassInfo>().eq(SrClassInfo::getGradeId,gradeId));
        List<ClassReport> classReports = new ArrayList<>();

        for(SrClassInfo srClassInfo:classInfos)
        {
            int count = srStudentInfoService.count(new LambdaQueryWrapper<SrStudentInfo>().eq(SrStudentInfo::getClassId,
                    srClassInfo.getId()));
            ClassReport classReport =new ClassReport();
            classReport.setClassName(srClassInfo.getClassName());
            classReport.setClassNum(count);
            classReports.add(classReport);
        }

        return classReports;
    }
}
