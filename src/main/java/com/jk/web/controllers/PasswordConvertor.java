package com.jk.web.controllers;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@FacesConverter("password-convertor")
public class PasswordConvertor implements Converter{

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		return new BCryptPasswordEncoder().encode(value);
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		return null;
	}

}
