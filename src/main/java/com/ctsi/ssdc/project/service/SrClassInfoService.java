package com.ctsi.ssdc.project.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ctsi.ssdc.project.domain.ClassReport;
import com.ctsi.ssdc.project.domain.SrClassInfo;
import liquibase.pro.packaged.S;

import java.util.List;


public interface SrClassInfoService extends IService<SrClassInfo> {

    /**
     * 根据学校名称获取班级信息
     * @param school
     * @return
     */
    public List<SrClassInfo> getClassBySchool(String school);

    public List<SrClassInfo> getClassByGrade(Long gradeId);

    public List<SrClassInfo> getPage(SrClassInfo srClassInfo);

    /**
     * 查询当前班级是否存在
     * @param school
     * @param grade
     * @param className
     * @return
     */
    public SrClassInfo getExistClassInfo(String school,String grade,String className);

    /**
     * 获取学校班级数量
     * @param school
     * @return
     */
    public Integer getClassNum(String school);

    /**
     * 获取班级信息
     * @param gradeId
     * @return
     */
    public List<ClassReport> getClassReport(Long gradeId);


}
