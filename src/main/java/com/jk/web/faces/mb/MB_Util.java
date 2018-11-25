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
package com.jk.web.faces.mb;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import com.jk.util.JK;
import com.jk.util.JKDateTimeUtil;

// TODO: Auto-generated Javadoc
/**
 * The Class MB_Util.
 */
@ManagedBean(name = "util")
@ApplicationScoped
public class MB_Util extends JKManagedBean {

	/**
	 * Reload random.
	 *
	 * @return the int
	 */
	public int reloadRandom() {
		if (isDebug()) {
			return JK.randomNumber();
		}
		return 1;
	}

	/**
	 * Current year.
	 *
	 * @return the int
	 */
	public int currentYear() {
		return JKDateTimeUtil.getCurrentYear();
	}

	public String getPath() {
		String requestURI = request().getRequestURI();
		int lastIndexOfSlash = requestURI.lastIndexOf("/");
		if (lastIndexOfSlash == -1) {
			return "/";
		}
		return requestURI.substring(0, lastIndexOfSlash).concat("/");
	}

	public boolean isMobile() {
		return request().getHeader("User-Agent").indexOf("Mobile") != -1 ;
	}
}
