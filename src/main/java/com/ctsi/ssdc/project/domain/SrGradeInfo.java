package com.ctsi.ssdc.project.domain;

import com.ctsi.ssdc.annocation.AutoId;
import com.ctsi.ssdc.util.LongtoStringSerialize;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * SrGradeInfo 实体类
 *
 * @author hx
 * @date 2023-06-09 10:18:29
 */

@ApiModel(description = "SrGradeInfo")
public class SrGradeInfo implements Serializable {
    /**
     * Id
     */
    @AutoId(primaryKey = "id")
    @JsonSerialize(using = LongtoStringSerialize.class)

    private Long id;
    /**
     * 年级名称
     */

    private String name;
    /**
     * 校区名称
     */

    private String school;
    /**
     * 备注
     */

    private String remark;
    /**
     * 创建人 id
     */
    private String createBy;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")

    private ZonedDateTime createTime;
    /**
     * 修改人
     */
    private String updateBy;
    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")

    private ZonedDateTime updateTime;


    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getSchool() {
        return school;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRemark() {
        return remark;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateTime(ZonedDateTime createTime) {
        this.createTime = createTime;
    }

    public ZonedDateTime getCreateTime() {
        return createTime;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateTime(ZonedDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public ZonedDateTime getUpdateTime() {
        return updateTime;
    }


    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        SrGradeInfo other = (SrGradeInfo) that;
        return (this.getId() == null ? this.getId() != null : this.getId().equals(other.getId()))
                && (this.getName() == null ? this.getName() != null : this.getName().equals(other.getName()))
                && (this.getSchool() == null ? this.getSchool() != null : this.getSchool().equals(other.getSchool()))
                && (this.getRemark() == null ? this.getRemark() != null : this.getRemark().equals(other.getRemark()))
                && (this.getCreateBy() == null ? this.getCreateBy() != null : this.getCreateBy().equals(other.getCreateBy()))
                && (this.getCreateTime() == null ? this.getCreateTime() != null : this.getCreateTime().equals(other.getCreateTime()))
                && (this.getUpdateBy() == null ? this.getUpdateBy() != null : this.getUpdateBy().equals(other.getUpdateBy()))
                && (this.getUpdateTime() == null ? this.getUpdateTime() != null : this.getUpdateTime().equals(other.getUpdateTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getSchool() == null) ? 0 : getSchool().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        result = prime * result + ((getCreateBy() == null) ? 0 : getCreateBy().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateBy() == null) ? 0 : getUpdateBy().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        return result;
    }
}
