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
package com.jk.web.faces.mb;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import com.jk.util.JK;
import com.jk.util.locale.JKMessage;

// TODO: Auto-generated Javadoc
/**
 * The Class MBMessages.
 *
 * @author Jalal Kiswani
 */
@ManagedBean(name = "msg", eager = true)
@ApplicationScoped
public class MBMessages {

	/**
	 * Gets the.
	 *
	 * @param key the key
	 * @return the string
	 */
	// ////////////////////////////////////////////////////
	public String get(String key) {
		JK.fixMe("Solve the varargs issue with EL");
		return JKMessage.get(key);
	}

}
