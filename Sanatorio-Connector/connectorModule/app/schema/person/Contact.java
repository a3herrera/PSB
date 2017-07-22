package app.schema.person;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;

/**
 * 
 * @author Angel Alfaro
 * @since 1.0
 */
@Entity
@Table(name = Contact.tableName)
@SequenceGenerator(name = Contact.sequenceName, sequenceName = Contact.sequenceName, allocationSize = 50, initialValue = 1)
public class Contact implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7877425709988106019L;
	public static final String tableName = "PERSON_CONTACT";
	public static final String sequenceName = "SEQ_PERSON_CONTACT_ID";

	@Id
	@Column(name = "ID_CONTACT")
	@GeneratedValue(generator = Contact.sequenceName, strategy = GenerationType.SEQUENCE)
	private long ID;

	@Enumerated(EnumType.STRING)
	@Column(name = "Type", nullable = false, updatable = false, length = 50)
	private TypeContact type;

	@Version
	private long version;

	public Contact() {
		super();
	}

	public long getID() {
		return this.ID;
	}

	public void setID(long ID) {
		this.ID = ID;
	}

	public TypeContact getType() {
		return this.type;
	}

	public void setType(TypeContact type) {
		this.type = type;
	}

	public long getVersion() {
		return version;
	}

	public void setVersion(long version) {
		this.version = version;
	}

}
