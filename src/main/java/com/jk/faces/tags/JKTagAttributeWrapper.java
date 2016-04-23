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

	public JKTagAttributeWrapper(TagAttribute tagAttribute) {
		this.tagAttribute = tagAttribute;
		qName = tagAttribute.getQName();
		value = tagAttribute.getValue();
		localName = tagAttribute.getLocalName();
		namespace = tagAttribute.getNamespace();
		location = tagAttribute.getLocation();
	}

	public JKTagAttributeWrapper(Tag tag, String name, String value) {
		qName = name;
		// localName = name;
		this.value = value;
		namespace = tag.getNamespace();
		location = tag.getLocation();
	}

	public String getqName() {
		return qName;
	}

	public void setqName(String qName) {
		this.qName = qName;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getLocalName() {
		return localName == null ? JKJsfUtil.getLocalNameFromQName(getqName()) : localName;
	}

	public void setLocalName(String localName) {
		this.localName = localName;
	}

	public String getNamespace() {
		return namespace;
	}

	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public TagAttribute buildAttribute() {
		TagAttribute attr = new TagAttributeImpl(getLocation(), getNamespace(), getLocalName(), getqName(), getValue());
		return attr;
	}

}
