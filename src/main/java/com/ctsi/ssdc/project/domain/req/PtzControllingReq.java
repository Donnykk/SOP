package com.ctsi.ssdc.project.domain.req;

import lombok.Data;

/**
 * 监控云台控制请求
 */
@Data
public class PtzControllingReq {

    /**
     * 监控 id
     */
    private String cameraIndexCode;

    /**
     * 0：开始 ，1：结束
     */
    private Integer action;

    /**
     *
     */
    private String command;

    /**
     *
     */
    private Integer speed;


}
