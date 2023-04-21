package com.example.webnovelservice.config;

import com.example.webnovelservice.security.*;
import com.example.webnovelservice.security.oauth2.CustomOAuth2UserService;
import com.example.webnovelservice.security.oauth2.HttpCookieOAuth2AuthorizationRequestRepository;
import com.example.webnovelservice.security.oauth2.OAuth2AuthenticationFailureHandler;
import com.example.webnovelservice.security.oauth2.OAuth2AuthenticationSuccessHandler;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
public class SecurityConfig implements WebMvcConfigurer {
	private final CustomOAuth2UserService customOAuth2UserService;
	private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;
	private final OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler;
	private final TokenProvider tokenProvider;
	private final CustomUserDetailsService customUserDetailsService;

	public SecurityConfig(CustomOAuth2UserService customOAuth2UserService,
		OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler,
		OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler, TokenProvider tokenProvider,
		CustomUserDetailsService customUserDetailsService) {
		this.customOAuth2UserService = customOAuth2UserService;
		this.oAuth2AuthenticationSuccessHandler = oAuth2AuthenticationSuccessHandler;
		this.oAuth2AuthenticationFailureHandler = oAuth2AuthenticationFailureHandler;
		this.tokenProvider = tokenProvider;
		this.customUserDetailsService = customUserDetailsService;
	}

	@Bean
	public TokenAuthenticationFilter tokenAuthenticationFilter() {
		return new TokenAuthenticationFilter(tokenProvider, customUserDetailsService);
	}

	@Bean
	public HttpCookieOAuth2AuthorizationRequestRepository cookieAuthorizationRequestRepository() {
		return new HttpCookieOAuth2AuthorizationRequestRepository();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authenticationManagerBean(
		AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.cors()
			.and()
			.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			.csrf()
			.disable()
			.httpBasic()
			.disable()
			.exceptionHandling()
			.authenticationEntryPoint(new RestAuthenticationEntryPoint())
			.and()
			.authorizeHttpRequests()
			.requestMatchers("api/v1/auth/**", "/oauth2/**", "/",
				"/v3/api-docs/**", "/swagger-ui.html", "/swagger-ui/**", "/docs/**", "/error", "/favicon.ico",
				"/api/v1/novels/home-best")
			.permitAll()
			.requestMatchers("/api/v1/novels").hasRole("AUTHOR")
			.requestMatchers("/api/v1/transactions").hasRole("USER")
			.requestMatchers("/api/v1/purchases").hasRole("USER")
			.anyRequest()
			.authenticated()
			.and()
			.addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
			.oauth2Login()
			.authorizationEndpoint()
			// store the authorization request information to keep user information safe from malicious attack (e.g CSRF ...)
			.authorizationRequestRepository(cookieAuthorizationRequestRepository())
			.and()
			.userInfoEndpoint()
			.userService(customOAuth2UserService)
			.and()
			.successHandler(oAuth2AuthenticationSuccessHandler)
			.failureHandler(oAuth2AuthenticationFailureHandler);

		return http.build();
	}
}
