package app.web.base;

import app.client.utilities.Constants;

public class SecurityBeanBase<E> extends JPAEntityBean<E> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7031541663069061524L;

	protected E resultEntity;

	protected boolean logged;
	protected String isLoggedNR = "main";
	protected String notLoggedNR = "login";

	protected String msgError = "error";
	protected String msgNotResult = "not Result";

	public boolean isLogged() {
		return logged;
	}

	public void setLogged(boolean logged) {
		this.logged = logged;
	}

	@Override
	public void clear() {
		logged = false;
		params = null;
		resultEntity = null;
		super.clear();
	}

	protected boolean eligibilities() throws Exception {
		return true;
	}

	public String login() throws Exception {
		clear();
		beforeFind();
		int countResult = getEntityCount();

		if (countResult == 0) {
			warnMsg(msgNotResult);
			return null;
		}
		if (countResult == 1) {
			find();
		}
		if (countResult > 1) {
			getPaginatedList();
		}
		if (!eligibilities()) {
			entity = newInstace();
			resultEntity = null;
			clear();

			return null;
		}
		setLogged(true);
		getSessionScope().put(Constants.LOGGED, isLogged());
		getSessionScope().put(Constants.USER, resultEntity);
		return isLoggedNR;
	}

	public String logOut() {
		setLogged(false);
		getSessionScope().clear();
		getSessionScope().put(Constants.LOGGED, isLogged());
		clear();
		return notLoggedNR;
	}

	public E getResultEntity() {
		return resultEntity;
	}

}
