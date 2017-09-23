package app.schema.internals;

import java.util.Date;
import java.util.List;

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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import app.schema.embedable.Audit;
import app.schema.enumerated.Gender;
import app.schema.enumerated.InternalYear;
import app.security.User;

@Entity
@Access(AccessType.FIELD)
@Table(name = Internal.TABLE_NAME)
@TableGenerator(name = Internal.SEQUENCE_NAME, table = Internal.SEQUENCE_NAME, pkColumnName = "INTERNAL_KEY", valueColumnName = "INTERNAL_VALUE", pkColumnValue = "INTERNAL_ID", allocationSize = 1)
public class Internal extends Audit {

	private static final long serialVersionUID = -3500872455739252194L;
	public static final String TABLE_NAME = "INTERNALS";
	public static final String SEQUENCE_NAME = "SEQ_INTERNALS_ID";

	@Id
	@Column(name = "INTERNAL_ID")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = SEQUENCE_NAME)
	private long id;

	@NotNull(message = "{field.not.null}")
	@NotBlank(message = "{field.not.null}")
	@Size(max = 30, min = 1, message = "{field.size.error}")
	@Column(name = "FIRST_NAME", length = 30, nullable = false)
	private String firstName;

	@Column(name = "SECOND_NAME", length = 30, nullable = true)
	private String secondName;

	@Column(name = "THIRD_NAME", length = 30, nullable = true)
	private String thirdName;

	@NotNull(message = "{field.not.null}")
	@NotBlank(message = "{field.not.null}")
	@Size(max = 30, min = 1, message = "{field.size.error}")
	@Column(name = "SURNAME", length = 30, nullable = false)
	private String surname;

	@Column(name = "SECOND_SURNAME", length = 30, nullable = false)
	private String secondSurname;

	@NotNull(message = "{field.not.null}")
	@Enumerated(EnumType.STRING)
	@Column(name = "GENDER", length = 15, nullable = false)
	private Gender gender;

	@NotNull(message = "{field.not.null}")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "BIRTHDAY", nullable = false)
	private Date birthday;

	@NotNull(message = "{field.not.null}")
	@Enumerated(EnumType.STRING)
	@Column(name = "INTERNAL_YEAR", nullable = false, length = 15)
	private InternalYear internalYear;

	@OneToOne
	@JoinColumn(name = "INTERNAL_PARTNER_ID", nullable = true)
	private Internal internalPartner;

	@OneToOne
	@JoinColumn(name = "USER_ID", nullable = true)
	private User user;

	@OneToMany(mappedBy = "internal", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<InternalAdditionalInfo> information;

	@Version
	private long version;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getSecondName() {
		return secondName;
	}

	public void setSecondName(String secondName) {
		this.secondName = secondName;
	}

	public String getThirdName() {
		return thirdName;
	}

	public void setThirdName(String thirdName) {
		this.thirdName = thirdName;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getSecondSurname() {
		return secondSurname;
	}

	public void setSecondSurname(String secondSurname) {
		this.secondSurname = secondSurname;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public InternalYear getInternalYear() {
		return internalYear;
	}

	public void setInternalYear(InternalYear internalYear) {
		this.internalYear = internalYear;
	}

	public Internal getInternalPartner() {
		return internalPartner;
	}

	public void setInternalPartner(Internal internalPartner) {
		this.internalPartner = internalPartner;
	}

	public long getVersion() {
		return version;
	}

	public void setVersion(long version) {
		this.version = version;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<InternalAdditionalInfo> getInformation() {
		return information;
	}

	public void setInformation(List<InternalAdditionalInfo> information) {
		this.information = information;
	}

}
