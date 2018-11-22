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
package com.jk.web.util;

import com.jk.util.JK;
import com.jk.util.exceptions.handler.JKExceptionHandler;

// TODO: Auto-generated Javadoc
/**
 * The Class JKWebExceptionHandler.
 */
public class JKWebExceptionHandler implements JKExceptionHandler{

	/* (non-Javadoc)
	 * @see com.jk.util.exceptions.handler.JKExceptionHandler#handle(java.lang.Throwable, boolean)
	 */
	@Override
	public void handle(Throwable throwable, boolean throwRuntimeException) {
		JK.fixMe();
		JK.handle(throwable);
	}

}
