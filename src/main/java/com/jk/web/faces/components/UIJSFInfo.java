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
package com.jk.web.faces.components;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import javax.faces.component.FacesComponent;
import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import com.jk.util.JKIOUtil;
import com.jk.util.annotations.Author;
import com.jk.util.logging.JKLogger;
import com.jk.util.logging.JKLoggerFactory;
import com.jk.web.faces.util.JKJsfUtil;

// TODO: Auto-generated Javadoc
/**
 * <B>UIJSFInfo</B> is a {@link UIOutput} component that display JSF information
 * for debugging purposes.
 * <P/>
 *
 * @author Jalal H. Kiswani
 * @version 1.0
 * @see <a href=
 *      "http://docs.oracle.com/javaee/6/api/javax/faces/component/UIOutput.html">
 *      UIOutput</a>
 */
@Author(name = "Jalal Kiswani", date = "3/9/2014", version = "1.0")
@FacesComponent("com.jk.jsf.info")
public class UIJSFInfo extends UIOutput {

	/** The logger. */
	JKLogger logger = JKLoggerFactory.getLogger(getClass());

	/**
	 * this method is responsible for rendering {@link UIJSFInfo} component It
	 * renders JSF debugging information like Jar name, JSF version, ..etc
	 *
	 * @param context
	 *            instance of {@link FacesContext}
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	@Override
	public void encodeEnd(final FacesContext context) throws IOException {
		this.logger.info("@encodeEnd");
		final Map<String, Object> info = JKJsfUtil.getJSFInfo();
		final ResponseWriter w = context.getResponseWriter();
		w.startElement("table", null);
		w.writeAttribute("class", "ui-datatable", null);
		;
		final Set<String> keySet = info.keySet();
		for (final String key : keySet) {
			final Object value = info.get(key);
			w.startElement("tr", null);
			w.startElement("td", null);
			w.writeText(key, null);
			w.endElement("td");

			w.startElement("td", null);
			w.writeText(value.toString(), null);
			w.endElement("td");
			if (value instanceof Class) {
				final String jarFileName = JKIOUtil.findPathJar((Class) value);
				w.startElement("td", null);
				w.writeText(jarFileName, null);
				w.endElement("td");
			}

			w.endElement("tr");
		}
		w.endElement("table");

	}
}
