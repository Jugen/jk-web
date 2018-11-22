/*
 * Copyright 2002-2018 Jalal Kiswani. 
 * E-mail: Kiswani.Jalal@Gmail.com
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
package com.jk.mvc.api;

import java.util.Map;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

// TODO: Auto-generated Javadoc
/**
 * The listener interface for receiving mvcSession events.
 * The class that is interested in processing a mvcSession
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addMvcSessionListener<code> method. When
 * the mvcSession event occurs, that object's appropriate
 * method is invoked.
 *
 * @see MvcSessionEvent
 */
@WebListener
public class MvcSessionListener implements HttpSessionListener {

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpSessionListener#sessionCreated(javax.servlet.http.HttpSessionEvent)
	 */
	@Override
	public void sessionCreated(HttpSessionEvent arg) {
		HttpSession session = arg.getSession();
		Map<String, MvcUserBinding> map = MvcUtil.createUserBindingMap();
		session.setAttribute(MvcConstants.ATTRIBUTE_BINDINGS_NAME, map);
	}

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpSessionListener#sessionDestroyed(javax.servlet.http.HttpSessionEvent)
	 */
	@Override
	public void sessionDestroyed(HttpSessionEvent arg) {
		Map<String, MvcPageModelBinding> map = (Map) arg.getSession().getAttribute(MvcConstants.ATTRIBUTE_BINDINGS_NAME);
		map.clear();
	}
}