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
package com.jk.faces.mb;

import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import com.jk.util.exceptions.JKException;
import com.jk.util.locale.JKMessage;

// TODO: Auto-generated Javadoc
/**
 * The Class JKManagedBean.
 *
 * @author Jalal Kiswani
 */
public class JKManagedBean {

	/**
	 * Error.
	 *
	 * @param message
	 *            the message
	 */
	public void error(final String message) {
		final FacesMessage msg = new FacesMessage(message);
		msg.setSeverity(FacesMessage.SEVERITY_ERROR);
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	/**
	 * Gets the from appliaction.
	 *
	 * @param name
	 *            the name
	 * @return the from appliaction
	 */
	public Object getFromAppliaction(final String name) {
		return context().getApplicationMap().get(name);
	}

	/**
	 * Gets the from request.
	 *
	 * @param name
	 *            the name
	 * @return the from request
	 */
	public Object getFromRequest(final String name) {
		return context().getRequestMap().get(name);
	}

	/**
	 * Gets the from session.
	 *
	 * @param name
	 *            the name
	 * @return the from session
	 */
	public Object getFromSession(final String name) {
		return context().getSessionMap().get(name);
	}

	/**
	 * Gets the paramter from request.
	 *
	 * @param name
	 *            the name
	 * @return the paramter from request
	 */
	public String getParamterFromRequest(final String name) {
		return context().getRequestParameterMap().get(name);
	}

	protected ExternalContext context() {
		return FacesContext.getCurrentInstance().getExternalContext();
	}
	
	public HttpServletRequest request() {
		return (HttpServletRequest) context().getRequest();
	}

	/**
	 * Redirect.
	 *
	 * @param outcome
	 *            the outcome
	 * @return the string
	 */
	public String redirect(String outcome) {
		if (outcome == null) {
			outcome = "";
		}
		FacesContext context = FacesContext.getCurrentInstance();
		context.getExternalContext().getFlash().setKeepMessages(true);
		return outcome.concat("?faces-redirect=true");
	}

	/**
	 * Success.
	 *
	 * @param message
	 *            the message
	 */
	public void success(final String message) {
		final FacesMessage msg = new FacesMessage(JKMessage.get(message));
		msg.setSeverity(FacesMessage.SEVERITY_INFO);
		FacesContext.getCurrentInstance().addMessage(null,  msg);
	}

	/**
	 * Warning.
	 *
	 * @param message
	 *            the message
	 */
	public void warning(final String message) {
		final FacesMessage msg = new FacesMessage(JKMessage.get(message));
		msg.setSeverity(FacesMessage.SEVERITY_WARN);
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}


	/**
	 * Handle exception.
	 *
	 * @param e the e
	 */
	protected void handleException(Exception e) {
		if (e instanceof RuntimeException) {
			throw (RuntimeException) e;
		}
		throw new JKException(e);
	}
	
	/**
	 * Gets the param.
	 *
	 * @param paramName the param name
	 * @return the param
	 */
	protected String getParam(String paramName) {
		Map<String, String> params = context().getRequestParameterMap();
		return params.get(paramName);
	}
	
	/**
	 * Gets the context path.
	 *
	 * @return the context path
	 */
	protected String getContextPath() {
		return ((HttpServletRequest)context().getRequest()).getContextPath();
	}
}
