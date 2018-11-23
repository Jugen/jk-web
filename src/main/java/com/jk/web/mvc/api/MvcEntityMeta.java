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
package com.jk.web.mvc.api;

import java.util.List;
import java.util.Vector;

// TODO: Auto-generated Javadoc
/**
 * The Class MvcEntityMeta.
 */
public class MvcEntityMeta {

	/** The bean name. */
	private String beanName;
	
	/** The name. */
	private String name;
	
	/** The fields. */
	private List<MvcFieldMeta> fields=new Vector<>();

	/**
	 * Sets the bean name.
	 *
	 * @param beanName the new bean name
	 */
	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}
	
	/**
	 * Gets the bean name.
	 *
	 * @return the bean name
	 */
	public String getBeanName() {
		return beanName;
	}

	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Adds the field.
	 *
	 * @param field the field
	 */
	public void addField(MvcFieldMeta field) {
		this.fields.add(field);
	}
	
	/**
	 * Gets the fields.
	 *
	 * @return the fields
	 */
	public List<MvcFieldMeta> getFields() {
		return fields;
	}
	
	/**
	 * Sets the fields.
	 *
	 * @param fields the new fields
	 */
	public void setFields(List<MvcFieldMeta> fields) {
		this.fields = fields;
	}

}
