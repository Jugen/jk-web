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
package com.jk.web.util;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.jk.util.exceptions.JKException;
import com.jk.util.resources.JKDefaultResourceLoader;

// TODO: Auto-generated Javadoc
/**
 * The Class JKServletResourceLoader.
 *
 * @author Jalal Kiswani
 */
public class JKServletResourceLoader extends JKDefaultResourceLoader {
	
	/** The context. */
	ServletContext context;

	/**
	 * Construct a new <code>ServletResourceLoader</code> with the given
	 * context.
	 *
	 * @param context
	 *            ServletContext
	 */
	@Autowired
	public JKServletResourceLoader(ServletContext context) {
		this.context = context;
	}

	/* (non-Javadoc)
	 * @see com.jk.resources.JKDefaultResourceLoader#getResourceAsStream(java.lang.String)
	 */
	@Override
	public InputStream getResourceAsStream(String resourceName) {
		InputStream inputStream = context.getResourceAsStream(resourceName);
		if (inputStream == null) {
			return super.getResourceAsStream(resourceName);
		}
		return inputStream;
	}

	/* (non-Javadoc)
	 * @see com.jk.resources.JKDefaultResourceLoader#getResourceUrl(java.lang.String)
	 */
	@Override
	public URL getResourceUrl(String fileName) {
		try {
			URL resource = context.getResource(fileName);
			if (resource == null) {
				return super.getResourceUrl(fileName);
			}
			return resource;
		} catch (MalformedURLException e) {
			throw new JKException(e);
		}
	}

}
