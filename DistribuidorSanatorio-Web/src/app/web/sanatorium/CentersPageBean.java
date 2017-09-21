package app.web.sanatorium;

import java.util.HashMap;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import app.schema.sanatorium.Center;
import app.security.User;
import app.web.base.JPAEntityBean;

@SessionScoped
@ManagedBean(name = "CentersPageBean")
public class CentersPageBean extends JPAEntityBean<Center> {

	private static final long serialVersionUID = 1607208878288698556L;
	private static final Logger logger = LogManager.getLogger(CentersPageBean.class.getName());

	private static final String PAGE_EDIT = "/sanatorium/centersCU.xhtml?faces-redirect=false";
	private static final String PAGE_MAIN = "/sanatorium/centers.xhtml?faces-redirect=true";

	@Override
	protected String add() {
		return PAGE_EDIT;
	}

	@Override
	protected String update() {
		return PAGE_EDIT;

	}

	@Override
	protected String cancel() {
		entity = null;
		clear();
		return PAGE_MAIN;
	}

	@Override
	protected String save() {
		entity = null;
		return PAGE_MAIN;
	}

	private static final String QL_CENTER_NAME_NEW = "Select count(e) from Center e where upper(e.name) = :name";
	private static final String QL_CENTER_NAME_EDIT = "Select count(e) from Center e where upper(e.name) = :name and e.id <> :id";

	@Override
	protected boolean beforeSave() {
		int centersCount = 0;
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("name", entity.getName().trim().toUpperCase());

		if (isNew()) {
			centersCount = facadeHandler.countEntity(QL_CENTER_NAME_NEW, parameters, User.class);
		} else {
			parameters.put("id", entity.getId());
			centersCount = facadeHandler.countEntity(QL_CENTER_NAME_EDIT, parameters, User.class);
		}

		if (centersCount > 0) {
			logger.info("The Center Name: ".concat(entity.getName()).concat(" is already register in the system"));
			warnMsg(getMessages().getString("info.message.center.exists"));
			return false;
		}
		return super.beforeSave();
	}

}
