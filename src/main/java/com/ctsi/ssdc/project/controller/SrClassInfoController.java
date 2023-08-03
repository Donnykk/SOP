package com.ctsi.ssdc.project.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ctsi.ssdc.model.AjaxResult;
import com.ctsi.ssdc.model.PageResult;
import com.ctsi.ssdc.project.domain.SrClassInfo;
import com.ctsi.ssdc.project.domain.SrGradeInfo;
import com.ctsi.ssdc.project.service.SrClassInfoService;
import com.ctsi.ssdc.project.service.SrGradeInfoService;
import com.ctsi.ssdc.security.SecurityHxUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import liquibase.pro.packaged.E;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/srClassInfos")
@Api(tags = "班级管理接口")
public class SrClassInfoController  {
    @Autowired
    private SrClassInfoService srClassInfoService;
    @Autowired
    private SrGradeInfoService srGradeInfoService;

    /**
     * 新增班级
     * @param srClassInfo
     * @return
     */
    @PostMapping()
    @ApiOperation("新增班级")
    public AjaxResult save(@RequestBody SrClassInfo srClassInfo)
    {
        log.info("新增班级数据{}",srClassInfo.toString());
//        srClassInfo.setId(111L);
        srClassInfo.setCreateBy(SecurityHxUtils.getCurrentUserName());
        srClassInfo.setCreateTime(new Date());

        SrGradeInfo srGradeInfo = srGradeInfoService.findOne(srClassInfo.getGradeId());

        SrClassInfo currentClassInfo = srClassInfoService.getExistClassInfo(srClassInfo.getSchool(),srGradeInfo.getName(),srClassInfo.getClassName());
        if(null!=currentClassInfo)
        {
            return AjaxResult.error("已存在相同的班级");
        }

        if(srClassInfoService.save(srClassInfo)) {
            return AjaxResult.success("新增成功");
        }
        return AjaxResult.error("新增失败");
    }


    /**
     * 根据 id 查询班级
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @ApiOperation("根据 id查询班级")
    public AjaxResult getClassInfoById(@PathVariable  Long id)
    {
        SrClassInfo srClassInfo = srClassInfoService.getById(id);

        SrGradeInfo srGradeInfo = srGradeInfoService.findOne(srClassInfo.getGradeId());
        if(null!=srGradeInfo)
        {
            srClassInfo.setGradeName(srGradeInfo.getName());
            srClassInfo.setSchool(srGradeInfo.getSchool());
        }
        return AjaxResult.success(srClassInfo);
    }

    /**
     * 查询班级分页数据
     * @param srClassInfo
     * @param page
     * @return
     */
    @GetMapping()
    @ApiOperation("查询分页")
    public AjaxResult getClassInfoPage(SrClassInfo srClassInfo,Pageable page)
    {

        PageHelper.startPage(page.getPageNumber(),page.getPageSize());
        List<SrClassInfo> srClassInfoIPage =srClassInfoService.getPage(srClassInfo);


        PageInfo<E> info = new PageInfo(srClassInfoIPage);
        long count = info.getTotal();

        return AjaxResult.success(  new PageResult(srClassInfoIPage, count, count));
    }

    @PutMapping()
    @ApiOperation("修改数据")
    public AjaxResult edit(@RequestBody SrClassInfo srClassInfo)
    {
        if(srClassInfoService.updateById(srClassInfo))
        {
            return AjaxResult.success("修改成功");
        }
        return AjaxResult.error("修改失败");
    }

    /**
     * 删除指定的数据
     * @param ids
     * @return
     */
    @DeleteMapping("/{ids}")
    @ApiOperation("删除数据")
    public AjaxResult delete(@PathVariable Long[] ids)
    {
        List<Long> idList = Arrays.asList(ids);
        srClassInfoService.removeByIds(idList);
        return AjaxResult.success("删除成功");
    }

//    /**
//     * 批量删除数据
//     * @param ids
//     * @return
//     */
//    @DeleteMapping("/deleteList")
//    public AjaxResult deleteBatch(Long[] ids)
//    {
//
//       if(srClassInfoService.remove(new LambdaQueryWrapper<SrClassInfo>().in(SrClassInfo::getId,idList))) {
//           return AjaxResult.success("删除成功");
//       }
//       return AjaxResult.error("删除失败");
//    }

    @GetMapping("/list")
    @ApiOperation("获取所有列表")
    public AjaxResult getClassList(Long gradeId)
    {
        return AjaxResult.success(srClassInfoService.getClassByGrade(gradeId));
    }
}
