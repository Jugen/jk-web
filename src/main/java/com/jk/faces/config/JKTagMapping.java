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
package com.jk.faces.config;

import java.util.logging.Logger;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

import com.jk.faces.util.JKJsfUtil;
import com.jk.util.logging.JKLogger;
import com.jk.util.logging.JKLoggerFactory;

// TODO: Auto-generated Javadoc
/**
 * The Class JKTagMapping.
 *
 * @author Jalal Kiswani
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class JKTagMapping implements Comparable<JKTagMapping> {
	
	/** The logger. */
	static JKLogger logger = JKLoggerFactory.getLogger(JKTagMapping.class);

	/** The source Q name. */
	@XmlElement(name = "source-tag")
	String sourceQName;

	/** The target Q name. */
	@XmlElement(name = "target-tag")
	String targetQName;

	/** The attribute name. */
	@XmlElement(name = "attribute-name")
	String attributeName;

	/** The attribute value. */
	@XmlElement(name = "attribute-value")
	String attributeValue;

	/** The namespace. */
	@XmlTransient
	private JKNamespace namespace;
	
	/** The auto. */
	// loaded from JSF container at runtime
	@XmlTransient
	Boolean auto = false;

	// JKNamespace nameSpace;

	// private String targetLocalName;

	/**
	 * Instantiates a new JK tag mapping.
	 */
	public JKTagMapping() {
	}

	/**
	 * Instantiates a new JK tag mapping.
	 *
	 * @param sourceQName
	 *            the source Q name
	 * @param targetQName
	 *            the target Q name
	 * @param namespace
	 *            the namespace
	 */
	public JKTagMapping(final String sourceQName, final String targetQName, final JKNamespace namespace) {
		logger.debug(String.format("creating TagMapping with sourceTag(%s) and (%s)", sourceQName, targetQName));
		this.sourceQName = sourceQName;
		this.targetQName = targetQName;
		this.namespace = namespace;
		this.auto = true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(final JKTagMapping o) {
		if (this.auto != o.auto) {
			return this.auto.compareTo(o.auto);
		}
		if (getNamespace() != null) {
			if (o.getNamespace() != null) {
				return getNamespace().getIndex().compareTo(o.getNamespace().getIndex());
			} else {
				return -1;
			}
		} else {
			return getTargetQName().compareTo(o.getTargetQName());
		}
	}

	/**
	 * Gets the attribute name.
	 *
	 * @return the attribute name
	 */
	public String getAttributeName() {
		return this.attributeName;
	}

	/**
	 * Gets the attribute value.
	 *
	 * @return the attribute value
	 */
	public String getAttributeValue() {
		return this.attributeValue;
	}

	/**
	 * Gets the namespace.
	 *
	 * @return the namespace
	 */
	public JKNamespace getNamespace() {
		return this.namespace;
	}

	/**
	 * Gets the name space letter.
	 *
	 * @return the name space letter
	 */
	public String getNameSpaceLetter() {
		if (isAuto()) {
			return this.namespace.getLetter();
		}
		return JKJsfUtil.getNamespaceLetterFromQName(getTargetQName());

	}

	/**
	 * Gets the source Q name.
	 *
	 * @return the source Q name
	 */
	public String getSourceQName() {
		return this.sourceQName;
	}

	/**
	 * Gets the target local name.
	 *
	 * @return the target local name
	 */
	public String getTargetLocalName() {
		return JKJsfUtil.getLocalNameFromQName(getTargetQName());
	}

	/**
	 * Gets the target Q name.
	 *
	 * @return the target Q name
	 */
	public String getTargetQName() {
		return this.targetQName;
	}

	/**
	 * Checks if is auto.
	 *
	 * @return true, if is auto
	 */
	public boolean isAuto() {
		return this.auto;
	}

	/**
	 * Sets the attribute name.
	 *
	 * @param attributeName
	 *            the new attribute name
	 */
	public void setAttributeName(final String attributeName) {
		this.attributeName = attributeName;
	}

	/**
	 * Sets the attribute value.
	 *
	 * @param attributeValue
	 *            the new attribute value
	 */
	public void setAttributeValue(final String attributeValue) {
		this.attributeValue = attributeValue;
	}

	/**
	 * Sets the source Q name.
	 *
	 * @param sourceTag
	 *            the new source Q name
	 */
	public void setSourceQName(final String sourceTag) {
		this.sourceQName = sourceTag;
	}

	/**
	 * Sets the target Q name.
	 *
	 * @param targetTag
	 *            the new target Q name
	 */
	public void setTargetQName(final String targetTag) {
		this.targetQName = targetTag;
	}
}
