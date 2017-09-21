package app.schema.sanatorium;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import app.schema.embedable.Audit;

@Entity
@Table(name = Area.TABLE_NAME)
@TableGenerator(name = Area.SEQUENCE_NAME, table = Area.SEQUENCE_NAME, pkColumnName = "AREA_KEY", valueColumnName = "AREA_VALUE", pkColumnValue = "AREA_ID", allocationSize = 1)
public class Area extends Audit {

	private static final long serialVersionUID = 2474513481314312876L;
	public static final String TABLE_NAME = "CENTER_AREAS";
	public static final String SEQUENCE_NAME = "SEQ_CENTER_AREAS_ID";
	private static final String TO_STRING = "Entity Area Information: \n\t ID: {}" + "\n\t Name: {}" + "\n\t State: {}"
			+ "\n\t Version: {}";

	@Id
	@Column(name = "AREA_ID")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = SEQUENCE_NAME)
	private long id;

	@NotNull(message = "{field.not.null}")
	@NotBlank(message = "{field.not.null}")
	@Size(max = 50, min = 5, message = "{field.size.error}")
	@Column(name = "AREA_NAME", length = 50, nullable = false)
	private String name;

	@NotNull(message = "{field.not.null}")
	@Column(name = "STATE", length = 1, nullable = false)
	private boolean state;

	@NotNull(message = "{field.not.null}")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CENTER_ID", nullable = false, updatable = false, insertable = true)
	private Center center;

	@Version
	private long version;

	@Override
	@Transient
	public String toString() {
		return String.format(TO_STRING, getName(), isState() ? "Enabled" : "Disabled", getVersion());
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	public Center getCenter() {
		return center;
	}

	public void setCenter(Center center) {
		this.center = center;
	}

}
