package app.web.administration;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import app.security.Profile;
import app.web.base.JPAEntityBean;

@SessionScoped
@ManagedBean(name="ProfilesPageBean")
public class ProfilesPageBean extends JPAEntityBean<Profile>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 9074300633297029690L;

}
