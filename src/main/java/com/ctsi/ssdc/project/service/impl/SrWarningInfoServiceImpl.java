package com.ctsi.ssdc.project.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ctsi.ssdc.project.domain.SrWarningInfo;
import com.ctsi.ssdc.project.domain.req.SrWarningReq;
import com.ctsi.ssdc.project.mapper.SrWarningInfoMapper;
import com.ctsi.ssdc.project.service.SrWarningInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SrWarningInfoServiceImpl extends ServiceImpl<SrWarningInfoMapper, SrWarningInfo> implements SrWarningInfoService {

    @Autowired
    private SrWarningInfoMapper srWarningInfoMapper;

    @Override
    public List<SrWarningInfo> getPage(SrWarningReq req) {
        return srWarningInfoMapper.getPage(req);
    }

    @Override
    public SrWarningInfo getWarningById(Long id) {
        return srWarningInfoMapper.getWarningById(id);
    }
}
