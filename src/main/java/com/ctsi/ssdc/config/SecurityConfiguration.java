package com.ctsi.ssdc.config;

import com.ctsi.ssdc.admin.service.CscpTenantService;
import com.ctsi.ssdc.constants.AuthoritiesConstant;
import com.ctsi.ssdc.security.AuthoritiesConstants;
import com.ctsi.ssdc.security.jwt.JWTHxConfigurer;
import com.ctsi.ssdc.security.jwt.TokenHxProvider;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.vote.AuthenticatedVoter;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.access.vote.UnanimousBased;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.access.expression.WebExpressionVoter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;
import org.zalando.problem.spring.web.advice.security.SecurityProblemSupport;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@Import(SecurityProblemSupport.class)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private TokenHxProvider tokenHxProvider;
	@Autowired
	private CscpTenantService cscpTenantService;
	@Autowired
	private CorsFilter corsFilter;
	@Autowired
	private SecurityProblemSupport problemSupport;


	@Bean
	public AccessDecisionManager accessDecisionManager() {
		RoleVoter voter = new RoleVoter();
		voter.setRolePrefix(StringUtils.EMPTY);

		List<AccessDecisionVoter<? extends Object>> decisionVoters = Arrays.asList(new WebExpressionVoter(), voter,
				new AuthenticatedVoter());

		return new UnanimousBased(decisionVoters);
	}

	// 可在此添加自定义AuthenticationProvider（可参照CasAuthenticationProvider）
	/*@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

	}*/

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring()
			.antMatchers(HttpMethod.OPTIONS, "/**")
			.antMatchers("/swagger-ui/*")
			.antMatchers("/api/system/login")
				.antMatchers("/api/system/getCountKeyValue/**")
				.antMatchers("/api/system/oauth2/callback")
				.antMatchers("/oauth2-app/login")
				.antMatchers("/api/system/getQrCode")
				.antMatchers("/api/system/thirdAuth/**")
			.antMatchers("/api/system/loginByCas")
			.antMatchers("/api/system/cscpUserPassword")
				.antMatchers("/api/common/getIp")
			.antMatchers("/api/system/cscpUserPasswordRule");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)
		
			.exceptionHandling().authenticationEntryPoint(problemSupport)
			
			.accessDeniedHandler(problemSupport)
			.and()
				.csrf().disable()
				// .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()).and()
				.headers().frameOptions().disable()
			.and()
				.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
				.apply(securityConfigurerAdapter())
			.and()
				.authorizeRequests()
				.accessDecisionManager(accessDecisionManager())

				.antMatchers("/v2/api-docs/**").permitAll()
				.antMatchers("/swagger-resources/configuration/ui").permitAll()
				.antMatchers("/swagger-ui/*").permitAll()
				.antMatchers("/ureport/**").anonymous()
				// 接口鉴权
//				.antMatchers("/v2/api-docs/**").authenticated()
//				.antMatchers("/swagger-resources/configuration/ui").authenticated()
//				.antMatchers("/swagger-ui/*").authenticated()
//				.antMatchers("/swagger-ui.html").authenticated()

				.antMatchers("/actuator").authenticated()
				.antMatchers("/actuator/prometheus").authenticated()

				// 获取验证码
				.antMatchers("/api/catpcha/digitalCaptcha").permitAll()
				.antMatchers("/api/captcha/**").permitAll()

				.antMatchers("/api/system/login").permitAll()
				// 企业微信回调接口
				.antMatchers("/api/system/workWxLogin/callback").permitAll()
				// 企业微信二维码
				.antMatchers("/api/system/getQrCode").permitAll()
				.antMatchers("/api/system/refreshToken").authenticated()
				.antMatchers("/api/system/cscpMenus").authenticated()
				.antMatchers("/api/system/cscpCurrentUserDetails").authenticated()
				// 企业微信同步通讯录接口
				.antMatchers("/api/system/syncWorkWxUser").authenticated()
			    .antMatchers("/api/system/cscpUserDetailsByUserId").authenticated()

				.antMatchers(HttpMethod.GET, "/api/system/cscpRolessByCriteria").authenticated()
				.antMatchers(HttpMethod.PUT, "/api/system/cscpUserDetails").authenticated()

				.antMatchers(HttpMethod.GET, "/api/system/cscpUserDetailsOr")
					.hasAnyAuthority(AuthoritiesConstants.USER_QUERY, AuthoritiesConstants.USER_EDIT, AuthoritiesConstants.WG_EDIT,
							AuthoritiesConstants.WG_ADD, AuthoritiesConstants.ORG_EDIT,AuthoritiesConstant.WORKFLOW_BPMNEDIT)
				.antMatchers(HttpMethod.POST, "/api/system/cscpUserDetails").hasAuthority(AuthoritiesConstants.USER_ADD)
				.antMatchers(HttpMethod.DELETE, "/api/system/cscpUsers/*").hasAuthority(AuthoritiesConstants.USER_DEL)
				.antMatchers(HttpMethod.GET, "/api/system/cscpUserExistByUsername").hasAuthority(AuthoritiesConstants.USER_ADD)
				.antMatchers(HttpMethod.GET, "/api/system/deblockingAccount/*").hasAuthority(AuthoritiesConstants.USER_DEBLOCKING)

				.antMatchers(HttpMethod.GET,"/api/system/cscpRoless/*").hasAnyAuthority(AuthoritiesConstants.ROLE_QUERY,AuthoritiesConstants.ROLE_ADD)
				.antMatchers(HttpMethod.POST,"/api/system/menu/queryByRoleId").hasAuthority(AuthoritiesConstants.ROLE_ADD)
				.antMatchers(HttpMethod.POST,"/api/system/cscpRoless").hasAuthority(AuthoritiesConstants.ROLE_ADD)
				.antMatchers(HttpMethod.POST,"/api/system/menu/save").hasAuthority(AuthoritiesConstants.ROLE_ADD)					
				.antMatchers(HttpMethod.PUT,"/api/system/cscpRoless").hasAuthority(AuthoritiesConstants.ROLE_EDIT)
				.antMatchers(HttpMethod.DELETE,"/api/system/cscpRoless/*").hasAuthority(AuthoritiesConstants.ROLE_DEL)
					
				.antMatchers(HttpMethod.GET,"/api/cscpTenants/*","/api/cscpTenants").hasAnyAuthority(AuthoritiesConstant.TENANT_QUERY)
				.antMatchers(HttpMethod.PUT,"/api/cscpTenants/changeStatus","/api/updateTenant").hasAuthority(AuthoritiesConstant.TENANT_EDIT)
				.antMatchers(HttpMethod.DELETE,"/api/cscpTenants/delAll","/api/cscpTenants/*").hasAuthority(AuthoritiesConstant.TENANT_DEL)
				.antMatchers(HttpMethod.POST,"/api/insertTenant").hasAuthority(AuthoritiesConstant.TENANT_ADD)

				.antMatchers(HttpMethod.GET,"/api/cscpDepts/treeselect").permitAll()
				.antMatchers(HttpMethod.GET,"/api/cscpDepts/*","/api/cscpDepts","/api/cscpDepts/exclude/*",
						"/api/roleDeptTreeselect/*").hasAnyAuthority(AuthoritiesConstant.DEPT_QUERY,AuthoritiesConstants.ROLE_QUERY,AuthoritiesConstants.USER_QUERY)
				.antMatchers(HttpMethod.PUT,"/api/cscpDepts").hasAuthority(AuthoritiesConstant.DEPT_EDIT)
				.antMatchers(HttpMethod.DELETE,"/api/cscpDepts/*").hasAuthority(AuthoritiesConstant.DEPT_DEL)
				.antMatchers(HttpMethod.POST,"/api/cscpDepts").hasAuthority(AuthoritiesConstant.DEPT_ADD)

				.antMatchers(HttpMethod.GET, "/api/system/cscpLogLoginsByCriteria").hasAuthority(AuthoritiesConstants.LOGGING_LOGIN)
				.antMatchers(HttpMethod.GET, "/api/system/cscpLogOperationsByCriteria").hasAuthority(AuthoritiesConstants.LOGGING_OPERATION)
				.antMatchers("/api/getCscpSysConfigByConfigKey/**").permitAll()
				.antMatchers("/api/system/getCountKeyValue/**").permitAll()
				.antMatchers("/api/system//getCaptchaAttempts").permitAll()
				.antMatchers("/kafka/**").permitAll()
				.antMatchers("/api/test/**").permitAll()
				.antMatchers("/api/authLogin/**").permitAll()
				.antMatchers("/api/report/**").permitAll()
				.antMatchers("/api/callback/updatePeople").permitAll()
				.antMatchers("/api/**").authenticated();

	}

	// 添加token拦截校验的过滤器
	private JWTHxConfigurer securityConfigurerAdapter() {
		return new JWTHxConfigurer(tokenHxProvider,cscpTenantService);
	}

}
