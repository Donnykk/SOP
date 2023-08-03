package com.ctsi.ssdc.auth.conf;

import com.ctsi.ssdc.auth.bean.AuthBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IAuthConfig {

    @Value("${spring.profiles.active}")
    private String active;

    @Bean
    public AuthBean authConfig(){
        AuthBean authConfig = new AuthBean();

        authConfig.setAppId("7oJVxT8E");
        authConfig.setAppSecret("6c1d596743d3d5446b2302faf2ab3737");
        authConfig.setRsaPublic("MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC7hQRIwL2EE+RHwhCfPAfSUUxuP6JAyZG+Tx6vouYGxR4Y+LW2zuo7Ry+EigeaZp7IRPz5ivec0B7mwap2SOFCCdrJx5JgSxzITAJIlZnZvgNK9GjodWT5VuhtqJmBIHzE69Belkt7iDjJ/ueh0f5TvTwPMihkSJsPAQXauTQ4WQIDAQAB");
        authConfig.setRsaPrivate("MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBALuFBEjAvYQT5EfCEJ88B9JRTG4/okDJkb5PHq+i5gbFHhj4tbbO6jtHL4SKB5pmnshE/PmK95zQHubBqnZI4UIJ2snHkmBLHMhMAkiVmdm+A0r0aOh1ZPlW6G2omYEgfMTr0F6WS3uIOMn+56HR/lO9PA8yKGRImw8BBdq5NDhZAgMBAAECgYEAs4LI8SShyBiKxi7/XpBHJI1zT29XUhIklTyEsbqN3hlBkMI2ooQ+MvUYhhbk5GgsxKdWTUU9eT669kibeJSYHc3E6UI1TG96NnGzOgGU3QuvCAbcKWmUcYpfCVtVbMyt+x2WoBY5p62ofX0ANfh26kLI02KSaC4z8jm1j3qBVAkCQQDoGI+rqKRwk5jo2mOtc+zl/7otOoJumwT/RSeTlVZ/fJXKfYy/bvlhvLDzlRAO+yneIw2xOp7rDoM2w1bvJpAjAkEAztUn+Iyp/Hl5xC6xg6J5gNSZwWkjxvaAGEYmi/mQn6rUcIjUet9bYW4v2yhyZ51UHy5UZYZ3iD8Wx7j/lxffUwJBALeKBMNov3FPJxKs8TpPfD1990qhBFhXOqJrm5p39EkoyoOwuUcBiTCjOQPHE0XllPxXmBJZGyci+NyrqOnCAyUCQHAJbrloGLw2YQsv7+BKJl7uysrF1Sd1ZzW+6ipwaTLUKnc65z4Xtzwzn6CrbC1MP8EUK4tDJ4SaCBDv2SNTPxkCQQCgNxn2GG5CMUFm8GsKBUQhGVeWtFsZw6xV/ms9uubea8VFcKdSIto1S0iVexe/mXvu3qy/gYIWawXvUzVe3koy");

//        if(active.contains("pro")){
            /**
             * 生产
             */
            authConfig.setGetWayIp("http://114.216.5.39:9005/sopgateway/" );
            authConfig.setLogoutIp("114.216.5.39");
            authConfig.setLogoutPort("9005");
//        } else {
//
//            /**
//             * 开发，测试
//             */
//            authConfig.setIp("127.0.0.1");
//            authConfig.setPort("9002");
//            authConfig.setLogoutIp("127.0.0.1");
//            authConfig.setLogoutPort("9090");
//        }
        return authConfig;
    }

}
