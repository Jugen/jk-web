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
package com.jk.web.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

import com.jk.util.annotations.Author;
import com.jk.util.logging.JKLogger;
import com.jk.util.logging.JKLoggerFactory;
import com.jk.util.thread.JKThreadLocal;
import com.jk.web.JKWebConstants;
import com.jk.web.util.CharResponseWrapper;
import com.jk.web.util.JKWebContextUtil;
import com.jk.web.util.JKWebUtil;

// TODO: Auto-generated Javadoc
/**
 * The Class JKDefaultFilter.
 *
 * @author Jalal Kiswani
 */
@Author(name = "Jalal H. Kiswani", date = "1/11/2014", version = "1.0")
public class JKDefaultFilter implements Filter {

	/** The logger. */
	JKLogger logger = JKLoggerFactory.getLogger(getClass());

	/*
	 * (non-Javadoc)
	 *
	 * @see javax.servlet.Filter#destroy()
	 */
	@Override
	public void destroy() {

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest,
	 * javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	public void doFilter(final ServletRequest req, final ServletResponse res, final FilterChain chain)
			throws IOException, ServletException {
		try {
			final HttpServletRequest request = (HttpServletRequest) req;
			final HttpServletResponse response = (HttpServletResponse) res;
			JKWebContextUtil.sync(request);
//			final boolean multipart = ServletFileUpload.isMultipartContent(request);
//			if (!multipart) {
				final boolean toPdf = request.getParameter(JKWebConstants.PARAMTER_CONVERT_TO_PDF) != null;
				if (toPdf) {
					this.logger.debug("toPDF request");
					final HttpServletRequestWrapper rq = new HttpServletRequestWrapper(request);
					final CharResponseWrapper rs = new CharResponseWrapper(response);
					chain.doFilter(rq, rs);
					JKWebUtil.toPDF(request, response, rs.toString());
				} else {
					chain.doFilter(request, response);
				}
//			} else {
//				chain.doFilter(request, response);
//			}
		} catch (final Exception e) {
			if (e instanceof ServletException) {
				throw (ServletException) e;
			}
			throw new ServletException(e);
		} finally {
			JKThreadLocal.remove();
//			this.logger.info("@End of doFilter");
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	@Override
	public void init(final FilterConfig arg0) throws ServletException {
		this.logger.debug("@JKDefaultFilter initialized... ");

	}

}
