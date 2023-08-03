package com.ctsi.ssdc.project.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ctsi.ssdc.model.AjaxResult;
import com.ctsi.ssdc.project.domain.SrCamerasInfo;
import com.ctsi.ssdc.project.domain.SrRegionsInfoTree;

import java.util.List;

public interface SrCamerasInfoService extends IService<SrCamerasInfo> {

    public boolean syncCamerasInfo();

    public AjaxResult getPlayUrl(String cameraIndexCode,String protocol);

    public List<SrCamerasInfo> getPage(SrCamerasInfo srCamerasInfo);

    public SrRegionsInfoTree getRegionCarmeaTree(String regionIndexCode);
}
