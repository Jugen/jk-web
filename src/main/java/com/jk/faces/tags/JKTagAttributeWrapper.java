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
package com.jk.faces.tags;

import javax.faces.view.Location;
import javax.faces.view.facelets.Tag;
import javax.faces.view.facelets.TagAttribute;

import com.jk.faces.util.JKJsfUtil;
import com.sun.faces.facelets.tag.TagAttributeImpl;

// TODO: Auto-generated Javadoc
/**
 * The Class JKTagAttributeWrapper.
 *
 * @author Jalal Kiswani
 */
public class JKTagAttributeWrapper {

	/** The q name. */
	private String qName;
	
	/** The value. */
	private String value;
	
	/** The local name. */
	private String localName;
	
	/** The namespace. */
	private String namespace;
	
	/** The location. */
	private Location location;
	
	/** The tag attribute. */
	private TagAttribute tagAttribute;

	/**
	 * Instantiates a new JK tag attribute wrapper.
	 *
	 * @param tag
	 *            the tag
	 * @param name
	 *            the name
	 * @param value
	 *            the value
	 */
	public JKTagAttributeWrapper(final Tag tag, final String name, final String value) {
		this.qName = name;
		// localName = name;
		this.value = value;
		this.namespace = tag.getNamespace();
		this.location = tag.getLocation();
	}

	/**
	 * Instantiates a new JK tag attribute wrapper.
	 *
	 * @param tagAttribute
	 *            the tag attribute
	 */
	public JKTagAttributeWrapper(final TagAttribute tagAttribute) {
		this.tagAttribute = tagAttribute;
		this.qName = tagAttribute.getQName();
		this.value = tagAttribute.getValue();
		this.localName = tagAttribute.getLocalName();
		this.namespace = tagAttribute.getNamespace();
		this.location = tagAttribute.getLocation();
	}

	/**
	 * Builds the attribute.
	 *
	 * @return the tag attribute
	 */
	public TagAttribute buildAttribute() {
		final TagAttribute attr = new TagAttributeImpl(getLocation(), getNamespace(), getLocalName(), getqName(), getValue());
		return attr;
	}

	/**
	 * Gets the local name.
	 *
	 * @return the local name
	 */
	public String getLocalName() {
		return this.localName == null ? JKJsfUtil.getLocalNameFromQName(getqName()) : this.localName;
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
	 * Gets the value.
	 *
	 * @return the value
	 */
	public String getValue() {
		return this.value;
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
	 * Sets the value.
	 *
	 * @param value
	 *            the new value
	 */
	public void setValue(final String value) {
		this.value = value;
	}

}
