package com.ctsi.ssdc.auth.service;

import com.ctsi.ssdc.admin.domain.dto.CscpMenusDTO;
import com.ctsi.ssdc.controller.UserJwtController;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ILoginService {

    /**
     * 票据登录
     * @param ticket
     * @return
     */
    ResponseEntity<UserJwtController.JwtToken> loginByTicket(String ticket) throws Exception;

    /**
     * 获取菜单权限
     * @return
     */
    List<CscpMenusDTO> getMenus(String ticket) throws Exception;
}
