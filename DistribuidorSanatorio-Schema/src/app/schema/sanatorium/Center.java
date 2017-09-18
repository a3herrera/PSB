package app.schema.sanatorium;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import app.schema.embedable.Audit;

/**
 * <p>
 * Contains the information about the medical centers
 * </p>
 * 
 * @category JPA Entity
 * @author Angel Alfaro
 * @since v1.0
 */
@Entity
@Access(AccessType.FIELD)
@Table(name = Center.TABLE_NAME)
@TableGenerator(name = Center.SEQUENCE_NAME, table = Center.SEQUENCE_NAME, pkColumnName = "CENTER_KEY", valueColumnName = "CENTER_VALUE", pkColumnValue = "CENTER_ID", allocationSize = 1)
public class Center extends Audit {

	private static final long serialVersionUID = 461916502599513623L;
	public static final String TABLE_NAME = "CENTERS";
	public static final String SEQUENCE_NAME = "SEQ_CENTERS_ID";
	private static final String TO_STRING = "Entity Center Information: \n\t ID: {1}" + "\n\t Name: {1}"
			+ "\n\t State: {2}" + "\n\t Version {3}" + "\n\t Total Areas: {5}";

	@Id
	@Column(name = "CENTER_ID")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = SEQUENCE_NAME)
	private long id;

	@NotNull(message = "{field.not.null}")
	@Size(max = 50, min = 5, message = "{field.size.error}")
	@Column(name = "CENTER_NAME", nullable = false, length = 50)
	private String name;

	@NotNull(message = "{field.not.null}")
	@Column(name = "ACTIVE", nullable = false, length = 1)
	private boolean state;

	@OneToMany(mappedBy = "center", fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL)
	private List<Area> areas = new ArrayList<Area>();

	@Version
	private long version;

	@Override
	@Transient
	public String toString() {
		return String.format(TO_STRING, getId(), getName(), isState() ? "Enabled" : "Disable", getVersion(),
				getAreas() == null ? "0" : getAreas().size());
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

	public List<Area> getAreas() {
		return areas;
	}

	public void setAreas(List<Area> areas) {
		this.areas = areas;
	}

}
