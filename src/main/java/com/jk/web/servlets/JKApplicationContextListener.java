/*
 * Copyright 2002-2018 Jalal Kiswani. 
 * E-mail: Kiswani.Jalal@Gmail.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.jk.web.servlets;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.slf4j.bridge.SLF4JBridgeHandler;

import com.jk.util.JK;
import com.jk.util.JKConversionUtil;
import com.jk.util.logging.JKLogger;
import com.jk.util.logging.JKLoggerFactory;

/**
 * The listener interface for receiving JKApplicationContext events. The class
 * that is interested in processing a JKApplicationContext event implements this
 * interface, and the object created with that class is registered with a
 * component using the component's <code>addJKApplicationContextListener<code>
 * method. When the JKApplicationContext event occurs, that object's appropriate
 * method is invoked.
 *
 * @see JKApplicationContextEvent
 */
@WebListener
public class JKApplicationContextListener implements ServletContextListener {

	/** The logger. */
	JKLogger logger = JKLoggerFactory.getLogger(getClass());

	/** The initazlied. */
	static boolean initazlied;

	/**
	 * Inits the.
	 */
	public void init() {
		logger.debug("JKApplicationContextListener is initialied");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.
	 * ServletContextEvent)
	 */
	@Override
	public void contextInitialized(ServletContextEvent event) {
		System.setProperty("org.jboss.logging.provider", "slf4j");
		SLF4JBridgeHandler.install();

		String multiTenant = event.getServletContext().getInitParameter("jk.multi-tenant");
		if (multiTenant != null && JKConversionUtil.toBoolean(multiTenant)) {
			logger.info("Multi-tenant is enabled by context paramter");
			JK.setMultiTenantApp(true);
		}
		logger.debug("contextInitialized");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.
	 * ServletContextEvent)
	 */
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		logger.debug("contextDestroyed");
	}

}
