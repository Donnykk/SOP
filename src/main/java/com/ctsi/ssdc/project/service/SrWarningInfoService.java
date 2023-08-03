package com.ctsi.ssdc.project.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ctsi.ssdc.project.domain.SrWarningInfo;
import com.ctsi.ssdc.project.domain.req.SrWarningReq;

import java.util.List;

public interface SrWarningInfoService extends IService<SrWarningInfo> {

    public List<SrWarningInfo> getPage(SrWarningReq req);


    public SrWarningInfo getWarningById(Long id);
}
