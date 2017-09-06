package app.schema.sanatorium;

import app.schema.embedable.Audit;

public class Area extends Audit {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2474513481314312876L;

	private long id;
	private String name;
	private boolean state;
	private long version;

	private Center center;

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
