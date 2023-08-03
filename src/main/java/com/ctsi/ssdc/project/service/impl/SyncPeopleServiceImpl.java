package com.ctsi.ssdc.project.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.ctsi.ssdc.admin.domain.CscpUser;
import com.ctsi.ssdc.admin.domain.CscpUserDetail;
import com.ctsi.ssdc.admin.domain.dto.CscpUserDTO;
import com.ctsi.ssdc.admin.domain.dto.CscpUserDetailDTO;
import com.ctsi.ssdc.admin.repository.CscpUserDetailRepository;
import com.ctsi.ssdc.admin.repository.CscpUserRepository;
import com.ctsi.ssdc.admin.service.CscpUserDetailService;
import com.ctsi.ssdc.admin.service.CscpUserService;
import com.ctsi.ssdc.admin.service.mapper.CscpUserMapper;
import com.ctsi.ssdc.project.domain.SyncPeopleData;
import com.ctsi.ssdc.project.service.SyncPeopleService;
import liquibase.pro.packaged.C;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class SyncPeopleServiceImpl implements SyncPeopleService {


    @Resource
    private CscpUserRepository cscpUserRepository;
    @Resource
    private CscpUserDetailRepository cscpUserDetailRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public boolean synycPeople(String data) {
        try {
            List<SyncPeopleData> syncPeopleDataList = JSONObject.parseArray(data, SyncPeopleData.class);
            List<CscpUser> cscpUsers = new ArrayList<>();
            List<CscpUserDetail> details = new ArrayList<>();
            //将人员信息同步到人员表中
            for (SyncPeopleData peopleData : syncPeopleDataList) {
                CscpUser cscpUser = convertCscpUser(peopleData);
                CscpUserDetail detail = covertCscpUserDetail(peopleData);
                cscpUserRepository.deleteByPrimaryKey(cscpUser.getId());
                cscpUserRepository.insert(cscpUser);
                CscpUserDetailDTO cscpUserDetailDTO = cscpUserDetailRepository.queryByUserId(cscpUser.getId());
                if(null!=cscpUserDetailDTO) {
                    cscpUserDetailRepository.deleteByPrimaryKey(cscpUserDetailDTO.getId());

                }
                cscpUserDetailRepository.insert(detail);

            }


            return true;
        }catch (Exception e)
        {
            e.printStackTrace();
            log.error(e.getMessage());
            return false;
        }
    }


    /**
     * 转化成数字孪生用户信息
     * @param peopleData
     * @return
     */
    private CscpUser convertCscpUser(SyncPeopleData peopleData)
    {
        CscpUser cscpUser = new CscpUser();
        cscpUser.setId(Long.valueOf(peopleData.getUser_id()));
        cscpUser.setUsername(peopleData.getUser_name());
        cscpUser.setPassword(passwordEncoder.encode("szls@srzx"));
        return cscpUser;
    }


    /**
     * 转化数字孪生用户详情信息
     * @param peopleData
     * @return
     */
    private CscpUserDetail covertCscpUserDetail(SyncPeopleData peopleData)
    {
        CscpUserDetail detail = new CscpUserDetail();
        detail.setUserId(Long.valueOf(peopleData.getUser_id()));
        detail.setFamilyName(peopleData.getUser_name());
        detail.setName(peopleData.getNick_name());
        detail.setMobile(peopleData.getPhonenumber());
        detail.setEmail(peopleData.getEmail());
        detail.setDeptId(1510104311760801794L);
        detail.setTenantId(1478649882954985474L);
        return detail;
    }
}
