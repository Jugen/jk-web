package com.jk.faces.tags;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.faces.view.Location;
import javax.faces.view.facelets.Tag;
import javax.faces.view.facelets.TagAttribute;
import javax.faces.view.facelets.TagAttributes;

import org.jboss.logging.Logger;

import com.jk.faces.config.JKNamespace;
import com.jk.util.JKKeyValue;
import com.jk.util.ObjectUtil;
import com.jk.util.StringUtil;
import com.sun.faces.facelets.tag.TagAttributeImpl;
import com.sun.faces.facelets.tag.TagAttributesImpl;

public class JKTagWrapper {
	static Logger logger = Logger.getLogger(JKTagWrapper.class.getName());
	public static String URLABLE_TAGS = "img,script,a,link";
	public static String LINKS_ATTRIBUTES = "src,href";
	private Tag tag;
	private String localName;
	private Location location;
	private String namespace;
	private String qName;
	private List<JKTagAttributeWrapper> attributesList;

	public JKTagWrapper(Tag tag) {
		this.tag = tag;
		localName = tag.getLocalName();
		location = tag.getLocation();
		namespace = tag.getNamespace();
		qName = tag.getQName();

		TagAttributes attributes = tag.getAttributes();
		TagAttribute[] all = attributes.getAll();
		attributesList = new ArrayList<>();
		for (TagAttribute tagAttribute : all) {
			attributesList.add(new JKTagAttributeWrapper(tagAttribute));
		}
	}

	public Tag getTag() {
		return tag;
	}

	public void setTag(Tag tag) {
		this.tag = tag;
	}

	public String getLocalName() {
		return localName;
	}

	public void setLocalName(String localName) {
		this.localName = localName;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public String getNamespace() {
		return namespace;
	}

	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}

	public String getqName() {
		return qName;
	}

	public void setqName(String qName) {
		this.qName = qName;
	}

	public void setAttributeValue(String name, String value) {
		for (JKTagAttributeWrapper attr : attributesList) {
			if (attr.getLocalName().equals(name)) {
				attr.setValue(value);
			}
		}
	}

	public Tag buildTag() {
		logger.info("old Tag:" + ObjectUtil.toString(tag));
		Tag tag = new Tag(getLocation(), getNamespace(), getLocalName(), getqName(), buildAttribues());
		logger.info("New Tag:" + ObjectUtil.toString(tag));
		return tag;
	}

	protected TagAttributes buildAttribues() {
		TagAttribute[] attributes = new TagAttributeImpl[attributesList.size()];
		for (int i = 0; i < attributesList.size(); i++) {
			JKTagAttributeWrapper attr = attributesList.get(i);
			attributes[i] = attr.buildAttribute();
		}
		TagAttributesImpl attrs = new TagAttributesImpl(attributes);
		return attrs;
	}

	public boolean isHtmlTag() {
		return getLocalName().equals("html");
	}

	public void addAttributes(List<JKKeyValue<String, String>> attributes) {
		for (JKKeyValue<String, String> keyValue : attributes) {
			addAttribue(keyValue.getKey(), keyValue.getValue());
		}
	}

	public JKTagAttributeWrapper findAttribute(String key) {
		for (JKTagAttributeWrapper attr : attributesList) {
			if (attr.getLocalName().equals(key)) {
				return attr;
			}
		}
		return null;
	}

	public void addAttribue(String name, String value) {
		JKTagAttributeWrapper attr = findAttribute(name);
		if (attr == null) {
			logger.debug(String.format("add attribute %s with value %s ",name,value));
			this.attributesList.add(new JKTagAttributeWrapper(tag, name, value));
		}
	}

	public String getAttributeValue(String attributeName) {
		JKTagAttributeWrapper attr = findAttribute(attributeName);
		if (attr != null) {
			return attr.getValue();
		}
		return null;
	}

	public boolean isUrlable() {
		return URLABLE_TAGS.contains(getLocalName());
	}

	public List<JKTagAttributeWrapper> getLinksAttributes() {
		List<JKTagAttributeWrapper> attrs = new Vector<>();
		for (JKTagAttributeWrapper attr : attributesList) {
			if (LINKS_ATTRIBUTES.contains(attr.getLocalName())) {
				attrs.add(attr);
			}
		}
		return attrs;
	}

}
