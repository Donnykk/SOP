package com.ctsi.ssdc.project.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ctsi.ssdc.annocation.AutoId;
import lombok.Data;

import java.util.Date;

@Data
@TableName("sr_student_info")
public class SrStudentInfo {

    @AutoId(primaryKey = "id")
    private Long id;

    /**
     * 班级 id
     */
    private Long classId;

    /**
     * 学生姓名
     */
    private String stuName;

    /**
     * 学生性别
     */
    private Integer sex;

    /**
     * 身份证号
     */
    private String cardNo;

    /**
     * 学号
     */
    private String stuId;

    /**
     * 选科
     */
    private String subject;

    /**
     * 状态
     */
    private String status;

    /**
     * 备注
     */
    private String remark;

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

    /**
     * 班级名称
     */
    @TableField(exist = false)
    private String className;

    /**
     * 年级名称
     */
    @TableField(exist = false)
    private String gradeName;

    /**
     * 校区
     */
    @TableField(exist = false)
    private String school;

    @TableField(exist = false)
    private Long gradeId;
}
