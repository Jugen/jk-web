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

// TODO: Auto-generated Javadoc
/**
 * The Class MvcPageModelBinding.
 */
public class MvcPageModelBinding {
	
	/** The model. */
	Class<?> model;
	
	/** The page. */
	MvcPage page;

	/**
	 * Instantiates a new mvc page model binding.
	 */
	public MvcPageModelBinding() {
	}

	/**
	 * Instantiates a new mvc page model binding.
	 *
	 * @param pagePath the page path
	 * @param model the model
	 */
	public MvcPageModelBinding(String pagePath, Class<?> model) {
		this.page = new MvcPage(pagePath);
		this.model = model;
	}

	/**
	 * Gets the model.
	 *
	 * @return the model
	 */
	public Class<?> getModel() {
		return model;
	}

	/**
	 * Sets the model.
	 *
	 * @param model the new model
	 */
	public void setModel(Class<?> model) {
		this.model = model;
	}

	/**
	 * Gets the page.
	 *
	 * @return the page
	 */
	public MvcPage getPage() {
		return page;
	}

	/**
	 * Sets the page.
	 *
	 * @param page the new page
	 */
	public void setPage(MvcPage page) {
		this.page = page;
	}

}
