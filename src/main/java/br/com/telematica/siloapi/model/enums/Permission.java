package br.com.telematica.siloapi.model.enums;

public enum Permission {
	ADMIN("admin"), USER_1("user_1"), USER_2("user_2"), USER_3("user_3"), USER_4("user_4")

	;

	private final String permission;

	public String getPermission() {
		return permission;
	}

	private Permission(String permission) {
		this.permission = permission;
	}

}
