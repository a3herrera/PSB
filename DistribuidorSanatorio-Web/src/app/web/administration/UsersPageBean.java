package app.web.administration;

import java.util.ArrayList;
import java.util.List;
import java.util.MissingResourceException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import app.client.utilities.StringUtility;
import app.schema.enumerated.UserStates;
import app.security.Profile;
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

	private Logger logger = LogManager.getLogger(SecurityPageBean.class.getName());

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

	private List<SelectItem> usersStatesUI;

	public List<SelectItem> getStates() {
		if (usersStatesUI == null) {
			usersStatesUI = new ArrayList<SelectItem>();
			for (UserStates state : UserStates.values()) {
				String label = getLabel(state.getKey(), state.toString());
				usersStatesUI.add(new SelectItem(state, label));
			}
		}
		return usersStatesUI;
	}

	private List<SelectItem> profilesUI;

	public List<SelectItem> getProfilesUI() {
		if (profilesUI == null) {
			profilesUI = new ArrayList<SelectItem>();
			for (Profile profile : getAllProfiles()) {
				profilesUI.add(new SelectItem(profile, profile.getName()));
			}
		}
		return profilesUI;
	}

	private List<Profile> profiles;

	private List<Profile> getAllProfiles() {
		if (profiles == null) {
			profiles = new ArrayList<Profile>();
			profiles = facadeHandler.findListEntity(Profile.class);
		}
		return profiles;
	}

	@Override
	protected void afterSave() {
		profiles = null;
		super.afterSave();
	}

	@Override
	public void clear() {
		profiles = null;
		super.clear();
	}

	public String label2(UserStates state) {
		String temporal = getLanguage().getString(state.getKey());
		try {
			if (StringUtility.isEmpty(temporal)) {
				return state.toString();
			} else {
				return temporal;
			}
		} catch (MissingResourceException ex) {
			return state.toString();
		}
	}
}
