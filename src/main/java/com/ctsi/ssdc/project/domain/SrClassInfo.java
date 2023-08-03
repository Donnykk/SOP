package com.ctsi.ssdc.project.domain;



import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ctsi.ssdc.annocation.AutoId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.Date;

@Data
@TableName("sr_class_info")
@ApiModel(description = "班级信息")
public class SrClassInfo {

    @TableId(value = "id",type = IdType.AUTO)
    @AutoId(primaryKey = "id")
    private Long id;

    @ApiModelProperty(value = "年级 id")
    private Long gradeId;

    @ApiModelProperty(value = "班级名称")
    private String className;

    private String remark;

    private Date createTime;

    private String createBy;

    private Date updateTime;

    private String updateBy;

    @TableField(exist = false)
    private String gradeName;

    @TableField(exist = false)
    private String school;

}
