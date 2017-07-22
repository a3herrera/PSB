package app.schema.embedable;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 
 * @author Angel Alfaro
 * @since 1.0
 */
@Embeddable()
@Access(AccessType.FIELD)
public abstract class Audit implements Serializable {

	private static final long serialVersionUID = -1120297788227336748L;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATION_DATE", insertable = true, updatable = false)
	private Date creationDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LAST_UPDATE", insertable = false, updatable = true)
	private Date lastUpdate;

	@PreUpdate
	private void preUpdate() {
		lastUpdate = new Date();
	}

	@PrePersist
	private void prePersist() {
		creationDate = new Date();

	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

}
