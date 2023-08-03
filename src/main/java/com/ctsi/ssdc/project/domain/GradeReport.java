package com.ctsi.ssdc.project.domain;

import lombok.Data;

/**
 * 年级划分
 */
@Data
public class GradeReport {
    /**
     * 年级名称
     */
    public String gradeName;

    /**
     * 年级数量
     */
    public Integer gradeNum;
}
