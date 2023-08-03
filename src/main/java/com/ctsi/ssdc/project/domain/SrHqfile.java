package com.ctsi.ssdc.project.domain;

import java.util.Date;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ctsi.ssdc.annocation.AutoId;
import liquibase.pro.packaged.S;
import lombok.Data;
import java.util.Date;

/**
 * 后勤管理文件信息(SrHqfile)实体类
 *
 * @author makejava
 * @since 2023-08-01 15:09:36
 */
@ApiModel("后勤管理文件信息")
@Data
@TableName(value = "sr_hqfile") //指定表名
public class SrHqfile {
    private static final long serialVersionUID = 289782865068947909L;
        
    /**
    * id
    */
    @AutoId(primaryKey = "id")//默认使用自增
    private Long id;
    
    
    /**
    * 文件名称
    */
    @ApiModelProperty("文件名称")
    private String fileName;
    
    
    /**
    * 使用的模版id
    */
    @ApiModelProperty("使用的模版id")
    private Long moduleId;

    @TableField(exist = false)
    @ApiModelProperty("模版名称")
    private String moduleName;
    
    
    /**
    * 文件内容
    */
    @ApiModelProperty("文件内容")
    private String fileContent;
    
    
    /**
    * 创建时间
    */
    @ApiModelProperty("创建时间")
    private Date createTime;
    
    
    /**
    * 创建人
    */
    @ApiModelProperty("创建人")
    private String createBy;
    


}