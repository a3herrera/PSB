/**
 * 
 */
package app.security;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import app.schema.embedable.Audit;
import app.schema.enumerated.UserStates;
import app.schema.internals.Internal;

/**
 * @author Angel Alfaro
 * @since v.1.0
 */
@Entity
@Access(AccessType.FIELD)
@Table(name = User.TABLE_NAME, uniqueConstraints = {
		@UniqueConstraint(columnNames = "USER_NAME", name = "CNN_UN_USER_NAME") })
@TableGenerator(name = User.SEQUENCE_NAME, table = User.SEQUENCE_NAME, pkColumnName = "USER_KEY", valueColumnName = "USER_VALUE", pkColumnValue = "USER_ID", allocationSize = 1)
public class User extends Audit {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3073672547941581169L;
	public static final String TABLE_NAME = "USERS";
	public static final String SEQUENCE_NAME = "SEQ_USER_ID";

	@Id
	@Column(name = "USER_ID")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = SEQUENCE_NAME)
	private long id;

	@NotNull(message = "{field.not.null}")
	@NotBlank(message = "{field.not.null}")
	@Size(max = 50, min = 4, message = "{field.size.error}")
	@Column(name = "USER_NAME", nullable = false, length = 50)
	private String userName;
	
	@NotNull(message = "{field.not.null}")
	@NotBlank(message = "{field.not.null}")
	@Size(max = 50, min = 4, message = "{field.size.error}")
	@Column(name = "EMAIL", nullable = false, length = 50)
	private String email;

	@NotNull(message = "{field.not.null}")
	@NotBlank(message = "{field.not.null}")
	@Size(max = 150, min = 4, message = "{field.size.error}")
	@Column(name = "PASSWORD", nullable = false, length = 150)
	private String passWord;

	@NotNull(message = "{field.not.null}")
	@Enumerated(EnumType.STRING)
	@Column(name = "STATE", nullable = false)
	private UserStates state;

	@NotNull(message = "{field.not.null}")
	@ManyToOne()
	@JoinColumn(name = "PROFILE_ID", updatable = true, insertable = true)
	private Profile profile;

	@OneToOne(fetch = FetchType.LAZY, orphanRemoval = false, cascade = { CascadeType.DETACH, CascadeType.MERGE,
			CascadeType.REFRESH }, mappedBy = "user")
	private Internal internal;

	@Version
	private long version;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public Internal getInternal() {
		return internal;
	}

	public void setInternal(Internal internal) {
		this.internal = internal;
	}

}
