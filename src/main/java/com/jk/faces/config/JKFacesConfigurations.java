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

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import org.omg.IOP.ServiceContext;

import com.jk.faces.tags.JKTagWrapper;
import com.jk.faces.util.JKJsfUtil;
import com.jk.util.JKConversionUtil;
import com.jk.util.JKIOUtil;
import com.jk.util.JKObjectUtil;
import com.jk.util.logging.JKLogger;
import com.jk.util.logging.JKLoggerFactory;
import com.jk.util.xml.JKXmlHandler;
import com.jk.web.util.JKWebUtil;
import com.sun.faces.application.ApplicationAssociate;
import com.sun.faces.facelets.compiler.Compiler;
import com.sun.faces.facelets.tag.AbstractTagLibrary;

/**
 * The Class JKFacesConfigurations.
 *
 * @author Jalal Kiswani
 */
@XmlRootElement(name = "jk-faces")
@XmlAccessorType(XmlAccessType.FIELD)
public class JKFacesConfigurations {
	private static final String META_INF_JK_FACES_CONFIG_XML = "/META-INF/jk-faces-config.xml";

	static JKLogger logger = JKLoggerFactory.getLogger(JKFacesConfigurations.class);
	private static JKFacesConfigurations instance;

	/**
	 * Gets the single instance of JKFacesConfigurations.
	 *
	 * @return single instance of JKFacesConfigurations
	 */
	public static JKFacesConfigurations getInstance() {
		if (instance == null) {
			instance = new JKFacesConfigurations();
			instance = JKXmlHandler.getInstance().parse(JKIOUtil.getInputStream(META_INF_JK_FACES_CONFIG_XML), JKFacesConfigurations.class,
					JKNamespace.class, JKTagMapping.class);

		}
		return instance;
	}

	// @XmlTransient
	// Map<String, List<String>> publicNameSpaces;

	/**
	 * The main method.
	 *
	 * @param args
	 *            the arguments
	 */
	public static void main(final String[] args) {
		final JKFacesConfigurations test = getInstance();
		final List<JKNamespace> namespaces = test.getNamespaces();
		for (final JKNamespace ns : namespaces) {
			System.out.println(ns.getPrefix() + ", " + ns.getUrl());
		}
		System.out.println("----------------");
		final List<JKTagMapping> tagMapping2 = test.getTagMapping();
		for (final JKTagMapping tagMapping : tagMapping2) {
			System.out.println(JKObjectUtil.toString(tagMapping));
		}
		System.out.println("Done");
	}

	@XmlElementWrapper(name = "name-spaces")
	@XmlElement(name = "namespace")
	List<JKNamespace> namespaces;

	@XmlElementWrapper(name = "tags-mapping")
	@XmlElement(name = "tag")
	List<JKTagMapping> tagMapping;

	private Boolean decorate;

	private Boolean decorateMapping;

	private Boolean decorateFixLinks;

	// JAXb callback
	void afterUnmarshal(final Unmarshaller u, final Object parent) {
		loadAllNamesSpacesFromJsfContainer();
		Collections.sort(this.tagMapping);
		System.err.println("---------------------------------------------");
		logger.debug("All tags mappings:");
		System.err.println("---------------------------------------------");
		for (final JKTagMapping mapping : this.tagMapping) {
			logger.debug(JKObjectUtil.toString(mapping));
		}
	}

	/**
	 * Find tag mapping.
	 *
	 * @param wrapper
	 *            the wrapper
	 * @return the tag mapping
	 */
	public JKTagMapping findTagMapping(final JKTagWrapper wrapper) {
		// Add caching
		logger.debug("Find mapping to tag ".concat(wrapper.getqName()));
		for (final JKTagMapping mapping : this.tagMapping) {
			if (mapping.getSourceQName().equals(wrapper.getqName())) {
				if (mapping.getAttributeName() == null) {
					return mapping;
				} else {
					final String value = wrapper.getAttributeValue(mapping.getAttributeName());
					if (value != null && value.equals(mapping.getAttributeValue())) {
						return mapping;
					}
				}
			}
		}
		return null;
	}

	/**
	 * Find tag mapping.
	 *
	 * @param tagName
	 *            the tag name
	 * @return the JK tag mapping
	 */
	public JKTagMapping findTagMapping(final String tagName) {
		for (final JKTagMapping mapping : this.tagMapping) {
			if (mapping.getSourceQName().equals(tagName)) {
				return mapping;
			}
		}
		return null;
	}

	/**
	 * Gets the namespace by letter.
	 *
	 * @param namespaceLetter
	 *            the namespace letter
	 * @return the namespace by letter
	 */
	public JKNamespace getNamespaceByLetter(final String namespaceLetter) {
		for (final JKNamespace namespace : this.namespaces) {
			if (namespace.getLetter().equals(namespaceLetter)) {
				return namespace;
			}
		}
		return null;
	}

	/**
	 * Gets the name space by url.
	 *
	 * @param url
	 *            the url
	 * @param create
	 *            the create
	 * @return the name space by url
	 */
	public JKNamespace getNameSpaceByUrl(final String url, final boolean create) {
		logger.debug("getNameSpaceByUrl :".concat(url));
		for (final JKNamespace namespace : this.namespaces) {
			if (namespace.getUrl().equals(url)) {
				return namespace;
			}
		}
		// if not found , add?
		if (create) {
			logger.debug("not found , create namespace ");
			final JKNamespace namespace = new JKNamespace(url);
			this.namespaces.add(namespace);
			return namespace;
		}
		return null;
	}

	/**
	 * Gets the namespaces.
	 *
	 * @return the namespaces
	 */
	public List<JKNamespace> getNamespaces() {
		return this.namespaces;
	}

	/**
	 * Gets the tag mapping.
	 *
	 * @return the tag mapping
	 */
	public List<JKTagMapping> getTagMapping() {
		return this.tagMapping;
	}

	protected void loadAllNamesSpacesFromJsfContainer() {
		logger.debug("loadAllNamesSpacesFromJsfContainer....");
		// HashMap<String, Object> publicNameSpaces = new HashMap<>();
		final ApplicationAssociate currentInstance = ApplicationAssociate.getCurrentInstance();
		if (currentInstance != null) {
			final Compiler instance = currentInstance.getCompiler();
			logger.debug("loading libraries from JSF compiler");
			final List libraries = JKObjectUtil.getFieldValue(Compiler.class, instance, "libraries");
			for (final Object libObject : libraries) {
				if (libObject instanceof AbstractTagLibrary) {
					final AbstractTagLibrary library = (AbstractTagLibrary) libObject;
					logger.debug(String.format("fetching %s library", library.getNamespace()));
					// load components using reflection
					final Map map = JKObjectUtil.getFieldValue(AbstractTagLibrary.class, library, "factories");
					final JKNamespace namespace = getNameSpaceByUrl(library.getNamespace(), true);

					final Set keySet = map.keySet();
					for (final Object object : keySet) {
						// add tag names
						final String localName = object.toString();
						final String qName = namespace.getLetter().concat(":").concat(localName);
						// check if mapping already exists
						if (findTagMapping(qName) == null) {
							final JKTagMapping mapping = new JKTagMapping(localName, qName, namespace);
							this.tagMapping.add(mapping);
						}
					}
				}
			}
		}
	}

	/**
	 * Sets the namespaces.
	 *
	 * @param namespaces
	 *            the new namespaces
	 */
	public void setNamespaces(final List<JKNamespace> namespaces) {
		this.namespaces = namespaces;
	}

	/**
	 * Sets the tag mapping.
	 *
	 * @param tagMapping
	 *            the new tag mapping
	 */
	public void setTagMapping(final List<JKTagMapping> tagMapping) {
		this.tagMapping = tagMapping;
	}

	/**
	 * Checks if is decorate.
	 *
	 * @return true, if is decorate
	 */
	public boolean isDecorate() {
		if (decorate == null) {
			ServletContext context = JKJsfUtil.getServletContext();
			decorate = JKConversionUtil.toBoolean(context.getInitParameter("jk.decorate"), true);
		}
		return decorate;
	}

	public boolean isDecorateMapping() {
		if (decorateMapping == null) {
			ServletContext context = JKJsfUtil.getServletContext();
			decorateMapping = JKConversionUtil.toBoolean(context.getInitParameter("jk.decorate.mapping"), true);
		}
		return decorateMapping;
	}

	public boolean isDecorateFixLinks() {
		if (decorateFixLinks == null) {
			ServletContext context = JKJsfUtil.getServletContext();
			decorateFixLinks = JKConversionUtil.toBoolean(context.getInitParameter("jk.decorate.fixlinks"), true);
		}
		return decorateFixLinks;
	}

}
