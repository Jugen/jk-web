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

import javax.faces.component.UIPanel;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import com.jk.util.annotations.Author;
import com.jk.web.faces.components.TagAttributeConstants;
import com.jk.web.faces.components.UIComponentWrapper;

// TODO: Auto-generated Javadoc
/**
 * <B>UIAbstractRegion</B> is a panel that manages the layout of its child
 * components with a defined style
 * <P>
 * <B>UIAbstractRegion</B> is the base class for all regions classes.
 *
 * @author Jalal H. Kiswani
 * @version 1.0
 * @see <a href=
 *      "http://docs.oracle.com/javaee/6/api/javax/faces/component/UIPanel.html">
 *      UIPanel</a>
 */
@Author(name = "Jalal Kiswani", date = "26/8/2014", version = "1.0")
public class UIAbstractRegion extends UIPanel {

	/** The wrapper. */
	private UIComponentWrapper wrapper = new UIComponentWrapper(this);

	/** The respect width. */
	boolean respectWidth;

	/** The respect hight. */
	boolean respectHight;

	/** The width. */
	String width;

	/** The height. */
	String height;

	/** The stretch width. */
	boolean stretchWidth;

	/** The stretch height. */
	boolean stretchHeight;

	/** The full span. */
	boolean fullSpan;

	// Alignment align = Alignment.CENTER;;

	/**
	 * Override the render of the panel component, by wrapping it into div tag
	 * then add the following attributes:
	 * <ul>
	 * id attribute with value equal to
	 * {@link javax.faces.component.UIComponent#getClientId()}
	 * </ul>
	 * <ul>
	 * alignment to center
	 * </ul>
	 * then call the panel {@link UIPanel#encodeEnd(FacesContext)} method
	 * <P>
	 *
	 * @param context
	 *            , <code>FacesContext</code> for the response we are creating
	 * @exception IOException
	 *                Signals that an I/O exception has occurred.
	 */
	@Override
	public void encodeAll(final FacesContext context) throws IOException {
		if (!isRendered()) {
			return;
		}
		final ResponseWriter writer = context.getResponseWriter();
		writer.startElement("div", null);
		writer.writeAttribute("id", getClientId(), null);
		getWrapper().writeAttribute(TagAttributeConstants.ALIGN, getAlign());
		encodeChildren(context);
		writer.endElement("div");

	}

	/**
	 * Gets the align.
	 *
	 * @return the align
	 */
	private String getAlign() {
		if (getAttributes().get("align") != null) {
			return getAttributes().get("align").toString();
		}
		return "center";
	}

	/**
	 * Returns the region's height
	 * <P>
	 * .
	 *
	 * @return {@link UIAbstractRegion#height}
	 */
	public String getHeight() {
		return this.height;
	}

	/**
	 * Returns the region's width
	 * <P>
	 * .
	 *
	 * @return {@link UIAbstractRegion#width}
	 */
	public String getWidth() {
		return this.width;
	}

	/**
	 * Returns the component wrapper.
	 * <P>
	 *
	 * @return wrapper
	 * @see UIComponentWrapper
	 */
	public UIComponentWrapper getWrapper() {
		return this.wrapper;
	}

	/**
	 * Checks whether or not the region have a full span.
	 * <P>
	 *
	 * @return {@link UIAbstractRegion#fullSpan}
	 */
	public boolean isFullSpan() {
		return this.fullSpan;
	}

	/**
	 * Checks whether or not the region have a respected height, if
	 * <code>true</code> region's height will be set to the defined
	 * {@link UIAbstractRegion#height}.
	 * <P>
	 *
	 * @return respectHight
	 */
	public boolean isRespectHight() {
		return this.respectHight;
	}

	/**
	 * Checks whether or not the region have a respected width, if
	 * <code>true</code> the region's width will be set to the defined
	 * {@link UIAbstractRegion#width}.
	 * <P>
	 *
	 * @return respectWidth
	 */
	public boolean isRespectWidth() {
		return this.respectWidth;
	}

	/**
	 * Checks whether or not the region's height can be stretched.
	 * <P>
	 *
	 * @return {@link UIAbstractRegion#stretchHeight}
	 */
	public boolean isStretchHeight() {
		return this.stretchHeight;
	}

	/**
	 * Checks whether or not the region's width can be stretched.
	 * <P>
	 *
	 * @return stretchWidth
	 */
	public boolean isStretchWidth() {
		return this.stretchWidth;
	}

	/**
	 * Specify whether or not the region have a full span.
	 * <P>
	 *
	 * @param fullSpan
	 *            the new full span
	 */
	public void setFullSpan(final boolean fullSpan) {
		this.fullSpan = fullSpan;
	}

	/**
	 * Sets the region's height
	 * <P>
	 * .
	 *
	 * @param height
	 *            the new height
	 */
	public void setHeight(final String height) {
		this.height = height;
	}

	/**
	 * Specify whether or not the region have a respected height.
	 * <P>
	 *
	 * @param respectHight
	 *            if true the region's height will be set to the defined
	 *            {@link UIAbstractRegion#height}.
	 */
	public void setRespectHight(final boolean respectHight) {
		this.respectHight = respectHight;
	}

	/**
	 * Specify whether or not the region have a respected width.
	 * <P>
	 *
	 * @param respectWidth
	 *            if true the region's width will be set to the defined
	 *            {@link UIAbstractRegion#width}.
	 */
	public void setRespectWidth(final boolean respectWidth) {
		this.respectWidth = respectWidth;
	}

	/**
	 * Specify whether or not the region's height can be stretched, if
	 * <code>true</code> region's height will be set to 100%.
	 * <P>
	 *
	 * @param stretchHeight
	 *            the new stretch height
	 */
	public void setStretchHeight(final boolean stretchHeight) {
		this.stretchHeight = stretchHeight;
	}

	/**
	 * Specify whether or not the region's width can be stretched,if
	 * <code>true</code> region's {@link UIAbstractRegion#width} will be set to
	 * 100%.
	 * <P>
	 *
	 * @param stretchWith
	 *            the new stretch width
	 */
	public void setStretchWidth(final boolean stretchWith) {
		this.stretchWidth = stretchWith;
	}

	/**
	 * Sets the region's width
	 * <P>
	 * .
	 *
	 * @param width
	 *            the new width
	 */
	public void setWidth(final String width) {
		this.width = width;
	}

	/**
	 * Sets the component wrapper.
	 * <P>
	 *
	 * @param wrapper
	 *            the new wrapper
	 * @see UIComponentWrapper
	 */
	public void setWrapper(final UIComponentWrapper wrapper) {
		this.wrapper = wrapper;
	}

}
