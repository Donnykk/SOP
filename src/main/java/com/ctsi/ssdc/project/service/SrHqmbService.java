package com.ctsi.ssdc.project.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ctsi.ssdc.project.domain.SrHqmb;

import java.util.List;

/**
 * 后勤管理-模版(SrHqmb)表服务接口
 *
 * @author makejava
 * @since 2023-08-01 11:37:04
 */
public interface SrHqmbService extends IService<SrHqmb> {

    public List<SrHqmb> getSrHqmb(SrHqmb srHqmb);

}