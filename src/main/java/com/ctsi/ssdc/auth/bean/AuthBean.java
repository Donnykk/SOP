package com.ctsi.ssdc.auth.bean;

import lombok.Data;

@Data
public class AuthBean {

    private String appId;

    private String appSecret;

    private String rsaPublic;

    private String rsaPrivate;

    private String getWayIp;

    private String logoutIp;

    private String logoutPort;

    public String buildLogoutHttp(){
        return "http://" + logoutIp + ":" + logoutPort;
    }

}
