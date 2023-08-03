package com.gitee.sop.gatewaycommon.gateway.controller;

import com.gitee.sop.gatewaycommon.bean.SpringContext;
import com.gitee.sop.gatewaycommon.gateway.loadbalancer.SopLoadBalancerClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.gateway.webflux.ProxyExchange;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 处理restful请求
 * @author tanghc
 */
@Controller
public class RestfulController {

    @Value("${sop.restful.path:/rest}")
    private String prefix;

    @RequestMapping("${sop.restful.path:/rest}/**")
    public Mono<ResponseEntity<byte[]>> proxy(ProxyExchange<byte[]> proxy, ServerWebExchange exchange) {
        String path = proxy.path();
        String serviceId = getServiceId(path);
        String targetPath = getTargetPath(serviceId, path);
        String rawQuery = exchange.getRequest().getURI().getRawQuery();
        if (StringUtils.hasLength(rawQuery)) {
            targetPath = targetPath + "?" + rawQuery;
        }
        // 负载均衡
        ServiceInstance serviceInstance = SpringContext.getBean(SopLoadBalancerClient.class).choose(serviceId, exchange);
        String uri = "http://" + serviceInstance.getHost() + ":" + serviceInstance.getPort() + targetPath;
        return proxy.uri(uri).forward();
    }

    /**
     * 从path中解析出serviceId
     * @param path 格式：/rest/<serviceId><real_path>
     * @return 返回serviceId
     */
    private String getServiceId(String path) {
        int length = prefix.length() + 1;
        path = path.substring(length);
        int index = path.indexOf('/');
        path = path.substring(0, index);
        return path;
    }

    private String getTargetPath(String serviceId, String path) {
        int length = prefix.length() + 1;
        int len = length + serviceId.length();
        return path.substring(len);
    }
}
