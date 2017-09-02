package app.web.base;

import java.util.ResourceBundle;

import javax.faces.bean.ManagedProperty;

import app.client.utilities.StringUtility;
import app.schema.enumerated.UserStates;

public abstract class JPAEntityBean<E> extends JPAEntityBase<E> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4284260281478004795L;

	@ManagedProperty(value = "#{message}")
	private ResourceBundle messages;

	@ManagedProperty(value = "#{language}")
	private ResourceBundle language;

	public ResourceBundle getMessages() {
		return messages;
	}

	public void setMessages(ResourceBundle messages) {
		this.messages = messages;
	}

	public ResourceBundle getLanguage() {
		return language;
	}

	public void setLanguage(ResourceBundle language) {
		this.language = language;
	}

	protected String getCreateMsg() {
		return getMessages().getString("info.message.new");
	}

	protected String getUpdateMsg() {
		return getMessages().getString("info.message.update");
	}

	protected String getRemoveMsg() {
		return getMessages().getString("info.message.remove");
	}

	

	public String getLabel(String key, String defaultValue) {
		if (StringUtility.isEmpty(key)) {
			return defaultValue;
		} else {
			String temporal = getLanguage().getString(key);
			if (StringUtility.isEmpty(temporal)) {
				return defaultValue;
			} else {
				return temporal;
			}
		}
	}
}
