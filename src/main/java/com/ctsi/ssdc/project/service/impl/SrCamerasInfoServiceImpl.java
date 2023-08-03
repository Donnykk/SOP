package com.ctsi.ssdc.project.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ctsi.ssdc.common.utils.HKCameraUtils;
import com.ctsi.ssdc.model.AjaxResult;
import com.ctsi.ssdc.project.domain.SrCamerasInfo;
import com.ctsi.ssdc.project.domain.SrRegionsInfoTree;
import com.ctsi.ssdc.project.mapper.SrCamerasInfoMapper;
import com.ctsi.ssdc.project.service.SrCamerasInfoService;
import com.ctsi.ssdc.project.service.SrRegionsInfoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SrCamerasInfoServiceImpl extends ServiceImpl<SrCamerasInfoMapper, SrCamerasInfo> implements SrCamerasInfoService {

    @Autowired
    private SrCamerasInfoMapper srCamerasInfoMapper;

    @Autowired
    private SrRegionsInfoService srRegionsInfoService;

    /**
     * 同步监控数据
     * @return
     */
    @Override
    public boolean syncCamerasInfo() {

        //清空数据库
        srCamerasInfoMapper.delete(null);

        //接口地址
        String interfacePath = "/api/resource/v1/cameras";

        //请求体
        JSONObject jsonBody = new JSONObject();
        jsonBody.put("pageNo", 1);
        jsonBody.put("pageSize", 100);

        List<SrCamerasInfo> srCamerasInfoRes =new ArrayList<>();

        //获取返回结果
        String result = HKCameraUtils.getHaikangData(interfacePath,jsonBody);
        JSONObject jsonRes = JSONObject.parseObject(result);
        String code = jsonRes.getString("code");
        if(!code.equals("0"))
        {
            return false;
        }

        //将结果存入数据库
        List<SrCamerasInfo> srCamerasInfos = JSONObject.parseArray(jsonRes.getJSONObject("data").getJSONArray("list").toJSONString(),SrCamerasInfo.class);
        srCamerasInfoRes.addAll(srCamerasInfos);

        int total = jsonRes.getJSONObject("data").getInteger("total");
        int sizeTotal = total/100+1;

        for(int i=2;i<=sizeTotal;i++)
        {
            jsonBody.put("pageNo",i);

            result = HKCameraUtils.getHaikangData(interfacePath,jsonBody);
            jsonRes = JSONObject.parseObject(result);
            code = jsonRes.getString("code");
            if(!code.equals("0"))
            {
                return false;
            }

            //将结果存入数据库
            srCamerasInfos = JSONObject.parseArray(jsonRes.getJSONObject("data").getJSONArray("list").toJSONString(),SrCamerasInfo.class);
            srCamerasInfoRes.addAll(srCamerasInfos);
        }

        //存入数据库
        this.saveBatch(srCamerasInfoRes);

        return true;
    }

    @Override
    public AjaxResult getPlayUrl(String cameraIndexCode,String protocol) {
        //接口地址
        String interfacePath = "/api/video/v1/cameras/previewURLs";

        //请求体
        JSONObject jsonBody = new JSONObject();
        jsonBody.put("cameraIndexCode", cameraIndexCode);
        jsonBody.put("streamType", 1);
        if(StringUtils.isEmpty(protocol)) {
            jsonBody.put("protocol", "hls");
        }else{
            jsonBody.put("protocol", protocol);
        }
        jsonBody.put("transmode", 1);
        jsonBody.put("expand", "streamform=ps");
        String res = HKCameraUtils.getHaikangData(interfacePath,jsonBody);
        if(null==res)
        {
            return AjaxResult.error("获取播放地址失败");
        }
        JSONObject jsonObject = JSONObject.parseObject(res);
        return AjaxResult.success(jsonObject);
    }

    @Override
    public List<SrCamerasInfo> getPage(SrCamerasInfo srCamerasInfo) {

        return srCamerasInfoMapper.getPage(srCamerasInfo);
    }

    @Override
    public SrRegionsInfoTree getRegionCarmeaTree(String regionIndexCode) {
        SrRegionsInfoTree regionsInfoTrees = srRegionsInfoService.getTreeByIndexCode(regionIndexCode);
        return null;
    }
}
