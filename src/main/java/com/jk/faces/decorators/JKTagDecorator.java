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
package com.jk.faces.decorators;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.Vector;
import java.util.logging.Logger;

import javax.faces.view.facelets.Tag;
import javax.faces.view.facelets.TagAttribute;
import javax.faces.view.facelets.TagAttributes;
import javax.faces.view.facelets.TagDecorator;

import com.jk.annotations.Author;
import com.jk.exceptions.JKException;
import com.jk.faces.config.JKFacesConfigurations;
import com.jk.faces.config.JKNamespace;
import com.jk.faces.config.JKTagMapping;
import com.jk.faces.tags.JKTagAttributeWrapper;
import com.jk.faces.tags.JKTagWrapper;
import com.jk.faces.util.JSFUtil;
import com.jk.resources.JKResourceLoaderFactory;
import com.jk.util.IOUtil;
import com.sun.faces.facelets.tag.TagAttributeImpl;
import com.sun.faces.facelets.tag.TagAttributesImpl;

/**
 * <B>JKTagDecorator</B> this class represents the JK implementation for
 * <code>JKTagDecorator</code>.
 *
 * @author Jalal H. Kiswani
 * @see <a href=
 *      "http://docs.oracle.com/javaee/7/api/javax/faces/view/facelets/TagDecorator.html">
 *      TagDecorator</a>
 */
@Author(name = "Jalal Kiswani", date = "3/9/2014", version = "1.0")
public final class JKTagDecorator implements TagDecorator {

	public final static JKTagDecorator Instance = new JKTagDecorator();

	JKFacesConfigurations config = JKFacesConfigurations.getInstance();
	/** The logger. */
	Logger logger = Logger.getLogger(getClass().getName());

	/**
	 * Instantiates a new JK tag decorator.
	 */
	public JKTagDecorator() {
	}

	/**
	 * Unfortunaly , this didnt work since failuer occures on the persing level
	 * if perfix not bound.
	 *
	 * @param tag
	 *            the tag
	 * @return the tag
	 */
	/**
	 * <code>JK_NAMESPACE</code>.
	 *
	 * @param tag
	 *            the tag
	 * @return the tag
	 * @see com.sun.facelets.tag.TagDecorator#decorate(com.sun.facelets.tag.Tag)
	 */
	@Override
	public Tag decorate(final Tag tag) {
		JKTagWrapper wrapper = new JKTagWrapper(tag);
		this.logger.fine("decorate tag :".concat(tag.getLocalName()));
		if (wrapper.isHtmlTag()) {
			addMissingNamespaces(wrapper);
		} else {
			handleMapping(wrapper);
			if (wrapper.isUrlable()) {
				fixLiks(wrapper);
			}
		}

		return wrapper.buildTag();
	}

	/**
	 * 
	 * @param wrapper
	 */
	protected void fixLiks(JKTagWrapper wrapper) {
		List<JKTagAttributeWrapper> links = wrapper.getLinksAttributes();
		for (JKTagAttributeWrapper link : links) {
			if (!link.getValue().startsWith("http")) {
				String prefix = "#{request.contextPath}";
				if (!link.getValue().startsWith("/")) {
					prefix = prefix.concat("/");
				}
				link.setValue(prefix.concat(link.getValue()));

			}
		}
	}

	/**
	 * 
	 * @param wrapper
	 * @return
	 */
	protected void handleMapping(JKTagWrapper wrapper) {
		JKTagMapping mapping = config.findTagMapping(wrapper);
		if (mapping != null) {
			wrapper.setNamespace(mapping.getNameSpace().getUrl());
			wrapper.setqName(mapping.getTargetTag());
			wrapper.setLocalName(mapping.getTargetLocalName());
		}
	}

	/**
	 * Adds the names spaces.
	 *
	 * @param wrapper
	 *            the tag
	 * @return the tag
	 */
	protected void addMissingNamespaces(JKTagWrapper wrapper) {
		logger.info("addNamesSpaces to tag : ".concat(wrapper.toString()));
		List<JKNamespace> namespaces = config.getNamespaces();
		for (JKNamespace namespace : namespaces) {
			wrapper.addAttribue(namespace.getPrefix(), namespace.getUrl());
		}
		// final List<JKNamespace> copy = new Vector(config.getNamespaces());
		// final TagAttributes attributes = wrapper.getAttributes();
		// final TagAttribute[] all = attributes.getAll();
		// for (final TagAttribute tagAttribute : all) {
		// for (int i = 0; i < copy.size(); i++) {
		// JKNamespace namespace = copy.get(i);
		// if (namespace.getPrefix().equals(tagAttribute.getLocalName())) {
		// copy.remove(i--);
		// continue;
		// }
		// }
		// }
		// if (copy.size() > 0) {
		// // name spaces not defined
		// final List<TagAttribute> newAttributes = new
		// Vector<>(Arrays.asList(all));
		// for (JKNamespace namespace : copy) {
		// this.logger.fine("adding missing namespace : " +
		// namespace.getPrefix());
		// newAttributes.add(createAttribute(wrapper, namespace.getPrefix(),
		// namespace.getUrl()));
		// }
		// // final TagAttributes newTagAttributes = );
		// this.logger.info("create new tag instance");
		// wrapper = createTag(wrapper, newAttributes);
		// }
		// return wrapper;
	}

	// protected Tag createTag(Tag oldTag, final List<TagAttribute>
	// newAttributes) {
	// TagAttributesImpl attributes = new
	// TagAttributesImpl(newAttributes.toArray(new TagAttribute[0]));
	// Tag tag = new Tag(oldTag.getLocation(), oldTag.getNamespace(),
	// oldTag.getLocalName(), oldTag.getQName(), attributes);
	// return tag;
	// }

	// protected TagAttributeImpl createAttribute(Tag tag, final String
	// nameSpaceKey, String property) {
	// return new TagAttributeImpl(tag.getLocation(), tag.getNamespace(),
	// nameSpaceKey, nameSpaceKey, property);
	// }

}
