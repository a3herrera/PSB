package app.security;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;

import app.schema.embedable.Audit;

/**
 * 
 * @author Angel Alfaro
 * @since v1.0
 */
@Entity
@Table(name = Profile.TABLE_NAME, uniqueConstraints = {
		@UniqueConstraint(columnNames = "NAME", name = "CNN_UN_PROFILE_NAME") })
@TableGenerator(name = Profile.SEQUENCE_NAME, table = Profile.SEQUENCE_NAME, pkColumnName = "PROFILE_KEY", valueColumnName = "PROFILE_VALUE", pkColumnValue = "PROFILE_ID", allocationSize = 1)
public class Profile extends Audit {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7027329364385849881L;

	public static final String TABLE_NAME = "PROFILES";
	public static final String SEQUENCE_NAME = "SEQ_PROFILE_ID";

	@Id
	@Column(name = "PROFILE_ID")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = SEQUENCE_NAME)
	private long id;

	@Column(name = "NAME", nullable = false, length = 30)
	private String name;

	@Column(name = "STATE", nullable = false)
	private boolean state;

	@Version
	private long version;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "PROFILE_OPTIONS", joinColumns = { @JoinColumn(name = "PROFILE_ID") }, inverseJoinColumns = {
			@JoinColumn(name = "MENU_ID") })
	private List<Menu> options;

	public long getID() {
		return id;
	}

	public void setID(long iD) {
		id = iD;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isState() {
		return state;
	}

	public void setState(boolean state) {
		this.state = state;
	}

	public long getVersion() {
		return version;
	}

	public void setVersion(long version) {
		this.version = version;
	}

	public List<Menu> getOptions() {
		return options;
	}

	public void setOptions(List<Menu> options) {
		this.options = options;
	}

}
