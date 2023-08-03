package com.ctsi.ssdc.auth.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.ctsi.ssdc.admin.domain.CscpTenant;
import com.ctsi.ssdc.admin.domain.dto.CscpMenusDTO;
import com.ctsi.ssdc.admin.domain.dto.CscpUserDetailDTO;
import com.ctsi.ssdc.admin.service.CscpMenusService;
import com.ctsi.ssdc.admin.service.CscpTenantService;
import com.ctsi.ssdc.admin.service.UserService;
import com.ctsi.ssdc.auth.bean.AuthBean;
import com.ctsi.ssdc.auth.service.IAuthService;
import com.ctsi.ssdc.auth.service.ILoginService;
import com.ctsi.ssdc.auth.bean.vo.AppStoreMenu;
import com.ctsi.ssdc.config.CtsiProperties;
import com.ctsi.ssdc.controller.UserJwtController;
import com.ctsi.ssdc.exception.BadRequestAlertException;
import com.ctsi.ssdc.model.AjaxResult;
import com.ctsi.ssdc.security.CscpHxUserDetail;
import com.ctsi.ssdc.security.SecurityHxUtils;
import com.ctsi.ssdc.thirdauth.tianyi.utils.cipher.RSAUtils;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class LoginServiceImpl implements ILoginService {

    @Autowired
    AuthBean auth;
    @Autowired
    IAuthService authService;
    @Autowired
    UserService userService;

    @Autowired
    CscpTenantService cscpTenantService;
    @Autowired
    CscpMenusService cscpMenusService;
    @Autowired
    CtsiProperties ctsiProperties;

    private final Base64.Encoder encoder = Base64.getEncoder();

    @Override
    public ResponseEntity<UserJwtController.JwtToken> loginByTicket(String ticket) throws Exception {

        JSONObject respone = authService.getUserInfoByTicket(ticket);
        if(!"200".equals(respone.getString("code"))) {
            throw new BadRequestAlertException("登录失败!", null, null);
        }

        String data = RSAUtils.decryptByPrivateKeyForLongStr(respone.get("data").toString(), auth.getRsaPrivate());
        /** 解密 */
        JSONObject userInfo = JSONObject.parseObject( data );

        String tenantAccount = "default";
        String userName = userInfo.getString("username");

        int ret = this.cscpTenantService.checkTenantAccount(tenantAccount, userName);
        if (ret == 0) {
            throw new BadRequestAlertException("租户不存在", (String)null, (String)null);
        } else if (ret == 2) {
            throw new BadRequestAlertException("租户已过期", (String)null, (String)null);
        } else if (ret == 3) {
            throw new BadRequestAlertException("用户不存在", (String)null, (String)null);
        } else {
            CscpTenant cscpTenant = this.cscpTenantService.selectByTenantAccount(tenantAccount);
            if(cscpTenant == null) throw new RuntimeException("无效的租户！");

            CscpUserDetailDTO cscpUserDetailDTO = this.userService.finUserByUsernameAndTenantId(cscpTenant.getId(), userName).get();
            if(cscpUserDetailDTO == null) new UsernameNotFoundException("User  " + userName + " was not found");

            ArrayList<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>() {{
                getMenus(ticket).forEach((menu) -> {
                    if (StringUtils.isNotEmpty(menu.getPermissionCode())) {
                        add(new SimpleGrantedAuthority(menu.getPermissionCode()));
                    }
                });
            }};

            CscpHxUserDetail cscpHxUserDetail = new CscpHxUserDetail(cscpUserDetailDTO.getTenantId(),
                    cscpTenant.getTenantAccount(),
                    cscpUserDetailDTO.getUserId(),
                    cscpUserDetailDTO.getUsername(),
                    cscpUserDetailDTO.getPassword(),
                    grantedAuthorities
            );

            long now = (new Date()).getTime();
            Date validity;
            validity = new Date(now + 1000L * this.ctsiProperties.getSecurity().getAuthentication().getJwt().getTokenValidityInSeconds());

            JwtBuilder builder = Jwts.builder()
                    .setSubject(cscpUserDetailDTO.getUsername())
                    .claim("auth", grantedAuthorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(",")))
                    .claim("rem", 0);

            builder.claim("id", cscpHxUserDetail.getId());
            builder.claim("tenantId", cscpHxUserDetail.getTenantId());
            builder.claim("tenantAccount", cscpHxUserDetail.getTenantAccount());

            String jwt = builder
                    .signWith(SignatureAlgorithm.HS512, encoder.encodeToString(this.ctsiProperties.getSecurity().getAuthentication().getJwt().getSecret().getBytes(StandardCharsets.UTF_8)))
                    .setExpiration(validity)
                    .compact();

            SecurityHxUtils.getOptionalCurrentUserId().map((userId) -> {
                this.userService.updateUserDetailForLogin(String.valueOf(userId));
                return null;
            });

            HttpHeaders httpHeaders = new HttpHeaders();
            String token = "Bearer " + jwt;
            httpHeaders.add("Authorization", token);
            return new ResponseEntity(new UserJwtController.JwtToken(token, 0), httpHeaders, HttpStatus.OK);

        }
    }

    @Override
    public List<CscpMenusDTO> getMenus(String ticket) throws Exception {

        JSONObject respone = authService.getMenu(ticket);

        if("200".equals(respone.getString("code"))){

            String data = RSAUtils.decryptByPrivateKeyForLongStr(respone.get("data").toString(), auth.getRsaPrivate());

            List<AppStoreMenu> datas = JSONObject.parseArray(data, AppStoreMenu.class);

            return new ArrayList<CscpMenusDTO>(){{

                datas.forEach(d -> {
                    CscpMenusDTO vo = new CscpMenusDTO();
                    vo.setId(Long.valueOf(d.getMenuId()));
                    vo.setName(d.getMenuName());
                    vo.setIcon(d.getIcon());
                    vo.setTitle(d.getMenuName());
                    vo.setUrl(d.getPath());
                    vo.setComponent(d.getComponent());
                    vo.setParentId(Long.valueOf(d.getMenuParentId()));
                    switch (d.getMenuType()){
                        case "M":
                            vo.setType("menu1");
                            break;
                        case "C":
                            vo.setType("non-menu");
                            break;
                        case "F":
                            vo.setType("button");
                            break;
                        default:
                            break;
                    }
                    vo.setPermissionCode(d.getPerms());
                    vo.setOrderby(d.getOrderNum());
                    add(vo);
                });

            }};
        }
        return null;
    }
}
