package com.polaris.Stockify.security;

import com.polaris.Stockify.exception.RestAccessDeniedHandler;
import com.polaris.Stockify.exception.RestAuthenticationEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

	@Autowired
	RestAccessDeniedHandler accessDeniedHandler;

	@Autowired
	RestAuthenticationEntryPoint authenticationEntryPoint;

	@Value("${resource.id}")
	private String RESOURCE_ID;
	
    @Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
    	resources.accessDeniedHandler(accessDeniedHandler)
				.authenticationEntryPoint(authenticationEntryPoint)
				.resourceId(RESOURCE_ID);
	}

	@Override
    public void configure(HttpSecurity http) throws Exception {

		http
				.cors()
				.and()
				.csrf()
				.disable()
//				.exceptionHandling().accessDeniedHandler(accessDeniedHandler).authenticationEntryPoint(authenticationEntryPoint)
//				.and()
				.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
				.authorizeRequests()
				.antMatchers("/auth/**").permitAll()
				.antMatchers("/transactions/**").access("hasRole('ADMIN')")
				.antMatchers("/stocks/**").access("hasRole('ADMIN')")
				.antMatchers("/").permitAll()
				.anyRequest().denyAll();

		http.headers().frameOptions().disable();

    }



}