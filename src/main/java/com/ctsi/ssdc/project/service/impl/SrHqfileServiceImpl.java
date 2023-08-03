package com.ctsi.ssdc.project.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ctsi.ssdc.project.mapper.SrHqfileMapper;
import com.ctsi.ssdc.project.domain.SrHqfile;
import com.ctsi.ssdc.project.service.SrHqfileService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 后勤管理文件信息(SrHqfile)表服务实现类
 *
 * @author makejava
 * @since 2023-08-01 15:09:36
 */
@Service
public class SrHqfileServiceImpl extends ServiceImpl<SrHqfileMapper, SrHqfile> implements SrHqfileService {
    @Autowired
    private SrHqfileMapper srHqfileMapper;

    @Override
    public List<SrHqfile> getSrHqfileList(SrHqfile srHqfile) {
        return srHqfileMapper.getSrHqfileList(srHqfile);
    }

    @Override
    public SrHqfile getSrHqfileById(Long id) {
        return srHqfileMapper.getSrHqfileById(id);
    }
}