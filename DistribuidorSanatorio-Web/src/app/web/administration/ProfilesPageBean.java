package app.web.administration;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import app.security.Profile;
import app.web.base.JPAEntityBean;

@SessionScoped
@ManagedBean(name = "ProfilesPageBean")
public class ProfilesPageBean extends JPAEntityBean<Profile> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9074300633297029690L;

	private static final String PAGE_EDIT = "/administration/profilesCU.xhtml?faces-redirect=false";
	private static final String PAGE_MAIN = "/administration/profiles.xhtml?faces-redirect=true";

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
