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
package com.jk.mvc.api;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.jk.util.thread.JKThreadLocal;

// TODO: Auto-generated Javadoc
/**
 * The Class MvcUtil.
 */
public class MvcUtil {
	
	/** The context. */
	private static ServletContext context;

	/**
	 * Inits the.
	 *
	 * @param context the context
	 */
	public static void init(ServletContext context) {
		MvcUtil.context = context;
	}

	/**
	 * Creates the user binding map.
	 *
	 * @return the map
	 */
	public static Map<String, MvcUserBinding> createUserBindingMap() {
		Map<String, MvcUserBinding> map = new LinkedHashMap<>();
		return map;
	}

	/**
	 * Gets the user binding.
	 *
	 * @param req the req
	 * @return the user binding
	 */
	public static Map<String, MvcUserBinding> getUserBinding(HttpServletRequest req) {
		return (Map<String, MvcUserBinding>) req.getSession().getAttribute(MvcConstants.ATTRIBUTE_BINDINGS_NAME);
	}

	/**
	 * Inits the request info.
	 *
	 * @param req the req
	 */
	public static void initRequestInfo(HttpServletRequest req) {
		JKThreadLocal.setValue(MvcConstants.ATTRIBUTE_SERVLET_CONTEXT, req.getServletContext());
		JKThreadLocal.setValue(MvcConstants.ATTRIBUTE_REQUEST, req);
		JKThreadLocal.setValue(MvcConstants.ATTRIBUTE_SESSION, req.getSession());
	}

	/**
	 * Gets the servlet context.
	 *
	 * @return the servlet context
	 */
	public static ServletContext getServletContext() {
		return (ServletContext) JKThreadLocal.getValue(MvcConstants.ATTRIBUTE_SERVLET_CONTEXT);
	}

	/**
	 * Gets the current servlet request.
	 *
	 * @return the current servlet request
	 */
	public static HttpServletRequest getCurrentServletRequest() {
		return (HttpServletRequest) JKThreadLocal.getValue(MvcConstants.ATTRIBUTE_REQUEST);
	}

	/**
	 * Gets the current session.
	 *
	 * @return the current session
	 */
	public static HttpSession getCurrentSession() {
		return (HttpSession) JKThreadLocal.getValue(MvcConstants.ATTRIBUTE_SESSION);
	}

}
