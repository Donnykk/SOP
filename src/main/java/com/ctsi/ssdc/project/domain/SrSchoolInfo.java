package com.ctsi.ssdc.project.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ctsi.ssdc.annocation.AutoId;
import liquibase.pro.packaged.S;
import lombok.Data;

import java.util.Date;

@Data
@TableName("sr_school_info")
public class SrSchoolInfo {
    @TableId(value = "id",type = IdType.AUTO)
    @AutoId(primaryKey = "id")
    private Long id;

    /**
     * 校区
     */
    private String school;

    /**
     * 员工数量
     */
    private Integer staffNum;

    /**
     * 学校简介
     */
    private String schoolDesc;

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
