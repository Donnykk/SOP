package com.ctsi.ssdc.project.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ctsi.ssdc.project.mapper.SrHqmbMapper;
import com.ctsi.ssdc.project.domain.SrHqmb;
import com.ctsi.ssdc.project.service.SrHqmbService;
import liquibase.pro.packaged.S;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 后勤管理-模版(SrHqmb)表服务实现类
 *
 * @author makejava
 * @since 2023-08-01 11:37:04
 */
@Service
public class SrHqmbServiceImpl extends ServiceImpl<SrHqmbMapper, SrHqmb> implements SrHqmbService {
    @Autowired
    private SrHqmbMapper srHqmbMapper;

    @Override
    public List<SrHqmb> getSrHqmb(SrHqmb srHqmb) {
        List<SrHqmb> list = srHqmbMapper.getSrHqmbList(srHqmb);

        return list;
    }
}