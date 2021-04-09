package com.polaris.Stockify.security;

import com.polaris.Stockify.exception.RestAccessDeniedHandler;
import com.polaris.Stockify.exception.RestAuthenticationEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;


@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

	@Autowired
	RestAccessDeniedHandler accessDeniedHandler;

	@Autowired
	RestAuthenticationEntryPoint authenticationEntryPoint;

	@Value("${client.id}")
	private String CLIENT_ID;
	
	@Value("${client.secret}")
	private String CLIENT_SECRET;
	
	@Value("${resource.id}")
	private String RESOURCE_ID;
	
	@Value("${keystore.alias}")
	private String KEYSTORE_ALIAS;
	
	@Value("${keystore.file}")
	private String KEYSTORE_FILE;
	
	@Value("${keystore.password}")
	private String KEYSTORE_PASSWORD;
	
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	MyWebResponseExceptionTranslator oauth2ResponseExceptionTranslator;

	static final int ACCESS_TOKEN_VALIDITY_SECONDS = 1*60*60;

	static final int REFRESH_TOKEN_VALIDITY_SECONDS = 6*60*60;

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.inMemory().withClient(CLIENT_ID)
		.secret(passwordEncoder.encode(CLIENT_SECRET))
		.authorizedGrantTypes("password","refresh_token")
		.scopes("read","write")
		.accessTokenValiditySeconds(ACCESS_TOKEN_VALIDITY_SECONDS)
		.refreshTokenValiditySeconds(REFRESH_TOKEN_VALIDITY_SECONDS)
		.resourceIds(RESOURCE_ID);
	}

    

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {

		endpoints
		.pathMapping("/oauth/token", "/auth/signin")
		.tokenStore(tokenStore()).accessTokenConverter(accessTokenConverter())
		.authenticationManager(authenticationManager)
		.userDetailsService(customUserDetailsService)
		.exceptionTranslator(oauth2ResponseExceptionTranslator);
	}

	
	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		security.accessDeniedHandler(accessDeniedHandler)
				.authenticationEntryPoint(authenticationEntryPoint);
	}
	
	@Bean
	public TokenStore tokenStore() {
		return new JwtTokenStore(accessTokenConverter());
	}

	
	@Bean
	public JwtAccessTokenConverter accessTokenConverter() {
		final JwtAccessTokenConverter converter = new JwtAccessTokenConverter();

		final KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(
				new ClassPathResource(KEYSTORE_FILE), KEYSTORE_PASSWORD.toCharArray());
		converter.setKeyPair(keyStoreKeyFactory.getKeyPair(KEYSTORE_ALIAS));
		return converter;
	}


}

