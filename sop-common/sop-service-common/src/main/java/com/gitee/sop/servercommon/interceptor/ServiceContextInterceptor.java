package com.gitee.sop.servercommon.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.gitee.sop.servercommon.annotation.Open;
import com.gitee.sop.servercommon.bean.OpenContextImpl;
import com.gitee.sop.servercommon.bean.ServiceContext;
import com.gitee.sop.servercommon.util.OpenUtil;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author tanghc
 */
public class ServiceContextInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        ServiceContext context = ServiceContext.getCurrentContext();
        context.setRequest(request);
        context.setResponse(response);
        this.initOpenContextWithNoParam(context, request, handler);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        ServiceContext.getCurrentContext().unset();
    }

    /**
     * 修复没有参数的情况下无法获取OpenContext
     * @param context ServiceContext
     * @param request HttpServletRequest
     * @param handler HandlerMethod
     */
    private void initOpenContextWithNoParam(ServiceContext context, HttpServletRequest request, Object handler) {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Open open = handlerMethod.getMethodAnnotation(Open.class);
            if (open != null && getArrayLength(handlerMethod.getMethodParameters()) == 0) {
                JSONObject requestParams = OpenUtil.getRequestParams(request);
                OpenContextImpl openContext = new OpenContextImpl(requestParams);
                context.setOpenContext(openContext);
            }
        }
    }

    private static <T> int getArrayLength(T[] arr) {
        return arr == null ? 0 : arr.length;
    }
}
