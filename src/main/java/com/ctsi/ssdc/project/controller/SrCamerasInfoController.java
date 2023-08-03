package com.ctsi.ssdc.project.controller;


import com.alibaba.fastjson.JSONObject;
import com.ctsi.ssdc.common.utils.HKCameraUtils;
import com.ctsi.ssdc.model.AjaxResult;
import com.ctsi.ssdc.model.PageResult;
import com.ctsi.ssdc.project.domain.SrCamerasInfo;
import com.ctsi.ssdc.project.domain.SrClassInfo;
import com.ctsi.ssdc.project.domain.SrRegionsInfoTree;
import com.ctsi.ssdc.project.domain.req.PtzControllingReq;
import com.ctsi.ssdc.project.service.SrCamerasInfoService;
import com.ctsi.ssdc.project.service.SrRegionsInfoService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import liquibase.pro.packaged.E;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/api/srCamerasInfos")
public class SrCamerasInfoController {

    @Autowired
    private SrCamerasInfoService srCamerasInfoService;
    @Autowired
    private SrRegionsInfoService srRegionsInfoService;
    /**
     * 同步监控点位数据
     * @return
     */
    @GetMapping("/syncData")
    public AjaxResult syncCamerasInfo()
    {
        boolean res = srCamerasInfoService.syncCamerasInfo();
        if(res)
        {
            return AjaxResult.success("同步成功");

        }
        return AjaxResult.error("同步失败") ;
    }

    /**
     * 获取播放地址
     * @param cameraIndexCode
     * @return
     */
    @GetMapping("/getPlayUrl")
    public AjaxResult getPlayUrl(String cameraIndexCode,String protocol)
    {
        return srCamerasInfoService.getPlayUrl(cameraIndexCode,protocol);
    }

    @GetMapping()
    public AjaxResult getCarmeraPage(SrCamerasInfo srCamerasInfo, Pageable page)
    {
        if(srCamerasInfo.getRegionIndexCode()!=null)
        {
            //获取区域下所有code
            SrRegionsInfoTree srRegionsInfoTree = srRegionsInfoService.getTreeWihoutCameraByIndexCode(srCamerasInfo.getRegionIndexCode());
            List returnList = new ArrayList();
            List<String> regionIdexCodeList = srRegionsInfoService.getAllChildrenIndexCode(srRegionsInfoTree,returnList);
            regionIdexCodeList.add(srCamerasInfo.getRegionIndexCode());
            srCamerasInfo.setRegionIndexCode(String.join(",",regionIdexCodeList));
        }
        PageHelper.startPage(page.getPageNumber(),page.getPageSize());
        List<SrCamerasInfo> list =srCamerasInfoService.getPage(srCamerasInfo);
        PageInfo<E> info = new PageInfo(list);
        long count = info.getTotal();

        return AjaxResult.success(  new PageResult(list, count, count));
    }

    @PostMapping("/ptzControlling")
    public AjaxResult ptzControlling(@RequestBody PtzControllingReq req)
    {
        String interfacePath = "/api/frs/v1/resource/recognition";

        JSONObject jsonBody = new JSONObject();
        jsonBody.put("cameraIndexCode", req.getCameraIndexCode());
        jsonBody.put("action", req.getAction());
        jsonBody.put("command",req.getCommand());
        jsonBody.put("speed",req.getSpeed());


        return AjaxResult.success(JSONObject.parseObject(HKCameraUtils.getHaikangData(interfacePath,jsonBody)));
    }

//    /**
//     *
//     * @return
//     */
//    @GetMapping("/getRegionCamera")
//    public AjaxResult getRegionCamera(Map<String,Object> req)
//    {
//        String regionIndexCode = (String) req.get("regionIndexCode");
//        return AjaxResult.success();
//    }


}
