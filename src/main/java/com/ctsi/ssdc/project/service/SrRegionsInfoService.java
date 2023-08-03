package com.ctsi.ssdc.project.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ctsi.ssdc.project.domain.SrRegionsInfo;
import com.ctsi.ssdc.project.domain.SrRegionsInfoTree;

import java.util.List;

public interface SrRegionsInfoService extends IService<SrRegionsInfo> {

    /**
     * 同步区域数据
     * @return
     */
    public boolean syncRegions();

    /**
     * 获取分页数据
     * @param srRegionsInfo
     * @return
     */
    public List<SrRegionsInfo> getPage(SrRegionsInfo srRegionsInfo);

    /**
     * 获取树形结构数据
     * @param srRegionsInfo
     * @return
     */
    public  SrRegionsInfoTree getTree(SrRegionsInfo srRegionsInfo);

    /**
     * 根据区域编码获取下级树结构
     * @param indexCode
     * @return
     */
    public SrRegionsInfoTree getTreeByIndexCode(String indexCode);


    /**
     * 根据区域编码获取下级树结构 不包含摄像机信息
     * @param indexCode
     * @return
     */
    public SrRegionsInfoTree getTreeWihoutCameraByIndexCode(String indexCode);

    /**
     * 获取所有子节点
     * @param srRegionsInfoTree
     * @return
     */
    public List<String> getAllChildrenIndexCode(SrRegionsInfoTree srRegionsInfoTree,List returnList);
}
