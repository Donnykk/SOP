package com.ctsi.ssdc.project.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ctsi.ssdc.annocation.AutoId;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@TableName("sr_teacher_info")
public class SrTeacherInfo {

    @AutoId(primaryKey = "id")
    private Long id;

    private String school;

    @TableField(exist = false)
    private List<String> schoolList;

    private String teacherName;

    private String teacherDesc;

    private String teacherImage;

    private String createBy;

    private Date createTime;

    private String updateBy;

    private Date updateTime;
}
