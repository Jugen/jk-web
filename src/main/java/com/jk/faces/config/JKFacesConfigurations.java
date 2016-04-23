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

import javax.faces.view.facelets.TagAttribute;
import javax.faces.view.facelets.TagAttributes;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlList;
import javax.xml.bind.annotation.XmlRootElement;

import org.jboss.logging.Logger;

import com.jk.faces.tags.JKTagWrapper;
import com.jk.util.IOUtil;
import com.jk.util.ObjectUtil;
import com.jk.xml.JKXmlHandler;
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

	static Logger logger = Logger.getLogger(JKFacesConfigurations.class.getName());
	private static JKFacesConfigurations instance;

	@XmlElementWrapper(name = "name-spaces")
	@XmlElement(name = "namespace")
	List<JKNamespace> namespaces;

	// @XmlTransient
	// Map<String, List<String>> publicNameSpaces;

	@XmlElementWrapper(name = "tags-mapping")
	@XmlElement(name = "tag")
	List<JKTagMapping> tagMapping;

	/**
	 * Gets the single instance of JKFacesConfigurations.
	 *
	 * @return single instance of JKFacesConfigurations
	 */
	public static JKFacesConfigurations getInstance() {
		if (instance == null) {
			instance = new JKFacesConfigurations();
			instance = JKXmlHandler.getInstance().parse(IOUtil.getInputStream(META_INF_JK_FACES_CONFIG_XML), JKFacesConfigurations.class,
					JKNamespace.class, JKTagMapping.class);

		}
		return instance;
	}

	// JAXb callback
	void afterUnmarshal(Unmarshaller u, Object parent) {
		loadAllNamesSpacesFromJsfContainer();
		Collections.sort(this.tagMapping);
		System.err.println("---------------------------------------------");
		logger.info("All tags mappings:");
		System.err.println("---------------------------------------------");
		for (JKTagMapping mapping : this.tagMapping) {
			logger.info(ObjectUtil.toString(mapping));
		}
	}

	/**
	 * Gets the namespaces.
	 *
	 * @return the namespaces
	 */
	public List<JKNamespace> getNamespaces() {
		return namespaces;
	}

	/**
	 * Sets the namespaces.
	 *
	 * @param namespaces
	 *            the new namespaces
	 */
	public void setNamespaces(List<JKNamespace> namespaces) {
		this.namespaces = namespaces;
	}

	/**
	 * Gets the tag mapping.
	 *
	 * @return the tag mapping
	 */
	public List<JKTagMapping> getTagMapping() {
		return tagMapping;
	}

	/**
	 * Sets the tag mapping.
	 *
	 * @param tagMapping
	 *            the new tag mapping
	 */
	public void setTagMapping(List<JKTagMapping> tagMapping) {
		this.tagMapping = tagMapping;
	}

	/**
	 * The main method.
	 *
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {
		JKFacesConfigurations test = getInstance();
		List<JKNamespace> namespaces = test.getNamespaces();
		for (JKNamespace ns : namespaces) {
			System.out.println(ns.getPrefix() + ", " + ns.getUrl());
		}
		System.out.println("----------------");
		List<JKTagMapping> tagMapping2 = test.getTagMapping();
		for (JKTagMapping tagMapping : tagMapping2) {
			System.out.println(ObjectUtil.toString(tagMapping));
		}
		System.out.println("Done");
	}

	/**
	 * Find tag mapping.
	 *
	 * @param tagName
	 *            the tag name
	 * @param tagAttributes
	 *            the tag attributes
	 * @return the tag mapping
	 */
	public JKTagMapping findTagMapping(JKTagWrapper wrapper) {
		// Add caching
		logger.info("Find mapping to tag ".concat(wrapper.getqName()));
		for (JKTagMapping mapping : tagMapping) {
			if (mapping.getSourceQName().equals(wrapper.getqName())) {
				if (mapping.getAttributeName() == null) {
					return mapping;
				} else {
					String value = wrapper.getAttributeValue(mapping.getAttributeName());
					if (value != null && value.equals(mapping.getAttributeValue())) {
						return mapping;
					}
				}
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
	public JKNamespace getNamespaceByLetter(String namespaceLetter) {
		for (JKNamespace namespace : namespaces) {
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
	 * @return the name space by url
	 */
	public JKNamespace getNameSpaceByUrl(String url, boolean create) {
		logger.info("getNameSpaceByUrl :".concat(url));
		for (JKNamespace namespace : namespaces) {
			if (namespace.getUrl().equals(url)) {
				return namespace;
			}
		}
		// if not found , add?
		if (create) {
			logger.info("not found , create namespace ");
			JKNamespace namespace = new JKNamespace(url);
			namespaces.add(namespace);
			return namespace;
		}
		return null;
	}

	protected void loadAllNamesSpacesFromJsfContainer() {
		logger.info("loadAllNamesSpacesFromJsfContainer....");
		// HashMap<String, Object> publicNameSpaces = new HashMap<>();
		ApplicationAssociate currentInstance = ApplicationAssociate.getCurrentInstance();
		if (currentInstance != null) {
			Compiler instance = currentInstance.getCompiler();
			logger.info("loading libraries from JSF compiler");
			List libraries = ObjectUtil.getFieldValue(Compiler.class, instance, "libraries");
			for (Object libObject : libraries) {
				if (libObject instanceof AbstractTagLibrary) {
					AbstractTagLibrary library = (AbstractTagLibrary) libObject;
					logger.info(String.format("fetching %s library", library.getNamespace()));
					// load components using reflection
					Map map = ObjectUtil.getFieldValue(AbstractTagLibrary.class, library, "factories");
					JKNamespace namespace = getNameSpaceByUrl(library.getNamespace(), true);

					Set keySet = map.keySet();
					for (Object object : keySet) {
						// add tag names
						String localName = object.toString();
						String qName = namespace.getLetter().concat(":").concat(localName);
						// check if mapping already exists
						if (findTagMapping(qName) == null) {
							JKTagMapping mapping = new JKTagMapping(localName, qName, namespace);
							this.tagMapping.add(mapping);
						}
					}
				}
			}
		}
	}

	public JKTagMapping findTagMapping(String tagName) {
		for (JKTagMapping mapping : tagMapping) {
			if (mapping.getSourceQName().equals(tagName)) {
				return mapping;
			}
		}
		return null;
	}

}
