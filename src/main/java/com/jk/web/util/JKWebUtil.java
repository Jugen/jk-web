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

import java.io.ByteArrayInputStream;
import java.io.OutputStream;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;

import com.jk.util.JK;
import com.jk.util.JKConversionUtil;

// TODO: Auto-generated Javadoc
/**
 * The Class WebUtil.
 *
 * @author Jalal Kiswani
 */
public class JKWebUtil {

	/**
	 * Fix html.
	 *
	 * @param request the request
	 * @param xhtml   the xhtml
	 * @return the string
	 */
	protected static String fixHtml(final HttpServletRequest request, String xhtml) {
		xhtml = xhtml.replaceAll("&nbsp", " ");
		// xhtml=xhtml.replaceAll("</input", "</div");
		xhtml = xhtml.replaceAll("rel=\"stylesheet\" href=\"/", "rel=\"stylesheet\" href=\"" + JKWebUtil.getContextPath(request));
		return xhtml;
	}

	/**
	 * Gets the context path.
	 *
	 * @param req the req
	 * @return the context path
	 */
	public static String getContextPath(final HttpServletRequest req) {
		final StringBuffer url = req.getRequestURL();
		final String uri = req.getRequestURI();
		// String ctx = req.getContextPath();
		final String base = url.substring(0, url.length() - uri.length()) + "/";
		return base;
	}

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(final String[] args) {
		System.out.println("12<abc>34</abc>56".replaceAll("<abc>|</abc>", ""));
	}

	/**
	 * To pdf.
	 *
	 * @param request  the request
	 * @param response the response
	 * @param xhtml    the xhtml
	 * @throws Exception the exception
	 */
	public static void toPDF(final HttpServletRequest request, final HttpServletResponse response, String xhtml) throws Exception {
		JK.implementMe();
//		response.setContentType("application/pdf");
//
//		final DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
//		xhtml = JKWebUtil.fixHtml(request, xhtml);
//		final ByteArrayInputStream baos = new ByteArrayInputStream(xhtml.getBytes());
//		final Document doc = builder.parse(baos);
//
//		final ITextRenderer renderer = new ITextRenderer();
//		renderer.setDocument(doc, null);
//
//		final OutputStream os = response.getOutputStream();
//		renderer.layout();
//		renderer.createPDF(os);
//		os.flush();
//		os.close();
	}

	public static boolean isDebug() {
		return false;
	}

}
