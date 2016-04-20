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
package com.jk.faces.exceptions;

import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerFactory;

import com.jk.annotations.Author;

/**
 * A factory for creating FacesMessageExceptionHandler objects.
 */
@Author(name = "Jalal H. Kiswani", date = "1/10/2014", version = "1.0")
public class FacesMessageExceptionHandlerFactory extends ExceptionHandlerFactory {

	/** The wrapped. */
	private final ExceptionHandlerFactory wrapped;

	/**
	 * Instantiates a new faces message exception handler factory.
	 *
	 * @param wrapped
	 *            the wrapped
	 */
	public FacesMessageExceptionHandlerFactory(final ExceptionHandlerFactory wrapped) {
		this.wrapped = wrapped;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see javax.faces.context.ExceptionHandlerFactory#getExceptionHandler()
	 */
	@Override
	public ExceptionHandler getExceptionHandler() {
		return new FacesMessageExceptionHandler(this.wrapped.getExceptionHandler());
	}

}
