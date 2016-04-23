package com.jk.faces.config;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.jboss.logging.Logger;

@XmlAccessorType(XmlAccessType.FIELD)
public class JKNamespace {
	private static int counter = 0;
	static Logger logger = Logger.getLogger(JKNamespace.class.getName());
	@XmlTransient
	Integer index=counter++;

	@XmlElement
	String prefix;

	@XmlElement
	String url;

	private String letter;

	public JKNamespace() {
	}

	public JKNamespace(String url) {
		this(url, createPrefix(url));
	}

	public JKNamespace(String url, String prefix) {
		logger.info(String.format("create Namespace with url(%s) and Prefix(%s) ", url, prefix));
		this.url = url;
		this.prefix = prefix;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getLetter() {
		if (letter == null) {
			String[] split = getPrefix().split(":");
			if (split.length == 2) {
				letter = split[1];
			} else {
				letter = split[0];
			}
		}
		return letter;
	}

	protected static String createPrefix(String url) {
		return "xmlns:".concat(url.substring(url.length() - 3));
	}

	public Integer getIndex() {
		return index;
	}
}
