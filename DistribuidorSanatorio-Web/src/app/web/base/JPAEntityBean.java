package app.web.base;

import java.util.ResourceBundle;

import javax.faces.bean.ManagedProperty;

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
	
	protected  String getCreateMsg() {
		return getMessages().getString("info.message.new");
	}

	protected  String getUpdateMsg() {
		return getMessages().getString("info.message.update");
	}
	
	protected  String getRemoveMsg() {
		return getMessages().getString("info.message.remove");
	}
}
