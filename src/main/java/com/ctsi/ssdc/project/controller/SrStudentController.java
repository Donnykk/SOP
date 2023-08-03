package com.ctsi.ssdc.project.controller;

import com.ctsi.ssdc.model.AjaxResult;
import com.ctsi.ssdc.model.PageResult;
import com.ctsi.ssdc.project.domain.SrClassInfo;
import com.ctsi.ssdc.project.domain.SrGradeInfo;
import com.ctsi.ssdc.project.domain.SrStudentInfo;
import com.ctsi.ssdc.project.service.SrClassInfoService;
import com.ctsi.ssdc.project.service.SrGradeInfoService;
import com.ctsi.ssdc.project.service.SrStudentInfoService;
import com.ctsi.ssdc.security.SecurityHxUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import liquibase.pro.packaged.D;
import liquibase.pro.packaged.E;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/srStudentInfos")
public class SrStudentController {

    @Autowired
    private SrStudentInfoService srStudentInfoService;
    @Autowired
    private SrClassInfoService srClassInfoService;
    @Autowired
    private SrGradeInfoService srGradeInfoService;

    /**
     * 新增学生数据
     * @param srStudentInfo
     * @return
     */
    @PostMapping()
    public AjaxResult save(@RequestBody SrStudentInfo srStudentInfo)
    {
        srStudentInfo.setCreateBy(SecurityHxUtils.getCurrentUserName());
        srStudentInfo.setCreateTime(new Date());

        SrStudentInfo currentStudent = srStudentInfoService.getExistStudent(srStudentInfo.getCardNo(),srStudentInfo.getStuId());
        if(null!=currentStudent)
        {
            return AjaxResult.error("当前学生已存在");
        }

        if(srStudentInfoService.save(srStudentInfo))
        {
            return AjaxResult.success("新增成功");
        }
        return AjaxResult.error("新增失败");

    }

    /**
     * 获取分页数据
     * @param srStudentInfo
     * @param page
     * @return
     */
    @GetMapping()
    public AjaxResult page(SrStudentInfo srStudentInfo, Pageable page)
    {
        PageHelper.startPage(page.getPageNumber(),page.getPageSize());
        List<SrStudentInfo> list = srStudentInfoService.getStuPage(srStudentInfo);

        PageInfo<E> info = new PageInfo(list);
        long count = info.getTotal();
        return AjaxResult.success(new PageResult(list, count, count));
    }

    /**
     * 根据 id 查询详情
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public AjaxResult detail(@PathVariable("id") Long id)
    {
        SrStudentInfo srStudentInfo = srStudentInfoService.getById(id);

        if(null!=srStudentInfo.getClassId()) {
            //获取班级信息
            SrClassInfo srClassInfo = srClassInfoService.getById(srStudentInfo.getClassId());
            if(null!=srClassInfo)
            {
                srStudentInfo.setClassName(srClassInfo.getClassName());
                SrGradeInfo srGradeInfo = srGradeInfoService.findOne(srClassInfo.getGradeId());
                if(null!=srGradeInfo)
                {
                    srStudentInfo.setGradeName(srGradeInfo.getName());
                    srStudentInfo.setSchool(srGradeInfo.getSchool());
                    srStudentInfo.setGradeId(srGradeInfo.getId());
                }
            }
        }


        return AjaxResult.success(srStudentInfo);
    }


    /**
     * 修改
     * @param srStudentInfo
     * @return
     */
    @PutMapping()
    public AjaxResult edit(@RequestBody SrStudentInfo srStudentInfo)
    {
        srStudentInfo.setUpdateBy(SecurityHxUtils.getCurrentUserName());
        srStudentInfo.setUpdateTime(new Date());
        if(srStudentInfoService.updateById(srStudentInfo))
        {
            return AjaxResult.success("修改成功");
        }
        return AjaxResult.error("修改失败");
    }

    /**
     * 删除
     * @param ids
     * @return
     */
    @DeleteMapping("/{ids}")
    public AjaxResult del(@PathVariable Long[] ids)
    {
        List<Long> idList = Arrays.asList(ids);
        boolean res =srStudentInfoService.removeByIds(idList);
        if(res)
        {
            return AjaxResult.success("删除成功");
        }
        return AjaxResult.error("删除失败");
    }

}
