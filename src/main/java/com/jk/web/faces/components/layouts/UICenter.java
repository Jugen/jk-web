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

import javax.faces.component.FacesComponent;

import com.jk.util.annotations.Author;

// TODO: Auto-generated Javadoc
/**
 * <B>UICenter</B> is a UIRegion that manages the layout of its child components
 * with a stretched height and width, and with respected height and width.
 * <P/>
 *
 * @author Jalal H. Kiswani
 * @version 1.0
 * @see UIAbstractRegion
 */
@Author(name = "Jalal Kiswani", date = "26/8/2014", version = "1.0")
@FacesComponent("jk.layout.center")
public class UICenter extends UIAbstractRegion {

	/**
	 * Returns true.
	 *
	 * @return respectHight
	 */
	@Override
	public boolean isRespectHight() {
		return true;
	}

	/**
	 * Returns true.
	 *
	 * @return respectWidth
	 */
	@Override
	public boolean isRespectWidth() {
		return true;
	}

	/**
	 * Returns true, in other words the region height is 100%.
	 *
	 * @return stretchHeight
	 */
	@Override
	public boolean isStretchHeight() {
		return true;
	}

	/**
	 * Returns true, in other words the region width is 100%.
	 *
	 * @return stretchWidth
	 */
	@Override
	public boolean isStretchWidth() {
		return true;
	}
}
