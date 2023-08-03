package com.ctsi.ssdc.project.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ctsi.ssdc.project.domain.SrHqfile;

import java.util.List;

/**
 * 后勤管理文件信息(SrHqfile)表服务接口
 *
 * @author makejava
 * @since 2023-08-01 15:09:36
 */
public interface SrHqfileService extends IService<SrHqfile> {

    public List<SrHqfile> getSrHqfileList(SrHqfile srHqfile);

    public SrHqfile getSrHqfileById(Long id);

}