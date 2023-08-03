package com.ctsi.ssdc.project.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ctsi.ssdc.model.AjaxResult;
import com.ctsi.ssdc.model.PageResult;
import com.ctsi.ssdc.project.domain.SrSchoolInfo;
import com.ctsi.ssdc.project.domain.SrStudentInfo;
import com.ctsi.ssdc.project.service.SrSchoolInfoService;
import com.ctsi.ssdc.security.SecurityHxUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import liquibase.pro.packaged.D;
import liquibase.pro.packaged.E;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;


import java.util.Arrays;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/srSchoolInfos")
public class SrSchoolInfoController {

    @Autowired
    private SrSchoolInfoService srSchoolInfoService;

    /**
     * 新增学校简介信息
     * @param srSchoolInfo
     * @return
     */
    @PostMapping()
    public AjaxResult save(@RequestBody SrSchoolInfo srSchoolInfo)
    {
        srSchoolInfo.setCreateBy(SecurityHxUtils.getCurrentUserName());
        srSchoolInfo.setCreateTime(new Date());

        SrSchoolInfo currentSchool = srSchoolInfoService.getOne(new LambdaQueryWrapper<SrSchoolInfo>().eq(SrSchoolInfo::getSchool,srSchoolInfo.getSchool()).last("limit 1"));
        if(null!=currentSchool)
        {
            return AjaxResult.error("当前校区已存在");
        }

         if(srSchoolInfoService.save(srSchoolInfo))
         {
             return AjaxResult.success("创建成功");
         }
         return AjaxResult.error("创建失败");
    }

    /**
     * 获取学校简介分页数据
     * @param srSchoolInfo
     * @param page
     * @return
     */
    @GetMapping()
    public AjaxResult page(SrSchoolInfo srSchoolInfo, Pageable page)
    {
        PageHelper.startPage(page.getPageNumber(),page.getPageSize());
//        List<SrSchoolInfo> list = srSchoolInfoService.list(new LambdaQueryWrapper<SrSchoolInfo>().eq(StringUtils.isNotEmpty(srSchoolInfo.getSchool()),SrSchoolInfo::getSchool,srSchoolInfo.getSchool()));
        QueryWrapper<SrSchoolInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.select(SrSchoolInfo.class,info->!info.getColumn().equals("school_desc")
                ).eq(StringUtils.isNotEmpty(srSchoolInfo.getSchool()),"school",srSchoolInfo.getSchool());
        List<SrSchoolInfo> list = srSchoolInfoService.list(queryWrapper);
        PageInfo<E> info = new PageInfo(list);
        long count = info.getTotal();
        return AjaxResult.success(new PageResult(list, count, count));
    }

    @GetMapping("{id}")
    public AjaxResult getDetail(@PathVariable Long id)
    {
        SrSchoolInfo srSchoolInfo = srSchoolInfoService.getById(id);
        return AjaxResult.success(srSchoolInfo);
    }

    /**
     * 修改学校信息
     * @param srSchoolInfo
     * @return
     */
    @PutMapping
    public AjaxResult edit(@RequestBody SrSchoolInfo srSchoolInfo)
    {
        srSchoolInfo.setUpdateBy(SecurityHxUtils.getCurrentUserName());
        srSchoolInfo.setUpdateTime(new Date());
        if(srSchoolInfoService.updateById(srSchoolInfo))
        {
            return AjaxResult.success("修改成功");
        }
        return AjaxResult.error("修改失败");
    }

    /**
     * 删除 ids
     * @param ids
     * @return
     */
    @DeleteMapping("{ids}")
    public AjaxResult del(@PathVariable Long[] ids)
    {
        List<Long> idList = Arrays.asList(ids);
        if(srSchoolInfoService.removeByIds(idList))
        {
            return AjaxResult.success("删除成功");
        }
        return AjaxResult.error("删除失败");
    }
}
