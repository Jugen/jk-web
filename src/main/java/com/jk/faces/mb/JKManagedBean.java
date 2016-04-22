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

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 * The Class JKManagedBean.
 *
 * @author Jalal Kiswani
 */
public class JKManagedBean {

	/**
	 * Success.
	 *
	 * @param message
	 *            the message
	 */
	public void success(String message) {
		FacesMessage msg = new FacesMessage(message);
		msg.setSeverity(FacesMessage.SEVERITY_INFO);
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	/**
	 * Warning.
	 *
	 * @param message
	 *            the message
	 */
	public void warning(String message) {
		FacesMessage msg = new FacesMessage(message);
		msg.setSeverity(FacesMessage.SEVERITY_WARN);
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	/**
	 * Error.
	 *
	 * @param message
	 *            the message
	 */
	public void error(String message) {
		FacesMessage msg = new FacesMessage(message);
		msg.setSeverity(FacesMessage.SEVERITY_ERROR);
		FacesContext.getCurrentInstance().addMessage(null, msg);
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
		return outcome.concat("?faces-redirect=true");
	}

	/**
	 * Gets the from session.
	 *
	 * @param name
	 *            the name
	 * @return the from session
	 */
	public Object getFromSession(String name) {
		return FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(name);
	}

	/**
	 * Gets the from request.
	 *
	 * @param name
	 *            the name
	 * @return the from request
	 */
	public Object getFromRequest(String name) {
		return FacesContext.getCurrentInstance().getExternalContext().getRequestMap().get(name);
	}

	/**
	 * Gets the from appliaction.
	 *
	 * @param name
	 *            the name
	 * @return the from appliaction
	 */
	public Object getFromAppliaction(String name) {
		return FacesContext.getCurrentInstance().getExternalContext().getApplicationMap().get(name);
	}

	/**
	 * Gets the paramter from request.
	 *
	 * @param name
	 *            the name
	 * @return the paramter from request
	 */
	public String getParamterFromRequest(String name) {
		return FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(name);
	}

}
