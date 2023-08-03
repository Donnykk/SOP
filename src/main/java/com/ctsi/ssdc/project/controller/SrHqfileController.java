package com.ctsi.ssdc.project.controller;



import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ctsi.ssdc.model.AjaxResult;
import com.ctsi.ssdc.model.PageResult;
import com.ctsi.ssdc.security.SecurityHxUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.data.domain.Pageable;
import liquibase.pro.packaged.E;
import org.springframework.beans.factory.annotation.Autowired;
import lombok.extern.slf4j.Slf4j;
import io.swagger.annotations.ApiOperation;
import com.ctsi.ssdc.project.domain.SrHqfile;
import com.ctsi.ssdc.project.service.SrHqfileService;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 后勤管理文件信息(SrHqfile)表控制层
 *
 * @author makejava
 * @since 2023-08-01 15:09:36
 */
@Api(tags = "后勤管理文件信息") 
@Slf4j
@RestController
@RequestMapping("/api/srHqfile")
public class SrHqfileController {
    /**
     * 服务对象
     */
    @Autowired
    private SrHqfileService srHqfileService;

    
    /**
     * 加载表格分页数据
     * @param srHqfile
     * @return IPage<SrHqfile>
     */
    @ApiOperation(value = "后勤管理文件信息分页数据")
    @GetMapping
    public AjaxResult pageList( SrHqfile srHqfile, Pageable page) {
         PageHelper.startPage(page.getPageNumber(),page.getPageSize());
         List<SrHqfile> list = this.srHqfileService.getSrHqfileList(srHqfile);
       
        PageInfo<E> info = new PageInfo(list);
        long count = info.getTotal();
        return AjaxResult.success(  new PageResult(list, count, count));
    }
    
    

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @ApiOperation(value = "通过ID加载后勤管理文件信息")
    @GetMapping("/{id}")
    public AjaxResult selectOne(@PathVariable("id") Long id) {
        return AjaxResult.success(srHqfileService.getSrHqfileById(id));
    }
    
    
    /**
     * 创建后勤管理文件信息资源对象
     * @param entity
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "新建后勤管理文件信息 数据")
    @PostMapping()
    public AjaxResult save(@RequestBody @Valid SrHqfile entity) {
        if (null!=entity){
            try {
                entity.setCreateBy(SecurityHxUtils.getCurrentUserName());
                this.srHqfileService.save(entity);
                return AjaxResult.success();
            }catch (Exception e){
                log.info("创建后勤文件异常");
                e.printStackTrace();
                return AjaxResult.error(e.getMessage());
            }
        }
        return AjaxResult.error();
    }

    /**
     * 编辑更新后勤管理文件信息 对象
     * @param srHqfile
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "编辑后勤管理文件信息更新数据")
    @PutMapping
    public AjaxResult edit( @RequestBody  SrHqfile srHqfile) {
        if (null!=srHqfile&&null!=srHqfile. getId()){
            try {
                SrHqfile queryEntity=this.srHqfileService.getById(srHqfile. getId());
                if (null!=queryEntity){
                    this.srHqfileService.updateById(srHqfile);
                    return AjaxResult.success();
                }else{
                    return AjaxResult.error("当前记录不存在");
                }
            }catch (Exception e){
               
                log.info(e.getStackTrace().toString());
                return AjaxResult.error();
            }
        }
       return AjaxResult.error();
    }


    /**
     * 根据id删除资源对象
     * 这里的删除为数据库真是删除，需要慎用。
     */
    @ApiOperation(value = "根据ID删除数据，多个以逗号分隔")
    @DeleteMapping("/{ids}")
    public AjaxResult deleteByIds(@PathVariable("ids") Long[] ids){
       
        try {
            List<Long> idlist = Arrays.asList(ids);
            this.srHqfileService.removeByIds(idlist);
            return AjaxResult.success();
        }catch (Exception e){
            log.info(e.getStackTrace().toString());
            return AjaxResult.error();
        }
       
    }

  

}