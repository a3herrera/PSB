package app.security;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
	private long ID;

	@Column(name = "NAME", nullable = false, length = 30)
	private String name;

	@Column(name = "STATE", nullable = false)
	private boolean state;

	@Version
	private long version;

	public long getID() {
		return ID;
	}

	public void setID(long iD) {
		ID = iD;
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

}
