package app.web.security;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.validation.constraints.NotNull;

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
import app.web.administration.Controlador;
import app.web.administration.Correo;
import app.web.administration.UsersPageBean;
import app.web.base.SecurityBeanBase;

@SessionScoped
@ManagedBean(name = "SecurityPageBean")
public class SecurityPageBean extends SecurityBeanBase<User> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6193822077008977126L;
	private static final Logger logger = LogManager.getLogger(SecurityPageBean.class.getName());
	private String userName;
	private String previousPassword;

	@NotNull(message = "El campo es requerido ABAC")
	private String confirmPassword;
	private String newPassword;
	private String subject = "Mensaje de Prueba";
	private String message = "Este es el cuerpo del mensaje, aqui debe ir la contraseñia de recuperación";
	private String mail = "rodas-alex@hotmail.com";
	private String passWordT;

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
			if (resultEntity.getState() != UserStates.ACTIVE) {
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
		logger.debug("login");
		if (!StringUtility.isEmpty(result) && !CollectionsUtiliy.isEmptyList(mainOptions)) {
			logger.debug("success login ");
			List<Menu> allOptions = allOption(mainOptions);
			getSessionScope().put(Constants.USER_OPTIONS, allOptions);
		} else
			logger.error("Error login - Please try again ");
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

	public void changePassword() {
		logger.debug("changePassword() Begin");
		String validatePasswordEcr = StringUtility.encryptMessage(previousPassword, EncryptionTypes.MD5);
		try {
			if (validatePasswordEcr.equals(resultEntity.getPassWord())) {
				if (newPassword.equals(confirmPassword)) {
					String newPasswordEcr = StringUtility.encryptMessage(newPassword, EncryptionTypes.MD5);
					resultEntity.setPassWord(newPasswordEcr);
					saveChangePassword(resultEntity);
					logger.debug("Password successfully changed");
					warnMsg(getMessages().getString("message.changePassword.newPassSuccess"));
				} else {
					logger.debug("Error Changed Password, New Password does not match");
					warnMsg(getMessages().getString("message.changePassword.newPass"));
				}

			} else {
				logger.debug("Error Changed Password, Previous Password does not match");
				warnMsg(getMessages().getString("message.changePassword.previusPass"));

			}

		} catch (Exception e) {
			logger.error("Error : " + e);
		}
	}

	@Override
	public String receiverPass() throws Exception {
		Correo c = new Correo();
		c.setContrasenia(Constants.CNST_PASSWORD);
		c.setUsuarioCorreo(Constants.CNST_USER_MAIL);
		c.setAsunto(getSubject());
		c.setMensaje(getMessage());
		c.setDestino(getMail());
		Controlador co = new Controlador();
		if (co.enviarCorreo(c)) {
			logger.debug("Mail send successfully");
		} else
			logger.error("Error send to Message ");
		return Constants.RECOVER_PAGE.concat("?faces-redirect=true");
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
		} else if (sessionMenu != null) {
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

	@Override
	protected String add() {
		return "";
	}

	@Override
	protected String update() {
		return "";
	}

	@Override
	protected String cancel() {
		return "";
	}

	@Override
	protected String save() {
		return "";
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getPreviousPassword() {
		return previousPassword;
	}

	public void setPreviousPassword(String previousPassword) {
		this.previousPassword = previousPassword;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public String getPassWordT() {
		return passWordT;
	}

	public void setPassWordT(String passWordT) {
		this.passWordT = passWordT;
	}

}
