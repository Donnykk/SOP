package com.ctsi.ssdc.project.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ctsi.ssdc.project.domain.SrStudentInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SrStudentInfoMapper extends BaseMapper<SrStudentInfo> {

    public List<SrStudentInfo> getStuPage(SrStudentInfo srStudentInfo);

    public SrStudentInfo getExistStudent(@Param("cardNo") String cardNo, @Param("stuId") String stuId);

    public Integer getStudentNum(String school);
}
