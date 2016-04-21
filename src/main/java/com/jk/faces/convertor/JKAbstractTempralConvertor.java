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
package com.jk.faces.convertor;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;
import javax.faces.convert.DateTimeConverter;

import com.sun.faces.util.MessageFactory;

/**
 * The Class JKAbstractTempralConvertor.
 *
 * @author Jalal Kiswani
 */
public abstract class JKAbstractTempralConvertor extends DateTimeConverter {

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * javax.faces.convert.DateTimeConverter#getAsString(javax.faces.context.
	 * FacesContext, javax.faces.component.UIComponent, java.lang.Object)
	 */
	@Override
	public String getAsString(final FacesContext context, final UIComponent component, final Object value) {
		if (context == null || component == null) {
			throw new NullPointerException();
		}
		try {
			if (value == null) {
				return "";
			}
			if (value instanceof String) {
				return (String) value;
			}
			final DateFormat formatter = getFormatter();
			return formatter.format(value);

		} catch (final ConverterException e) {
			throw new ConverterException(MessageFactory.getMessage(context, DateTimeConverter.STRING_ID, value,
					MessageFactory.getLabel(context, component)), e);
		} catch (final Exception e) {
			throw new ConverterException(MessageFactory.getMessage(context, DateTimeConverter.STRING_ID, value,
					MessageFactory.getLabel(context, component)), e);
		}
	}

	/**
	 * Gets the formatter.
	 *
	 * @return the formatter
	 */
	protected SimpleDateFormat getFormatter() {
		return new SimpleDateFormat(getPattern());
	}

}
