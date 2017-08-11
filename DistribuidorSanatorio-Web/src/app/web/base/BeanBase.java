package app.web.base;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

public abstract class BeanBase implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6361895282240253862L;

	/**
	 * <p>
	 * Return the Faces Context from the current instance
	 * </p>
	 */
	public FacesContext getFacesContext() {
		return FacesContext.getCurrentInstance();
	}

	/**
	 * <p>
	 * Return the ExternalContext from the FacesContext current instance
	 * </p>
	 */
	public ExternalContext getExternalContext() {
		return getFacesContext().getExternalContext();
	}

	/**
	 * <p>
	 * Return the Application value of the FacesContext current instance
	 * </p>
	 */
	public Application getApplication() {
		return getFacesContext().getApplication();
	}

	/**
	 * <p>
	 * Return the values of request scope of the current external instance
	 * </p>
	 */
	public Map<String, Object> getRequestScope() {
		return getExternalContext().getRequestMap();
	}

	/**
	 * <p>
	 * Return the values of session scope of the current external instance
	 * </p>
	 */
	public Map<String, Object> getSessionScope() {
		return getExternalContext().getSessionMap();
	}

	/**
	 * <p>
	 * Return the values of application scope of the current external instance
	 * </p>
	 * 
	 * @return <code>Map</code>
	 */
	public Map<String, Object> getApplicationScope() {
		return getExternalContext().getApplicationMap();
	}

	/**
	 * <p>
	 * Remove the Request Scope if exist
	 * </p>
	 * 
	 * @param name
	 */
	public void removeRequestScope(String name) {
		if (getRequestScope().containsKey(name)) {
			getRequestScope().remove(name);
		}
	}

	/**
	 * <p>
	 * Remove the session scope if exist
	 * </p>
	 * 
	 * @param name
	 */
	public void removeSessionScope(String name) {
		if (getSessionScope().containsKey(name)) {
			getSessionScope().remove(name);
		}
	}

	/**
	 * <p>
	 * Remove the Application Scope if exist
	 * </p>
	 * 
	 * @param name
	 */
	public void removeApplicationScope(String name) {
		if (getApplicationScope().containsKey(name)) {
			getApplicationScope().remove(name);
		}
	}

	/**
	 * <p>
	 * Find a component with the type of component of the same
	 * </p>
	 * 
	 * @param component
	 * @param id
	 * @return result
	 */
	public static UIComponent findComponent(UIComponent component, String id) {
		if (id.equals(component.getId())) {
			return component;
		}
		UIComponent base = null;
		UIComponent result = null;
		Iterator<UIComponent> bases = component.getFacetsAndChildren();
		while (bases.hasNext() && result == null) {
			base = bases.next();
			if (id.equals(base.getId())) {
				result = base;
				continue;
			}
			result = findComponent(base, id);
			if (result != null) {
				continue;
			}
		}
		return result;
	}

	public UIComponent findComponent(String id) {
		FacesContext fctx = getFacesContext();
		if (fctx != null) {
			UIComponent root = fctx.getViewRoot();
			return findComponent(root, id);
		}
		return null;
	}

	/*
	 * Messages
	 */

	protected FacesMessage message(FacesMessage.Severity severity, String summary, String detail) {
		return new FacesMessage(severity, summary, detail);
	}

	protected void addMessage(String id, String summary, String detail, FacesMessage.Severity severity) {
		UIComponent component = findComponent(id);
		if (component == null)
			return;
		getFacesContext().addMessage(component.getClientId(getFacesContext()), message(severity, summary, detail));
	}

	protected void addUniqueMessage(String summary, String detail, FacesMessage.Severity severity) {
		for (Iterator<FacesMessage> i = getFacesContext().getMessages(); i.hasNext();) {
			FacesMessage m = i.next();
			String s = m.getSummary();
			if (s != null && s.equals(summary) && m.getSeverity() == severity)
				return;
		}
		getFacesContext().addMessage(null, new FacesMessage(severity, summary, detail));
	}

	protected Severity infoLvl = FacesMessage.SEVERITY_INFO;
	protected Severity warnLvl = FacesMessage.SEVERITY_WARN;
	protected Severity errorLvl = FacesMessage.SEVERITY_ERROR;
	protected Severity fatalLvl = FacesMessage.SEVERITY_FATAL;

	protected void infMsg(String summary) {
		addUniqueMessage(summary, null, infoLvl);
	}

	protected void infMsg(String summary, String detail) {
		addUniqueMessage(summary, detail, infoLvl);
	}

	protected void infoMsg(String id, String summary) {
		addMessage(id, summary, null, infoLvl);
	}

	protected void infoMsg(String id, String summary, String detail) {
		addMessage(id, summary, detail, infoLvl);
	}

	protected void warnMsg(String summary) {
		addUniqueMessage(summary, null, warnLvl);
	}

	protected void warnMsg(String summary, String detail) {
		addUniqueMessage(summary, detail, warnLvl);
	}

	protected void warningMsg(String id, String summary) {
		addMessage(id, summary, null, warnLvl);
	}

	protected void warningMsg(String id, String summary, String detail) {
		addMessage(id, summary, detail, warnLvl);
	}

	protected void errorMsg(String summary) {
		addUniqueMessage(summary, null, errorLvl);
	}

	protected void errorMsg(String summary, String detail) {
		addUniqueMessage(summary, detail, errorLvl);
	}

	protected void errMsg(String id, String summary) {
		addMessage(id, summary, null, errorLvl);
	}

	protected void errMsg(String id, String summary, String detail) {
		addMessage(id, summary, detail, errorLvl);
	}

	public Object getValue(String expression) {
		return getApplication().evaluateExpressionGet(getFacesContext(), expression, Object.class);
	}
}
