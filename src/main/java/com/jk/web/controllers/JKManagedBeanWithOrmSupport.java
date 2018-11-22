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
package com.jk.web.controllers;

import java.util.List;

import com.jk.db.dataaccess.core.JKDbConstants;
import com.jk.db.dataaccess.orm.JKObjectDataAccess;
import com.jk.db.datasource.JKDataSourceFactory;
import com.jk.faces.mb.JKManagedBean;
import com.jk.util.JKObjectUtil;
import com.jk.util.factory.JKFactory;
import com.jk.util.test.JKMockUtil;

/**
 * The Class JKOrmManagedBean.
 *
 * @author Jalal Kiswani
 * @param <T> the generic type
 */
public abstract class JKManagedBeanWithOrmSupport<T> extends JKManagedBean {

	/** The entity. */
	private T model;

	/** The entity class. */
	private Class<T> modelClass;

	/** The all. */
	private List<T> modelList;

	/** The always refresh list. */
	private boolean alwaysRefreshList;

	protected ControllerMode mode;

	/**
	 * Instantiates a new JK orm managed bean.
	 */
	public JKManagedBeanWithOrmSupport() {
		modelClass = JKFactory.type(JKObjectUtil.getGenericClassFromParent(this));
		mode = ControllerMode.ADD;
	}

	/**
	 * Insert.
	 *
	 * @return the string
	 */
	public String add() {
		if (model == null) {
			throw new IllegalStateException("Model is null while calling merge");
		}

		beforeInsert();
		T model = this.model;
		getDataAccess().insert(model);
		reset();
		afterInsert();
		find(getIdValue(model));
		return null;
	}

	/**
	 * Edits the.
	 */
	public String edit() {
		mode = ControllerMode.EDIT;
		return null;

	}

	/**
	 * Update.
	 *
	 * @return the string
	 */
	public String save() {
		if (model == null) {
			throw new IllegalStateException("Model is null while calling merge");
		}
		beforeUpdate();
		T model = this.model;
		getDataAccess().update(model);
		reset();
		afterUpdate();
		find(getIdValue(model));
		return null;
	}

	/**
	 * Before insert.
	 */
	protected void beforeInsert() {
	}

	/**
	 * After insert.
	 */
	protected void afterInsert() {
	}

	/**
	 * Before update.
	 */
	protected void beforeUpdate() {

	}

	/**
	 * After update.
	 */
	protected void afterUpdate() {

	}

	/**
	 * Before delete.
	 */
	protected void beforeDelete() {

	}

	/**
	 * Find.
	 *
	 * @param object the object
	 * @return the string
	 */
	public String find(int id) {
		T model = (T) getDataAccess().find(getModelClass(), id);
		setModel(model);
		mode = ControllerMode.READONLY;
		return null;
	}

	/**
	 * Gets the entity class.
	 *
	 * @return the entity class
	 */
	protected Class<T> getModelClass() {
		return modelClass;
	}

	/**
	 * Gets the all.
	 *
	 * @return the all
	 */
	public List<T> getModelList() {
		if (modelList == null) {
			modelList = getDataAccess().getList(getModelClass());
		}
		return modelList;
	}

	/**
	 * Delete.
	 *
	 * @return the string
	 */
	// ///////////////////////////////////////////////////
	public String delete() {
		beforeDelete();
		getDataAccess().delete(getModelClass(), getIdValue());
		afterDelete();
		reset();
		return null;
	}

	/**
	 * After delete.
	 */
	protected void afterDelete() {
	}

	/**
	 * Gets the id value.
	 *
	 * @return the id value
	 */
	public Integer getIdValue() {
		return getIdValue(getModel());
	}

	protected Integer getIdValue(T model) {
		return (Integer) JKObjectUtil.getFieldValue(model, JKDbConstants.DEFAULT_PRIMARY_KEY_FIELD_NAME);
	}

	/**
	 * Gets the entity.
	 *
	 * @return the entity
	 */
	public T getModel() {
		if (model == null) {
			model = createEmptyModel();
		}
		return model;
	}

	/**
	 * Creates the empty entity.
	 *
	 * @return the t
	 */
	protected T createEmptyModel() {
		return JKObjectUtil.newInstance(getModelClass());
	}

	/**
	 * Sets the entity.
	 *
	 * @param model the new entity
	 */
	public void setModel(T model) {
		// to avoid loosing any local views value
		if (model != this.model) {
			this.model = model;
			if (model == null) {
				mode = ControllerMode.ADD;
			} else {
				mode = ControllerMode.READONLY;
			}
		}
	}

	/**
	 * Gets the data access.
	 *
	 * @return the data access
	 */
	// ///////////////////////////////////////////////////
	protected JKObjectDataAccess getDataAccess() {
		return JKFactory.instance(JKObjectDataAccess.class);
	}

	/**
	 * Reset.
	 *
	 * @return the string
	 */
	// ///////////////////////////////////////////////////
	public String reset() {
		this.model = null;
		this.modelList = null;
		mode = ControllerMode.ADD;
		return null;
	}

	/**
	 * Checks if is always refresh list.
	 *
	 * @return true, if is always refresh list
	 */
	// ///////////////////////////////////////////////////
	public boolean isAlwaysRefreshList() {
		return alwaysRefreshList;
	}

	/**
	 * Sets the always refresh list.
	 *
	 * @param alwaysRefreshList the new always refresh list
	 */
	public void setAlwaysRefreshList(boolean alwaysRefreshList) {
		this.alwaysRefreshList = alwaysRefreshList;
	}

	/**
	 * Sets the id value.
	 *
	 * @param value the new id value
	 */
	public void setIdValue(Object value) {
		JKObjectUtil.setPeopertyValue(getModel(), JKDbConstants.DEFAULT_PRIMARY_KEY_FIELD_NAME, value);
	}

	/**
	 * Duplicate.
	 *
	 * @return the string
	 */
	public String duplicate() {
		model = JKObjectUtil.cloneBean(getModel());
		setIdValue(0);
		return null;
	}

	/**
	 * Gets the empty entity.
	 *
	 * @return the empty entity
	 */
	public T getEmptyModel() {
		return JKFactory.instance(modelClass);
	}

	/**
	 * Fill.
	 * 
	 * @return
	 */
	public String fill() {
		JKMockUtil.fillFields(getModel());
		return null;
	}

	/**
	 * Checks if is allow add.
	 *
	 * @return true, if is allow add
	 */
	public boolean isAllowAdd() {
		return mode == ControllerMode.ADD;
	}

	/**
	 * Checks if is allow edit.
	 *
	 * @return true, if is allow edit
	 */
	public boolean isAllowEdit() {
		return mode == ControllerMode.READONLY;
	}

	/**
	 * Checks if is allow save.
	 *
	 * @return true, if is allow save
	 */
	public boolean isAllowSave() {
		return mode == ControllerMode.EDIT;
	}

	/**
	 * Checks if is allow delete.
	 *
	 * @return true, if is allow delete
	 */
	public boolean isAllowDelete() {
		return mode == ControllerMode.EDIT;
	}

	/**
	 * Checks if is allow reset.
	 *
	 * @return true, if is allow reset
	 */
	public boolean isAllowReset() {
		return true;
	}

	/**
	 * Checks if is allow fill.
	 *
	 * @return true, if is allow fill
	 */
	public boolean isAllowFill() {
		return isAllowAdd() && isDevelopmentMode();
	}

	/**
	 * Checks if is edits the mode.
	 *
	 * @return true, if is edits the mode
	 */
	public boolean isEditMode() {
		return mode == ControllerMode.EDIT;
	}

	/**
	 * Cancel edit.
	 */
	public void cancelEdit() {
		mode = ControllerMode.READONLY;
	}

	/**
	 * Checks if is read only mode.
	 *
	 * @return true, if is read only mode
	 */
	public boolean isReadOnlyMode() {
		return mode == ControllerMode.READONLY;
	}

}
