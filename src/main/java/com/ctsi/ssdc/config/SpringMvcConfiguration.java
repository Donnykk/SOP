package com.ctsi.ssdc.config;

import com.ctsi.ssdc.captcha.interceptor.CaptchaHandlerEnhanceInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class SpringMvcConfiguration implements WebMvcConfigurer {

    @Bean
    public CaptchaHandlerEnhanceInterceptor captchaHandlerInterceptor() {
        return new CaptchaHandlerEnhanceInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        WebMvcConfigurer.super.addInterceptors(registry);
        registry.addInterceptor(captchaHandlerInterceptor());

    }

    @Bean("taskExecutor")
    @Primary
    public ThreadPoolTaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setWaitForTasksToCompleteOnShutdown(true);
        taskExecutor.setAwaitTerminationSeconds(60);
        return taskExecutor;
    }
}
