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
package com.jk.faces.renderers;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.primefaces.component.panelgrid.PanelGrid;
import org.primefaces.component.panelgrid.PanelGridRenderer;

// TODO: Auto-generated Javadoc
/**
 * The Class JKPanelGridRenderer.
 *
 * @author Jalal Kiswani
 */
/*
 * Extends PrimeFaces renderer to fix the bug in non rendered components which
 * cause final tr not to be printed
 */
public class JKPanelGridRenderer extends PanelGridRenderer {

	/**
	 * Instantiates a new JK panel grid renderer.
	 */
	public JKPanelGridRenderer() {
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.primefaces.component.panelgrid.PanelGridRenderer#encodeDynamicBody(
	 * javax.faces.context.FacesContext,
	 * org.primefaces.component.panelgrid.PanelGrid, int)
	 */
	@Override
	public void encodeDynamicBody(final FacesContext context, final PanelGrid grid, final int columns) throws IOException {
		final ResponseWriter writer = context.getResponseWriter();
		final String columnClassesValue = grid.getColumnClasses();
		final String[] columnClasses = columnClassesValue == null ? new String[0] : columnClassesValue.split(",");

		int i = 0;
		boolean closed = false;
		for (final UIComponent child : grid.getChildren()) {
			if (!child.isRendered()) {
				continue;
			}

			int colMod = i % columns;
			if (colMod == 0) {
				writer.startElement("tr", null);
				closed = false;
				writer.writeAttribute("class", PanelGrid.TABLE_ROW_CLASS, null);
				writer.writeAttribute("role", "row", null);
			}

			final String columnClass = colMod < columnClasses.length ? PanelGrid.CELL_CLASS + " " + columnClasses[colMod].trim()
					: PanelGrid.CELL_CLASS;
			writer.startElement("td", null);
			writer.writeAttribute("role", "gridcell", null);
			writer.writeAttribute("class", columnClass, null);
			child.encodeAll(context);
			writer.endElement("td");

			i++;
			colMod = i % columns;

			if (colMod == 0) {
				closed = true;
				writer.endElement("tr");
			}
		}
		if (!closed) {
			writer.endElement("tr");
		}

	}

}
