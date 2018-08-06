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
package com.jk.faces.components.layouts;

import java.io.IOException;
import java.util.List;

import javax.faces.component.FacesComponent;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import com.jk.util.annotations.Author;

/**
 * <B>UIBorderLayout</B> is a UILayout that manages the layout of its child
 * components, in form of <code>Border</code> layout.
 * <P/>
 * <B>UIBorderLayout</B> is a UILayout that composed of five
 * <code>UIRegions</code> {@link UIStart}, {@link UIEnd}, {@link UICenter},
 * {@link UINorth} and {@link UISouth}
 *
 * @author Jalal H. Kiswani
 * @version 1.0
 * @see UILayout
 */
@Author(name = "Jalal Kiswani", date = "26/8/2014", version = "1.0")
@FacesComponent(value = UIBorderLayout.COMPONENT_TYPE)
public class UIBorderLayout extends UILayout {

	/** The Constant COMPONENT_TYPE. */
	public static final String COMPONENT_TYPE = "jk.layout.border";

	/** The north. */
	private UIAbstractRegion north;

	/** The south. */
	private UIAbstractRegion south;

	/** The center. */
	private UIAbstractRegion center;

	/** The start. */
	private UIAbstractRegion start;

	/** The end. */
	private UIAbstractRegion end;

	/** The colspan. */
	private int colspan;

	/**
	 * this method is responsible for rendering {@link UIBorderLayout} in form
	 * of <code>Border</code> layout.
	 * <P/>
	 * It render {@link UIBorderLayout} child components in five regions
	 * {@link UIStart}, {@link UIEnd}, {@link UICenter}, {@link UINorth} and
	 * {@link UISouth}
	 *
	 * @param context
	 *            instance of {@link FacesContext}
	 * @throws IOException
	 *             if an input/output error occurs during response writing
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
		encodeBegin(context);
		fetchRegions();
		final ResponseWriter writer = context.getResponseWriter();

		writer.startElement("table", null);
		// north
		if (this.north != null && this.north.isRendered()) {
			writer.startElement("tr", null);
			writerRegionCell(context, this.north);
			writer.endElement("tr");
		}
		// to insure that there is not empty row
		if (this.colspan > 0) {
			writer.startElement("tr", null);
			if (this.start != null && this.start.isRendered()) {
				writerRegionCell(context, this.start);
			}
			if (this.center != null && this.center.isRendered()) {
				writerRegionCell(context, this.center);
			}
			if (this.end != null && this.end.isRendered()) {
				writerRegionCell(context, this.end);
			}
			writer.endElement("tr");
		}

		// south
		if (this.south != null) {
			writer.startElement("tr", null);
			writerRegionCell(context, this.south);
			writer.endElement("tr");
		}

		writer.endElement("table");
	}

	/**
	 * Fetch the defined regions identified by BorderLayout.
	 */
	private void fetchRegions() {
		final List<UIComponent> childeren = getChildren();
		for (final UIComponent uiComponent : childeren) {
			if (uiComponent instanceof UINorth) {
				this.north = (UIAbstractRegion) uiComponent;
			}
			if (uiComponent instanceof UISouth) {
				this.south = (UIAbstractRegion) uiComponent;
			}
			if (uiComponent instanceof UICenter) {
				this.center = (UIAbstractRegion) uiComponent;
				this.colspan++;
			}
			if (uiComponent instanceof UIStart) {
				this.start = (UIAbstractRegion) uiComponent;
				this.colspan++;
			}
			if (uiComponent instanceof UIEnd) {
				this.end = (UIAbstractRegion) uiComponent;
				this.colspan++;
			}
		}
	}

	/**
	 * Writer region cell.
	 *
	 * @param context
	 *            the context
	 * @param region
	 *            the region
	 * @throws IOException
	 *             if an input/output error occurs during response writing
	 */
	private void writerRegionCell(final FacesContext context, final UIAbstractRegion region) throws IOException {
		final ResponseWriter writer = context.getResponseWriter();
		writer.startElement("td", null);
		// TODO : check me
		region.getWrapper().writeAttribute("valign", "top");

		// TODO : handle the styleclasss and class
		if (region.isFullSpan()) {
			writer.writeAttribute("colspan", this.colspan, null);
		}
		if (region.isStretchWidth() && region.getWidth() == null) {
			writer.writeAttribute("width", "100%", null);
		} else if (region.isRespectWidth()) {
			writer.writeAttribute("width", region.getWidth(), null);
		}
		if (region.isStretchHeight() && region.getHeight() == null) {
			writer.writeAttribute("height", "100%", null);
		} else if (region.isRespectHight()) {
			writer.writeAttribute("height", region.getHeight(), null);
		}
		// to write the passthrough attributes of the region on the td
		// region.getWrapper().renderPassThruAttributes(context);
		region.encodeAll(context);
		writer.endElement("td");
	}
}
