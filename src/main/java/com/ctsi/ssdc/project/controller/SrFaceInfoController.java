package com.ctsi.ssdc.project.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ctsi.ssdc.common.oos.OosConfig;
import com.ctsi.ssdc.common.oos.OosUtils;
import com.ctsi.ssdc.model.AjaxResult;
import com.ctsi.ssdc.model.PageResult;
import com.ctsi.ssdc.poi.excel.util.ExcelUtil;
import com.ctsi.ssdc.project.domain.SrFaceInfo;
import com.ctsi.ssdc.project.domain.SrGradeInfo;
import com.ctsi.ssdc.project.service.SrFaceInfoService;
import com.ctsi.ssdc.security.SecurityHxUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import liquibase.pro.packaged.E;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/srFaceInfos")
public class SrFaceInfoController {

    @Autowired
    private SrFaceInfoService srFaceInfoService;

    /**
     * 新增
     *
     * @param srFaceInfo
     * @return
     */
    @PostMapping
    public AjaxResult save(@RequestBody SrFaceInfo srFaceInfo) {
        srFaceInfo.setCreateBy(SecurityHxUtils.getCurrentUserName());
        srFaceInfo.setCreateTime(new Date());

        SrFaceInfo current = srFaceInfoService.getOne(new LambdaQueryWrapper<SrFaceInfo>().eq(SrFaceInfo::getCardNo, srFaceInfo.getCardNo()));
        if (null != current) {
            return AjaxResult.error("当前人脸信息已存在");
        }

        if (srFaceInfoService.save(srFaceInfo)) {
            return AjaxResult.success("新增成功");
        }
        return AjaxResult.error("新增失败");
    }

    /**
     * 修改
     *
     * @param srFaceInfo
     * @return
     */
    @PutMapping
    public AjaxResult edit(@RequestBody SrFaceInfo srFaceInfo) {
        srFaceInfo.setCreateBy(SecurityHxUtils.getCurrentUserName());
        srFaceInfo.setCreateTime(new Date());

        if (srFaceInfoService.updateById(srFaceInfo)) {
            return AjaxResult.success("修改成功");
        }
        return AjaxResult.error("修改失败");
    }


    /**
     * 删除
     *
     * @param ids
     * @return
     */
    @DeleteMapping("/{ids}")
    public AjaxResult del(@PathVariable Long[] ids) {
        List<Long> idList = Arrays.asList(ids);
        if (srFaceInfoService.removeByIds(idList)) {
            return AjaxResult.success("删除成功");
        }
        return AjaxResult.error("删除失败");
    }

    /**
     * 分页
     *
     * @param srFaceInfo
     * @param page
     * @return
     */
    @GetMapping
    public AjaxResult getPage(SrFaceInfo srFaceInfo, Pageable page) {
        PageHelper.startPage(page.getPageNumber(), page.getPageSize());
        List<SrFaceInfo> list = srFaceInfoService.list(
                new LambdaQueryWrapper<SrFaceInfo>()
                        .eq(StringUtils.isNotEmpty(srFaceInfo.getSchool()), SrFaceInfo::getSchool, srFaceInfo.getSchool())
                        .eq(StringUtils.isNotEmpty(srFaceInfo.getName()), SrFaceInfo::getName, srFaceInfo.getName())
                        .eq(StringUtils.isNotEmpty(srFaceInfo.getCardNo()), SrFaceInfo::getCardNo, srFaceInfo.getCardNo()));

        for (SrFaceInfo faceInfo : list) {
            if(StringUtils.isNotEmpty(faceInfo.getImage())) {
                String key = OosUtils.getKey(faceInfo.getImage());
                faceInfo.setImage(OosUtils.generatePresignedUrl(key, OosConfig.OOS_BUCKET_NAME, 30));
            }
            }

        PageInfo<E> info = new PageInfo(list);
        long count = info.getTotal();

        return AjaxResult.success(new PageResult(list, count, count));
    }

    /**
     * 根据 id查询详情
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public AjaxResult getDetail(@PathVariable Long id) {
        SrFaceInfo srFaceInfo = srFaceInfoService.getById(id);

        if(StringUtils.isNotEmpty(srFaceInfo.getImage())) {
            String key = OosUtils.getKey(srFaceInfo.getImage());
            srFaceInfo.setImage(OosUtils.generatePresignedUrl(key, OosConfig.OOS_BUCKET_NAME, 30));
        }
        return AjaxResult.success(srFaceInfo);
    }

    /**
     * 导出文件
     *
     * @param srFaceInfo
     * @return
     */
    @GetMapping("/export")
    public ResponseEntity<byte[]> export(SrFaceInfo srFaceInfo) {
        List<SrFaceInfo> list = srFaceInfoService.list(
                new LambdaQueryWrapper<SrFaceInfo>()
                        .eq(StringUtils.isNotEmpty(srFaceInfo.getSchool()), SrFaceInfo::getSchool, srFaceInfo.getSchool())
                        .eq(StringUtils.isNotEmpty(srFaceInfo.getName()), SrFaceInfo::getName, srFaceInfo.getName())
                        .eq(StringUtils.isNotEmpty(srFaceInfo.getCardNo()), SrFaceInfo::getCardNo, srFaceInfo.getCardNo()));

        ExcelUtil<SrFaceInfo> util = new ExcelUtil<SrFaceInfo>(SrFaceInfo.class);
        return util.exportExcel(list, "人脸库");
    }
}