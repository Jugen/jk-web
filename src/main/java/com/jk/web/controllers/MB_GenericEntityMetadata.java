//package com.jk.web.controllers;
//
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//import java.util.Vector;
//
//import javax.faces.bean.ManagedBean;
//import javax.faces.bean.ViewScoped;
//
//import com.jk.db.dynamic.dataaccess.JKDynamicDataAccess;
//import com.jk.db.dynamic.dataaccess.JKVirtualDynamicDataAccess;
//import com.jk.metadata.core.EntityMetaData;
//import com.jk.metadata.core.EntityRecord;
//import com.jk.util.JK;
//import com.jk.util.logging.JKLogger;
//import com.jk.util.logging.JKLoggerFactory;
//
//@ManagedBean(name = "mb")
//@ViewScoped
//public class MB_GenericEntityMetadata extends JKManagedBeanWithDataAccess<Map> {
//	JKLogger logger = JKLoggerFactory.getLogger(getClass());
//	Map<String, Object> model;
//	List<Map<String, Object>> modelList;
//
//	EntityMetaData entity;
//	JKDynamicDataAccess dataAccess;
//
//	public MB_GenericEntityMetadata() {
//		logger.debug("MB_GenericEntityMetadata()");
//	}
//
//	/**
//	 * When called programmatcailly
//	 * 
//	 * @param guestMode
//	 */
//
//	@Override
//	public void init() {
//		logger.debug("Init from super class");
//		mode = ControllerMode.ADD;
//	}
//
//	@Override
//	public Map getModel() {
//		if (model == null) {
//			model = entity.createEmptyRecord().toMapWithCodeNames();
//		}
//		return model;
//	}
//
//	@Override
//	public void setModel(Map model) {
//		this.model = model;
//	}
//
//	@Override
//	public String add() {
//		logger.debug("Add in generic entity metadata");
//		EntityRecord record = entity.mapToRecordUsingCodeName(getModel());
//		getDataAccess().insertRecord(record);
//		return null;
//	}
//
//	@Override
//	public String findModel(Integer id) {
//		logger.debug("findModel ({}) in Generic Metadata", id);
//		JKDynamicDataAccess dataAccess = getDataAccess();
//		EntityRecord record = dataAccess.findRecord(id);
//		if (record != null) {
//			model = record.toMap();
//		} else {
//			model = null;
//		}
//		return null;
//	}
//
//	@Override
//	public String save() {
//		logger.debug("Save record in Generic..");
//		EntityRecord record = entity.mapToRecord(getModel());
//		getDataAccess().updateRecord(record);
//		return null;
//	}
//
//	@Override
//	public String delete() {
//		logger.debug("Delete");
//		getDataAccess().deleteRecord(getModel().get("id"));
//		return null;
//	}
//
//	@Override
//	public String fill() {
//		logger.debug("Fill");
//		this.model = entity.fillDummyData().toMap();
//		return null;
//	}
//
//	@Override
//	public List getModelList() {
//		logger.debug("Get model list");
//		if (modelList == null) {
//			modelList = new Vector<>();
//			List<EntityRecord> records = getDataAccess().lstRecords();
//			for (EntityRecord entityRecord : records) {
//				modelList.add(entityRecord.toMapWithCodeNames());
//			}
//		}
//		return modelList;
//	}
//
//	public EntityMetaData getEntity() {
//		return entity;
//	}
//
//	public void setEntity(EntityMetaData entity) {
//		this.entity = entity;
//	}
//
//	protected JKDynamicDataAccess getDataAccess() {
//		logger.debug("GetDataAccess");
//		if (dataAccess == null) {
//			if (isAllowGuestMode()) {
//				dataAccess = new JKVirtualDynamicDataAccess(entity);
//			} else {
//				dataAccess = new JKDynamicDataAccess(entity);
//			}
//		}
//		return dataAccess;
//	}
//
//	@Override
//	public String reset() {
//		logger.debug("Reset");
//		super.reset();
////		dataAccess=null;
////		this.modelList = null;
//		this.model = null;
//		return null;
//	}
//}
