package app.web.administration;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.MissingResourceException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import app.client.enums.EncryptionTypes;
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

	private static final Logger logger = LogManager.getLogger(SecurityPageBean.class.getName());

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
		profileId = null;
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
				profilesUI.add(new SelectItem(profile.getID(), profile.getName()));
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

	private static final String QL_CHECK_SECURITY_TOKEN = "Select count(e) from User e where e.id = :ID and e.passWord = :password";
	private static final String QL_CHECK_EXIST_USER = "Select count(e) from User e where e.userName = :userName";

	@Override
	protected boolean beforeSave() {

		Map<String, Object> parameters = new HashMap<String, Object>();

		parameters.put("userName", getEntity().getUserName().trim());
		int existUser = facadeHandler.countEntity(QL_CHECK_EXIST_USER, parameters, User.class);

		parameters = new HashMap<String, Object>();
		parameters.put("ID", getEntity().getId());
		parameters.put("password", getEntity().getPassWord());
		int count = facadeHandler.countEntity(QL_CHECK_SECURITY_TOKEN, parameters, User.class);

		String encryption = "";
		try {
			encryption = StringUtility.encryptMessage(entity.getPassWord(), EncryptionTypes.MD5);
		} catch (NoSuchAlgorithmException e) {
			logger.error("Error to encrypt the password");
			return false;
		}

		if (isNew()) {
			if (existUser > 0) {
				warnMsg(getMessages().getString("info.message.user.exists"));
			}
			getEntity().setPassWord(encryption);

		} else {
			// Pendiente de verificar si el usuario ya se encuentra duplicado en el sistema
			if (count <= 0) {
				getEntity().setPassWord(encryption);
			}
		}

		if (profileId == null) {
			warnMsg(getMessages().getString("info.message.user.profile.required"));
		} else {
			for (Profile profile : getAllProfiles()) {
				if (profile.getID() == profileId) {
					entity.setProfile(profile);
				}
			}
		}
		return super.beforeSave();
	}

	private Long profileId;

	public Long getProfileId() {
		if (profileId == null) {
			if (entity != null && entity.getProfile() != null) {
				profileId = entity.getProfile().getID();
			} else if (entity == null) {
				profileId = 0l;
			}

		}

		return profileId;
	}

	public void setProfileId(Long profileId) {
		this.profileId = profileId;
	}

}
