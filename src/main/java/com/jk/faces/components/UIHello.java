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

import javax.faces.component.FacesComponent;
import javax.faces.component.UIPanel;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import com.jk.annotations.Author;

/**
 * <B>JKHello</B> is a UI component that display Hello statement from JK
 * <P/>
 * .
 *
 * @author Jalal H. Kiswani
 * @version 1.0
 * @see <a href=
 *      "http://docs.oracle.com/javaee/6/api/javax/faces/component/UIPanel.html">
 *      UIPanel</a>
 */
@Author(name = "Jalal Kiswani", date = "26/8/2014", version = "1.0")
@FacesComponent(value = UIHello.COMPONENT_TYPE)
public class UIHello extends UIPanel {

	/** The Constant COMPONENT_TYPE. */
	public static final String COMPONENT_TYPE = "jk.hello";

	/**
	 * the default constructure.
	 */
	public UIHello() {
	}

	/**
	 * this method is responsible for rendering {@link UIHello} component.
	 *
	 * @param context
	 *            an instance from {@link FacesContext}
	 * @throws IOException
	 *             if an input/output error occurs during response writing.
	 */
	@Override
	public void encodeAll(final FacesContext context) throws IOException {
		final ResponseWriter writer = context.getResponseWriter();
		writer.startElement("div", null);
		writer.writeText("Hello From JK", null);
		writer.endElement("div");
	}

}
