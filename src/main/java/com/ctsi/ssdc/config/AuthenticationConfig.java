package com.ctsi.ssdc.config;

import com.ctsi.ssdc.admin.service.CscpMenusService;
import com.ctsi.ssdc.admin.service.UserService;
import com.ctsi.ssdc.security.CasHxAuthenticationProvider;
import com.ctsi.ssdc.security.UserDetailsServiceImpl;
import com.ctsi.ssdc.security.ThirdAuthProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableConfigurationProperties({CtsiProperties.class})
public class AuthenticationConfig {

	@Bean
	@ConditionalOnMissingBean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	@ConditionalOnMissingBean
	public CasHxAuthenticationProvider casAuthenticationProvider(CtsiProperties ctsiProperties,
                                                                 UserDetailsService userDetailsService) {
		return new CasHxAuthenticationProvider(ctsiProperties, userDetailsService);
	}


	@Bean
	@ConditionalOnMissingBean
	public ThirdAuthProvider thridAuthProvider(CtsiProperties ctsiProperties,
											   UserDetailsService userDetailsService) {
		return new ThirdAuthProvider(ctsiProperties, userDetailsService);
	}
	@Bean
	@ConditionalOnMissingBean
	public DaoAuthenticationProvider daoAuthenticationProvider(PasswordEncoder passwordEncoder,
			UserDetailsService userDetailsService) {
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setUserDetailsService(userDetailsService);
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
		return daoAuthenticationProvider;
	}

	@Bean
	@ConditionalOnMissingBean
	public AuthenticationManager authenticationManager(UserDetailsService userDetailsService,
			DaoAuthenticationProvider daoAuthenticationProvider
			, CasHxAuthenticationProvider casHxAuthenticationProvider,
		   ThirdAuthProvider thirdAuthProvider) {
		List<AuthenticationProvider> providers = Arrays
				.asList(daoAuthenticationProvider, casHxAuthenticationProvider, thirdAuthProvider);
		return new ProviderManager(providers);
	}

	@Bean
	@ConditionalOnMissingBean
	public UserDetailsService userDetailsService(UserService userService, CscpMenusService cscpMenusService) {

		return new UserDetailsServiceImpl(userService, cscpMenusService);

	}
}
