package com.gitee.sop.gatewaycommon.manager;

import com.gitee.sop.gatewaycommon.bean.InstanceDefinition;
import com.gitee.sop.gatewaycommon.route.RegistryEvent;

import java.util.List;

/**
 * @author tanghc
 */
public interface InstanceManager extends RegistryEvent {

    List<InstanceDefinition> listInstance(String serviceId);

}
