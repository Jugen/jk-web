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
package com.jk.web.faces.config;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

import com.jk.util.logging.JKLogger;
import com.jk.util.logging.JKLoggerFactory;

// TODO: Auto-generated Javadoc
/**
 * The Class JKNamespace.
 *
 * @author Jalal Kiswani
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class JKNamespace {
	
	/** The counter. */
	private static int counter = 0;
	
	/** The logger. */
	static JKLogger logger = JKLoggerFactory.getLogger(JKNamespace.class);

	/**
	 * Creates the prefix.
	 *
	 * @param url the url
	 * @return the string
	 */
	protected static String createPrefix(final String url) {
		return "xmlns:".concat(url.substring(url.length() - 3));
	}

	/** The index. */
	@XmlTransient
	Integer index = counter++;

	/** The prefix. */
	@XmlElement
	String prefix;

	/** The url. */
	@XmlElement
	String url;

	/** The letter. */
	private String letter;

	/**
	 * Instantiates a new JK namespace.
	 */
	public JKNamespace() {
	}

	/**
	 * Instantiates a new JK namespace.
	 *
	 * @param url
	 *            the url
	 */
	public JKNamespace(final String url) {
		this(url, createPrefix(url));
	}

	/**
	 * Instantiates a new JK namespace.
	 *
	 * @param url
	 *            the url
	 * @param prefix
	 *            the prefix
	 */
	public JKNamespace(final String url, final String prefix) {
		logger.debug(String.format("create Namespace with url(%s) and Prefix(%s) ", url, prefix));
		this.url = url;
		this.prefix = prefix;
	}

	/**
	 * Gets the index.
	 *
	 * @return the index
	 */
	public Integer getIndex() {
		return this.index;
	}

	/**
	 * Gets the letter.
	 *
	 * @return the letter
	 */
	public String getLetter() {
		if (this.letter == null) {
			final String[] split = getPrefix().split(":");
			if (split.length == 2) {
				this.letter = split[1];
			} else {
				this.letter = split[0];
			}
		}
		return this.letter;
	}

	/**
	 * Gets the prefix.
	 *
	 * @return the prefix
	 */
	public String getPrefix() {
		return this.prefix;
	}

	/**
	 * Gets the url.
	 *
	 * @return the url
	 */
	public String getUrl() {
		return this.url;
	}

	/**
	 * Sets the prefix.
	 *
	 * @param prefix
	 *            the new prefix
	 */
	public void setPrefix(final String prefix) {
		this.prefix = prefix;
	}

	/**
	 * Sets the url.
	 *
	 * @param url
	 *            the new url
	 */
	public void setUrl(final String url) {
		this.url = url;
	}
}
