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
package com.jk.faces.tags;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.logging.Logger;

import javax.faces.view.Location;
import javax.faces.view.facelets.Tag;
import javax.faces.view.facelets.TagAttribute;
import javax.faces.view.facelets.TagAttributes;

import com.jk.util.JKKeyValue;
import com.jk.util.JKObjectUtil;
import com.sun.faces.facelets.tag.TagAttributeImpl;
import com.sun.faces.facelets.tag.TagAttributesImpl;

/**
 * The Class JKTagWrapper.
 *
 * @author Jalal Kiswani
 */
public class JKTagWrapper {
	static Logger logger = Logger.getLogger(JKTagWrapper.class.getName());
	
	/** The urlable tags. */
	public static String URLABLE_TAGS = "img,script,a,link";
	
	/** The links attributes. */
	public static String LINKS_ATTRIBUTES = "src,href";
	private Tag tag;
	private String localName;
	private Location location;
	private String namespace;
	private String qName;
	private final List<JKTagAttributeWrapper> attributesList;

	/**
	 * Instantiates a new JK tag wrapper.
	 *
	 * @param tag
	 *            the tag
	 */
	public JKTagWrapper(final Tag tag) {
		this.tag = tag;
		this.localName = tag.getLocalName();
		this.location = tag.getLocation();
		this.namespace = tag.getNamespace();
		this.qName = tag.getQName();

		final TagAttributes attributes = tag.getAttributes();
		final TagAttribute[] all = attributes.getAll();
		this.attributesList = new ArrayList<>();
		for (final TagAttribute tagAttribute : all) {
			this.attributesList.add(new JKTagAttributeWrapper(tagAttribute));
		}
	}

	/**
	 * Adds the attribue.
	 *
	 * @param name
	 *            the name
	 * @param value
	 *            the value
	 */
	public void addAttribue(final String name, final String value) {
		final JKTagAttributeWrapper attr = findAttribute(name);
		if (attr == null) {
			logger.fine(String.format("add attribute %s with value %s ", name, value));
			this.attributesList.add(new JKTagAttributeWrapper(this.tag, name, value));
		}
	}

	/**
	 * Adds the attributes.
	 *
	 * @param attributes
	 *            the attributes
	 */
	public void addAttributes(final List<JKKeyValue<String, String>> attributes) {
		for (final JKKeyValue<String, String> keyValue : attributes) {
			addAttribue(keyValue.getKey(), keyValue.getValue());
		}
	}

	protected TagAttributes buildAttribues() {
		final TagAttribute[] attributes = new TagAttributeImpl[this.attributesList.size()];
		for (int i = 0; i < this.attributesList.size(); i++) {
			final JKTagAttributeWrapper attr = this.attributesList.get(i);
			attributes[i] = attr.buildAttribute();
		}
		final TagAttributesImpl attrs = new TagAttributesImpl(attributes);
		return attrs;
	}

	/**
	 * Builds the tag.
	 *
	 * @return the tag
	 */
	public Tag buildTag() {
		logger.info("old Tag:" + JKObjectUtil.toString(this.tag));
		final Tag tag = new Tag(getLocation(), getNamespace(), getLocalName(), getqName(), buildAttribues());
		logger.info("New Tag:" + JKObjectUtil.toString(tag));
		return tag;
	}

	/**
	 * Find attribute.
	 *
	 * @param key
	 *            the key
	 * @return the JK tag attribute wrapper
	 */
	public JKTagAttributeWrapper findAttribute(final String key) {
		for (final JKTagAttributeWrapper attr : this.attributesList) {
			if (attr.getLocalName().equals(key)) {
				return attr;
			}
		}
		return null;
	}

	/**
	 * Gets the attribute value.
	 *
	 * @param attributeName
	 *            the attribute name
	 * @return the attribute value
	 */
	public String getAttributeValue(final String attributeName) {
		final JKTagAttributeWrapper attr = findAttribute(attributeName);
		if (attr != null) {
			return attr.getValue();
		}
		return null;
	}

	/**
	 * Gets the links attributes.
	 *
	 * @return the links attributes
	 */
	public List<JKTagAttributeWrapper> getLinksAttributes() {
		final List<JKTagAttributeWrapper> attrs = new Vector<>();
		for (final JKTagAttributeWrapper attr : this.attributesList) {
			if (LINKS_ATTRIBUTES.contains(attr.getLocalName())) {
				attrs.add(attr);
			}
		}
		return attrs;
	}

	/**
	 * Gets the local name.
	 *
	 * @return the local name
	 */
	public String getLocalName() {
		return this.localName;
	}

	/**
	 * Gets the location.
	 *
	 * @return the location
	 */
	public Location getLocation() {
		return this.location;
	}

	/**
	 * Gets the namespace.
	 *
	 * @return the namespace
	 */
	public String getNamespace() {
		return this.namespace;
	}

	/**
	 * Gets the q name.
	 *
	 * @return the q name
	 */
	public String getqName() {
		return this.qName;
	}

	/**
	 * Gets the tag.
	 *
	 * @return the tag
	 */
	public Tag getTag() {
		return this.tag;
	}

	/**
	 * Checks if is html tag.
	 *
	 * @return true, if is html tag
	 */
	public boolean isHtmlTag() {
		return getLocalName().equals("html");
	}

	/**
	 * Checks if is urlable.
	 *
	 * @return true, if is urlable
	 */
	public boolean isUrlable() {
		return URLABLE_TAGS.contains(getLocalName());
	}

	/**
	 * Sets the attribute value.
	 *
	 * @param name
	 *            the name
	 * @param value
	 *            the value
	 */
	public void setAttributeValue(final String name, final String value) {
		for (final JKTagAttributeWrapper attr : this.attributesList) {
			if (attr.getLocalName().equals(name)) {
				attr.setValue(value);
			}
		}
	}

	/**
	 * Sets the local name.
	 *
	 * @param localName
	 *            the new local name
	 */
	public void setLocalName(final String localName) {
		this.localName = localName;
	}

	/**
	 * Sets the location.
	 *
	 * @param location
	 *            the new location
	 */
	public void setLocation(final Location location) {
		this.location = location;
	}

	/**
	 * Sets the namespace.
	 *
	 * @param namespace
	 *            the new namespace
	 */
	public void setNamespace(final String namespace) {
		this.namespace = namespace;
	}

	/**
	 * Sets the q name.
	 *
	 * @param qName
	 *            the new q name
	 */
	public void setqName(final String qName) {
		this.qName = qName;
	}

	/**
	 * Sets the tag.
	 *
	 * @param tag
	 *            the new tag
	 */
	public void setTag(final Tag tag) {
		this.tag = tag;
	}

}
