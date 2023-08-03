package com.ctsi.ssdc.project.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ctsi.ssdc.project.domain.SrStudentInfo;

import java.util.List;

public interface SrStudentInfoService extends IService<SrStudentInfo> {

    /**
     *  获取分页数据
     * @param srStudentInfo
     * @return
     */
    public List<SrStudentInfo> getStuPage(SrStudentInfo srStudentInfo);

    /**
     * 获取当前学生
     * @param cardNo
     * @param stuId
     * @return
     */
    public SrStudentInfo getExistStudent(String cardNo,String stuId);

    /**
     * 获取学生数量
     * @param school
     * @return
     */
    public Integer getStudentNum(String school);

}
