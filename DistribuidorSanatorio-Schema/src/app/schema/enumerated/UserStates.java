package app.schema.enumerated;

public enum UserStates {
	ACTIVE("label.state.active"), SUSPEND("label.state.suspend"), INACTIVE("label.state.inactive");

	private String key;

	UserStates(String key) {
		this.key = key;
	}

	public String getKey() {
		return key;
	}

}
