package com.gitee.sop.gateway.interceptor;

import com.gitee.sop.gatewaycommon.interceptor.RouteInterceptor;
import com.gitee.sop.gatewaycommon.interceptor.RouteInterceptorContext;
import com.gitee.sop.gatewaycommon.param.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;

import java.net.URI;

/**
 * 演示拦截器
 *
 * @author tanghc
 */
@Slf4j
@Component
public class MyRouteInterceptor implements RouteInterceptor {

    @Override
    public void preRoute(RouteInterceptorContext context) {
        ApiParam apiParam = context.getApiParam();
        ServerWebExchange exchange = (ServerWebExchange) context.getRequestContext();
        URI uri = exchange.getRequest().getURI();
        log.info("请求URL:{}, 请求接口:{}, request_id:{}, app_id:{}, ip:{}",
                uri,
                apiParam.fetchNameVersion(),
                apiParam.fetchRequestId(),
                apiParam.fetchAppKey(),
                apiParam.fetchIp()
        );
    }

    @Override
    public void afterRoute(RouteInterceptorContext context) {
        String serviceErrorMsg = context.getServiceErrorMsg();
        if (StringUtils.hasText(serviceErrorMsg)) {
            log.error("错误信息:{}", serviceErrorMsg);
        }
    }

    @Override
    public int getOrder() {
        return 0;
    }

}
