package com.ctsi.ssdc.common.handler;

import com.gitee.sop.servercommon.exception.ExceptionHolder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
@Slf4j
public class StoryGlobalExceptionHandler {


    /**
     * 捕获手动抛出的异常
     *
     * @param request   request
     * @param response  response
     * @param exception 异常信息
     * @return 返回提示信息
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Object exceptionHandler(HttpServletRequest request, HttpServletResponse response, Exception exception) {
        // 在返回前加这一句
        ExceptionHolder.hold(request, response, exception);
        // 下面可以实现自己的全局异常处理
        return new ErrorResult(500, exception.getMessage());
    }

    @Data
    @AllArgsConstructor
    public static class ErrorResult {
        private int code;
        private String msg;
    }
}