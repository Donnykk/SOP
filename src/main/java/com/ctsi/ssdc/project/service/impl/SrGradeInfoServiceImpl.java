package com.ctsi.ssdc.project.service.impl;

import com.ctsi.ssdc.project.domain.GradeReport;
import com.ctsi.ssdc.project.domain.SrGradeInfo;
import com.ctsi.ssdc.project.domain.SrGradeInfoExample;
import com.ctsi.ssdc.project.repository.SrGradeInfoRepository;
import com.ctsi.ssdc.project.service.SrGradeInfoService;
import com.ctsi.ssdc.service.impl.StrengthenBaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Service Implementation for managing SrGradeInfo.
 *
 * @author hx
 * @date 2023-06-09 10:18:29
 *
 */

@Service
public class SrGradeInfoServiceImpl
	extends StrengthenBaseServiceImpl<SrGradeInfoRepository, SrGradeInfo, Long, SrGradeInfoExample>
	implements SrGradeInfoService {

    @Autowired
    private SrGradeInfoRepository srGradeInfoRepository;


    /**
     * 批量删除
     * @param ids
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteByIds(Long[] ids) {
        List<Long> delList = new ArrayList<>(Arrays.asList(ids));
        // 批量删除
        srGradeInfoRepository.deleteByIds(delList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(Long id) {
        srGradeInfoRepository.deleteByPrimaryKey(id);
    }

    @Override
    public List<SrGradeInfo> getGradeInfoBySchool(String school) {
        return srGradeInfoRepository.getGradeBySchool(school);
    }

    @Override
    public SrGradeInfo getExistGradeInfo(String school, String name) {
        return srGradeInfoRepository.getExistGrade(school,name);
    }

    @Override
    public List<GradeReport> getGradeReport(String school) {
        return srGradeInfoRepository.getGradeRepport(school);
    }


}
