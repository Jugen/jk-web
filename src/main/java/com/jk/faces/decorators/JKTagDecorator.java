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
package com.jk.faces.decorators;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import java.util.logging.Logger;

import javax.faces.view.facelets.Tag;
import javax.faces.view.facelets.TagAttribute;
import javax.faces.view.facelets.TagAttributes;
import javax.faces.view.facelets.TagDecorator;

import com.jk.annotations.Author;
import com.sun.faces.facelets.tag.TagAttributeImpl;
import com.sun.faces.facelets.tag.TagAttributesImpl;

/**
 * <B>JKTagDecorator</B> this class represents the JK implementation for
 * <code>JKTagDecorator</code>.
 *
 * @author Jalal H. Kiswani
 * @see <a href=
 *      "http://docs.oracle.com/javaee/7/api/javax/faces/view/facelets/TagDecorator.html">
 *      TagDecorator</a>
 */
@Author(name = "Jalal Kiswani", date = "3/9/2014", version = "1.0")
public final class JKTagDecorator implements TagDecorator {
	
	/** The Constant Instance. */
	public final static JKTagDecorator Instance = new JKTagDecorator();

	/** The logger. */
	Logger logger = Logger.getLogger(getClass().getName());

	/**
	 * Instantiates a new JK tag decorator.
	 */
	public JKTagDecorator() {
		super();
	}

	/**
	 * Unfortunaly , this didnt work since failuer occures on the persing level
	 * if perfix not bound.
	 *
	 * @param tag
	 *            the tag
	 * @return the tag
	 */
	/**
	 * <code>JK_NAMESPACE</code>.
	 *
	 * @param tag
	 *            the tag
	 * @return the tag
	 * @see com.sun.facelets.tag.TagDecorator#decorate(com.sun.facelets.tag.Tag)
	 */
	@Override
	public Tag decorate(final Tag tag) {
		this.logger.fine("decorate tag :".concat(tag.getLocalName()));
		return tag;
	}


}
