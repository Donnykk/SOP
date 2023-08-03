package com.ctsi.ssdc.project.domain;

import java.util.Date;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ctsi.ssdc.annocation.AutoId;
import lombok.Data;
import java.util.Date;

/**
 * 后勤管理-模版(SrHqmb)实体类
 *
 * @author makejava
 * @since 2023-08-01 11:37:04
 */
@ApiModel("后勤管理-模版")
@Data
@TableName(value = "sr_hqmb") //指定表名
public class SrHqmb {
    private static final long serialVersionUID = -41793299376741649L;
        
    /**
    * id
    */
    @AutoId(primaryKey = "id")//默认使用自增
    private Long id;
    
    
    /**
    * 模版名称
    */
    @ApiModelProperty("模版名称")
    private String moduleName;
    
    
    /**
    * 是否启用
    */
    @ApiModelProperty("是否启用")
    private Integer status;
    
    
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
    
    
    /**
    * 模版表头内容，多个逗号隔开
    */
    @ApiModelProperty("模版表头内容，多个逗号隔开")
    private String moduleContent;
    


}