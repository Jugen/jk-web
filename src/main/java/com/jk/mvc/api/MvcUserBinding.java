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
 * The Class MvcUserBinding.
 */
public class MvcUserBinding {
	
	/** The binding. */
	MvcPageModelBinding binding;
	
	/** The model object. */
	Object modelObject;

	/**
	 * Instantiates a new mvc user binding.
	 *
	 * @param binding the binding
	 * @param modelInstance the model instance
	 */
	public MvcUserBinding(MvcPageModelBinding binding, Object modelInstance) {
		this.binding = binding;
		modelObject = modelInstance;
	}
	
	/**
	 * Gets the binding.
	 *
	 * @return the binding
	 */
	public MvcPageModelBinding getBinding() {
		return binding;
	}

	/**
	 * Sets the binding.
	 *
	 * @param binding the new binding
	 */
	public void setBinding(MvcPageModelBinding binding) {
		this.binding = binding;
	}

	/**
	 * Gets the model object.
	 *
	 * @return the model object
	 */
	public Object getModelObject() {
		return modelObject;
	}

	/**
	 * Sets the model object.
	 *
	 * @param modelObject the new model object
	 */
	public void setModelObject(Object modelObject) {
		this.modelObject = modelObject;
	}

}
