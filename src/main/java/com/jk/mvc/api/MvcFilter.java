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

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import com.jk.util.JK;
import com.jk.util.JKObjectUtil;
import com.jk.util.annotations.AnnotationDetector;
import com.jk.util.annotations.AnnotationHandler;
import com.jk.util.logging.JKLogger;
import com.jk.util.logging.JKLoggerFactory;
import com.jk.util.thread.JKThreadLocal;

// TODO: Auto-generated Javadoc
/**
 * The Class MvcFilter.
 */
@WebFilter(urlPatterns = "*.jsp")
public class MvcFilter implements Filter {
	
	/** The logger. */
	JKLogger logger = JKLoggerFactory.getLogger(getClass());
	
	/** The bindings. */
	Map<String, MvcPageModelBinding> bindings = new LinkedHashMap<>();
	
	/** The current page. */
	String currentPage;

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		AnnotationDetector.scan(MvcModel.class, new String[] { "com.jk" }, new AnnotationHandler() {

			@Override
			public void handleAnnotationFound(String className) {
				logger.debug("New MvcModel detected :" + className);
				try {
					Class<?> model = Class.forName(className);
					String pagePath = model.getAnnotation(MvcModel.class).pagePath();
					MvcPageModelBinding pageBinding = new MvcPageModelBinding(pagePath, model);
					bindings.put(pagePath, pageBinding);
					logger.debug("Add new MVC Binding ", pagePath, className);

				} catch (ClassNotFoundException e) {
					JK.throww(e);
				}
			}
		});
	}

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		MvcUtil.initRequestInfo(req);
		String pageUrl = req.getRequestURI();
		if (pageUrl.indexOf("?") != -1) {
			pageUrl = pageUrl.substring(0, pageUrl.indexOf("?"));
		}
		MvcPageModelBinding binding = bindings.get(pageUrl);
		if (binding == null) {
			logger.debug("No binding available for page: " + pageUrl);
			chain.doFilter(request, response);
		} else {
			logger.debug("Binding available for page: " + pageUrl + " with model: " + binding.getModel().getName());
			Map<String, MvcUserBinding> map = MvcUtil.getUserBinding(req);
			MvcUserBinding pageBinding = map.get(pageUrl);
			if (pageBinding == null) {
				logger.debug("User binding object doesnot exist for this user for page : " + pageUrl + ", creating new one");
				Object instance = createModelInstance(req, binding);
				pageBinding = new MvcUserBinding(binding, instance);
				map.put(pageUrl, pageBinding);
				// varilable name will be classname to lowercase
			}

			Object instance = pageBinding.getModelObject();
			//call run method on the model
			JKObjectUtil.callMethod(instance, "run");
			String variableName = binding.getModel().getSimpleName().toLowerCase();
			req.setAttribute(variableName, instance);
			chain.doFilter(request, response);
		}
	}

	/**
	 * Creates the model instance.
	 *
	 * @param req the req
	 * @param binding the binding
	 * @return the object
	 */
	private Object createModelInstance(HttpServletRequest req, MvcPageModelBinding binding) {
		Object instance = JKObjectUtil.newInstance(binding.getModel());
		if (JKObjectUtil.isMethodDirectlyExists(instance, "init")) {
			logger.debug("init method exists, calling it on class : " + instance.getClass().getName());
			JKObjectUtil.callMethod(instance, "init");
		}
		return instance;
	}

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#destroy()
	 */
	@Override
	public void destroy() {

	}

}
