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
import java.util.logging.Logger;

import javax.faces.application.ResourceDependency;
import javax.faces.component.NamingContainer;
import javax.faces.component.UIComponent;
import javax.faces.component.UIPanel;
import javax.faces.context.FacesContext;

import com.jk.annotations.Author;
import com.jk.faces.components.UIComponentWrapper;

/**
 * <B>UILayout</B> is <B>UIPanel</B> that is wrapped by
 * <B>JKComponentWrapper</B>.
 * <P/>
 *
 * @author Jalal H. Kiswani
 * @version 1.0
 * @see <a href=
 *      "http://docs.oracle.com/javaee/6/api/javax/faces/component/UIPanel.html">
 *      UIPanel</a> , <a href=
 *      "http://docs.oracle.com/javaee/6/api/javax/faces/component/NamingContainer.html"
 *      >NamingContainer</a>
 */
@Author(name = "Jalal Kiswani", date = "26/8/2014", version = "1.0")
@ResourceDependency(library = "its", name = "jk.css")
public class UILayout extends UIPanel implements NamingContainer {

	/**
	 * The Enum Properties.
	 *
	 * @author Jalal Kiswani
	 */
	enum Properties {

		/** The prepend id. */
		prependId;
	}

	/** The logger. */
	protected Logger logger = Logger.getLogger(getClass().getName());

	/** The wrapper. */
	private UIComponentWrapper wrapper = new UIComponentWrapper(this);

	/**
	 * Overrides encodeBegin to insure that no body is calling the encode end
	 * manually.
	 *
	 * @param context
	 *            instance of {@link FacesContext}
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	@Override
	public void encodeBegin(final FacesContext context) throws IOException {
		// to insure that no body is calling the encode end manually
		super.encodeBegin(context);
	}

	/**
	 * Overrides encodeChildren to insure that no body is calling the encode end
	 * manually.
	 *
	 * @param context
	 *            instance of {@link FacesContext}
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	@Override
	public void encodeChildren(final FacesContext context) throws IOException {
		// to insure no body is calling encode manually
	}

	/**
	 * Overrides encodeEnd to insure that no body is calling the encode end
	 * manually.
	 *
	 * @param context
	 *            instance of {@link FacesContext}
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	@Override
	public void encodeEnd(final FacesContext context) throws IOException {
		// to insure that no body is calling the encode end manually
		encodeAll(context);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see javax.faces.component.UIComponent#getContainerClientId(javax.faces.
	 * context .FacesContext)
	 */
	@Override
	public String getContainerClientId(final FacesContext context) {
		if (isPrependId()) {
			return super.getContainerClientId(context);
		} else {
			UIComponent parent = getParent();
			while (parent != null) {
				if (parent instanceof NamingContainer) {
					return parent.getContainerClientId(context);
				}
				parent = parent.getParent();
			}
		}
		return null;
	}

	/**
	 * TODO: not clear.
	 *
	 * @return the renders children
	 */
	@Override
	public boolean getRendersChildren() {
		return true;
	}

	/**
	 * returns the component wrapper.
	 *
	 * @return {@link UIComponentWrapper}
	 */
	public UIComponentWrapper getWrapper() {
		return this.wrapper;
	}

	/**
	 * Checks if is prepend id.
	 *
	 * @return true, if is prepend id
	 */
	public boolean isPrependId() {
		return (Boolean) getStateHelper().eval(Properties.prependId, true);

	}

	/**
	 * Sets the prepend id.
	 *
	 * @param prependId
	 *            the new prepend id
	 */
	public void setPrependId(final boolean prependId) {
		getStateHelper().put(Properties.prependId, prependId);
	}

	/**
	 * sets the component wrapper.
	 *
	 * @param wrapper
	 *            instance of {@link UIComponentWrapper}
	 */
	public void setWrapper(final UIComponentWrapper wrapper) {
		this.wrapper = wrapper;
	}

}
