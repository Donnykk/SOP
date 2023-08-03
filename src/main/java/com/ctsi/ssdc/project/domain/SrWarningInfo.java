package com.ctsi.ssdc.project.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ctsi.ssdc.annocation.AutoId;

import com.ctsi.ssdc.poi.excel.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
@TableName("sr_warning_info")
public class SrWarningInfo {

    @AutoId(primaryKey = "id")
    private Long id;


    @TableField(exist = false)
    private String currentPeople;

    @TableField(exist = false)
    private Date currentTime;

    /**
     * 校区
     */
    @Excel(name="校区")
    private String school;

    /**
     * 告警类型
     */
    @Excel(name="告警类型")
    private String warningType;

    /**
     * 告警信息
     */
    @Excel(name="告警信息")
    private String warningInfo;

    /**
     * 告警时间
     */
    @Excel(name="告警时间",dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date warningTime;

    @Excel(name="状态",readConverterExp = "1=待处理,2=处理中,3=已处理")
    private Integer status;


    /**
     * 负责人
     */
    @TableField(exist = false)
    @Excel(name="责任人")
    private String responsible;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 确认人
     */
    private String confirmer;

    /**
     * 确认时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date confirmTime;

    /**
     * 确认备注
     */
    private String confirmRemark;

    /**
     * 处理人
     */
    private String handler;

    /**
     * 处理时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date handleTime;

    /**
     * 处理备注
     */
    private String handleRemark;

    /**
     * 处理图片
     */
    private String handleImage;

    /**
     * 抓拍图片
     */
    private String image;

}
