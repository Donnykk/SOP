package com.ctsi.ssdc.project.controller;

import com.ctsi.ssdc.model.AjaxResult;
import com.ctsi.ssdc.model.PageResult;
import com.ctsi.ssdc.project.domain.SrRegionsInfo;
import com.ctsi.ssdc.project.domain.SrRegionsInfoTree;
import com.ctsi.ssdc.project.service.SrRegionsInfoService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import liquibase.pro.packaged.E;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/srRegionsInfos")
public class SrRegionsInfoController {

    @Autowired
    private SrRegionsInfoService srRegionsInfoService;

    /**
     * 同步区域信息
     * @return
     */
    @GetMapping("/syncData")
    public AjaxResult syncRegions()
    {
        if(srRegionsInfoService.syncRegions())
        {
            return AjaxResult.success("同步成功");

        }
        return AjaxResult.error("同步失败");
    }


    /**
     * 获取分页数据
     * @param srRegionsInfo
     * @param page
     * @return
     */
    @GetMapping()
    public AjaxResult getPage(SrRegionsInfo srRegionsInfo, Pageable page)
    {
        PageHelper.startPage(page.getPageNumber(),page.getPageSize());
        List<SrRegionsInfo> list = srRegionsInfoService.getPage(srRegionsInfo);

        PageInfo<E> info = new PageInfo(list);
        long count = info.getTotal();
        return AjaxResult.success(new PageResult(list, count, count));
    }

    /**
     * 获取树形结构图
     * @param srRegionsInfo
     * @return
     */
    @GetMapping("/tree")
    public AjaxResult getTree(SrRegionsInfo srRegionsInfo)
    {
        SrRegionsInfoTree tree = srRegionsInfoService.getTree(srRegionsInfo);
        return AjaxResult.success(tree);
    }

    @GetMapping("/getTreeByIndexCode")
    public AjaxResult getTreeByIndexCode(String indexCode)
    {
        SrRegionsInfoTree tree = srRegionsInfoService.getTreeByIndexCode(indexCode);
        return AjaxResult.success(tree);
    }


}
