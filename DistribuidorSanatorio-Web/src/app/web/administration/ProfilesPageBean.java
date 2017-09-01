package app.web.administration;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

import app.client.utilities.CollectionsUtiliy;
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
		entityOptions = null;
		return PAGE_MAIN;
	}

	@Override
	protected String save() {
		return PAGE_MAIN;
	}

	private static final String QL_MENU = "SELECT e from Menu e where e.isParent = true and e.parentID is null order by e.id";

	private List<Menu> allMenus;
	private List<SelectItem> options;

	private List<Menu> getAllMenus() {

		if (allMenus == null) {
			allMenus = facadeHandler.findListEntity(QL_MENU, Menu.class);
			if (!CollectionsUtiliy.isEmptyList(allMenus)) {
				menuLabels(allMenus);
			}
		}
		return allMenus;
	}

	public List<SelectItem> getOptions() {
		if (options == null) {
			options = new ArrayList<SelectItem>();
			for (Menu menu : getAllMenus()) {
				options.add(new SelectItem(menu.getID(), menu.getLabel()));
			}
		}

		return options;
	}

	private String[] entityOptions;

	public String[] getEntityOptions() {
		if (entityOptions == null) {
			List<String> temporal = new ArrayList<String>();
			for (Menu option : entity.getOptions()) {
				temporal.add(String.valueOf(option.getID()));
			}
			entityOptions = new String[temporal.size()];
			entityOptions = temporal.toArray(entityOptions);
		}
		return entityOptions;
	}

	public void setEntityOptions(String[] entityOptions) {
		this.entityOptions = entityOptions;
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
	protected boolean beforeSave() {
		entity.setOptions(new ArrayList<Menu>(getNewOptions()));
		return super.beforeSave();
	}

	@Override
	protected void afterSave() {
		entityOptions = null;
		super.afterSave();
	}

	@Override
	public void clear() {
		entityOptions = null;
		super.clear();
	}

	private List<Menu> getNewOptions() {
		List<Menu> newOptions = new ArrayList<Menu>();

		for (String id : entityOptions) {
			Long temporal = Long.valueOf(id);

			for (Menu option : getAllMenus()) {
				if (temporal == option.getID()) {
					newOptions.add(option);
				}
			}
		}
		return newOptions;
	}
}