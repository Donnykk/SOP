package com.ctsi.ssdc.project.controller;

import com.ctsi.ssdc.common.oos.OosConfig;
import com.ctsi.ssdc.common.oos.OosUtils;
import com.ctsi.ssdc.model.AjaxResult;
import com.ctsi.ssdc.model.PageResult;
import com.ctsi.ssdc.poi.excel.util.ExcelUtil;
import com.ctsi.ssdc.project.domain.SrFaceInfo;
import com.ctsi.ssdc.project.domain.SrWarningInfo;
import com.ctsi.ssdc.project.domain.req.SrWarningReq;
import com.ctsi.ssdc.project.service.SrWarningInfoService;
import com.ctsi.ssdc.security.SecurityHxUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import liquibase.pro.packaged.D;
import liquibase.pro.packaged.E;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * 告警信息
 */
@RestController
@RequestMapping("/api/srWarningInfos")
public class SrWarningInfoController {

    @Autowired
    private SrWarningInfoService srWarningInfoService;

    /**
     * 新增告警信息
     * @param srWarningInfo
     * @return
     */
    @PostMapping()
    public AjaxResult save(@RequestBody SrWarningInfo srWarningInfo)
    {
        if(srWarningInfoService.save(srWarningInfo))
        {
            return AjaxResult.success("新增成功");
        }
        return AjaxResult.error("新增失败");

    }

    /**
     * 修改告警信息
     * @param srWarningInfo
     * @return
     */
    @PutMapping
    public AjaxResult edit(@RequestBody SrWarningInfo srWarningInfo)
    {
        if(srWarningInfoService.updateById(srWarningInfo))
        {
            return AjaxResult.success("修改完成");
        }
        return AjaxResult.error("修改失败");
    }

    /**
     * 查看分页
     * @param req
     * @param page
     * @return
     */
    @GetMapping
    public AjaxResult getPage(SrWarningReq req, Pageable page)
    {
        PageHelper.startPage(page.getPageNumber(),page.getPageSize());
        List<SrWarningInfo> list = srWarningInfoService.getPage(req);

        PageInfo<E> info = new PageInfo(list);
        long count = info.getTotal();
        return AjaxResult.success(new PageResult(list, count, count));
    }

    @GetMapping("/{id}")
    public AjaxResult getDetail(@PathVariable  Long id)
    {
        SrWarningInfo srWarningInfo = srWarningInfoService.getWarningById(id);
        srWarningInfo.setCurrentPeople(SecurityHxUtils.getCurrentUserName());

        //转换图片
        srWarningInfo.setHandleImage(OosUtils.changeUrl(srWarningInfo.getHandleImage()));
        srWarningInfo.setImage(OosUtils.changeUrl(srWarningInfo.getImage()));


        srWarningInfo.setCurrentTime(new Date());
        return AjaxResult.success(srWarningInfo);
    }

    /**
     * 导出
     * @param req
     * @return
     */
    @GetMapping("/export")
    public ResponseEntity<byte[]> export(SrWarningReq req)
    {
        List<SrWarningInfo> list = srWarningInfoService.getPage(req);
        ExcelUtil<SrWarningInfo> util = new ExcelUtil<SrWarningInfo>(SrWarningInfo.class);
        return util.exportExcel(list, "告警库");
    }

//    @PostMapping("import")
//    public AjaxResult importExcel(MultipartFile file) throws Exception {
//        ExcelUtil<SrWarningInfo> util = new ExcelUtil<SrWarningInfo>(SrWarningInfo.class);
//        List<SrWarningInfo> warningInfos = util.importExcel(file.getInputStream());
//
//    }

}
