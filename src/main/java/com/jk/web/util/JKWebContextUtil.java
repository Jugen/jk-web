/*
 * Copyright 2002-2016 Jalal Kiswani.
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
package com.jk.web.util;

import java.util.Enumeration;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.jk.util.context.JKContextConstants;
import com.jk.util.context.JKContextFactory;
import com.jk.util.context.impl.JKWebContext;
import com.jk.util.thread.JKThreadLocal;

// TODO: Auto-generated Javadoc
/**
 * The Class JKContextUtil.
 *
 * @author Jalal Kiswani
 */
public class JKWebContextUtil {

	/**
	 * Gets the session map.
	 *
	 * @param httpSession
	 *            the http session
	 * @return the session map
	 */
	private static HashMap<String, Object> getSessionMap(final HttpSession httpSession) {
		final HashMap<String, Object> sessionMap = new HashMap<String, Object>();
		final Enumeration<String> attributes = httpSession.getAttributeNames();
		while (attributes.hasMoreElements()) {
			final String attributeName = attributes.nextElement();
			sessionMap.put(attributeName, httpSession.getAttribute(attributeName));
		}
		return sessionMap;
	}

	/**
	 * Sync.
	 *
	 * @param request
	 *            the request
	 */
	public static void sync(final HttpServletRequest request) {
		// intialize HTTPServletSession
		final HttpSession session = request.getSession();
		// initializing ITSDefault context and set values inside it
		JKWebContext context = new JKWebContext();
		context.setConfigPath("/WEB-INF/");
		JKContextFactory.setCurrentContext(context);
		context.setRemoteIP(request.getRemoteAddr());
		context.setSessionMap(JKWebContextUtil.getSessionMap(session));
		context.setSessionID(session.getId());
		context.setMachineName(request.getRemoteHost());
		context.setRemotPort(request.getRemotePort());
		context.setUser(request.getUserPrincipal());
		
		JKContextFactory.setCurrentContext(context);
	}

}
