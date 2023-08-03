package com.gitee.sop.gatewaycommon.route;

import com.alibaba.fastjson.JSON;
import com.gitee.sop.gatewaycommon.bean.InstanceDefinition;
import com.gitee.sop.gatewaycommon.bean.RouteDefinition;
import com.gitee.sop.gatewaycommon.bean.ServiceBeanInitializer;
import com.gitee.sop.gatewaycommon.bean.ServiceRouteInfo;
import com.gitee.sop.gatewaycommon.gateway.route.GatewayRouteCache;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author tanghc
 */
@Slf4j
public class ServiceRouteListener extends BaseServiceListener {

    private static final String SOP_ROUTES_PATH = "/sop/routes";

    private static final String METADATA_SOP_ROUTES_PATH = "sop.routes.path";

    @Autowired
    private GatewayRouteCache gatewayRouteCache;

    @Autowired
    private RoutesProcessor routesProcessor;

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public void onRemoveService(String serviceId) {
        log.info("服务下线，删除路由配置，serviceId: {}", serviceId);
        gatewayRouteCache.remove(serviceId);
        routesProcessor.removeAllRoutes(serviceId);
    }

    @Override
    public void onAddInstance(InstanceDefinition instance) {
        String serviceId = instance.getServiceId();
        String url = getRouteRequestUrl(instance);
        log.info("拉取路由配置，serviceId: {}, url: {}", serviceId, url);
        ResponseEntity<String> responseEntity = getRestTemplate().getForEntity(url, String.class);
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            String body = responseEntity.getBody();
            ServiceRouteInfo serviceRouteInfo;
            try {
                serviceRouteInfo =  this.parseServiceRouteInfo(body);
            } catch (Exception e) {
                log.error("解析路由配置错误，body:{}", body, e);
                return;
            }
            gatewayRouteCache.load(serviceRouteInfo, callback -> routesProcessor.saveRoutes(serviceRouteInfo, instance));
            this.initServiceBeanInitializer(serviceId);
        } else {
            log.error("拉取路由配置异常，url: {}, status: {}, body: {}", url, responseEntity.getStatusCodeValue(), responseEntity.getBody());
        }
    }

    private void initServiceBeanInitializer(String serviceId) {
        Map<String, ServiceBeanInitializer> serviceBeanInitializerMap = applicationContext.getBeansOfType(ServiceBeanInitializer.class);
        serviceBeanInitializerMap.values().forEach(serviceBeanInitializer -> serviceBeanInitializer.load(serviceId));
    }

    private ServiceRouteInfo parseServiceRouteInfo(String body) {
        ServiceRouteInfo serviceRouteInfo = JSON.parseObject(body, ServiceRouteInfo.class);
        serviceRouteInfo.setServiceId(serviceRouteInfo.getServiceId().toLowerCase());
        List<RouteDefinition> routeDefinitionList = serviceRouteInfo.getRouteDefinitionList();
        String md5 = buildMd5(routeDefinitionList);
        serviceRouteInfo.setMd5(md5);
        return serviceRouteInfo;
    }

    /**
     * 构建路由id MD5
     *
     * @param routeDefinitionList 路由列表
     * @return 返回MD5
     */
    private String buildMd5(List<RouteDefinition> routeDefinitionList) {
        List<String> routeIdList = routeDefinitionList.stream()
                .map(JSON::toJSONString)
                .sorted()
                .collect(Collectors.toList());
        String md5Source = org.apache.commons.lang3.StringUtils.join(routeIdList, "");
        return DigestUtils.md5DigestAsHex(md5Source.getBytes(StandardCharsets.UTF_8));
    }

    protected HttpEntity<String> getHttpEntity() {
        HttpHeaders headers = new HttpHeaders();
        return new HttpEntity<>(headers);
    }

    /**
     * 拉取路由请求url
     *
     * @param instance 服务实例
     * @return 返回最终url
     */
    private String getRouteRequestUrl(InstanceDefinition instance) {
        Map<String, String> metadata = instance.getMetadata();
        String customPath = metadata.get(METADATA_SOP_ROUTES_PATH);
        String homeUrl;
        String servletPath;
        // 如果metadata中指定了获取路由的url
        if (StringUtils.isNotBlank(customPath)) {
            // 自定义完整的url
            if (customPath.startsWith("http")) {
                homeUrl = customPath;
                servletPath = "";
            } else {
                homeUrl = getHomeUrl(instance);
                servletPath = customPath;
            }
        } else {
            // 默认处理
            homeUrl = getHomeUrl(instance);
            String contextPath = this.getContextPath(metadata);
            servletPath = contextPath + SOP_ROUTES_PATH;
        }
        if (StringUtils.isNotBlank(servletPath) && !servletPath.startsWith("/")) {
            servletPath = '/' + servletPath;
        }
        String query = buildQuery();
        return homeUrl + servletPath + query;
    }

    private static String getHomeUrl(InstanceDefinition instance) {
        return "http://" + instance.getIp() + ":" + instance.getPort();
    }
}
