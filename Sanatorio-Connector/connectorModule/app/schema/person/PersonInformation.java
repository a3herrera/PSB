package app.schema.person;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapKeyColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Version;

import app.schema.embedable.Audit;

@Entity
@Table(name = PersonInformation.TABLE_NAME)
@TableGenerator(name = PersonInformation.SEQUENCE_NAME, table = PersonInformation.SEQUENCE_NAME, pkColumnName = "PERSON_INF_KEY", valueColumnName = "PERSON_INF_VALUE", pkColumnValue = "PERSON_INF_ID", allocationSize = 1)
public class PersonInformation extends Audit {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2350115216076062679L;

	public static final String TABLE_NAME = "PERSON_INFORMATION";
	public static final String SEQUENCE_NAME = "SEQ_PERSON_INFORMATION_ID";

	@Id
	@Column(name = "INFORMATION_ID")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = SEQUENCE_NAME)
	private long ID;    

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name= "PERSON_ID", nullable = false)
	private Person person;

	@Enumerated(EnumType.STRING)
	@Column(name = "TYPE", length = 30, nullable = false)
	private InformationType type;

	@ElementCollection(fetch = FetchType.LAZY)
	@MapKeyColumn(name = "KEY_", nullable = false)
	@Column(name = "VALUE_", nullable = false)
	@CollectionTable(name = "PERSON_ADD_INFO", joinColumns = @JoinColumn(name = "INFORMATION_ID"))
	private Map<String, String> additionalInfo = new HashMap<String, String>();

	@Version
	private long version;

	public long getID() {
		return ID;
	}

	public void setID(long iD) {
		ID = iD;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public InformationType getType() {
		return type;
	}

	public void setType(InformationType type) {
		this.type = type;
	}

	public Map<String, String> getAdditionalInfo() {
		return additionalInfo;
	}

	public void setAdditionalInfo(Map<String, String> additionalInfo) {
		this.additionalInfo = additionalInfo;
	}

	public long getVersion() {
		return version;
	}

	public void setVersion(long version) {
		this.version = version;
	}

}
