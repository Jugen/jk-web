package com.jk.faces.config;

import java.util.logging.Logger;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
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
	Boolean auto=false;

	// JKNamespace nameSpace;

	// private String targetLocalName;

	public JKTagMapping() {
	}

	public JKTagMapping(String sourceQName, String targetQName, JKNamespace namespace) {
		logger.info(String.format("creating TagMapping with sourceTag(%s) and (%s)", sourceQName, targetQName));
		this.sourceQName = sourceQName;
		this.targetQName = targetQName;
		this.namespace = namespace;
		auto = true;
	}

	public String getSourceQName() {
		return sourceQName;
	}

	public void setSourceQName(String sourceTag) {
		this.sourceQName = sourceTag;
	}

	public String getTargetQName() {
		return targetQName;
	}

	public void setTargetQName(String targetTag) {
		this.targetQName = targetTag;
	}

	public String getAttributeName() {
		return attributeName;
	}

	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}

	public String getAttributeValue() {
		return attributeValue;
	}

	public void setAttributeValue(String attributeValue) {
		this.attributeValue = attributeValue;
	}

	public String getNameSpaceLetter() {
		if(isAuto()){
			return namespace.getLetter(); 	
		}
		return  JKJsfUtil.getNamespaceLetterFromQName(getTargetQName());
		
	}

	public String getTargetLocalName() {
		return JKJsfUtil.getLocalNameFromQName(getTargetQName());
	}

	public JKNamespace getNamespace() {
		return namespace;
	}

	@Override
	public int compareTo(JKTagMapping o) {
		if(this.auto!=o.auto){
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

	public boolean isAuto() {
		return auto;
	}
}
