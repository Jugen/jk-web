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
package com.jk.web.faces.decorators;

import java.util.List;

import javax.faces.view.facelets.Tag;
import javax.faces.view.facelets.TagDecorator;

import com.jk.util.JKObjectUtil;
import com.jk.util.annotations.Author;
import com.jk.util.logging.JKLogger;
import com.jk.util.logging.JKLoggerFactory;
import com.jk.web.faces.config.JKFacesConfigurations;
import com.jk.web.faces.config.JKNamespace;
import com.jk.web.faces.config.JKTagMapping;
import com.jk.web.faces.tags.JKTagAttributeWrapper;
import com.jk.web.faces.tags.JKTagWrapper;
import com.jk.web.faces.util.JKJsfUtil;

// TODO: Auto-generated Javadoc
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
	
	/** The logger. */
	JKLogger logger = JKLoggerFactory.getLogger(getClass());

	/** The Constant Instance. */
	public final static JKTagDecorator Instance = new JKTagDecorator();

	/**
	 * Instantiates a new JK tag decorator.
	 */
	public JKTagDecorator() {
	}

	/**
	 * Decorate.
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
		if (JKFacesConfigurations.getInstance().isDecorate()) {
			final JKTagWrapper wrapper = new JKTagWrapper(tag);
			this.logger.debug("decorate tag :", tag.getQName());
			if (wrapper.isHtmlTag()) {
				this.logger.debug("add missing namespaces");
				addMissingNamespaces(wrapper);
			} else {
				this.logger.debug("handle mapping for tag: " + tag.getQName());
				handleMapping(wrapper);
				if (wrapper.isUrlable()) {
					this.logger.debug("fixing links:" + tag.getQName());
					fixLinks(wrapper);
				}
			}
			return wrapper.buildTag();
		} else {
			return tag;
		}
	}

	/**
	 * Adds the names spaces.
	 *
	 * @param wrapper
	 *            the tag
	 * @return the tag
	 */
	protected void addMissingNamespaces(final JKTagWrapper wrapper) {
		final JKFacesConfigurations config = JKFacesConfigurations.getInstance();

		final List<JKNamespace> namespaces = config.getNamespaces();
		for (final JKNamespace namespace : namespaces) {
			wrapper.addAttribue(namespace.getPrefix(), namespace.getUrl());
		}
	}

	/**
	 * Fix links.
	 *
	 * @param wrapper the wrapper
	 */
	protected void fixLinks(final JKTagWrapper wrapper) {
		if (JKFacesConfigurations.getInstance().isDecorateFixLinks()) {
			 logger.debug("fix links");
			final List<JKTagAttributeWrapper> links = wrapper.getLinksAttributes();
			for (final JKTagAttributeWrapper link : links) {
				 logger.debug("fix link :", link.getValue());
				// TODO: check if contextPath already set
				if (link.getValue().contains("#{request.contextPath}")) {
					continue;
				}
				if (link.getValue().startsWith("/")) {
					String context = JKJsfUtil.evaluateExpressionToObject("#{request.contextPath}").toString();
					if (context != null && !context.trim().equals("")) {
						link.setValue(context.concat(link.getValue()));
					}
				}
				 logger.debug("final-link :", link.getValue());
			}
		}
	}

	/**
	 * Handle mapping.
	 *
	 * @param wrapper the wrapper
	 */
	protected void handleMapping(final JKTagWrapper wrapper) {
		if (JKFacesConfigurations.getInstance().isDecorateMapping()) {
			final JKFacesConfigurations config = JKFacesConfigurations.getInstance();

			final JKTagMapping mapping = config.findTagMapping(wrapper);
			if (mapping != null) {
				this.logger.debug("mapping found : ", JKObjectUtil.toString(mapping));
				final String nameSpaceLetter = mapping.getNameSpaceLetter();
				if (nameSpaceLetter != null) {
					final JKNamespace namespace = config.getNamespaceByLetter(nameSpaceLetter);
					wrapper.setNamespace(namespace.getUrl());
				}
				wrapper.setqName(mapping.getTargetQName());
				wrapper.setLocalName(mapping.getTargetLocalName());
			}
		}
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
