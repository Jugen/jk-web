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

import javax.faces.view.Location;
import javax.faces.view.facelets.Tag;
import javax.faces.view.facelets.TagAttribute;

import com.jk.faces.util.JKJsfUtil;
import com.sun.faces.facelets.tag.TagAttributeImpl;

public class JKTagAttributeWrapper {

	private String qName;
	private String value;
	private String localName;
	private String namespace;
	private Location location;
	private TagAttribute tagAttribute;

	public JKTagAttributeWrapper(final Tag tag, final String name, final String value) {
		this.qName = name;
		// localName = name;
		this.value = value;
		this.namespace = tag.getNamespace();
		this.location = tag.getLocation();
	}

	public JKTagAttributeWrapper(final TagAttribute tagAttribute) {
		this.tagAttribute = tagAttribute;
		this.qName = tagAttribute.getQName();
		this.value = tagAttribute.getValue();
		this.localName = tagAttribute.getLocalName();
		this.namespace = tagAttribute.getNamespace();
		this.location = tagAttribute.getLocation();
	}

	public TagAttribute buildAttribute() {
		final TagAttribute attr = new TagAttributeImpl(getLocation(), getNamespace(), getLocalName(), getqName(), getValue());
		return attr;
	}

	public String getLocalName() {
		return this.localName == null ? JKJsfUtil.getLocalNameFromQName(getqName()) : this.localName;
	}

	public Location getLocation() {
		return this.location;
	}

	public String getNamespace() {
		return this.namespace;
	}

	public String getqName() {
		return this.qName;
	}

	public String getValue() {
		return this.value;
	}

	public void setLocalName(final String localName) {
		this.localName = localName;
	}

	public void setLocation(final Location location) {
		this.location = location;
	}

	public void setNamespace(final String namespace) {
		this.namespace = namespace;
	}

	public void setqName(final String qName) {
		this.qName = qName;
	}

	public void setValue(final String value) {
		this.value = value;
	}

}
