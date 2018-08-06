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
package com.jk.faces.components.layouts;

import java.io.IOException;
import java.util.List;
import java.util.Vector;

import javax.faces.component.FacesComponent;
import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlOutputLabel;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.primefaces.component.outputlabel.OutputLabel;

import com.jk.faces.components.TagAttributeConstants;
import com.jk.faces.util.JKJsfUtil;
import com.jk.util.annotations.Author;

// TODO: Auto-generated Javadoc
/**
 * <B>UIFormLayout</B> is a UILayout that manages the layout of its child
 * components in <code>Form</code> layout.
 * <P/>
 * <B>UIFormLayout</B> is composed mainly from two UIRegions, Fields region
 * <code>fieldsRegion</code> for fields components and Actions region
 * <code>actionsRegion</code> for actions components, Also it can have a title
 * <code>title</code> in the top of the form.
 * <P/>
 * Fields region is a region for layout a fields components in a defined number
 * of columns <code>cols</code>, if <code>addLables</code> is true, the fields
 * will be located in form label and field pair.
 * <P/>
 * Actions region is a region for layout an action components.
 *
 * @author Jalal H. Kiswani
 * @version 1.0
 * @see UIAbstractRegion
 */
@Author(name = "Jalal Kiswani", date = "28/8/2014", version = "1.0")
@FacesComponent(value = UIFormLayout.JK_LAYOUT_FORM_LAYOUT)
public class UIFormLayout extends UILayout {

	/** The Constant JK_LAYOUT_FORM_LAYOUT. */
	public static final String JK_LAYOUT_FORM_LAYOUT = "jk.layout.formLayout";

	/** The cols. */
	private int cols = 1;

	/** The fields region. */
	private UIFields fieldsRegion;

	/** The actions region. */
	private UIActions actionsRegion;

	/** The title. */
	private String title;

	/** The cell width. */
	private int cellWidth;

	/**
	 * This method is responsible for rendering {@link UIFormLayout} component
	 * and all its children that return <code>true</code> from
	 * {@link UIComponent#isRendered()}, in <code>Form</code> layout.
	 * <P/>
	 * <li>the first section is the form title which are returned from
	 * {@link UIFormLayout#getTitle()}
	 * </ul>
	 * <li>The second section is the fields region, all children components that
	 * return <code>true</code> from {@link UIComponent#isRendered()}, they will
	 * be rendered in columns and rows form,<BR/>
	 * the number of columns are returned from {@link UIFormLayout#getCols()},
	 * if {@link UIFormLayout#addLables} return <code>true</code> a label will
	 * be rendered before each component
	 * </ul>
	 * <li>The third section is the action region, all children components that
	 * return <code>true</code> from {@link UIComponent#isRendered()}, will be
	 * rendered in one row
	 * </ul>
	 *
	 * @param context
	 *            instance of <code>FacesContext</code> for the response we are
	 *            creating
	 * @exception IOException
	 *                if {@link ResponseWriter} throws {@link IOException}
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
		int cols = this.cols;
		if (JKJsfUtil.getBooleanAttribute(this, TagAttributeConstants.ADD_LABELS, true) == true) {
			cols *= 2;
		}
		fetchRegions(context);
		final ResponseWriter writer = context.getResponseWriter();
		writer.startElement("table", null);
		// getWrapper().writeAttribute("id", getClientId());
		// JSFUtil.evaluateExpressionToObject(getValueExpression(null));

		final Object title = JKJsfUtil.getAttribute(this, getStateHelper(), "title");
		if (title != null) {
			JKJsfUtil.addFullRow(title.toString(), cols, "");
		}
		int childsComponentIndex = 0;
		if (this.fieldsRegion == null) {
			this.logger.warning("No FieldRegion found for UIFormLayout : " + getId());
			return;
		}
		final List<UIComponent> components = getComponentsToRender();
		// logger.info("Rendiring formlayout Components.size() " +
		// components.size());

		while (childsComponentIndex < components.size() && cols > 0) {
			// logger.info("ChildsComponentIndex : " + childsComponentIndex +
			// " Cols " + cols);
			writer.startElement("tr", null);
			for (int j = 0; j < cols && childsComponentIndex < components.size(); j++) {
				final UIComponent uiComponent = components.get(childsComponentIndex++);
				final int colspan = JKJsfUtil.getIntegerAttribute(uiComponent, TagAttributeConstants.COLSPAN, 1);
				writer.startElement("td", null);
				if (colspan > 1) {
					JKJsfUtil.writeAttribue(uiComponent, TagAttributeConstants.COLSPAN, null, colspan);
				}
				uiComponent.encodeAll(context);
				writer.endElement("td");
				// Mesh fahem keef momken afahmak leesh !!!!
				j += colspan - 1;
			}
			writer.endElement("tr");
		}
		JKJsfUtil.addFullRow(this.actionsRegion, cols, "");
		writer.endElement("table");
	}

	/**
	 * Fetch regions.
	 *
	 * @param context
	 *            the context
	 */
	private void fetchRegions(final FacesContext context) {
		final List<UIComponent> children = getChildren();
		final List<UIComponent> componentsOutOfFieldsTRegion = new Vector();
		for (final UIComponent uiComponent : children) {
			if (uiComponent instanceof UIFields) {
				this.fieldsRegion = (UIFields) uiComponent;
			} else if (uiComponent instanceof UIActions) {
				this.actionsRegion = (UIActions) uiComponent;
			} else {
				componentsOutOfFieldsTRegion.add(uiComponent);
			}
		}
		if (this.fieldsRegion == null) {
			this.fieldsRegion = new UIFields();
			this.fieldsRegion.getChildren().addAll(componentsOutOfFieldsTRegion);
		}

	}

	/**
	 * gets the cell width.
	 *
	 * @return cellWidth
	 */
	public int getCellWidth() {
		return this.cellWidth;
	}

	/**
	 * gets number of columns in fields region.
	 *
	 * @return cols
	 */
	public int getCols() {
		return this.cols;
	}

	/**
	 * Gets the components to render.
	 *
	 * @return the components to render
	 */
	private List<UIComponent> getComponentsToRender() {
		final List<UIComponent> children = this.fieldsRegion.getChildren();
		final List<UIComponent> componentsToRender = new Vector();
		for (final UIComponent uiComponent : children) {
			if (uiComponent instanceof OutputLabel) {
				uiComponent.setRendered(findComponent(((HtmlOutputLabel) uiComponent).getFor()).isRendered());
			}
			if (uiComponent.isRendered()) {
				if (JKJsfUtil.getBooleanAttribute(uiComponent, TagAttributeConstants.ATTRIBUTE_EXPLODE_CHILDS, false)) {
					componentsToRender.addAll(uiComponent.getChildren());
				} else {
					componentsToRender.add(uiComponent);
				}
			}
		}
		return componentsToRender;
	}

	/**
	 * Gets the table width.
	 *
	 * @return the table width
	 */
	private int getTableWidth() {
		return getCellWidth() * getCols();
	}

	/**
	 * Gets the title.
	 *
	 * @return the title
	 */
	public String getTitle() {
		return this.title;
	}

	/**
	 * sets the cell width.
	 *
	 * @param cellWidth
	 *            the new cell width
	 */
	public void setCellWidth(final int cellWidth) {
		this.cellWidth = cellWidth;
	}

	/**
	 * sets number of columns fields region.
	 *
	 * @param cols
	 *            the new cols
	 */
	public void setCols(final int cols) {
		this.cols = cols;
	}

	/**
	 * Sets the title.
	 *
	 * @param title
	 *            the new title
	 */
	public void setTitle(final String title) {
		this.title = title;
	}

}
