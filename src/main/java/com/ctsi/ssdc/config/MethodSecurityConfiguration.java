package com.ctsi.ssdc.config;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.vote.AbstractAccessDecisionManager;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import org.zalando.problem.spring.web.advice.security.SecurityProblemSupport;


@Configuration
@Import(SecurityProblemSupport.class)
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class MethodSecurityConfiguration extends GlobalMethodSecurityConfiguration {

	
	@Override
	protected AccessDecisionManager accessDecisionManager() {
		
		AccessDecisionManager manager = super.accessDecisionManager();
		
		if(manager instanceof AbstractAccessDecisionManager) {
		
			AbstractAccessDecisionManager m = (AbstractAccessDecisionManager)manager;
			
			for(AccessDecisionVoter<?> voter : m.getDecisionVoters()) {
				if(voter instanceof RoleVoter) {
					((RoleVoter)voter).setRolePrefix(StringUtils.EMPTY);
				}
			}		
		
		}
		
		return manager;
		
	}
	
	
	
}
