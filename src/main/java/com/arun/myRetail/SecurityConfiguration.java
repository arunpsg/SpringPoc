/**
 * 
 */
package com.arun.myRetail;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author Arun
 *
 */
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{
	
	private static final String[] AUTH_WHITELIST = {
            "/swagger-resources",
            "/swagger-resources/**",
            "/webjars/**"
	};
	
	private static final String[] swagger_apis = {
//			"/swagger-resources",
//            "/swagger-resources/**",
			"/v2/api-docs/**",
			"/configuration/ui",
			"/configuration/security",
			"/swagger-ui.html"
	};
	
	@Override
	public void configure(AuthenticationManagerBuilder auth)
			throws Exception {
		auth.inMemoryAuthentication().passwordEncoder(passwordEncoder())
			.withUser("user").password(passwordEncoder().encode("user"))
			.roles("USER").and().withUser("admin").password(passwordEncoder().encode("admin"))
			.roles("USER", "ADMIN");
	}
	
	// Authorization
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.anonymous().and().authorizeRequests().antMatchers(AUTH_WHITELIST).permitAll();
		http.httpBasic().and().authorizeRequests()
			.antMatchers(swagger_apis).hasRole("ADMIN")
			.antMatchers(HttpMethod.GET, "/products/v1/**").hasAnyRole("USER", "ADMIN")
			.antMatchers(HttpMethod.PUT, "/products/v1/**").hasRole("ADMIN")
			.and()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and().csrf().disable();
	}
	
	@Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
	
	@Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }
	
}
