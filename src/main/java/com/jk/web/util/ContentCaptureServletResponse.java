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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

// TODO: Auto-generated Javadoc
/**
 * The Class ContentCaptureServletResponse.
 *
 * @author Jalal Kiswani
 */
public class ContentCaptureServletResponse extends HttpServletResponseWrapper {

	/** The content buffer. */
	private ByteArrayOutputStream contentBuffer;

	/** The writer. */
	private PrintWriter writer;

	/**
	 * Instantiates a new content capture servlet response.
	 *
	 * @param resp
	 *            the resp
	 */
	public ContentCaptureServletResponse(final HttpServletResponse resp) {
		super(resp);
	}

	/**
	 * Gets the content.
	 *
	 * @return the content
	 */
	public String getContent() {
		this.writer.flush();
		final String xhtmlContent = new String(this.contentBuffer.toByteArray());
		return xhtmlContent;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see javax.servlet.ServletResponseWrapper#getWriter()
	 */
	@Override
	public PrintWriter getWriter() throws IOException {
		if (this.writer == null) {
			this.contentBuffer = new ByteArrayOutputStream();
			this.writer = new PrintWriter(this.contentBuffer);
		}
		return this.writer;
	}
}