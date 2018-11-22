package com.app.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.*;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.firewall.DefaultHttpFirewall;
import org.springframework.security.web.firewall.HttpFirewall;

@EnableWebSecurity
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	protected void configure(HttpSecurity http) throws Exception {
		// @formatter:off
		http.csrf().disable().authorizeRequests().antMatchers("/pages/error/**").permitAll()
				.antMatchers("/resources/**", "*.css", "*.js", "/javax.faces.resource/**").permitAll().
				 antMatchers("/pages/login/**").permitAll()
				.antMatchers("/*/components/**").denyAll()
				.antMatchers("/admin/**").hasRole("ADMIN").anyRequest().permitAll()
				.anyRequest().authenticated().and().formLogin().loginPage("/pages/login/index.xhtml").defaultSuccessUrl("/index.xhtml").permitAll()
				.and().logout().logoutUrl("/logout").logoutSuccessUrl("/pages/login/index.xhtml").permitAll();
		// @formatter:on
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider());
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		UserService service = new UserService();
		authProvider.setUserDetailsService(service);
		authProvider.setPasswordEncoder(encoder());
		return authProvider;
	}

	@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public HttpFirewall defaultHttpFirewall() {
		return new DefaultHttpFirewall();
	}
}