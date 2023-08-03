package com.gitee.sop.gatewaycommon.gateway.result;

import com.alibaba.fastjson.JSONObject;
import com.gitee.sop.gatewaycommon.param.ApiParam;
import org.springframework.web.server.ServerWebExchange;

import java.util.Map;

/**
 * 处理业务返回数据
 * @author thc
 */
public interface BizResultHandler {

    /**
    * 处理业务参数
     * @param serviceData 外层service数据
    * @param serviceObj 业务数据
    * @param apiParam apiParam
    * @param request ServerWebExchange
    * @author thc
    * @date 2021/7/7 10:41
    */
    void handle(Map<String, Object> serviceData, JSONObject serviceObj, ApiParam apiParam, ServerWebExchange request);
}
