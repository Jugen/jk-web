package com.app.security;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

public class WebInitializer extends AbstractSecurityWebApplicationInitializer {
	
	public WebInitializer() {
		super(WebSecurityConfig.class);
	}

}