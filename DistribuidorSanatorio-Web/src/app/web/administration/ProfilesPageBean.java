package app.web.administration;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.primefaces.model.DualListModel;

import app.client.utilities.StringUtility;
import app.security.Menu;
import app.security.Profile;
import app.web.base.JPAEntityBean;

@SessionScoped
@ManagedBean(name = "ProfilesPageBean")
public class ProfilesPageBean extends JPAEntityBean<Profile> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9074300633297029690L;

	private static final String PAGE_EDIT = "/administration/profilesCU.xhtml?faces-redirect=false";
	private static final String PAGE_MAIN = "/administration/profiles.xhtml?faces-redirect=true";

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
		return PAGE_MAIN;
	}

	@Override
	protected String save() {
		return PAGE_MAIN;
	}

	private final String QL_MENU = "SELECT e from Menu e where e.isParent = true and e.parentID is null";

	private List<Menu> getAllMenus() {
		return facadeHandler.findListEntity(QL_MENU, Menu.class);
	}

	private DualListModel<Menu> listModel;

	public DualListModel<Menu> getListModel() {
		if (listModel == null) {
			List<Menu> allMenus = getAllMenus();
			menuLabels(allMenus);
			List<Menu> profilesMenus = new ArrayList<Menu>();
			listModel = new DualListModel<Menu>(allMenus, profilesMenus);
		}
		return listModel;
	}

	public void setListModel(DualListModel<Menu> listModel) {
		this.listModel = listModel;
	}

	private void menuLabels(List<Menu> menus) {
		for (Menu menu : menus) {
			assignLabel(menu);
		}
	}

	private void assignLabel(Menu menu) {
		try {
			menu.setLabel(getLanguage().getString(menu.getKey()));
		} catch (Exception ex) {
			menu.setLabel(menu.getDefaultKey());
		}

		if (StringUtility.isEmpty(menu.getLabel())) {
			menu.setLabel(menu.getDefaultKey());
		}

	}

	@Override
	public void clear() {
		super.clear();
		listModel = null;
	}

	@Override
	protected void afterSave() {
		super.afterSave();
		listModel = null;
	}

	@Override
	protected void afterDelete() {
		super.afterDelete();
		listModel = null;
	}
}