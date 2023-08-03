package com.ctsi.ssdc.project.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ctsi.ssdc.model.AjaxResult;
import com.ctsi.ssdc.model.PageResult;
import com.ctsi.ssdc.project.domain.SrNewsInfo;
import com.ctsi.ssdc.project.domain.SrTeacherInfo;
import com.ctsi.ssdc.project.service.SrNewsInfoService;
import com.ctsi.ssdc.security.SecurityHxUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import liquibase.pro.packaged.A;
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
@RequestMapping("/api/srNewsInfos")
public class SrNewsInfoController {

    @Autowired
    private SrNewsInfoService srNewsInfoService;

    /**
     * 新增
     * @param srNewsInfo
     * @return
     */
    @PostMapping()
    public AjaxResult save(@RequestBody SrNewsInfo srNewsInfo)
    {
        srNewsInfo.setCreateBy(SecurityHxUtils.getCurrentUserName());
        srNewsInfo.setCreateTime(new Date());
        if(srNewsInfoService.save(srNewsInfo))
        {
            return AjaxResult.success("新增成功");
        }
        return AjaxResult.error("新增失败");
    }


    /**
     * 分页数据
     * @param srNewsInfo
     * @param page
     * @return
     */
    @GetMapping()
    public AjaxResult getPage(SrNewsInfo srNewsInfo, Pageable page)
    {
        PageHelper.startPage(page.getPageNumber(),page.getPageSize());
        QueryWrapper<SrNewsInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.select(SrNewsInfo.class, info->!info.getColumn().equals("news_content")
                ).like(StringUtils.isNotEmpty(srNewsInfo.getSchool()),"school",srNewsInfo.getSchool());
        List<SrNewsInfo> list = srNewsInfoService.list(queryWrapper);

        PageInfo<E> info = new PageInfo(list);
        long count = info.getTotal();
        return AjaxResult.success(new PageResult(list, count, count));
    }

    /**
     * 查询详情
     * @param id
     * @return
     */
    @GetMapping("{id}")
    public AjaxResult getDetail(@PathVariable Long id)
    {
        SrNewsInfo srNewsInfo = srNewsInfoService.getById(id);
        return AjaxResult.success(srNewsInfo);
    }

    /**
     * 修改数据
     * @param srNewsInfo
     * @return
     */
    @PutMapping
    public AjaxResult edit(@RequestBody SrNewsInfo srNewsInfo)
    {
        srNewsInfo.setUpdateBy(SecurityHxUtils.getCurrentUserName());
        srNewsInfo.setUpdateTime(new Date());

        if(srNewsInfoService.updateById(srNewsInfo))
        {
            return AjaxResult.success("修改成功");
        }
        return AjaxResult.error("修改失败");
    }

    /**
     *
     * @param ids
     * @return
     */
    @DeleteMapping("/{ids}")
     public  AjaxResult del(@PathVariable Long[] ids)
    {
        List<Long> idList = Arrays.asList(ids);
        if(srNewsInfoService.removeByIds(idList))
        {
            return AjaxResult.success("删除成功");
        }
        return AjaxResult.success("删除失败");
    }

}
