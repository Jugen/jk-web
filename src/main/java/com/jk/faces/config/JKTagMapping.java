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

@XmlAccessorType(XmlAccessType.FIELD)
public class JKTagMapping implements Comparable<JKTagMapping> {
	static Logger logger = Logger.getLogger(JKTagMapping.class.getName());

	@XmlElement(name = "source-tag")
	String sourceQName;

	@XmlElement(name = "target-tag")
	String targetQName;

	@XmlElement(name = "attribute-name")
	String attributeName;

	@XmlElement(name = "attribute-value")
	String attributeValue;

	@XmlTransient
	private JKNamespace namespace;
	// loaded from JSF container at runtime
	@XmlTransient
	Boolean auto = false;

	// JKNamespace nameSpace;

	// private String targetLocalName;

	public JKTagMapping() {
	}

	public JKTagMapping(final String sourceQName, final String targetQName, final JKNamespace namespace) {
		logger.info(String.format("creating TagMapping with sourceTag(%s) and (%s)", sourceQName, targetQName));
		this.sourceQName = sourceQName;
		this.targetQName = targetQName;
		this.namespace = namespace;
		this.auto = true;
	}

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

	public String getAttributeName() {
		return this.attributeName;
	}

	public String getAttributeValue() {
		return this.attributeValue;
	}

	public JKNamespace getNamespace() {
		return this.namespace;
	}

	public String getNameSpaceLetter() {
		if (isAuto()) {
			return this.namespace.getLetter();
		}
		return JKJsfUtil.getNamespaceLetterFromQName(getTargetQName());

	}

	public String getSourceQName() {
		return this.sourceQName;
	}

	public String getTargetLocalName() {
		return JKJsfUtil.getLocalNameFromQName(getTargetQName());
	}

	public String getTargetQName() {
		return this.targetQName;
	}

	public boolean isAuto() {
		return this.auto;
	}

	public void setAttributeName(final String attributeName) {
		this.attributeName = attributeName;
	}

	public void setAttributeValue(final String attributeValue) {
		this.attributeValue = attributeValue;
	}

	public void setSourceQName(final String sourceTag) {
		this.sourceQName = sourceTag;
	}

	public void setTargetQName(final String targetTag) {
		this.targetQName = targetTag;
	}
}
