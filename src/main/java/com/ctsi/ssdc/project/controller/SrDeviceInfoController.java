package com.ctsi.ssdc.project.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ctsi.ssdc.model.AjaxResult;
import com.ctsi.ssdc.model.PageResult;
import com.ctsi.ssdc.poi.excel.util.ExcelUtil;
import com.ctsi.ssdc.project.domain.SrDeviceInfo;
import com.ctsi.ssdc.project.service.SrDeviceInfoService;
import com.ctsi.ssdc.security.SecurityHxUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import liquibase.pro.packaged.E;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/srDeviceInfos")
public class SrDeviceInfoController {

    @Autowired
    private SrDeviceInfoService srDeviceInfoService;

    /**
     * 保存
     * @param srDeviceInfo
     * @return
     */
    @PostMapping
    public AjaxResult save(@RequestBody SrDeviceInfo srDeviceInfo)
    {
        srDeviceInfo.setCreateBy(SecurityHxUtils.getCurrentUserName());
        srDeviceInfo.setCreateTime(new Date());

        SrDeviceInfo current = srDeviceInfoService.getOne(new LambdaQueryWrapper<SrDeviceInfo>().eq(SrDeviceInfo::getDeviceCode,srDeviceInfo.getDeviceCode()));
        if(null != current)
        {
            return AjaxResult.error("当前设备已存在");
        }
        if(srDeviceInfoService.save(srDeviceInfo))
        {
            return AjaxResult.success("新增成功");
        }
        return AjaxResult.error("新增失败");
    }


    /**
     * 修改
     * @param srDeviceInfo
     * @return
     */
    @PutMapping
    public AjaxResult edit(@RequestBody SrDeviceInfo srDeviceInfo)
    {
         srDeviceInfo.setUpdateBy(SecurityHxUtils.getCurrentUserName());
         srDeviceInfo.setUpdateTime(new Date());

         if(srDeviceInfoService.updateById(srDeviceInfo))
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

        if(srDeviceInfoService.removeByIds(idList))
        {
            return AjaxResult.success("删除成功");
        }
        return AjaxResult.error("删除失败");
    }

    /**
     * 获取分页
     * @param srDeviceInfo
     * @param page
     * @return
     */
    @GetMapping
    public AjaxResult getDevicePage( SrDeviceInfo srDeviceInfo, Pageable page)
    {
        PageHelper.startPage(page.getPageNumber(),page.getPageSize());
        List<SrDeviceInfo> list = srDeviceInfoService.getDevicePage(srDeviceInfo);
        PageInfo<E> info = new PageInfo(list);
        long count = info.getTotal();

        return AjaxResult.success(  new PageResult(list, count, count));

    }

    /**
     * 根据 id
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public AjaxResult getDetail(@PathVariable Long id)
    {
        SrDeviceInfo srDeviceInfo = srDeviceInfoService.getById(id);
        return AjaxResult.success(srDeviceInfo);
    }

    @GetMapping("/export")
    public ResponseEntity<byte[]> export(SrDeviceInfo srDeviceInfo)
    {
        List<SrDeviceInfo> list = srDeviceInfoService.getDevicePage(srDeviceInfo);
        ExcelUtil<SrDeviceInfo> util = new ExcelUtil<SrDeviceInfo>(SrDeviceInfo.class);
        return util.exportExcel(list, "设备信息");
    }

    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file) throws Exception {
        ExcelUtil<SrDeviceInfo> util = new ExcelUtil<SrDeviceInfo>(SrDeviceInfo.class);
        List<SrDeviceInfo> list = util.importExcel(file.getInputStream());

        if(srDeviceInfoService.saveBatch(list)) {
            return AjaxResult.success("导入成功");
        }
        return AjaxResult.error("导入失败");

    }

}
