package app.schema.internals;

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
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Version;

import app.schema.embedable.Audit;
import app.schema.enumerated.InformationType;

@Entity
@Table(name = InternalAdditionalInfo.TABLE_NAME)
@TableGenerator(name = InternalAdditionalInfo.SEQUENCE_NAME, table = InternalAdditionalInfo.SEQUENCE_NAME, pkColumnName = "INT_ADD_INF_KEY", valueColumnName = "INT_ADD_INF_VALUE", pkColumnValue = "INT_ADD_INF_ID", allocationSize = 1)
public class InternalAdditionalInfo extends Audit {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2350115216076062679L;

	public static final String TABLE_NAME = "INTERNAL_INFORMATION";
	public static final String SEQUENCE_NAME = "SEQ_INTERNAL_ADDITIONAL_INFO_ID";

	@Id
	@Column(name = "INFORMATION_ID")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = SEQUENCE_NAME)
	private long id;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PERSON_ID", nullable = false)
	private Internal internal;

	@Enumerated(EnumType.STRING)
	@Column(name = "TYPE", length = 30, nullable = false)
	private InformationType type;

	@ElementCollection(fetch = FetchType.LAZY)
	@MapKeyColumn(name = "KEY_", nullable = false)
	@Column(name = "VALUE_", nullable = false)
	@CollectionTable(name = "INTERNAL_ADD_INFO", joinColumns = @JoinColumn(name = "INFORMATION_ID"))
	private Map<String, String> additionalInfo = new HashMap<String, String>();

	@Version
	private long version;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Internal getInternal() {
		return internal;
	}

	public void setInternal(Internal internal) {
		this.internal = internal;
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
