package com.ctsi.ssdc.project.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ctsi.ssdc.annocation.AutoId;
import liquibase.pro.packaged.S;
import lombok.Data;

import java.util.List;

@Data
@TableName("sr_regions_info")
public class SrRegionsInfo {

    @AutoId(primaryKey = "id")
    private Long id;

    private String indexCode;

    private String name;

    private String parentIndexCode;

    private String treeCode;

    @TableField(exist = false)
    private List<SrCamerasVo> srCameras;

}
