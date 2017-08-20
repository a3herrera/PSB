package app.web.security;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import app.client.enums.EncryptionTypes;
import app.client.utilities.StringUtility;
import app.schema.enumerated.UserStates;
import app.security.User;
import app.web.base.SecurityBeanBase;

@ViewScoped
@ManagedBean(name = "SecurityPageBean")
public class SecurityPageBean extends SecurityBeanBase<User> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6193822077008977126L;

	public SecurityPageBean() {
		entity = new User();
	}

	@Override
	protected String getMsgNotResult() {
		return getMessages().getString("message.login.notUser");
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
				warnMsg(getMessages().getString("message.login.userError"));
				return false;
			}

			String token = StringUtility.encryptMessage(entity.getPassWord(), EncryptionTypes.MD5);
			if (StringUtility.isEmpty(resultEntity.getPassWord())
					|| (!StringUtility.equals(resultEntity.getPassWord(), token))) {
				warnMsg(getMessages().getString("message.login.error"));
				return false;

			}

			// if (resultEntity.getPerfilID() == null
			// || !resultEntity.getPerfilID().isEstado()) {
			// errorMsg(getMsg().getString("message.login.profile.error"));
			// return false;
			// }
		} else {
			warnMsg(getMessages().getString("message.login.notUser"));
			return false;
		}
		return super.eligibilities();
	}

}
