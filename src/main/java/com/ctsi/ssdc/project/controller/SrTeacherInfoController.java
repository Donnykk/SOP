package com.ctsi.ssdc.project.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ctsi.ssdc.common.oos.OosConfig;
import com.ctsi.ssdc.common.oos.OosUtils;
import com.ctsi.ssdc.model.AjaxResult;
import com.ctsi.ssdc.model.PageResult;
import com.ctsi.ssdc.project.domain.SrSchoolInfo;
import com.ctsi.ssdc.project.domain.SrStudentInfo;
import com.ctsi.ssdc.project.domain.SrTeacherInfo;
import com.ctsi.ssdc.project.service.SrTeacherInfoService;
import com.ctsi.ssdc.security.SecurityHxUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import liquibase.pro.packaged.D;
import liquibase.pro.packaged.E;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;


import java.util.Arrays;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/srTeacherInfos")
public class SrTeacherInfoController {

    @Autowired
    private SrTeacherInfoService srTeacherInfoService;

    /**
     * 新增名师信息
     * @param srTeacherInfo
     * @return
     */
    @PostMapping
    public AjaxResult save(@RequestBody SrTeacherInfo srTeacherInfo)
    {
        srTeacherInfo.setCreateBy(SecurityHxUtils.getCurrentUserName());
        srTeacherInfo.setCreateTime(new Date());

        List<String> schoolList = srTeacherInfo.getSchoolList();
        if(null!=schoolList&& !CollectionUtils.isEmpty(schoolList))
        {
            srTeacherInfo.setSchool(StringUtils.join(schoolList,","));
        }

        if(srTeacherInfoService.save(srTeacherInfo))
        {
            return AjaxResult.success("新增成功");
        }
        return AjaxResult.error("新增失败");
    }

    /**
     * 获取分页
     * @param srTeacherInfo
     * @param page
     * @return
     */
    @GetMapping()
    public  AjaxResult getPage(SrTeacherInfo srTeacherInfo, Pageable page)
    {
        PageHelper.startPage(page.getPageNumber(),page.getPageSize());
        QueryWrapper<SrTeacherInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotEmpty(srTeacherInfo.getSchool()),"school",srTeacherInfo.getSchool())
                .like(StringUtils.isNotEmpty(srTeacherInfo.getTeacherName()),"teacher_name",srTeacherInfo.getTeacherName());
        List<SrTeacherInfo> list = srTeacherInfoService.list(queryWrapper);

        for(SrTeacherInfo teacherInfo:list)
        {
            teacherInfo.setTeacherImage(OosUtils.changeUrl(teacherInfo.getTeacherImage()));
        }
        PageInfo<E> info = new PageInfo(list);
        long count = info.getTotal();
        return AjaxResult.success(new PageResult(list, count, count));
    }

    /**
     * 查询详细信息
     * @param id
     * @return
     */
    @GetMapping("{id}")
    public AjaxResult getDetail(@PathVariable Long id)
    {
        SrTeacherInfo srTeacherInfo = srTeacherInfoService.getById(id);

        if(StringUtils.isNotEmpty(srTeacherInfo.getTeacherImage())) {
            String key = OosUtils.getKey(srTeacherInfo.getTeacherImage());
            srTeacherInfo.setTeacherImage(OosUtils.generatePresignedUrl(key, OosConfig.OOS_BUCKET_NAME, 30));
        }
        srTeacherInfo.setSchoolList(Arrays.asList(srTeacherInfo.getSchool().split(",")));
        return AjaxResult.success(srTeacherInfo);
    }

    /**
     * 修改
     * @param srTeacherInfo
     * @return
     */
    @PutMapping()
    public AjaxResult edit(@RequestBody SrTeacherInfo srTeacherInfo)
    {
        srTeacherInfo.setCreateBy(SecurityHxUtils.getCurrentUserName());
        srTeacherInfo.setCreateTime(new Date());

        List<String> schoolList = srTeacherInfo.getSchoolList();
        if(null!=schoolList&& !CollectionUtils.isEmpty(schoolList))
        {
            srTeacherInfo.setSchool(StringUtils.join(schoolList,","));
        }

        if(srTeacherInfoService.updateById(srTeacherInfo))
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
    @DeleteMapping("{ids}")
    public AjaxResult del(@PathVariable Long[] ids)
    {
        List<Long> idList = Arrays.asList(ids);
        if(srTeacherInfoService.removeByIds(idList))
        {
            return AjaxResult.success("删除成功");
        }
        return AjaxResult.error("删除失败");
    }

}
