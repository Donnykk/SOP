package com.ctsi.ssdc.project.service;

import com.ctsi.ssdc.project.domain.GradeReport;
import com.ctsi.ssdc.project.domain.SrGradeInfo;
import com.ctsi.ssdc.project.domain.SrGradeInfoExample;
import com.ctsi.ssdc.service.StrengthenBaseService;

import java.util.List;

/**
 * Service Interface for managing SrGradeInfo.
 *
 * @author hx
 * @date 2023-06-09 10:18:29
 *
 */

public interface SrGradeInfoService
	extends StrengthenBaseService<SrGradeInfo,Long , SrGradeInfoExample>{

	/**
	* 批量删除
	* @param ids
	*/
	void deleteByIds(Long[] ids);

	void deleteById(Long id);

	public List<SrGradeInfo> getGradeInfoBySchool(String school);

	/**
	 * 查看是否存在
	 * @param school
	 * @param name
	 * @return
	 */
	public SrGradeInfo getExistGradeInfo(String school,String name);


	/**
	 * 获取年级报表数据
	 * @param school
	 * @return
	 */
	public List<GradeReport> getGradeReport(String school);


}
