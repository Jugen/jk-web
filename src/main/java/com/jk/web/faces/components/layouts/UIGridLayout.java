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
package com.jk.web.faces.components.layouts;

import java.io.IOException;
import java.util.List;

import javax.faces.component.FacesComponent;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import com.jk.util.annotations.Author;

// TODO: Auto-generated Javadoc
/**
 * <B>UIGridLayout</B> is a UILayout that manages the layout of its child
 * components in <code>Grid</code> layout.
 * <P/>
 *
 * @author Jalal H. Kiswani
 * @version 1.0
 * @see UIAbstractRegion
 */
@Author(name = "Jalal Kiswani", date = "26/8/2014", version = "1.0")
@FacesComponent("jk.layout.grid")
public class UIGridLayout extends UILayout {

	/** The rows. */
	private int rows;

	/** The cols. */
	private int cols = 1;

	/**
	 * This method is responsible for rendering {@link UIGridLayout} component
	 * and all its children that return <code>true</code> from
	 * {@link UIComponent#isRendered()}, in <code>Grid</code> layout.
	 * <P/>
	 * It gets the number of columns returned from
	 * {@link UIGridLayout#getCols()} and the number of rows returned from
	 * {@link UIGridLayout#getRows()} to construct a <code>Grid</code>, and
	 * iterate on the child components that return <code>true</code> from
	 * {@link UIComponent#isRendered()} and render them on the <code>Grid</code>
	 * cells
	 *
	 * @param context
	 *            instance of {@link FacesContext}
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	@Override
	public void encodeAll(final FacesContext context) throws IOException {
		// the below section copied from the UIComponent
		if (context == null) {
			throw new NullPointerException();
		}
		if (!isRendered()) {
			return;
		}
		final ResponseWriter writer = context.getResponseWriter();

		writer.startElement("table", null);
		writer.writeAttribute("id", getClientId(), null);
		int childsComponentIndex = 0;
		final List<UIComponent> children = getChildren();
		for (int i = 0; i < this.rows; i++) {
			writer.startElement("tr", null);

			writer.writeAttribute("height", getHeightAttribute(), null);
			for (int j = 0; j < this.cols; j++) {
				writer.startElement("td", null);
				writer.writeAttribute("valign", "top", null);
				writer.writeAttribute("width", getWidthAttribute(), null);
				if (childsComponentIndex < getChildCount()) {
					final UIComponent uiComponent = children.get(childsComponentIndex++);
					uiComponent.getAttributes().put("style", "width:100%;height:100%;");
					uiComponent.encodeAll(context);
				}
				writer.endElement("td");
			}
			writer.endElement("tr");
		}
		writer.endElement("table");
	}

	/**
	 * gets the number of columns.
	 *
	 * @return the cols
	 */
	public int getCols() {
		return this.cols;
	}

	/**
	 * Gets the height attribute.
	 *
	 * @return the height attribute
	 */
	private Object getHeightAttribute() {
		if (this.cols != 0) {
			return 100 / this.rows + "%";
		}
		return "";
	}

	/**
	 * return the number of rows.
	 *
	 * @return the rows
	 */
	public int getRows() {
		return this.rows;
	}

	/**
	 * Gets the width attribute.
	 *
	 * @return the width attribute
	 */
	private Object getWidthAttribute() {
		if (this.cols != 0) {
			return 100 / this.cols + "%";
		}
		return "";
	}

	/**
	 * sets the number of columns.
	 *
	 * @param cols
	 *            the new cols
	 */
	public void setCols(final int cols) {
		this.cols = cols;
	}

	/**
	 * sets the number of rows.
	 *
	 * @param rows
	 *            the new rows
	 */
	public void setRows(final int rows) {
		this.rows = rows;
	}

}
