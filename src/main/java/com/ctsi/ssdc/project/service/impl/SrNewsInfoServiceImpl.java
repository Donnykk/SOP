package com.ctsi.ssdc.project.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ctsi.ssdc.project.domain.SrNewsInfo;
import com.ctsi.ssdc.project.mapper.SrNewsInfoMapper;
import com.ctsi.ssdc.project.service.SrNewsInfoService;
import org.springframework.stereotype.Service;

@Service
public class SrNewsInfoServiceImpl extends ServiceImpl<SrNewsInfoMapper, SrNewsInfo> implements SrNewsInfoService {
}
