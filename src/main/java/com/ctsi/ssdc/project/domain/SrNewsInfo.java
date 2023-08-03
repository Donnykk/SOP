package com.ctsi.ssdc.project.domain;

import com.ctsi.ssdc.annocation.AutoId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * 校内新闻
 */
@Data
public class SrNewsInfo {

    @AutoId(primaryKey = "id")
    private Long id;

    private String school;
    /**
     * 新闻标题
     */
    private String newsTitle;

    /**
     * 新闻内容
     */
    private String newsContent;

    /**
     * 作者
     */
    private String author;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新人
     */
    private String updateBy;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 发布时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date releaseTime;
}
