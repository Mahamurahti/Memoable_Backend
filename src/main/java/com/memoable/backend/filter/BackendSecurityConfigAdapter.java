package com.memoable.backend.filter;

import com.memoable.backend.security.SecurityConstants;
import com.memoable.backend.services.UserDetailServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Configuration class for Spring-boot security
 */
@Configuration
public class BackendSecurityConfigAdapter extends WebSecurityConfigurerAdapter {

	/**
	 * User details service to load users
	 */
	@Autowired
	private UserDetailServiceImpl userService;

	/**
	 * Password encoder for hashing passwords
	 */
	@Autowired
	private  PasswordEncoder passwordEncoder;


	/**
	 * Configures the applications HttpSecurity
	 * @param http HttpSecurity to configure
	 * @throws Exception
	 */
	@Override
    protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests()
			.antMatchers(HttpMethod.POST, SecurityConstants.SIGN_UP_URL).permitAll()
			.antMatchers(HttpMethod.POST, SecurityConstants.SIGN_IN_URL).permitAll()
			.antMatchers("/ping").permitAll()
			.anyRequest().authenticated()
			.and()
			.addFilter(new JWTAuthenticationFilter(authenticationManager()))
			.addFilter(new JWTAuthorizationFilter(authenticationManager()))
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and().httpBasic();
    }

	/**
	 * Configures the applications WebSecurity.
	 * Ignores all security filters to user endpoint
	 * @param web WebSecurity to configure
	 * @throws Exception
	 */
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring()
			.antMatchers(HttpMethod.POST, "/api/user");
	}

	/**
	 * Configures the applications AuthenticationManagerBuilder.
	 * Sets a userService and passwordEncoder to the AuthenticationManagerBuilder
	 * @param auth AuthenticationManagerBuilder to configure
	 * @throws Exception
	 */
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userService)
			.passwordEncoder(passwordEncoder);
	}

	
}