package com.ctsi.ssdc.project.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ctsi.ssdc.annocation.AutoId;
import com.ctsi.ssdc.poi.excel.annotation.Excel;
import lombok.Data;

import java.util.Date;

@Data
@TableName("sr_device_info")
public class SrDeviceInfo {

    @AutoId(primaryKey = "id")
    private Long id;

    /**
     * 校区
     */
    @Excel( name="校区")
    private String school;

    /**
     * 设备类型
     */
    @Excel(name="设备类型")
    private String deviceType;

    /**
     * 设备编码
     */
    @Excel(name="设备编码")
    private String deviceCode;

    /**
     * 设备名称
     */
    @Excel(name="设备名称")
    private String deviceName;

    /**
     * 经度
     */
    @Excel(name="经度")
    private String longitude;

    /**
     * 纬度
     */
    @Excel(name="纬度")
    private String latitude;

    /**
     * 设备状态
     */
    @Excel(name="设备状态",readConverterExp = "1=在线,2=离线")
    private Integer status;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改人
     */
    private String updateBy;

    /**
     * 修改时间
     */
    private Date updateTime;
}
