package com.ctsi.ssdc.project.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ctsi.ssdc.model.AjaxResult;
import com.ctsi.ssdc.model.PageResult;
import com.ctsi.ssdc.poi.excel.util.ExcelUtil;
import com.ctsi.ssdc.project.domain.SrDeviceInfo;
import com.ctsi.ssdc.project.domain.SrFaceInfo;
import com.ctsi.ssdc.project.domain.SrResponsibleInfo;
import com.ctsi.ssdc.project.service.SrResponsibleService;
import com.ctsi.ssdc.security.SecurityHxUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import liquibase.pro.packaged.E;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/srResponsibleInfos")
public class SrResponsibleController {
    @Autowired
    private SrResponsibleService srResponsibleService;

    /**
     * 新增责任人
     * @param srResponsibleInfo
     * @return
     */
    @PostMapping
    public AjaxResult save(@RequestBody SrResponsibleInfo srResponsibleInfo)
    {
        srResponsibleInfo.setCreateBy(SecurityHxUtils.getCurrentUserName());
        srResponsibleInfo.setCreateTime(new Date());

        SrResponsibleInfo current = srResponsibleService.getOne(new LambdaQueryWrapper<SrResponsibleInfo>()
                .eq(SrResponsibleInfo::getSchool,srResponsibleInfo.getSchool())
                .eq(SrResponsibleInfo::getWarningType,srResponsibleInfo.getWarningType()).last("limit 1"));

        if(null!=current)
        {
            return AjaxResult.error("当前告警已配置");
        }

        if(srResponsibleService.save(srResponsibleInfo))
        {
            return AjaxResult.success();
        }
        return AjaxResult.error("新增失败");
    }

    /**
     * 修改
     * @param srResponsibleInfo
     * @return
     */
    @PutMapping
    public AjaxResult edit(@RequestBody SrResponsibleInfo srResponsibleInfo)
    {
        srResponsibleInfo.setUpdateBy(SecurityHxUtils.getCurrentUserName());
        srResponsibleInfo.setUpdateTime(new Date());

        if(srResponsibleService.updateById(srResponsibleInfo))
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
         if(srResponsibleService.removeByIds(idList))
         {
             return AjaxResult.success("删除成功");
         }
         return AjaxResult.error("删除失败");
    }

    /**
     * 获取分页数据
     * @param srResponsibleInfo
     * @param page
     * @return
     */
    @GetMapping
    public AjaxResult getPage(SrResponsibleInfo srResponsibleInfo, Pageable page)
    {
        PageHelper.startPage(page.getPageNumber(),page.getPageSize());

        List<SrResponsibleInfo> list = srResponsibleService.list(new LambdaQueryWrapper<SrResponsibleInfo>()
                .eq(StringUtils.isNotEmpty(srResponsibleInfo.getSchool()),SrResponsibleInfo::getSchool,srResponsibleInfo.getSchool())
                .eq(StringUtils.isNotEmpty(srResponsibleInfo.getWarningType()),SrResponsibleInfo::getWarningType,srResponsibleInfo.getWarningType()));

        PageInfo<E> info = new PageInfo(list);
        long count = info.getTotal();
        return AjaxResult.success(new PageResult(list, count, count));
    }


    /**
     * 根据 id 获取详情
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public AjaxResult getDetail(@PathVariable Long id)
    {
         SrResponsibleInfo srResponsibleInfo = srResponsibleService.getById(id);
         return AjaxResult.success(srResponsibleInfo);
    }

    /**
     * 导出
     * @param srResponsibleInfo
     * @return
     */
    @GetMapping("/export")
    public ResponseEntity<byte[]> export(SrResponsibleInfo srResponsibleInfo)
    {
        List<SrResponsibleInfo> list = srResponsibleService.list(new LambdaQueryWrapper<SrResponsibleInfo>()
                .eq(StringUtils.isNotEmpty(srResponsibleInfo.getSchool()),SrResponsibleInfo::getSchool,srResponsibleInfo.getSchool())
                .eq(StringUtils.isNotEmpty(srResponsibleInfo.getWarningType()),SrResponsibleInfo::getWarningType,srResponsibleInfo.getWarningType()));
        ExcelUtil<SrResponsibleInfo> util = new ExcelUtil<SrResponsibleInfo>(SrResponsibleInfo.class);
        return util.exportExcel(list, "负责人");
    }


    /**
     * 导入
     * @param file
     * @return
     */
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file) throws Exception {
        ExcelUtil<SrResponsibleInfo> util = new ExcelUtil<SrResponsibleInfo>(SrResponsibleInfo.class);
        List<SrResponsibleInfo> list = util.importExcel(file.getInputStream());

        if(srResponsibleService.saveBatch(list)) {
            return AjaxResult.success("导入成功");
        }
        return AjaxResult.error("导入失败");
    }

}
