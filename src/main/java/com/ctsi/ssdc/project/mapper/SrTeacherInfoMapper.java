package com.ctsi.ssdc.project.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ctsi.ssdc.project.domain.SrTeacherInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SrTeacherInfoMapper extends BaseMapper<SrTeacherInfo> {

    public Integer getTeacherNum(String school);
}
