package com.ctsi.ssdc.project.controller;

import com.ctsi.ssdc.model.AjaxResult;
import com.ctsi.ssdc.model.PageResult;
import com.ctsi.ssdc.poi.excel.util.ExcelUtil;
import com.ctsi.ssdc.project.domain.SrGradeInfo;
import com.ctsi.ssdc.project.domain.SrGradeInfoExample;
import com.ctsi.ssdc.project.service.SrGradeInfoService;
import com.ctsi.ssdc.security.SecurityHxUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.search.suggest.SortBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

/**
 * REST controller for managing SrGradeInfo.
 *
 * @author hx
 * @date 2023-06-09 10:18:29
 */

@Api(value = "/api", tags = {"sr-grade-info-controller"})
@RestController
@RequestMapping("/api")
public class SrGradeInfoController {


    private final Logger log = LoggerFactory.getLogger(SrGradeInfoController.class);

    private static final String ENTITY_NAME = "srGradeInfo";

    private final SrGradeInfoService srGradeInfoService;

    public SrGradeInfoController(SrGradeInfoService srGradeInfoService) {
        this.srGradeInfoService = srGradeInfoService;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(true);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    /**
     * POST  /srGradeInfos : Create a new srGradeInfo.
     *
     * @param srGradeInfo the srGradeInfo to create
     * @return AjaxResult
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "SrGradeInfo", name = "srGradeInfo", value = "the srGradeInfo to create")
    })
    @ApiOperation(value = "POST  /srGradeInfos : create a new srGradeInfo.", notes = "POST  /srGradeInfos : create a new srGradeInfo.", httpMethod = "POST")
    @PostMapping("/srGradeInfos")
    public AjaxResult createSrGradeInfo(@RequestBody SrGradeInfo srGradeInfo) {

        log.debug("REST request to save SrGradeInfo : {}", srGradeInfo);

        //获取创建人
        srGradeInfo.setCreateBy(SecurityHxUtils.getCurrentUserName());
        srGradeInfo.setCreateTime(ZonedDateTime.now());

        SrGradeInfo existSrGradeInfo = srGradeInfoService.getExistGradeInfo(srGradeInfo.getSchool(),srGradeInfo.getName());
        if(null != existSrGradeInfo)
        {
            return AjaxResult.error("当前校区年级已存在");
        }
        SrGradeInfo result = srGradeInfoService.insert(srGradeInfo);
        return AjaxResult.success("新增成功", result);
    }

    /**
     * PUT  /srGradeInfos : Updates an existing srGradeInfo.
     *
     * @param srGradeInfo the srGradeInfo to update
     * @return AjaxResult
     * @throws
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "body", dataType = "SrGradeInfo", name = "srGradeInfo", value = "the srGradeInfo to update")
    })
    @ApiOperation(value = "PUT  /srGradeInfos : updates an existing srGradeInfo.", notes = "PUT  /srGradeInfos : updates an existing srGradeInfo.", httpMethod = "PUT")
    @PutMapping("/srGradeInfos")
    public AjaxResult updateSrGradeInfo(@RequestBody SrGradeInfo srGradeInfo) {

        log.debug("REST request to update SrGradeInfo : {}", srGradeInfo);
        srGradeInfo.setUpdateBy(SecurityHxUtils.getCurrentUserName());
        srGradeInfo.setUpdateTime(ZonedDateTime.now());

        if (srGradeInfo.getId() == null) {
            return createSrGradeInfo(srGradeInfo);
        }
        SrGradeInfo result = srGradeInfoService.update(srGradeInfo);
        return AjaxResult.success("更新成功", result);
    }

    /**
     * GET  /srGradeInfos/:id : get the "id" srGradeInfo.
     *
     * @param id the id of the srGradeInfo to retrieve
     * @return AjaxResult
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "Long", name = "id", value = "the id of the srGradeInfo to retrieve")
    })
    @ApiOperation(value = "GET  /srGradeInfos/id : get the id srGradeInfo.", notes = "GET  /srGradeInfos/id : get the id srGradeInfo.", httpMethod = "GET")
    @GetMapping("/srGradeInfos/{id}")
    public AjaxResult getSrGradeInfo(@PathVariable Long id) {

        log.debug("REST request to get SrGradeInfo : {}", id);

        SrGradeInfo srGradeInfo = srGradeInfoService.findOne(id);
        return AjaxResult.success("查询成功", srGradeInfo);
    }

    /**
     * GET  /srGradeInfos : get the srGradeInfos .
     *
     * @return the ResponseEntity with status 200 (OK) and the list of srGradeInfos in body
     */
    @ApiOperation(value = "GET  /srGradeInfos ")
    @GetMapping("/srGradeInfos")
    public AjaxResult getSrGradeInfosList(SrGradeInfoExample srGradeInfoExample, Pageable pageable) {

        log.debug("REST request to get SrGradeInfosList");
        Sort sort = Sort.by(Sort.Direction.DESC,"create_time");
        pageable= PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);
//        srGradeInfoExample.setCreateTime();
        return AjaxResult.success("分页查询成功", srGradeInfoService.findByExample(srGradeInfoExample, pageable));
    }

    /**
     * 根据校区获取年级列表
     * @param school
     * @return
     */
    @GetMapping("/srGradeInfos/getGradeBySchool")
    public AjaxResult getGradeBySchool(String school)
    {

         List<SrGradeInfo> srGradeInfos = srGradeInfoService.getGradeInfoBySchool(school);
         return AjaxResult.success(srGradeInfos);
    }

    /**
     * DELETE  /srGradeInfos/:id : delete the "id" srGradeInfo.
     *
     * @param id the id of the srGradeInfo to delete
     * @return the AjaxResult with status 200 (OK)
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "Long", name = "id", value = "the id of the srGradeInfo to delete")
    })
    @ApiOperation(value = "DELETE  /srGradeInfos/id : delete the id srGradeInfo.", notes = "DELETE  /srGradeInfos/id : delete the id srGradeInfo.", httpMethod = "DELETE")
    @DeleteMapping("/srGradeInfos/{id}")
    public AjaxResult deleteSrGradeInfo(@PathVariable Long id) {
        log.debug("REST request to delete SrGradeInfo : {}", id);
        srGradeInfoService.deleteById(id);
        return AjaxResult.success("删除成功", id);
    }


    /**
     * GET  /srGradeInfos/:id : get the "id" srGradeInfo.
     *
     * @return the ResponseEntity with status 200 (OK) and with body the srGradeInfo, or with status 404 (Not Found)
     */
    @ApiOperation(value = "POST  /srGradeInfos/export : export the srGradeInfo to excel", notes = "DELETE  /srGradeInfos/id : delete the id srGradeInfo.", httpMethod = "DELETE")
    @PostMapping("/srGradeInfos/export")
    public ResponseEntity<byte[]> export() {

        log.debug("REST request to export SrGradeInfo");

        PageResult<SrGradeInfo> result = srGradeInfoService.findAll();
        List<SrGradeInfo> list = result.getData();
        ExcelUtil<SrGradeInfo> util = new ExcelUtil<SrGradeInfo>(SrGradeInfo.class);
        return util.exportExcel(list, "srGradeInfo");
    }


    /**
     * DELETE  /ids : delete the srGradeInfo.", notes = "DELETE  /ids : delete the ids.", httpMethod = "DELETE"
     *
     * @return
     */
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "Long[]", name = "ids", value = "the ids of the srGradeInfo to delete")
    })
    @ApiOperation(value = "DELETE  /ids : delete the srGradeInfo.", notes = "DELETE  /ids : delete the ids.", httpMethod = "DELETE")
    @DeleteMapping("/srGradeInfos/delAll")
    public AjaxResult deleteSrGradeInfo(Long[] ids) {

        log.debug("REST request to delete ids");

        srGradeInfoService.deleteByIds(ids);
        return AjaxResult.success("批量删除成功", StringUtils.join(ids));
    }

}
