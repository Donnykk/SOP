package com.ctsi.ssdc.auth.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ctsi.ssdc.admin.domain.dto.CscpMenusDTO;
import com.ctsi.ssdc.admin.service.CscpMenusService;
import com.ctsi.ssdc.auth.bean.AuthBean;
import com.ctsi.ssdc.auth.service.IAuthService;
import com.ctsi.ssdc.auth.bean.vo.AppStoreMenu;
import com.ctsi.ssdc.auth.sign.AlipaySignature;
import com.ctsi.ssdc.auth.sign.HttpTool;
import com.ctsi.ssdc.common.utils.DateUtils;
import com.ctsi.ssdc.model.AjaxResult;
import com.ctsi.ssdc.thirdauth.tianyi.utils.cipher.RSAUtils;
import liquibase.pro.packaged.A;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AuthServiceImpl implements IAuthService {

    @Autowired
    RestTemplate restTemplate;
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    AuthBean auth;

    @Autowired
    CscpMenusService cscpMenusService;

    private JSONObject querySop(String method, String responeKey, Map<String, String> bizContent, HttpTool.HTTPMethod hTTPMethod) throws Exception {
        Map<String, String> params = new HashMap<String, String>();
        params.put("app_id", auth.getAppId());
        params.put("method", method);
        params.put("format", "json");
        params.put("charset", "utf-8");
        params.put("sign_type", "RSA2");
        params.put("timestamp", DateUtils.getTime());
        params.put("version", "1.0");

        params.put("biz_content", JSON.toJSONString(bizContent));
        String content = AlipaySignature.getSignContent(params);
        String sign = AlipaySignature.rsa256Sign(content, auth.getRsaPrivate(), "utf-8");
        params.put("sign", sign);

        String s = httpTool.request(auth.getGetWayIp(), params, Collections.emptyMap(), hTTPMethod);
        log.info("sop 接口返回：{}",s);
        JSONObject jsonObject = JSONObject.parseObject(s);

        if(jsonObject.containsKey(responeKey)) return jsonObject.getJSONObject(responeKey);

        throw new RuntimeException("请求失败！");
    }

//    private JSONObject postJsonSop(String method, String responeKey, Map<String, String> bizContent, HttpTool.HTTPMethod hTTPMethod) throws Exception {
//        Map<String, String> params = new HashMap<String, String>();
//        params.put("app_id", auth.getAppId());
//        params.put("method", method);
//        params.put("format", "json");
//        params.put("charset", "utf-8");
//        params.put("sign_type", "RSA2");
//        params.put("timestamp", DateUtils.getTime());
//        params.put("version", "1.0");
//
//        params.put("biz_content", JSON.toJSONString(bizContent));
//        String content = AlipaySignature.getSignContent(params);
//        String sign = AlipaySignature.rsa256Sign(content, auth.getRsaPrivate(), "utf-8");
//        params.put("sign", sign);
//
//        String s = httpTool.requestJson(auth.getGetWayIp(), JSON.toJSONString(params), Collections.emptyMap());
//        log.info("sop postJson 接口返回：{}",s);
//        JSONObject jsonObject = JSONObject.parseObject(s);
//
//        if(jsonObject.containsKey(responeKey)) return jsonObject.getJSONObject(responeKey);
//
//        throw new RuntimeException("请求失败！");
//    }

//    @PostConstruct
//    public void aa() throws Exception {
//
//        /** 封装对象 */
//        List<AppStoreMenu> data = new ArrayList<AppStoreMenu>(){{
//            AppStoreMenu appStoreMenu = new AppStoreMenu();
//            appStoreMenu.setMenuParentId("0");
//            appStoreMenu.setMenuName("123test");
//            add(appStoreMenu);
//        }};
//
//        JSONObject jsonObject = querySop("openapi.auth.uploadMenus", "openapi_auth_uploadMenus_response", new HashMap<String, String>() {{
//            put("appId", auth.getAppId());
//            put("data", RSAUtils.encryptByPublicKeyForLongStr(JSONObject.toJSONString(data), auth.getRsaPublic()));
//        }}, HttpTool.HTTPMethod.POST);
//
//        System.out.println(jsonObject);
//        System.out.println(jsonObject);
//    }

    @Override
    public JSONObject getUserInfoByTicket(String ticket) throws Exception {

        return querySop("openapi.auth.geninfo", "openapi_auth_geninfo_response", new HashMap<String, String>(){{
            put("ticket", ticket);
            put("appId", auth.getAppId());
        }}, HttpTool.HTTPMethod.GET);
    }

    @Override
    public JSONObject uploadsMenus() throws Exception {
        /** 获取所有的菜单 */
        List<CscpMenusDTO> allMenusByCondition = this.cscpMenusService.findAllMenusByCondition(new CscpMenusDTO());

        /** 封装对象 */
        List<AppStoreMenu> data = allMenusByCondition.stream().map(AppStoreMenu::new).collect(Collectors.toList());

        return querySop("openapi.auth.uploadMenus", "openapi_auth_uploadMenus_response", new HashMap<String, String>(){{
            put("appId", auth.getAppId());
            put("data", RSAUtils.encryptByPublicKeyForLongStr(JSONObject.toJSONString(data), auth.getRsaPublic()));
        }}, HttpTool.HTTPMethod.POST);
    }

    @Override
    public JSONObject getMenu(String ticket) throws Exception {

        return querySop("openapi.auth.getMenus", "openapi_auth_getMenus_response", new HashMap<String, String>(){{
            put("ticket", ticket);
            put("appId", auth.getAppId());
        }}, HttpTool.HTTPMethod.GET);
    }

    @Override
    public void logoutTicket(String ticket) {
        StringBuilder uri = new StringBuilder(auth.buildLogoutHttp() + "/usercenter/auth/logout");
        uri.append("?");
        uri.append("ticket=" + ticket);
        restTemplate.getForObject( uri.toString(), Object.class);
    }

}
