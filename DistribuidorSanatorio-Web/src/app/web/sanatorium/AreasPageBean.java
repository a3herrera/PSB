package app.web.sanatorium;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import app.schema.sanatorium.Area;
import app.schema.sanatorium.Center;
import app.security.User;
import app.web.base.JPAEntityBean;

@SessionScoped
@ManagedBean(name = "AreasPageBean")
public class AreasPageBean extends JPAEntityBean<Area> {

	private static final long serialVersionUID = 4028543085466770258L;
	private static final Logger logger = LogManager.getLogger(AreasPageBean.class.getName());
	private static final String PAGE_EDIT = "/sanatorium/areasCU.xhtml?faces-redirect=false";
	private static final String PAGE_MAIN = "/sanatorium/areas.xhtml?faces-redirect=true";

	@Override
	protected String add() {
		return PAGE_EDIT;
	}

	@Override
	protected String update() {
		return PAGE_EDIT;

	}

	@Override
	protected String cancel() {
		entity = null;
		centers = null;
		centerUI = null;
		centerID = null;
		clear();
		return PAGE_MAIN;
	}

	@Override
	protected String save() {
		entity = null;
		centers = null;
		centerUI = null;
		centerID = null;
		return PAGE_MAIN;
	}

	private List<Center> centers;

	private List<Center> getAllCenters() {
		if (centers == null) {
			centers = new ArrayList<Center>();
			centers = facadeHandler.findListEntity(Center.class);
		}
		return centers;
	}

	private List<SelectItem> centerUI;

	public List<SelectItem> getCenterUI() {
		if (centerUI == null) {
			centerUI = new ArrayList<SelectItem>();
			for (Center center : getAllCenters()) {
				centerUI.add(new SelectItem(center.getId(), center.getName()));
			}
		}
		return centerUI;
	}

	private Long centerID;

	public Long getCenterId() {
		if (centerID == null) {
			if (entity != null && entity.getCenter() != null) {
				centerID = entity.getCenter().getId();
			} else if (entity == null) {
				centerID = 0l;
			}

		}

		return centerID;
	}

	public void setCenterId(Long centerID) {
		this.centerID = centerID;
	}

	private static final String QL_AREA_NAME_NEW = "Select count(e) from Area e where upper(e.name) = :name and e.center.id = :centerId";
	private static final String QL_AREA_NAME_EDIT = "Select count(e) from Area e where upper(e.name) = :name and e.center.id = :centerId and e.id <> :id";

	@Override
	protected boolean beforeSave() {
		if (centerID == null) {
			warnMsg(getMessages().getString("info.message.area.center.required"));
			return false;
		} else {
			for (Center center : getAllCenters()) {
				if (center.getId() == centerID) {
					entity.setCenter(center);
				}
			}
		}

		int areasCount = 0;
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("name", entity.getName().trim().toUpperCase());
		parameters.put("centerId", entity.getCenter().getId());

		if (isNew()) {
			areasCount = facadeHandler.countEntity(QL_AREA_NAME_NEW, parameters, User.class);
		} else {
			parameters.put("id", entity.getId());
			areasCount = facadeHandler.countEntity(QL_AREA_NAME_EDIT, parameters, User.class);
		}

		if (areasCount > 0) {
			warnMsg(getMessages().getString("info.message.area.exists"));
			logger.info("The Area Name: ".concat(entity.getName())
					.concat(" is already register in the system in the Center ").concat(entity.getCenter().getName()));
			return false;
		}

		return super.beforeSave();
	}
}
