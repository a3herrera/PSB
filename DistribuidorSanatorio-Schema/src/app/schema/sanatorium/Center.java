package app.schema.sanatorium;

import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import app.schema.embedable.Audit;

@Entity
@Access(AccessType.FIELD)
@Table()
public class Center extends Audit {

	/**
	 * 
	 */
	private static final long serialVersionUID = 461916502599513623L;
	
	public static final String TABLE_NAME = "";
	public static final String SEQUENCE_NAME = "";
	
	
	@Id
	private long id;
	private String name;
	private long version;

	private List<Area> areas;

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
