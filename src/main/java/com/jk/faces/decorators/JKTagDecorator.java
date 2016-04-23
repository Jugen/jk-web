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

	private static final String NAMES_SPACES_PROPERTIES = "/META-INF/names-spaces.properties";
	private static final String COMPONENTS_MAPPING_PROPERTIES = "/META-INF/components-mapping.properties";

	/** The name spaces map. */
	static Properties nameSpacesMap;
	static Properties componentsMap;

	/** The Constant JK_NAMESPACE. */
	private static final String JK_NAMESPACE = "http://jalalkiswani.com/jsf";
	/** The Constant Instance. */
	public final static JKTagDecorator Instance = new JKTagDecorator();

	/** The logger. */
	Logger logger = Logger.getLogger(getClass().getName());

	/**
	 * Instantiates a new JK tag decorator.
	 */
	public JKTagDecorator() {
		super();
		if (nameSpacesMap == null) {
			nameSpacesMap = new Properties();
			componentsMap = new Properties();
			try {
				nameSpacesMap.load(IOUtil.getInputStream(NAMES_SPACES_PROPERTIES));
				componentsMap.load(IOUtil.getInputStream(COMPONENTS_MAPPING_PROPERTIES));
			} catch (IOException e) {
				throw new JKException(e);
			}
		}
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
		String mappedQualifiedName = componentsMap.getProperty(tag.getLocalName());
		if (mappedQualifiedName != null) {
			String localName = mappedQualifiedName.substring(mappedQualifiedName.indexOf(":"));
			tag = new Tag(tag.getLocation(), tag.getNamespace(), localName, mappedQualifiedName, tag.getAttributes());
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
		final Properties copy = new Properties(JKTagDecorator.nameSpacesMap);
		final TagAttributes attributes = tag.getAttributes();
		final TagAttribute[] all = attributes.getAll();
		for (final TagAttribute tagAttribute : all) {
			this.logger.fine(String.format("Found location(%s),namespace(%s),qName(%s)", tagAttribute.getLocation(), tagAttribute.getNamespace(),
					tagAttribute.getQName()));
			copy.remove(tagAttribute.getLocalName());
		}
		if (copy.size() > 0) {
			// name spaces not defined
			final List<TagAttribute> newAttributes = new Vector<>(Arrays.asList(all));
			final Set<?> keySet = copy.keySet();
			for (final Object element : keySet) {
				final String attribueName = (String) element;
				this.logger.fine("adding missing namespace : " + attribueName);
				String attributeValue = copy.getProperty(attribueName);
				newAttributes.add(createAttribute(tag, attribueName, attributeValue));
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
		return new TagAttributeImpl(tag.getLocation(), tag.getNamespace(), nameSpaceKey, tag.getQName(), property);
	}

}
