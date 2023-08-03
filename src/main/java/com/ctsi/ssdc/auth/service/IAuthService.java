package com.ctsi.ssdc.auth.service;

import com.alibaba.fastjson.JSONObject;
import com.ctsi.ssdc.auth.sign.HttpTool;
import com.ctsi.ssdc.model.AjaxResult;

import java.io.IOException;

public interface IAuthService {


    HttpTool httpTool = new HttpTool();

    /**
     * 根据票据获取用户信息
     * @param ticket
     * @return
     */
    JSONObject getUserInfoByTicket(String ticket) throws Exception;

    /**
     * 登出
     */
    void logoutTicket(String ticket);

    /**
     * 上传菜单
     */
    JSONObject uploadsMenus() throws Exception;

    /**
     * 获取菜单
     * @param ticket
     * @return
     * @throws Exception
     */
    JSONObject getMenu(String ticket) throws Exception;
}
