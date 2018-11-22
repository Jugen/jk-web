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

import java.io.CharArrayWriter;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

// TODO: Auto-generated Javadoc
/**
 * The Class CharResponseWrapper.
 *
 * @author Jalal Kiswani
 */
public class CharResponseWrapper extends HttpServletResponseWrapper {

	/** The output. */
	private final CharArrayWriter output;

	/**
	 * Instantiates a new char response wrapper.
	 *
	 * @param response
	 *            the response
	 */
	public CharResponseWrapper(final HttpServletResponse response) {
		super(response);
		this.output = new CharArrayWriter();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see javax.servlet.ServletResponseWrapper#getWriter()
	 */
	@Override
	public PrintWriter getWriter() {
		return new PrintWriter(this.output);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return this.output.toString();
	}
}