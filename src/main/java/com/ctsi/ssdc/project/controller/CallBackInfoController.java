package com.ctsi.ssdc.project.controller;

import com.alibaba.fastjson.JSONObject;
import com.ctsi.ssdc.auth.bean.AuthBean;
import com.ctsi.ssdc.model.AjaxResult;
import com.ctsi.ssdc.project.domain.SyncPeopleData;
import com.ctsi.ssdc.project.service.SyncPeopleService;
import com.ctsi.ssdc.thirdauth.tianyi.utils.cipher.RSAUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/callback")
public class CallBackInfoController {

    @Autowired
    AuthBean authBean;

    @Autowired
    private SyncPeopleService syncPeopleService;

    /**
     * 更新同步过来的组织架构数据
     * @param req
     * @return
     */
    @PostMapping("/updatePeople")
    public AjaxResult updatePeople(@RequestBody JSONObject req)
    {
        try {
//            String aa = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBALuFBEjAvYQT5EfCEJ88B9JRTG4/okDJkb5PHq+i5gbFHhj4tbbO6jtHL4SKB5pmnshE/PmK95zQHubBqnZI4UIJ2snHkmBLHMhMAkiVmdm+A0r0aOh1ZPlW6G2omYEgfMTr0F6WS3uIOMn+56HR/lO9PA8yKGRImw8BBdq5NDhZAgMBAAECgYEAs4LI8SShyBiKxi7/XpBHJI1zT29XUhIklTyEsbqN3hlBkMI2ooQ+MvUYhhbk5GgsxKdWTUU9eT669kibeJSYHc3E6UI1TG96NnGzOgGU3QuvCAbcKWmUcYpfCVtVbMyt+x2WoBY5p62ofX0ANfh26kLI02KSaC4z8jm1j3qBVAkCQQDoGI+rqKRwk5jo2mOtc+zl/7otOoJumwT/RSeTlVZ/fJXKfYy/bvlhvLDzlRAO+yneIw2xOp7rDoM2w1bvJpAjAkEAztUn+Iyp/Hl5xC6xg6J5gNSZwWkjxvaAGEYmi/mQn6rUcIjUet9bYW4v2yhyZ51UHy5UZYZ3iD8Wx7j/lxffUwJBALeKBMNov3FPJxKs8TpPfD1990qhBFhXOqJrm5p39EkoyoOwuUcBiTCjOQPHE0XllPxXmBJZGyci+NyrqOnCAyUCQHAJbrloGLw2YQsv7+BKJl7uysrF1Sd1ZzW+6ipwaTLUKnc65z4Xtzwzn6CrbC1MP8EUK4tDJ4SaCBDv2SNTPxkCQQCgNxn2GG5CMUFm8GsKBUQhGVeWtFsZw6xV/ms9uubea8VFcKdSIto1S0iVexe/mXvu3qy/gYIWawXvUzVe3koy";

            String privateKey = authBean.getRsaPrivate();
            System.out.println(req.toJSONString());
            String data = RSAUtils.decryptByPrivateKeyForLongStr(req.getString("data"), privateKey);
            if(syncPeopleService.synycPeople(data))
            {
                return AjaxResult.success("同步成功");
            }
            return AjaxResult.error("同步失败");

        }catch (Exception e)
        {
            return AjaxResult.error(e.getMessage());
        }

    }
}
