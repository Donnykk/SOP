package com.gitee.sop.gateway.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;

/**
 * @author tanghc
 */
@Mapper
public interface SystemLockMapper {

    /**
     * 插入唯一值
     * @param content 唯一值
     * @return 1：返回成功，0：插入失败
     */
    @Insert("INSERT IGNORE INTO system_lock(content) VALUES (#{content})")
    @ResultType(int.class)
    int insert(@Param("content") String content);

    @Select("SELECT id FROM system_lock WHERE id=1 FOR UPDATE")
    @ResultType(long.class)
    long lock();
}
