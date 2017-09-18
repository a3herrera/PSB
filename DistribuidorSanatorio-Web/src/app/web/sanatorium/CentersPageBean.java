package app.web.sanatorium;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import app.schema.sanatorium.Center;
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

}
