package com.ctsi.ssdc.project.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ctsi.ssdc.project.domain.ClassReport;
import com.ctsi.ssdc.project.domain.SrClassInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SrClassInfoMapper extends BaseMapper<SrClassInfo> {

    /**
     * 根据学校获取班级信息
     * @param school
     * @return
     */
    public List<SrClassInfo> getSrClassBySchool(String school);

    /**
     * 根据年级 id 获取班级信息
     * @param gradeId
     * @return
     */
    public List<SrClassInfo> getSrClassByGradeId(Long gradeId);

    /**
     * 获取分页数据
     * @param srClassInfo
     * @return
     */
    public List<SrClassInfo> getPage(SrClassInfo srClassInfo);

    /**
     * 获取班级是否存在
     * @param school
     * @param grade
     * @param className
     * @return
     */
    SrClassInfo getExistClassInfo(@Param("school") String school,@Param("grade") String grade, @Param("className") String className);

    /**
     * 获取校区班级数量
     * @param school
     * @return
     */
    Integer getClassNUm(String school);

    /**
     * 获取班级报表
     * @param gradeId
     * @return
     */
    List<ClassReport> getClassReport(Long gradeId);
}
