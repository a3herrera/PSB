package app.schema.person;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import app.schema.embedable.Audit;
import app.schema.security.User;

@Entity
@Table(name = Person.TABLE_NAME)
@TableGenerator(name = Person.SEQUENCE_NAME, table = Person.SEQUENCE_NAME, pkColumnName = "PERSON_KEY", valueColumnName = "PERSON_VALUE", pkColumnValue = "PERSON_ID", allocationSize = 1)
public class Person extends Audit {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3456992457637660485L;
	public static final String TABLE_NAME = "PERSONS";
	public static final String SEQUENCE_NAME = "SEQ_PERSON_ID";

	@Id
	@Column(name = "PERSON_ID")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = SEQUENCE_NAME)
	private long ID;

	@Column(name = "FIRTS_NAME", nullable = false, length = 20)
	private String firstName;

	@Column(name = "SECOND_NAME", nullable = true, length = 20)
	private String secondName;

	@Column(name = "SURNAME", nullable = true, length = 20)
	private String surname;

	@Column(name = "SECOND_SURNAME", nullable = true, length = 20)
	private String secondSurname;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "BIRTHDAY", nullable = true, length = 20)
	private Date birthDay;

	@Enumerated(EnumType.STRING)
	@Column(name = "GENDER", nullable = true, length = 20)
	private Gender gender;

	@OneToOne(fetch = FetchType.LAZY, mappedBy = "person")
	private User user;

	@OneToMany(mappedBy = "person", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<PersonInformation> information;

	@Version
	private long version;

	public long getID() {
		return ID;
	}

	public void setID(long iD) {
		ID = iD;
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

	public Date getBirthDay() {
		return birthDay;
	}

	public void setBirthDay(Date birthDay) {
		this.birthDay = birthDay;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<PersonInformation> getInformation() {
		return information;
	}

	public void setInformation(List<PersonInformation> information) {
		this.information = information;
	}

	public long getVersion() {
		return version;
	}

	public void setVersion(long version) {
		this.version = version;
	}

}
