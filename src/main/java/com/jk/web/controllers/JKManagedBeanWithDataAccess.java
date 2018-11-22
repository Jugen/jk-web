///*
// * Copyright 2002-2016 Jalal Kiswani.
// *
// * Licensed under the Apache License, Version 2.0 (the "License");
// * you may not use this file except in compliance with the License.
// * You may obtain a copy of the License at
// *
// * http://www.apache.org/licenses/LICENSE-2.0
// *
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS,
// * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// * See the License for the specific language governing permissions and
// * limitations under the License.
// */
//package com.jk.web.controllers;
//
//import java.util.List;
//
//import javax.annotation.PostConstruct;
//import javax.faces.bean.ManagedProperty;
//
//import com.jk.db.dataaccess.core.JKDataAccessService;
//import com.jk.db.datasource.JKDataSourceFactory;
//import com.jk.db.dynamic.dataaccess.JKObjectDataAccess;
//import com.jk.db.dynamic.dataaccess.JKObjectDataAccessImpl;
//import com.jk.db.dynamic.dataaccess.JKObjectDataAccessInMemory;
//import com.jk.faces.mb.JKManagedBean;
//import com.jk.faces.util.JKJsfUtil;
//import com.jk.util.JK;
//import com.jk.util.JKObjectUtil;
//import com.jk.util.factory.JKFactory;
//import com.jk.util.test.JKMockUtil;
//
//// TODO: Auto-generated Javadoc
///**
// * The Class JKDbManagedBean.
// *
// * @author Jalal Kiswani
// * @param <T> the generic type
// */
//public class JKManagedBeanWithDataAccess<T> extends JKManagedBean {
//
//	/** The data access. */
//	protected JKDataAccessService sqlDataAccess = JKDataSourceFactory.getDataAccessService();
//
//	/** The object data access. */
//	protected JKObjectDataAccess objectDataAccess;
//
//	/** The model. */
//	protected T model;
//
//	/** The model class. */
//	protected Class<T> modelClass;
//
//	/** The model list. */
//	protected List<T> modelList;
//
//	/** The mode. */
//	protected ControllerMode mode;
//
//	boolean allowGuestMode;
//
//	/**
//	 * Execute.
//	 *
//	 * @param query  the query
//	 * @param params the params
//	 */
//	protected void execute(final String query, final Object... params) {
//		this.sqlDataAccess.execute(query, params);
//	}
//
//	/**
//	 * Inits the.
//	 */
//	@PostConstruct
//	public void init() {
//		// to ensure that we get the right implementation if overriden somewhere
//		modelClass = JKFactory.type(JKObjectUtil.getGenericClassFromParent(this));
//		mode = ControllerMode.ADD;
//
//		if (allowGuestMode && isGuestMode()) {
//			objectDataAccess = JKFactory.instance(JKObjectDataAccessInMemory.class);
//		} else {
//			objectDataAccess = JKFactory.instance(JKObjectDataAccessImpl.class);
//		}
//	}
//
//	/**
//	 * Gets the model.
//	 *
//	 * @return the model
//	 */
//	public T getModel() {
//		if (model == null) {
//			try {
//				model = JKFactory.instance(modelClass);
//			} catch (Exception e) {
//				JK.throww(e);
//			}
//		}
//		return model;
//	}
//
//	/**
//	 * Sets the model.
//	 *
//	 * @param model the new model
//	 */
//	public void setModel(T model) {
//		if (model != this.model) {
//			this.model = model;
//			if(model==null) {
//				mode = ControllerMode.ADD;
//			}else {
//				mode = ControllerMode.READONLY;
//			}
//		}
//	}
//
//	/**
//	 * Adds the.
//	 * @return 
//	 */
//	public String add() {
//		Integer id = objectDataAccess.insert(model);
//		reset();
//		findModel(id);
//		return null;
//	}
//
//	/**
//	 * Find model.
//	 *
//	 * @param id the id
//	 */
//	public String findModel(Integer id) {
//		setModel(objectDataAccess.find(modelClass, id));
//		return null;
//	}
//
//	/**
//	 * Edits the.
//	 */
//	public String edit() {
//		mode = ControllerMode.EDIT;
//		return null;
//
//	}
//
//	/**
//	 * Save.
//	 */
//	public String save() {
//		objectDataAccess.update(model);
//		mode = ControllerMode.READONLY;
//		return null;
//	}
//
//	/**
//	 * Delete.
//	 */
//	public String delete() {
//		objectDataAccess.delete(model);
//		reset();
//		return null;
//	}
//
//	/**
//	 * Fill.
//	 * @return 
//	 */
//	public String fill() {
//		JKMockUtil.fillFields(getModel());
//		return null;
//	}
//
//	/**
//	 * Reset.
//	 * @return 
//	 */
//	public String reset() {
//		modelList = null;
//		model = null;
//		mode = ControllerMode.ADD;
//		return null;
//	}
//
//	/**
//	 * Checks if is allow add.
//	 *
//	 * @return true, if is allow add
//	 */
//	public boolean isAllowAdd() {
//		return mode == ControllerMode.ADD;
//	}
//
//	/**
//	 * Checks if is allow edit.
//	 *
//	 * @return true, if is allow edit
//	 */
//	public boolean isAllowEdit() {
//		return mode == ControllerMode.READONLY;
//	}
//
//	/**
//	 * Checks if is allow save.
//	 *
//	 * @return true, if is allow save
//	 */
//	public boolean isAllowSave() {
//		return mode == ControllerMode.EDIT;
//	}
//
//	/**
//	 * Checks if is allow delete.
//	 *
//	 * @return true, if is allow delete
//	 */
//	public boolean isAllowDelete() {
//		return mode == ControllerMode.EDIT;
//	}
//
//	/**
//	 * Checks if is allow reset.
//	 *
//	 * @return true, if is allow reset
//	 */
//	public boolean isAllowReset() {
//		return true;
//	}
//
//	/**
//	 * Checks if is allow fill.
//	 *
//	 * @return true, if is allow fill
//	 */
//	public boolean isAllowFill() {
//		return isAllowAdd() && isDevelopmentMode();
//	}
//
//
//	/**
//	 * Checks if is edits the mode.
//	 *
//	 * @return true, if is edits the mode
//	 */
//	public boolean isEditMode() {
//		return mode == ControllerMode.EDIT;
//	}
//
//	/**
//	 * Cancel edit.
//	 */
//	public void cancelEdit() {
//		mode = ControllerMode.READONLY;
//	}
//
//	/**
//	 * Checks if is read only mode.
//	 *
//	 * @return true, if is read only mode
//	 */
//	public boolean isReadOnlyMode() {
//		return mode == ControllerMode.READONLY;
//	}
//
//
//
//	/**
//	 * Gets the model list.
//	 *
//	 * @return the model list
//	 */
//	public List<T> getModelList() {
//		if (this.modelList == null) {
//			this.modelList = objectDataAccess.getAll(modelClass);
//		}
//		return this.modelList;
//	}
//
//	public boolean isGuestMode() {
//		return !isUserLoggedIn();
//	}
//
//	public boolean isAllowGuestMode() {
//		return allowGuestMode;
//	}
//
//	public void setAllowGuestMode(boolean allowGuestMode) {
//		this.allowGuestMode = allowGuestMode;
//	}
//
//
//	
//}
