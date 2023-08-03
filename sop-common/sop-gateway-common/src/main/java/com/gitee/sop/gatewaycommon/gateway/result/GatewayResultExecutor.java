package com.gitee.sop.gatewaycommon.gateway.result;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gitee.sop.gatewaycommon.bean.ApiConfig;
import com.gitee.sop.gatewaycommon.bean.DefaultRouteInterceptorContext;
import com.gitee.sop.gatewaycommon.bean.SopConstants;
import com.gitee.sop.gatewaycommon.exception.ApiException;
import com.gitee.sop.gatewaycommon.gateway.ServerWebExchangeUtil;
import com.gitee.sop.gatewaycommon.interceptor.RouteInterceptorContext;
import com.gitee.sop.gatewaycommon.message.Error;
import com.gitee.sop.gatewaycommon.message.ErrorEnum;
import com.gitee.sop.gatewaycommon.param.ApiParam;
import com.gitee.sop.gatewaycommon.result.BaseExecutorAdapter;
import com.gitee.sop.gatewaycommon.result.ResultExecutorForGateway;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.gateway.support.TimeoutException;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.util.UriUtils;

import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;


/**
 * @author tanghc
 */
@Slf4j
public class GatewayResultExecutor extends BaseExecutorAdapter<ServerWebExchange, String>
        implements ResultExecutorForGateway {

    @Override
    public int getResponseStatus(ServerWebExchange exchange) {
        HttpStatus statusCode = exchange.getResponse().getStatusCode();
        return ServerWebExchangeUtil.getHeaderValue(exchange, SopConstants.X_SERVICE_ERROR_CODE)
                .map(Integer::parseInt)
                .orElse(statusCode.value());
    }

    @Override
    public Optional<String> getServiceResultForError(ServerWebExchange exchange, int status) {
        if (status == HttpStatus.OK.value()) {
            return Optional.empty();
        }
        return ServerWebExchangeUtil.getHeaderValue(exchange, SopConstants.X_SERVICE_ERROR_RESPONSE);
    }

    @Override
    public String getResponseErrorMessage(ServerWebExchange exchange) {
        return ServerWebExchangeUtil.getHeaderValue(exchange, SopConstants.X_SERVICE_ERROR_MESSAGE)
                .map(msg -> UriUtils.decode(msg, StandardCharsets.UTF_8))
                .orElse(null);
    }

    @Override
    public ApiParam getApiParam(ServerWebExchange exchange) {
        return ServerWebExchangeUtil.getApiParam(exchange);
    }

    @Override
    protected Locale getLocale(ServerWebExchange exchange) {
        return exchange.getLocaleContext().getLocale();
    }

    @Override
    protected RouteInterceptorContext getRouteInterceptorContext(ServerWebExchange exchange) {
        RouteInterceptorContext routeInterceptorContext = exchange.getAttribute(SopConstants.CACHE_ROUTE_INTERCEPTOR_CONTEXT);
        ServiceInstance serviceInstance = exchange.getAttribute(SopConstants.TARGET_SERVICE);
        DefaultRouteInterceptorContext context = (DefaultRouteInterceptorContext) routeInterceptorContext;
        context.setServiceInstance(serviceInstance);
        return routeInterceptorContext;
    }

    @Override
    protected void bindRouteInterceptorContextProperties(RouteInterceptorContext routeInterceptorContext, ServerWebExchange requestContext) {
    }

    @Override
    public String buildErrorResult(ServerWebExchange exchange, Throwable ex) {
        Locale locale = getLocale(exchange);
        Error error;
        if (ex.getCause() instanceof TimeoutException) {
            error = ErrorEnum.ISP_GATEWAY_RESPONSE_TIMEOUT.getErrorMeta().getError(locale);
        } else if (ex instanceof ApiException) {
            ApiException apiException = (ApiException) ex;
            error = apiException.getError(locale);
        } else {
            error = ErrorEnum.ISP_UNKNOWN_ERROR.getErrorMeta().getError(locale);
        }
        JSONObject jsonObject = (JSONObject) JSON.toJSON(error);
        return this.merge(exchange, jsonObject);
    }

    @Override
    protected void handleBizResult(Map<String, Object> serviceData, JSONObject serviceObj, ApiParam apiParam, ServerWebExchange request) {
        ApiConfig.getInstance().getBizResultHandler().handle(serviceData, serviceObj, apiParam, request);
    }
}
