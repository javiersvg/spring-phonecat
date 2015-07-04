package com.examplecorp.phonecat.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import com.examplecorp.phonecat.services.AppUserDetailsService;

@Configuration
@EnableWebSecurity
@ComponentScan(basePackages="com.examplecorp.phonecat.services")
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private AppUserDetailsService appUserDetailsService;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.exceptionHandling().authenticationEntryPoint(getAuthenticationEntryPoint());

		http
		.authorizeRequests()
			.antMatchers("/user").permitAll()
			.anyRequest().authenticated()
			.and()
		.formLogin()
			.loginPage("/login")
			.successHandler(getAuthenticationSuccessHandler())
			.failureHandler(getAuthenticationFailureHandler())
			.permitAll();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth)
			throws Exception {
		auth.userDetailsService(appUserDetailsService);
	}
	
	@Bean
	public AuthenticationManager getAuthenticationManager() throws Exception {
		return this.authenticationManager();
	}

	@Bean
	public AuthenticationFailureHandler getAuthenticationFailureHandler() {
		return new SimpleUrlAuthenticationFailureHandler();
	}

	@Bean
	public AuthenticationSuccessHandler getAuthenticationSuccessHandler() {
		return new CustomAuthenticationSucessHandler();
	}

	@Bean
	public AuthenticationEntryPoint getAuthenticationEntryPoint() {
		return new Http403ForbiddenEntryPoint();
	}
}
