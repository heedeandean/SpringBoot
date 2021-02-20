package com.study.springboot.auth;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	public AuthenticationFailureHandler authenticationFailureHandler;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers("/").permitAll()
				.antMatchers("/css/**", "/js/**", "/img/**").permitAll()				
				.antMatchers("/guest/**").permitAll()				
				.antMatchers("/member/**").hasAnyRole("USER", "ADMIN")				
				.antMatchers("/admin/**").hasAnyRole("ADMIN")		
				.anyRequest().authenticated();
		
		http.formLogin()
				.loginPage("/loginForm")
				.loginProcessingUrl("/j_spring_security_check")
				.failureHandler(authenticationFailureHandler)
				.usernameParameter("j_username")
				.passwordParameter("j_password")
				.permitAll();
		
		http.logout()
				.logoutUrl("/logout")
				.logoutSuccessUrl("/")
				.permitAll();
		
		http.csrf().disable();
	}
	
	@Autowired
	private DataSource dataSource;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
//		System.out.println(passwordEncoder().encode("123")); // 테스트용
		
		auth.jdbcAuthentication()
			.dataSource(dataSource)
			.usersByUsernameQuery("select name as userName, password, enabled" + " from user_list where name = ?")
			.authoritiesByUsernameQuery("select name as userName, authority" + " from user_list where name = ?")
			.passwordEncoder(new BCryptPasswordEncoder());
	}
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
}
