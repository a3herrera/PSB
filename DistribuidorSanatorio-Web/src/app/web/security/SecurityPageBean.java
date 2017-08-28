package app.web.security;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import app.client.enums.EncryptionTypes;
import app.client.utilities.CollectionsUtiliy;
import app.client.utilities.Constants;
import app.client.utilities.ConstantsMessages;
import app.client.utilities.StringUtility;
import app.schema.enumerated.UserStates;
import app.security.Menu;
import app.security.Profile;
import app.security.User;
import app.web.base.SecurityBeanBase;

@SessionScoped
@ManagedBean(name = "SecurityPageBean")
public class SecurityPageBean extends SecurityBeanBase<User> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6193822077008977126L;
	private static final Logger log = LogManager.getLogger(SecurityPageBean.class.getName());

	public SecurityPageBean() {
		entity = new User();
	}

	@Override
	protected String getMsgNotResult() {
		return getMessages().getString(ConstantsMessages.MSG_LOGIN_NOT_USER);
	}

	@Override
	protected void beforeFind() {
		setWhereQL(" where upper(e.userName) = :userName");
		setParam("userName", entity.getUserName().toUpperCase());
	}

	@Override
	protected boolean eligibilities() throws Exception {
		if (resultEntity != null) {
			if (resultEntity.getState() != UserStates.Active) {
				warnMsg(getMessages().getString(ConstantsMessages.MSG_LOGIN_USER_ERROR));
				return false;
			}

			String token = StringUtility.encryptMessage(entity.getPassWord(), EncryptionTypes.MD5);
			if (StringUtility.isEmpty(resultEntity.getPassWord())
					|| (!StringUtility.equals(resultEntity.getPassWord(), token))) {
				warnMsg(getMessages().getString("message.login.error"));
				return false;

			}

			final Profile profile = resultEntity.getProfile();

			if (profile == null || (!profile.isState())) {
				warnMsg(getMessages().getString(ConstantsMessages.MSG_LOGIN_USER_ERROR));
				return false;
			}

			if (CollectionsUtiliy.isEmptyList(getMainOptions())) {
				warnMsg(getMessages().getString(ConstantsMessages.MSG_LOGIN_USER_ERROR));
				return false;
			}

		} else {
			warnMsg(getMessages().getString(ConstantsMessages.MSG_LOGIN_NOT_USER));
			return false;
		}
		return super.eligibilities();
	}

	@Override
	public String login() throws Exception {
		String result = super.login();
		log.debug("login");
		if (!StringUtility.isEmpty(result) && !CollectionsUtiliy.isEmptyList(mainOptions)) {
			log.debug("success login ");
			List<Menu> allOptions = allOption(mainOptions);
			getSessionScope().put(Constants.USER_OPTIONS, allOptions);
		} else
			log.error("Error login - Please try again ");
		return result;
	}

	public List<Menu> allOption(List<Menu> options) {
		List<Menu> allOptions = new ArrayList<Menu>();
		for (Menu option : options) {
			allOptions.add(option);
			if (!CollectionsUtiliy.isEmptyList(option.getSubMenus())) {
				for (Menu subOption : option.getSubMenus()) {
					allOptions.add(subOption);
				}
			}
		}
		return allOptions;
	}

	private List<Menu> mainOptions = Collections.emptyList();
	private Menu currentOption;

	public List<Menu> getMainOptions() throws Exception {
		if (CollectionsUtiliy.isEmptyList(mainOptions)) {
			mainOptions = new ArrayList<Menu>();
			if (resultEntity != null && resultEntity.getProfile() != null
					&& !CollectionsUtiliy.isEmptyList(resultEntity.getProfile().getOptions())) {
				List<Menu> options = resultEntity.getProfile().getOptions();

				for (Menu option : options) {
					Menu temporal = checkMenu(option);
					if (temporal != null)
						mainOptions.add(temporal);
				}
			}
		}
		return mainOptions;
	}

	public List<Menu> getSubOptions() {
		Menu temporal = new Menu();
		Menu sessionMenu = (Menu) getSessionScope().get(Constants.USER_CURRENT_OPTION);
		boolean checkOptions = true;
		if (currentOption != null) {
			temporal = currentOption;
		} else if (sessionMenu != null && currentOption == null) {
			checkOptions = false;
			temporal = sessionMenu;

		}

		if (checkOptions && (currentOption != null && sessionMenu != null)
				&& (currentOption.getID() != sessionMenu.getID())) {

			temporal = sessionMenu;

		}

		List<Menu> subOptions = Collections.emptyList();

		if (!CollectionsUtiliy.isEmptyList(temporal.getSubMenus())) {
			subOptions = temporal.getSubMenus();
		}

		return subOptions;
	}

	private Menu checkMenu(Menu menu) throws Exception {
		if (menu.isActive()) {
			Menu menuCopy = menu.createCopy();
			List<Menu> subOptions = new ArrayList<Menu>();
			if (!CollectionsUtiliy.isEmptyList(menu.getSubMenus())) {
				for (Menu subOption : menu.getSubMenus()) {
					Menu temporal = checkSubMenu(menuCopy, subOption);
					if (temporal != null)
						subOptions.add(temporal);
				}
			}

			assignLabel(menuCopy);
			menuCopy.setSubMenus(subOptions);
			return menuCopy;
		}

		return null;
	}

	private Menu checkSubMenu(Menu parent, Menu child) throws Exception {
		if (child.isActive()) {
			Menu copy = child.createCopy();
			copy.setParentID(parent);
			assignLabel(copy);
			return copy;
		}
		return null;
	}

	private void assignLabel(Menu menu) {
		try {
			menu.setLabel(getLanguage().getString(menu.getKey()));
		} catch (Exception ex) {
			menu.setLabel(menu.getDefaultKey());
		}

		if (StringUtility.isEmpty(menu.getLabel())) {
			menu.setLabel(menu.getDefaultKey());
		}

	}

	public String actionMenu(Menu option) {
		if (option != null) {
			String url = option.getURL();

			if (!url.startsWith("/"))
				url = "/".concat(url);

			if (!url.endsWith(".view") && !url.endsWith(".xhtml"))
				url = url.concat(".xhtml");

			url = url.concat("?faces-redirect=").concat(String.valueOf(option.isRedirect()));
			currentOption = option;
			return url;
		}
		return "";
	}

	public String mainActionMenu(Menu option) {
		if (option != null) {
			String url = option.getURL();
			if (!url.startsWith("/"))
				url = "/".concat(url);

			if (!url.endsWith(".view") && !url.endsWith(".xhtml"))
				url = url.concat(".xhtml");

			url = url.concat("?faces-redirect=").concat(String.valueOf(option.isRedirect()));
			return url;
		}
		return "";
	}

}
