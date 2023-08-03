package com.ctsi.ssdc.project.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ctsi.ssdc.annocation.AutoId;
import com.ctsi.ssdc.poi.excel.annotation.Excel;
import liquibase.pro.packaged.S;
import lombok.Data;

import java.util.Date;

@Data
@TableName("sr_responsible_info")
public class SrResponsibleInfo {

    /**
     * id
     */
    @AutoId(primaryKey = "id")
    private Long id;

    @Excel(name = "校区")
    private String school;

    @Excel(name = "告警类型")
    private String warningType;

    @Excel(name = "负责人")
    private String responsible;

    private String createBy;

    private Date createTime;

    private String updateBy;

    private Date updateTime;
}
