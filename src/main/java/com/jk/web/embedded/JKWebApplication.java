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
package com.jk.web.embedded;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.LifecycleState;
import org.apache.catalina.WebResourceRoot;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.webresources.DirResourceSet;
import org.apache.catalina.webresources.StandardRoot;
import org.apache.jasper.servlet.TldScanner;
import org.apache.tomcat.JarScanFilter;
import org.apache.tomcat.JarScanType;
import org.apache.tomcat.util.scan.StandardJarScanFilter;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.util.StringUtils;

import com.jk.util.JK;
import com.jk.util.JKConversionUtil;
import com.jk.util.JKDebugUtil;
import com.jk.util.JKIOUtil;
import com.jk.util.logging.JKLogger;
import com.jk.util.logging.JKLoggerFactory;
import com.jk.web.embedded.spring.TldSkipPatterns;

// TODO: Auto-generated Javadoc
/**
 * The Class JKWebApplication.
 */
public class JKWebApplication {
	/** The Constant JK_WEBSERVER_PORT_KEY. */
	private static final String JK_WEBSERVER_PORT_KEY = "JK.WEBSERVER.PORT";

	/** The Constant DEFAULT_WEBSERVER_PORT. */
	private static final String DEFAULT_WEBSERVER_PORT = "8080";

	/** The Constant DEFAULT_WEB_APP_FOLDER. */
	private static final String DEFAULT_WEB_APP_FOLDER = "src/main/webapp/";

	/** The Constant DEFAULT_TEMP_WEBAPP_FOLDER. */
	private static final String DEFAULT_TEMP_WEBAPP_FOLDER = "jk-webapp-";
	// THE NAME IS THE DIRECTORT INSIDE THE SOURCE CODE, that contains the web
	/** The Constant RESOURCES_WEB_FOLDER. */
	// resources.
	private static final String RESOURCES_WEB_FOLDER = "jk/webapp";

	/** The logger. */
	static JKLogger logger = JKLoggerFactory.getLogger(JKWebApplication.class);

	/**
	 * Run.
	 *
	 * @param args the args
	 */
	public static void run() {
		String webPort = System.getenv(JK_WEBSERVER_PORT_KEY);
		if (webPort == null || webPort.isEmpty()) {
			logger.debug("Port config not founds, default port will be used : " + DEFAULT_WEBSERVER_PORT);
			webPort = DEFAULT_WEBSERVER_PORT;
		}
		run(JKConversionUtil.toInteger(webPort));
	}

	public static void run(int portNumber) {
		initLoggers();
		cleanUpPreviousInstance(portNumber);
		try {
			String webappDirLocation = DEFAULT_WEB_APP_FOLDER;
			if (!(new File(webappDirLocation).exists())) {
				// most likely because of executing using single a jar file(outside IDE)
				Path dir = Files.createTempDirectory(DEFAULT_TEMP_WEBAPP_FOLDER);
				dir.toFile().deleteOnExit();
				logger.debug("NO default webapp folder found, temp webapp directory created at : " + dir);
				File newTempDirectoryFile = dir.toFile();
				webappDirLocation = newTempDirectoryFile.getAbsolutePath();
				logger.debug("copying resources from :" + RESOURCES_WEB_FOLDER);
				JKIOUtil.copResourcesFromJarToDir(RESOURCES_WEB_FOLDER, newTempDirectoryFile);
			}
			logger.debug("Creating tomcat instance");
			Tomcat tomcat = new Tomcat();

			// The port that we should run on can be set into an environment variable
			// Look for that variable and default to 8080 if it isn't there.

			tomcat.setPort(Integer.valueOf(portNumber));

			StandardContext ctx = (StandardContext) tomcat.addWebapp("/", new File(webappDirLocation).getAbsolutePath());

			JK.fixMe("solve the bottleneck here");
			StandardJarScanFilter jarScanFilter = new StandardJarScanFilter();
			jarScanFilter.setTldSkip(StringUtils.collectionToCommaDelimitedString(TldSkipPatterns.DEFAULT));

			ctx.getJarScanner().setJarScanFilter(jarScanFilter);
			logger.debug("configuring app with basedir: " + new File("./" + webappDirLocation).getAbsolutePath());

			// Declare an alternative location for your "WEB-INF/classes" dir
			// Servlet 3.0 annotation will work
			WebResourceRoot resources = new StandardRoot(ctx);
			resources.setCachingAllowed(false);
			File file = new File("target/classes");
//			if (file.exists()) {
			logger.debug("Setting tomcat to read classes from target/classes");
			File additionWebInfClasses = file;
			resources.addPreResources(new DirResourceSet(resources, "/WEB-INF/classes", additionWebInfClasses.getAbsolutePath(), "/"));
			ctx.setResources(resources);
//			}
			logger.info("Starting web-server....");
			tomcat.start();
			final ServerSocket shutdownServer = new ServerSocket(portNumber + 1);
			;
			Thread thread = new Thread() {
				@Override
				public void run() {
					try {
						shutdownServer.accept();
						logger.info("Web server received a stop signal");
					} catch (Exception e) {
					}
				}
			};
			thread.start();

			Runtime.getRuntime().addShutdownHook(new Thread() {
				@Override
				public void run() {
					if (tomcat != null && tomcat.getServer().getState() == LifecycleState.STARTED) {
						logger.debug("Trying to stop tomcat...");
						try {
							tomcat.getServer().stop();
						} catch (Exception e) {
						}
						try {
							shutdownServer.close();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			});
			logger.info("Web server started at URL :  http://localhost:{}", portNumber);
			JKIOUtil.executeFile("start http://localhost:" + portNumber);
		} catch (Exception e) {
			JK.throww(e);
		}

	}

	private static void cleanUpPreviousInstance(int portNumber) {
		try {
			Socket client = new Socket("localhost", portNumber + 1);
			client.close();
		} catch (Exception e) {
		}
	}

	protected static void initLoggers() {
		System.setProperty("org.jboss.logging.provider", "slf4j");
		SLF4JBridgeHandler.removeHandlersForRootLogger();
		SLF4JBridgeHandler.install();
		Logger.getLogger("").setLevel(Level.FINEST); // Root logger, for example.
	}

}