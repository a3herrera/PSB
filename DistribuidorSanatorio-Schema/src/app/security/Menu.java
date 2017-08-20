package app.security;

import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Version;

import app.schema.embedable.Audit;

/**
 * 
 * @author Angel Alfaro
 * @since v1.0
 */
@Entity
@Table(name = Menu.TABLE_NAME)
@TableGenerator(name = Menu.SEQUENCE_NAME, table = Menu.SEQUENCE_NAME, pkColumnName = "MENU_KEY", valueColumnName = "MENU_VALUE", pkColumnValue = "MENU_ID", allocationSize = 1)
public class Menu extends Audit {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7639401498139920975L;

	public static final String TABLE_NAME = "MENU_OPTIONS";
	public static final String SEQUENCE_NAME = "SEQ_MENU_OPTION_ID";

	@Id
	@Column(name = "MENU_ID")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = SEQUENCE_NAME)
	private long ID;

	@Column(name = "KEY_", nullable = false, length = 50)
	private String key;

	@Column(name = "URL", nullable = true, length = 50)
	private String URL;

	@Column(name = "ACTIVE", nullable = false, length = 2)
	private boolean active;

	@Column(name = "REDIRECT", nullable = false, length = 2)
	private boolean redirect;

	@Column(name = "IS_PARENT", length = 2, nullable = false)
	private boolean isParent;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PARENT_ID", nullable = true)
	private Menu parentID;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "parentID", orphanRemoval = true)
	private List<Menu> subMenus;

	@Version
	private long version;

	public long getID() {
		return ID;
	}

	public void setID(long iD) {
		ID = iD;
	}

	public boolean isParent() {
		return isParent;
	}

	public void setParent(boolean isParent) {
		this.isParent = isParent;
	}

	public Menu getParentID() {
		return parentID;
	}

	public void setParentID(Menu parentID) {
		this.parentID = parentID;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getURL() {
		return URL;
	}

	public void setURL(String uRL) {
		URL = uRL;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public boolean isRedirect() {
		return redirect;
	}

	public void setRedirect(boolean redirect) {
		this.redirect = redirect;
	}

	public long getVersion() {
		return version;
	}

	public void setVersion(long version) {
		this.version = version;
	}

	public List<Menu> getSubMenus() {
		return subMenus;
	}

	public void setSubMenus(List<Menu> subMenus) {
		this.subMenus = subMenus;
	}

}
