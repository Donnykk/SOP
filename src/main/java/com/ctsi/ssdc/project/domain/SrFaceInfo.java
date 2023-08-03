package com.ctsi.ssdc.project.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ctsi.ssdc.annocation.AutoId;
import com.ctsi.ssdc.poi.excel.annotation.Excel;
import lombok.Data;

import java.util.Date;

@Data
@TableName("sr_face_info")
public class SrFaceInfo {

    @AutoId(primaryKey = "id")
    private Long id;

    /**
     * 校区
     */
    @Excel(name = "校区")
    private String school;

    @Excel(name = "姓名")
    private String name;

    @Excel(name = "性别",readConverterExp = "1=男,2=女")
    private Integer sex;

    @Excel(name = "身份证号")
    private String cardNo;

    private String image;

    private String createBy;

    private Date createTime;

    private String updateBy;

    private Date updateTime;
}
