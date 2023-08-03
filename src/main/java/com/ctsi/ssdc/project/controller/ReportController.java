package com.ctsi.ssdc.project.controller;


import com.ctsi.ssdc.common.domain.Constants;
import com.ctsi.ssdc.model.AjaxResult;
import com.ctsi.ssdc.project.domain.ClassReport;
import com.ctsi.ssdc.project.domain.GradeReport;
import com.ctsi.ssdc.project.mapper.SrStudentInfoMapper;
import com.ctsi.ssdc.project.service.*;
import com.gitee.sop.servercommon.annotation.Open;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/report")
@Api(tags = "教师信息接口")
public class ReportController {
    @Autowired
    private SrClassInfoService classInfoService;
    @Autowired
    private SrTeacherInfoService srTeacherInfoService;
    @Autowired
    private SrStudentInfoService srStudentInfoService;
    @Autowired
    private SrGradeInfoService srGradeInfoService;


    /**
     * 获取师资力量
     * @param school
     * @return
     */
    @ApiOperation(value = "获取师资力）", notes = "学校老师信息", position = -100/* position默认0，值越小越靠前 */)
    @Open(value="srDigitaltWin.getTsNumber",aliasName = "获取师资力量",remark="学校老师信息")
    @RequestMapping("/getTsNumber")
    public AjaxResult getTsNumber(String school)
    {
        if(StringUtils.isEmpty(school))
        {
            school = Constants.defaultSchool;
        }

        //获取班级数
        Integer classNum = classInfoService.getClassNum(school);

        //获取教职工总人数
        Integer teacherNum = srTeacherInfoService.getTeacherNum(school);

        //获取学生总人数
        Integer studentNum = srStudentInfoService.getStudentNum(school);

        Map<String,Integer> map = new HashMap<>();
        map.put("classNum",classNum);
        map.put("teacherNum",teacherNum);
        map.put("studentNum",studentNum);
        return AjaxResult.success(map);
    }


    /**
     * 学校划分
     * @param school
     * @return
     */
    @Open(value = "srDigitaltWin.gradeData",permission = true)
    @GetMapping("/gradeData")
    public AjaxResult gradeData(String school)
    {
        if(StringUtils.isEmpty(school))
        {
            school = Constants.defaultSchool;
        }

        List<GradeReport> list = srGradeInfoService.getGradeReport(school);
        return AjaxResult.success(list);

    }

    /**
     * 获取班级信息
     * @param gradeId
     * @return
     */
    @GetMapping("/classData")
    public AjaxResult classData(Long  gradeId)
    {
        List<ClassReport> classReports = classInfoService.getClassReport(gradeId);
        return AjaxResult.success(classReports);
    }
}
