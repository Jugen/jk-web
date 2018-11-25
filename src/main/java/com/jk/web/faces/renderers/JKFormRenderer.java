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
package com.jk.web.faces.renderers;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import com.jk.util.JK;
import com.sun.faces.renderkit.html_basic.FormRenderer;

// TODO: Auto-generated Javadoc
/**
 * The Class JKFormRenderer.
 *
 * @author Tareq.saad
 * @since v 1.0 To put hidden field in the form for present the CSRF token
 */

public class JKFormRenderer extends FormRenderer {

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.sun.faces.renderkit.html_basic.FormRenderer#encodeEnd(javax.faces.
	 * context.FacesContext, javax.faces.component.UIComponent)
	 */
	@Override
	public void encodeEnd(final FacesContext context, final UIComponent component) throws IOException {
		JK.implementMe();
//		final HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
//		final CsrfGuard csrfGuard = CsrfGuard.getInstance();
//		final ResponseWriter writer = context.getResponseWriter();
//		String token = (String) session.getAttribute(csrfGuard.getSessionKey());
//		if (FacesContext.getCurrentInstance().getPartialViewContext().isAjaxRequest()) {
//			token = updateToken(session, csrfGuard);
//			writer.startElement("script", component);
//			writer.append("tokenValue='".concat(token).concat("';"));
//			// TODO : check me
//			writer.append("if(typeof(injectTokens) == \"function\") injectTokens();");
//			writer.endElement("script");
//		}
//		writer.startElement("input", component);
//		writer.writeAttribute("type", "hidden", "type");
//		writer.writeAttribute("name", csrfGuard.getTokenName(), "name");
//		writer.writeAttribute("value", "Token_Value", "value");
//		writer.endElement("input");
//
//		super.encodeEnd(context, component);
	}

//	/**
//	 * Update token.
//	 *
//	 * @param session
//	 *            the session
//	 * @param csrfGuard
//	 *            the csrf guard
//	 * @return the string
//	 */
//	private String updateToken(final HttpSession session, final CsrfGuard csrfGuard) {
//		Object tokenFromSession;
//		try {
//			tokenFromSession = RandomGenerator.generateRandomId(csrfGuard.getPrng(), csrfGuard.getTokenLength());
//		} catch (final Exception e) {
//			throw new RuntimeException(String.format("unable to generate the random token - %s", e.getLocalizedMessage()), e);
//		}
//
//		session.setAttribute(csrfGuard.getSessionKey(), tokenFromSession);
//
//		final String token = (String) session.getAttribute(csrfGuard.getSessionKey());
//		System.err.println("@Token : " + token);
//		if (token == null) {
//			throw new IllegalStateException("OWASP_CSRF is not configured correctly");
//		}
//		return token;
//	}
}
