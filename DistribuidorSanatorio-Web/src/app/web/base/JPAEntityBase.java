package app.web.base;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;

import app.client.utilities.StringUtility;
import app.interfaces.base.FacadeHandlerLocal;

public abstract class JPAEntityBase<E> extends BeanBase {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4248396325711954104L;

	@EJB
	protected FacadeHandlerLocal facadeHandler;

	protected E entity;
	protected Object idEntity;
	protected Class<E> className;
	protected List<E> listEntity;
	protected Integer countEntity;

	protected int page = 1;
	protected int pageSize = 20;

	protected String listQL;
	protected String countQL;
	protected String whereQL;
	protected Map<String, Object> params = new HashMap<String, Object>();

	protected String msgCreate = "created";
	protected String msgUpdate = "updated";
	protected String msgRemove = "removed";

	protected String newNR = "new";
	protected String updateNR = "update";
	protected String removeNR = "remove";

	protected String add = "add";

	public void clear() {
		listEntity = null;
		countEntity = null;
		page = 1;
	}

	protected String getWhereQL() {
		return whereQL;
	}

	protected void setWhereQL(String whereQL) {
		this.whereQL = whereQL;
		listQL = null;
		countQL = null;
	}

	protected String getListQL() {
		if (StringUtility.isEmpty(listQL)) {
			listQL = "Select e from " + getClassName().getSimpleName() + " e ";
			if (!StringUtility.isEmpty(whereQL)) {
				listQL += whereQL;
			}
		}
		return listQL;
	}

	protected String getCountQL() {
		if (StringUtility.isEmpty(countQL)) {
			countQL = "Select count(e) from " + getClassName().getSimpleName() + " e ";
			if (!StringUtility.isEmpty(whereQL)) {
				countQL += whereQL;
			}
		}
		return countQL;
	}

	protected Map<String, Object> getParams() {
		if (params == null) {
			params = new HashMap<String, Object>();
		}
		return params;
	}

	protected void newParams() {
		params = new HashMap<String, Object>();
	}

	protected void setParam(String key, Object value) {
		getParams().put(key, value);
	}

	/*
	 * Functions of the entity
	 */

	public E getEntity() {
		return entity;
	}

	public void setEntity(E entity) {
		this.entity = entity;
	}

	public Object getIdEntity() {
		return idEntity;
	}

	public void setIdEntity(Object idEntity) {
		this.idEntity = idEntity;
		if (idEntity == null) {
			entity = null;
		} else {
			find();
		}
	}

	public String newEntity() throws Exception {
		entity = newInstace();
		idEntity = null;
		return add;
	}

	public boolean isNew() {
		return getIdEntity() == null;
	}

	protected void beforeFind() {
	}

	protected void afterFind() {

	}

	protected void find() {
		beforeFind();
		entity = facadeHandler.findEntity(getIdEntity(), getClassName());
		if (entity == null) {
			setIdEntity(null);
		} else {
			afterFind();
		}
	}

	protected String navigationOption() {
		if (isNew()) {
			infMsg(msgCreate);
			return newNR;
		} else {
			infMsg(msgUpdate);
			return updateNR;
		}
	}

	protected boolean beforeSave() {
		return true;
	}

	protected void afterSave() {
		listEntity = null;
		countEntity = null;
	}

	public String saveEntity() {
		if (beforeSave()) {
			try {
				if (isNew()) {
					entity = facadeHandler.createEntity(entity);
				} else {
					entity = facadeHandler.updateEntity(entity);
				}
				afterSave();
			} catch (Exception ex) {
				return null;
			}
			return navigationOption();
		}
		return null;
	}

	protected boolean beforeDelete() {
		return true;
	}

	protected void afterDelete() {
		listEntity = null;
		countEntity = null;
		entity = null;
		idEntity = null;
	}

	public String delete() {
		if (beforeDelete()) {
			try {
				E subEntity = facadeHandler.findEntity(idEntity, getClassName());
				facadeHandler.removeEntity(subEntity);
			} catch (Exception ex) {
				return null;
			}
			clear();
			infMsg(msgRemove);
			return removeNR;
		}
		return null;
	}

	/*
	 * Consulta de registros
	 */

	protected void beforeList() {

	}

	public List<E> getPaginatedList() {
		if (listEntity == null) {
			try {
				beforeList();
				listEntity = facadeHandler.findListEntity(getListQL(), page, pageSize, getParams(), getClassName());
				countEntity = facadeHandler.countEntity(getCountQL(), page, pageSize, getParams(), getClassName());
			} catch (Exception ex) {
				countEntity = 0;
			}
		}

		return listEntity;
	}

	public int getEntityCount() {
		if (countEntity == null) {
			beforeFind();
			countEntity = facadeHandler.countEntity(getCountQL(), page, pageSize, getParams(), getClassName());
		}
		return countEntity;
	}

	@SuppressWarnings("unchecked")
	protected Class<E> getClassName() {
		Type type = getClass().getGenericSuperclass();
		if (type instanceof ParameterizedType) {
			ParameterizedType paramType = (ParameterizedType) type;
			className = (Class<E>) paramType.getActualTypeArguments()[0];
		}
		return className;
	}

	protected E newInstace() throws Exception {
		return getClassName().newInstance();
	}

	public int getPageCount() {
		int count = getEntityCount();
		if (count % pageSize == 0) {
			return count / pageSize;
		}
		return (count / pageSize) + 1;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		clear();
		this.pageSize = pageSize;
	}

	public int getCurrPage() {
		return page;
	}

	public void setCurrPage(int currPageNo) {
		if (currPageNo < 0) {
			currPageNo = 1;
		} else if (currPageNo > getPageCount()) {
			currPageNo = getPageCount();
		}
		listEntity = null;
		page = currPageNo;
	}

	public String first() {
		setCurrPage(1);
		return null;
	}

	public String previous() {
		setCurrPage(--page);
		return null;
	}

	public String next() {
		setCurrPage(++page);
		return null;
	}

	public String last() {
		setCurrPage(getPageCount());
		return null;
	}

	public boolean getPreviousExists() {
		return page > 1;
	}

	public boolean getNextExists() {
		return page < getPageCount();
	}

	public void refreshList() {
		clear();
	}

}
