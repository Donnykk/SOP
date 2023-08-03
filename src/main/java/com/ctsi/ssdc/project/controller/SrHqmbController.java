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
import com.ctsi.ssdc.project.domain.SrHqmb;
import com.ctsi.ssdc.project.service.SrHqmbService;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 后勤管理-模版(SrHqmb)表控制层
 *
 * @author makejava
 * @since 2023-08-01 11:37:04
 */
@Api(tags = "后勤管理-模版") 
@Slf4j
@RestController
@RequestMapping("/api/srHqmb")
public class SrHqmbController {
    /**
     * 服务对象
     */
    @Autowired
    private SrHqmbService srHqmbService;

    
    /**
     * 加载表格分页数据
     * @param srHqmb
     * @return IPage<SrHqmb>
     */
    @ApiOperation(value = "后勤管理-模版分页数据")
    @GetMapping
    public AjaxResult pageList( SrHqmb srHqmb, Pageable page) {
         PageHelper.startPage(page.getPageNumber(),page.getPageSize());
         List<SrHqmb> list = this.srHqmbService.getSrHqmb(srHqmb);
       
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
    @ApiOperation(value = "通过ID加载后勤管理-模版")
    @GetMapping("/{id}")
    public AjaxResult selectOne(@PathVariable("id") Long id) {
        return AjaxResult.success(srHqmbService.getById(id));
    }
    
    
    /**
     * 创建后勤管理-模版资源对象
     * @param entity
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "新建后勤管理-模版 数据")
    @PostMapping()
    public AjaxResult save(@RequestBody @Valid SrHqmb entity) {
        if (null!=entity){
            try {
                entity.setCreateBy(SecurityHxUtils.getCurrentUserName());
                this.srHqmbService.save(entity);
                return AjaxResult.success();
            }catch (Exception e){
                log.info("创建后勤模版异常");
                e.printStackTrace();
                return AjaxResult.error(e.getMessage());
            }
        }
        return AjaxResult.error();
    }

    /**
     * 编辑更新后勤管理-模版 对象
     * @param srHqmb
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "编辑后勤管理-模版更新数据")
    @PutMapping
    public AjaxResult edit( @RequestBody  SrHqmb srHqmb) {
        if (null!=srHqmb&&null!=srHqmb. getId()){
            try {
                SrHqmb queryEntity=this.srHqmbService.getById(srHqmb. getId());
                if (null!=queryEntity){
                    this.srHqmbService.updateById(srHqmb);
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
            this.srHqmbService.removeByIds(idlist);
            return AjaxResult.success();
        }catch (Exception e){
            log.info(e.getStackTrace().toString());
            return AjaxResult.error();
        }
       
    }

  

}