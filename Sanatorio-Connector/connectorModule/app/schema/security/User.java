/**
 * 
 */
package app.schema.security;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import app.schema.embedable.Audit;
import app.schema.enumerate.UserStates;

/**
 * @author Angel Alfaro
 * @since v.1.0
 */
@Entity
@Access(AccessType.FIELD)
@Table(name = User.TABLE_NAME, uniqueConstraints = {
		@UniqueConstraint(columnNames = "USER_NAME", name = "CNN_UN_USER_NAME") })
@SequenceGenerator(name = User.SEQUENCE_NAME, sequenceName = User.SEQUENCE_NAME)
public class User extends Audit {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3073672547941581169L;
	public static final String TABLE_NAME = "USERS";
	public static final String SEQUENCE_NAME = "SEQ_USER_ID";

	@Id
	@Column(name = "USER_ID")
	private long ID;

	@NotNull(message = "{field.not.null}")
	@NotEmpty(message = "{field.not.null}")
	@Size(max = 50, min = 8, message = "{field.size.error}")
	@Column(name = "USER_NAME", nullable = false, length = 50)
	private String userName;

	@NotNull(message = "{field.not.null}")
	@NotEmpty(message = "{field.not.null}")
	@Size(max = 150, min = 8, message = "{field.size.error}")
	@Column(name = "PASSWORD", nullable = false, length = 150)
	private String passWord;

	@NotNull(message = "{field.not.null}")
	@NotEmpty(message = "{field.not.null}")
	@Enumerated(EnumType.STRING)
	@Column(name = "STATE", nullable = false)
	private UserStates state;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, optional = true)
	@JoinColumn(name = "PROFILE_ID", updatable = true, insertable = true)
	private Profile profile;

	@Version
	private long version;

	public long getID() {
		return ID;
	}

	public void setID(long iD) {
		ID = iD;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public UserStates getState() {
		return state;
	}

	public void setState(UserStates state) {
		this.state = state;
	}

	public long getVersion() {
		return version;
	}

	public void setVersion(long version) {
		this.version = version;
	}

	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}

}
