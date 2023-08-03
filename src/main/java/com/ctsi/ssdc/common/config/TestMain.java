package com.ctsi.ssdc.common.config;

import com.ctsi.ssdc.util.JasyptUtil;

public class TestMain {

    public static void main(String[] args) {
        String pwdEnc= "udp6";

        String encPwd = JasyptUtil.encyptPwd(pwdEnc, "1qaz@WSX");
        String decPwd = JasyptUtil.decyptPwd(pwdEnc,encPwd);
        System.out.println(encPwd);
        System.out.println(decPwd);
    }
}
