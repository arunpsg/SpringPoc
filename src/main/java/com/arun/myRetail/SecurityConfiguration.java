/**
 * 
 */
package com.arun.myRetail;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

/**
 * @author Arun
 *
 */
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{
	
	@Override
	public void configure(AuthenticationManagerBuilder auth)
			throws Exception {
		auth.inMemoryAuthentication().passwordEncoder(passwordEncoder())
			.withUser("user").password(passwordEncoder().encode("secret1"))
			.roles("USER").and().withUser("admin").password(passwordEncoder().encode("secret1"))
			.roles("ADMIN");
	}
	
	// Authorization : Role -> Access
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.httpBasic().and().authorizeRequests().anyRequest().authenticated()
			.antMatchers(HttpMethod.GET, "/products/v1/**").hasAnyRole("USER", "ADMIN")
			.antMatchers(HttpMethod.POST, "/products/v1/").hasRole("ADMIN")
			.antMatchers(HttpMethod.PUT, "/products/v1/**").hasRole("ADMIN").and()
			.sessionManagement().maximumSessions(1).sessionRegistry(sessionRegistry()).and().sessionFixation().none()
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
