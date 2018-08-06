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

import javax.faces.component.FacesComponent;

import com.jk.util.annotations.Author;

/**
 * <B>UINorth</B> is a UIRegion that manages the layout of its child components
 * with a stretched width, respected height and full span.
 * <P/>
 *
 * @author Jalal H. Kiswani
 * @version 1.0
 * @see UIAbstractRegion
 */
@Author(name = "Jalal Kiswani", date = "26/8/2014", version = "1.0")
@FacesComponent("jk.layout.north")
public class UINorth extends UIAbstractRegion {

	/**
	 * Returns true.
	 *
	 * @return fullSpan
	 */
	@Override
	public boolean isFullSpan() {
		return true;
	}

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
	 * Returns true, in other words the region width is 100%.
	 *
	 * @return stretchWidth
	 */
	@Override
	public boolean isStretchWidth() {
		return true;
	}

}
