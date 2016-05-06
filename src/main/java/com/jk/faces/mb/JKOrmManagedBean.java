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
package com.jk.faces.mb;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.component.EditableValueHolder;
import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlForm;
import javax.faces.context.FacesContext;
import javax.faces.context.PartialViewContext;

import com.jk.db.dataaccess.orm.JKEntity;
import com.jk.db.dataaccess.orm.JKOrmDataAccess;
import com.jk.db.datasource.JKDataSourceFactory;
import com.jk.exceptions.JKException;
import com.jk.util.ObjectUtil;

public abstract class JKOrmManagedBean<T extends JKEntity> extends JKManagedBean {
	private T entity;
	private List<T> all;
	private Class<T> entityClass;
	private boolean alwaysRefreshList;
	private boolean resetAfterSave = true;

	/**
	 * 
	 */
	public JKOrmManagedBean() {
		ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
		this.entityClass = (Class<T>) genericSuperclass.getActualTypeArguments()[0];
	}

	/**
	 * 
	 * @return
	 */
	public String insert() {
		if (entity == null) {
			throw new IllegalStateException("Entity is null while calling merge");
		}
		beforeInsert();
		getDataAccess().insert(entity);
		reloadAll();
		success("INSERT_SUCCESSFULLY");
		afterInsert();
		if (isResetAfterSave()) {
			reset();
		} else {
			// force reload value from database
			find(entity.getIdValue());
		}
		return null;
	}

	/**
	 * 
	 * @return
	 */
	public String update() {
		if (entity == null) {
			throw new IllegalStateException("Entity is null while calling merge");
		}
		beforeUpdate();
		getDataAccess().insert(entity);
		reloadAll();
		success("UPDATED_SUCCESSFULLY");
		afterUpdate();
		if (isResetAfterSave()) {
			reset();
		} else {
			// force reload value from database
			find(entity.getIdValue());
		}
		return null;
	}

	protected void beforeInsert() {
	}

	protected void afterInsert() {
	}

	protected void beforeUpdate() {

	}

	protected void afterUpdate() {

	}

	protected void beforeDelete() {

	}

	public String find(Object object) {
		T entity = (T) getDataAccess().find(getEntityClass(), object);
		setEntity(entity);
		return null;
	}

	protected Class<T> getEntityClass() {
		return entityClass;
	}

	public List<T> getAll() {
		if (all == null) {
			reloadAll();
		}
		return all;
	}

	// ///////////////////////////////////////////////////
	public String delete() {
		beforeDelete();
		getDataAccess().delete(getIdValue(), getEntityClass());
		afterDelete();
		reloadAll();
		success("DELETED_SUCCESSFULLY");
		reset();
		return null;
	}

	protected void afterDelete() {
	}

	public Object getIdValue() {
		return getEntity().getIdValue();
	}

	public void reloadAll() {
		all = getDataAccess().getList(getEntityClass());
	}

	public T getEntity() {
		if (entity == null) {
			entity = createEmptyEntity();
		}
		return entity;
	}

	protected T createEmptyEntity() {
		return ObjectUtil.newInstance(getEntityClass());
	}

	public void setEntity(T entity) {
		// to avoid loosing any local views value
		if (entity != this.entity) {
			if (this.entity == null || (!this.entity.equals(entity))) {
				this.entity = entity;
			}
		}
	}

	// ///////////////////////////////////////////////////
	protected JKOrmDataAccess getDataAccess() {
		return JKDataSourceFactory.getOrmDataAccess();
	}

	// ///////////////////////////////////////////////////
	public String reset() {
		entity = null;
		return null;
	}

	// ///////////////////////////////////////////////////
	public boolean isAlwaysRefreshList() {
		return alwaysRefreshList;
	}

	public void setAlwaysRefreshList(boolean alwaysRefreshList) {
		this.alwaysRefreshList = alwaysRefreshList;
	}

	public void setIdValue(Object value) {
		ObjectUtil.setPeopertyValue(getEntity(), getEntity().getIdColumn().getFieldName(), value);
	}

	public String duplicate() {
		entity = ObjectUtil.cloneBean(getEntity());
		setIdValue(0);
		return null;
	}

	public boolean isResetAfterSave() {
		return resetAfterSave;
	}

	public void setResetAfterSave(boolean resetAfterSave) {
		this.resetAfterSave = resetAfterSave;
	}

	public JKEntity getEmptyEntity() {
		return createEmptyEntity();
	}

}
