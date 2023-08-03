package com.ctsi.ssdc.project.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ctsi.ssdc.annocation.AutoId;
import lombok.Data;

import java.util.Date;

@Data
@TableName("sr_cameras_info")
public class SrCamerasInfo {

    @AutoId(primaryKey = "id")
    private Long id;

    /**
     * 海拔
     */
    private String altitude;

    private String cameraIndexCode;

    private String cameraName;

    private String cameraType;

    private String cameraTypeName;

    private String capabilitySet;

    private String capabilitySetName;

    private String intelligentSet;

    private String intelligentSetName;

    private String channelNo;

    private String channelType;

    private String channelTypeName;

    private Date createTime;

    private String encodeDevIndexCode;

    private String encodeDevResourceType;

    private String encodeDevResourceTypeName;

    private String gbIndexCode;

    private String installLocation;

    private String keyBoardCode;

    private String latitude;

    private String longitude;

    private String pixel;

    private String ptz;

    private String ptzName;

    private String ptzController;

    private String ptzControllerName;

    private String recordLocation;

    private String recordLocationName;

    private String regionIndexCode;

    /**
     * 区域名称
     */
    @TableField(exist = false)
    private String regionIndexName;

    private String status;

    private String statusName;

    private String transType;

    private String transTypeName;

    private String treatyType;

    private String treatyTypeName;

    private String viewshed;

    private Date updateTime;




}
