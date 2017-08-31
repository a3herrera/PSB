package app.web.administration;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import app.security.User;
import app.web.base.JPAEntityBean;
import app.web.security.SecurityPageBean;

/**
 * 
 * @author Angel Alfaro
 * @since v1.0
 */
@SessionScoped
@ManagedBean(name = "UsersPageBean")
public class UsersPageBean extends JPAEntityBean<User> {

	private static final long serialVersionUID = 8961479372329330554L;

	private Logger logger =  LogManager.getLogger(SecurityPageBean.class.getName());
	
	private static final String PAGE_EDIT = "/administration/usersCU.xhtml?faces-redirect=false";
	private static final String PAGE_MAIN = "/administration/users.xhtml?faces-redirect=true";

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
		return PAGE_MAIN;
	}

	@Override
	protected String save() {
		return PAGE_MAIN;
	}

}
