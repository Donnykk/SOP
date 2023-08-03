package com.ctsi.ssdc.auth.controller;

import com.ctsi.ssdc.admin.domain.dto.CscpMenusDTO;
import com.ctsi.ssdc.auth.service.IAuthService;
import com.ctsi.ssdc.auth.service.ILoginService;
import com.ctsi.ssdc.controller.UserJwtController;
import com.ctsi.ssdc.model.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AuthLoginController {

    @Autowired
    ILoginService loginService;
    @Autowired
    IAuthService authService;

    /**
     * 登录方法
     */
    @GetMapping("/api/authLogin/{ticket}")
    public ResponseEntity<UserJwtController.JwtToken> login(@PathVariable String ticket) throws Exception {
        return loginService.loginByTicket(ticket);
    }

    /**
     * 获取菜单
     */
    @GetMapping("/api/getMenus")
    public List<CscpMenusDTO> getMenus(@RequestParam(required = true) String ticket) throws Exception {
        return loginService.getMenus(ticket);
    }


    /**
     * 同步菜单
     * @return
     */
    @GetMapping("/api/uploadMenus")
    public AjaxResult uploadMenus()
    {
        try {
            return AjaxResult.success(authService.uploadsMenus());
        }catch (Exception e)
        {
            e.printStackTrace();
            return AjaxResult.error("上传菜单出错");
        }

    }

    /**
     * 退出
     */
    @GetMapping("/api/authLogout/{ticket}")
    public AjaxResult authLogout(@PathVariable String ticket) throws Exception {
        try {
            authService.logoutTicket(ticket);
        } catch (Exception e){
            e.printStackTrace();
        }
        return AjaxResult.success();
    }
}
