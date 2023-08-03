package com.ctsi.ssdc.project.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ctsi.ssdc.common.utils.HKCameraUtils;
import com.ctsi.ssdc.project.domain.SrCamerasVo;
import com.ctsi.ssdc.project.domain.SrRegionsInfo;
import com.ctsi.ssdc.project.domain.SrRegionsInfoTree;
import com.ctsi.ssdc.project.mapper.SrCamerasInfoMapper;
import com.ctsi.ssdc.project.mapper.SrRegionsInfoMapper;
import com.ctsi.ssdc.project.service.SrRegionsInfoService;
import org.neo4j.cypher.internal.compiler.v2_1.functions.Str;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SrRegionsInfoServiceImpl extends ServiceImpl<SrRegionsInfoMapper, SrRegionsInfo> implements SrRegionsInfoService {

    @Autowired
    SrRegionsInfoMapper srRegionsInfoMapper;
    @Autowired
    SrCamerasInfoMapper srCamerasInfoMapper;

    @Override
    @Transactional
    public boolean syncRegions() {

        //接口地址
        String interfacePath = "/api/resource/v1/regions";

        //请求体
        JSONObject jsonBody = new JSONObject();
        jsonBody.put("pageNo", 1);
        jsonBody.put("pageSize", 200);

        String result = HKCameraUtils.getHaikangData(interfacePath,jsonBody);
        JSONObject jsonRes = JSONObject.parseObject(result);
        String code = jsonRes.getString("code");
        if(!code.equals("0"))
        {
            return false;
        }

        //清空数据库
        srRegionsInfoMapper.delete(null);
        //将结果存入数据库
        List<SrRegionsInfo> srRegionsInfos = JSONObject.parseArray(jsonRes.getJSONObject("data").getJSONArray("list").toJSONString(),SrRegionsInfo.class);

        this.saveBatch(srRegionsInfos);

        return true;
    }

    @Override
    public List<SrRegionsInfo> getPage(SrRegionsInfo srRegionsInfo) {
        return srRegionsInfoMapper.getPage(srRegionsInfo);
    }

    @Override
    public SrRegionsInfoTree getTree(SrRegionsInfo query) {
        return getTreeByQuery("root000000",query,false);
    }

    @Override
    public SrRegionsInfoTree getTreeByIndexCode(String indexCode) {

        SrRegionsInfo query = new SrRegionsInfo();
        return getTreeByQuery(indexCode,query,true);
    }

    @Override
    public SrRegionsInfoTree getTreeWihoutCameraByIndexCode(String indexCode) {
        SrRegionsInfo query = new SrRegionsInfo();
        return getTreeByQuery(indexCode,query,false);
    }

    @Override
    public List<String> getAllChildrenIndexCode(SrRegionsInfoTree srRegionsInfoTree,List returnList) {
        List<SrRegionsInfoTree> childrenList = srRegionsInfoTree.getChildren();
        if(childrenList!=null&&childrenList.size()>0)
        {
            for(SrRegionsInfoTree tree:childrenList)
            {
                getAllChildrenIndexCode(tree,returnList);
                returnList.add(tree.getIndexCode());
            }
        }
        return returnList;
    }


//    private SrRegionsInfoTree getTreeWithoutCamera()

    private SrRegionsInfoTree getTreeByQuery(String indexCode,SrRegionsInfo query,boolean hasCamera)
    {
        Map<String,SrRegionsInfoTree> tree = new HashMap<>();

        //获取所有区域数据
        List<SrRegionsInfo> list = srRegionsInfoMapper.getPage(query);

        for(SrRegionsInfo srRegionsInfo:list)
        {
            SrRegionsInfoTree srRegionsInfoTree = new SrRegionsInfoTree();
            BeanUtils.copyProperties(srRegionsInfo,srRegionsInfoTree);
            tree.put(srRegionsInfo.getIndexCode(),srRegionsInfoTree);

            //获取监控列表
            if(hasCamera) {
                List<SrCamerasVo> camerasVos = srCamerasInfoMapper.getSrCamerasVo(srRegionsInfo.getIndexCode());
                srRegionsInfoTree.setSrCameras(camerasVos);
            }
        }
        //将 list 置空,垃圾回收
        list = null;

        tree.entrySet().forEach(item->{
            //获取当前节点
            SrRegionsInfoTree srRegionsInfoTree = item.getValue();
            //根据当前节点查询去map获取父级节点
            SrRegionsInfoTree fwcSrRegionsInfoTree = tree.get(srRegionsInfoTree.getParentIndexCode());
            //判断是否有父级节点
            if (fwcSrRegionsInfoTree != null){
                //给父级节点添加当前子
                fwcSrRegionsInfoTree.getChildren().add(srRegionsInfoTree);
            }

        });

        return tree.get(indexCode);
    }
}
