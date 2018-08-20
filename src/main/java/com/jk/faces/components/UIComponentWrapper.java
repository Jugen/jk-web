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
package com.jk.faces.components;

import java.io.IOException;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import com.jk.faces.util.JKJsfUtil;
import com.jk.util.annotations.Author;

/**
 * <B>JKComponentWrapper</B> is a component wrapper that wrap JK component.
 * <P/>
 *
 * @author Jalal H. Kiswani
 * @version 1.0
 */
@Author(name = "Jalal Kiswani", date = "28/8/2014", version = "1.0")
public class UIComponentWrapper {

	/**
	 * Excluded attributes for the wrapped components that will not be rendered
	 * to html attributes.
	 */
	String passthroughExcludedAttrs = "com.sun.faces.facelets.MARK_ID";

	/** Wrapped component. */
	UIComponent component;

	/**
	 * constructor that take the component to be wrapped.
	 *
	 * @param componener
	 *            instance of {@link UIComponent}
	 */
	public UIComponentWrapper(final UIComponent componener) {
		setComponent(componener);

	}

	/**
	 * add attribute to the wrapped component with the input <code>key</code>
	 * and <code>value</code>.
	 *
	 * @param key
	 *            the <code>key</code> of the attribute
	 * @param value
	 *            the <code>value</code> of the attribute
	 */
	public void addAttribute(final String key, final Object value) {
		final Object object = getComponent().getAttributes().get(key);
		if (object != null) {
			getComponent().getAttributes().put(key, value);
		}
	}

	/**
	 * get the <code>value</code> of a boolean value attribute.
	 *
	 * @param key
	 *            the attribute <code>key</code>
	 * @param defaultValue
	 *            if the attribute <code>value</code> is null,
	 *            <code>defaultValue</code> will be considered as the attribute
	 *            <code>value</code>
	 * @return the boolean attribute
	 */
	public boolean getBooleanAttribute(final String key, final boolean defaultValue) {
		return JKJsfUtil.getBooleanAttribute(getComponent(), key, defaultValue);
	}

	/**
	 * gets the wrapped component.
	 *
	 * @return the component
	 */
	public UIComponent getComponent() {
		return this.component;
	}

	/**
	 * This method is responsible for render the component attributes after
	 * excluding the attributes in
	 * {@link UIComponentWrapper#passthroughExcludedAttrs}.
	 *
	 * @param context
	 *            the context
	 * @throws IOException
	 *             if an input/output error occurs during response writing.
	 */
	public void renderPassThruAttributes(final FacesContext context) throws IOException {
		final ResponseWriter writer = context.getResponseWriter();
		final Map<String, Object> passThroughAttributes = this.component.getAttributes();
		for (final String key : passThroughAttributes.keySet()) {
			if (!this.passthroughExcludedAttrs.contains(key)) {
				final Object value = passThroughAttributes.get(key);
				writer.writeAttribute(key, value.toString(), null);
			}
		}
	}

	/**
	 * sets the wrapped component.
	 *
	 * @param component
	 *            the new component
	 */
	public void setComponent(final UIComponent component) {
		this.component = component;
	}

	/**
	 * write an attribute to the wrapped component.
	 *
	 * @param key
	 *            the attribute <code>key</code>
	 * @param defaultValue
	 *            the attribute <code>defaultValue</code>
	 * @throws IOException
	 *             if an input/output error occurs during response writing.
	 */
	public void writeAttribute(final String key, final Object defaultValue) throws IOException {
		if (key.equals("id")) {
			// TODO : document the following
			FacesContext.getCurrentInstance().getResponseWriter().writeAttribute("id", this.component.getClientId(), null);
		} else {
			JKJsfUtil.writeAttribue(getComponent(), key, defaultValue);
		}
	}

	/**
	 * write an attribute to the wrapped component.
	 *
	 * @param sourceKey
	 *            the attribute <code>key</code> in
	 *            {@link UIComponent#getAttributes()}
	 * @param targetKey
	 *            the target <code>key</code> in the output HTML Tag, allow
	 *            <code>null</code> in this case <code>sourceKey</code> will be
	 *            considered as <code>targetKey</code>
	 * @param defaultValue
	 *            the default value of the <code>value</code> of the attribute
	 * @throws IOException
	 *             if an input/output error occurs during response writing.
	 */
	public void writeAttribute(final String sourceKey, final String targetKey, final Object defaultValue) throws IOException {
		JKJsfUtil.writeAttribue(this.component, sourceKey, targetKey, defaultValue);
	}

	/**
	 * Method that writes the components attributes final HTML tag.
	 *
	 * @param context
	 *            the context
	 * @param style
	 *            the style
	 * @throws IOException
	 *             if an input/output error occurs during response writing.
	 */
	public void writerAttributes(final FacesContext context, final String style) throws IOException {
		context.getResponseWriter().writeAttribute("id", getComponent().getClientId(), null);
		String css = (String) this.component.getAttributes().get("styleClass");
		if (css == null) {
			css = "";
		}
		if (style != null) {
			css = css.concat(" ").concat(style);
		}
		if (!css.equals("")) {
			context.getResponseWriter().writeAttribute("class", css, null);
		}

		renderPassThruAttributes(context);
	}

}
