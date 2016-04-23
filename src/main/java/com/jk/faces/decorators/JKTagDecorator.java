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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.Vector;
import java.util.logging.Logger;

import javax.faces.view.facelets.Tag;
import javax.faces.view.facelets.TagAttribute;
import javax.faces.view.facelets.TagAttributes;
import javax.faces.view.facelets.TagDecorator;

import com.jk.annotations.Author;
import com.jk.exceptions.JKException;
import com.jk.faces.config.JKFacesConfigurations;
import com.jk.faces.config.Namespace;
import com.jk.faces.config.TagMapping;
import com.jk.faces.util.JSFUtil;
import com.jk.resources.JKResourceLoaderFactory;
import com.jk.util.IOUtil;
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

	public final static JKTagDecorator Instance = new JKTagDecorator();

	JKFacesConfigurations config = JKFacesConfigurations.getInstance();
	/** The logger. */
	Logger logger = Logger.getLogger(getClass().getName());

	/**
	 * Instantiates a new JK tag decorator.
	 */
	public JKTagDecorator() {
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
	public Tag decorate(Tag tag) {
		this.logger.fine("decorate tag :".concat(tag.getLocalName()));
		if (tag.getLocalName().equals("html")) {
			tag = addNamesSpaces(tag);
		}
		tag = handleMapping(tag);
		return tag;
	}

	/**
	 * 
	 * @param tag
	 * @return
	 */
	private Tag handleMapping(Tag tag) {
		TagMapping mapping = config.findTagMapping(tag.getLocalName(), tag.getAttributes().getAll());
		if (mapping != null) {
			tag = new Tag(tag.getLocation(), mapping.getNameSpace().getUrl(), mapping.getTargetLocalName(), mapping.getTargetTag(),
					tag.getAttributes());
		}
		return tag;
	}

	/**
	 * Adds the names spaces.
	 *
	 * @param tag
	 *            the tag
	 * @return the tag
	 */
	private Tag addNamesSpaces(Tag tag) {
		logger.info("addNamesSpaces to tag : ".concat(tag.toString()));
		final List<Namespace> copy = new Vector(config.getNamespaces());
		final TagAttributes attributes = tag.getAttributes();
		final TagAttribute[] all = attributes.getAll();
		for (final TagAttribute tagAttribute : all) {
			for (int i = 0; i < copy.size(); i++) {
				Namespace namespace = copy.get(i);
				if (namespace.getPrefix().equals(tagAttribute.getLocalName())) {
					copy.remove(i--);
					continue;
				}
			}
		}
		if (copy.size() > 0) {
			// name spaces not defined
			final List<TagAttribute> newAttributes = new Vector<>(Arrays.asList(all));
			for (Namespace namespace : copy) {
				this.logger.fine("adding missing namespace : " + namespace.getPrefix());
				newAttributes.add(createAttribute(tag, namespace.getPrefix(), namespace.getUrl()));
			}
			// final TagAttributes newTagAttributes = );
			this.logger.info("create new tag instance");
			tag = createTag(tag, newAttributes);
		}
		return tag;
	}

	protected Tag createTag(Tag oldTag, final List<TagAttribute> newAttributes) {
		TagAttributesImpl attributes = new TagAttributesImpl(newAttributes.toArray(new TagAttribute[0]));
		Tag tag = new Tag(oldTag.getLocation(), oldTag.getNamespace(), oldTag.getLocalName(), oldTag.getQName(), attributes);
		return tag;
	}

	protected TagAttributeImpl createAttribute(Tag tag, final String nameSpaceKey, String property) {
		return new TagAttributeImpl(tag.getLocation(), tag.getNamespace(), nameSpaceKey, nameSpaceKey, property);
	}

}
