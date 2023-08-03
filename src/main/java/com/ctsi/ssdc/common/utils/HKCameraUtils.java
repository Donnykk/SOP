package com.ctsi.ssdc.common.utils;

import com.alibaba.fastjson.JSONObject;
import com.hikvision.artemis.sdk.ArtemisHttpUtil;
import com.hikvision.artemis.sdk.config.ArtemisConfig;

import java.util.HashMap;
import java.util.Map;

/**
 * 海康监控平台工具类
 */
public class HKCameraUtils {

    public static String getHaikangData(String intefacePath,JSONObject jsonBody)
    {
        /**
         * STEP1：设置平台参数，根据实际情况,设置host appkey appsecret 三个参数.
         */
        ArtemisConfig.host = "172.30.55.250:8443"; // 平台的ip端口
        ArtemisConfig.appKey = "20982116";  // 密钥appkey
        ArtemisConfig.appSecret = "L02YfD0QF3dgPTEmjWuW";// 密钥appSecret

        /**
         * STEP2：设置OpenAPI接口的上下文
         */
        final String ARTEMIS_PATH = "/artemis";

        /**
         * STEP3：设置接口的URI地址
         */
        final String previewURLsApi = ARTEMIS_PATH + intefacePath;
        Map<String, String> path = new HashMap<String, String>(2) {
            {
                put("https://", previewURLsApi);//根据现场环境部署确认是http还是https
            }
        };

        /**
         * STEP4：设置参数提交方式
         */
        String contentType = "application/json";

        /**
         * STEP5：组装请求参数
         */
//        JSONObject jsonBody = new JSONObject();
//        jsonBody.put("pageNo", 2);
//        jsonBody.put("pageSize", 10);
        String body = jsonBody.toJSONString();
        /**
         * STEP6：调用接口
         */
        String result = ArtemisHttpUtil.doPostStringArtemis(path, body, null, null, contentType , null);// post请求application/json类型参数
        return result;
    }




}
