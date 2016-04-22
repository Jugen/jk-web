package com.jk.faces.mb;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class JKManagedBean {

	/**
	 * 
	 * @param message
	 */
	public void success(String message) {
		FacesMessage msg = new FacesMessage(message);
		msg.setSeverity(FacesMessage.SEVERITY_INFO);
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	/**
	 * 
	 * @param message
	 */
	public void warning(String message) {
		FacesMessage msg = new FacesMessage(message);
		msg.setSeverity(FacesMessage.SEVERITY_WARN);
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	/**
	 * 
	 * @param message
	 */
	public void error(String message) {
		FacesMessage msg = new FacesMessage(message);
		msg.setSeverity(FacesMessage.SEVERITY_ERROR);
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	/**
	 * 
	 * @param outcome
	 * @return
	 */
	public String redirect(String outcome) {
		if (outcome == null) {
			outcome = "";
		}
		return outcome.concat("?faces-redirect=true");
	}

	public Object getFromSession(String name) {
		return FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(name);
	}

	public Object getFromRequest(String name) {
		return FacesContext.getCurrentInstance().getExternalContext().getRequestMap().get(name);
	}

	public Object getFromAppliaction(String name) {
		return FacesContext.getCurrentInstance().getExternalContext().getApplicationMap().get(name);
	}

	public String getParamterFromRequest(String name) {
		return FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(name);
	}

}
