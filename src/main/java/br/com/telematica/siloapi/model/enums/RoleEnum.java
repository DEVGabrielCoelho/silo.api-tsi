package br.com.telematica.siloapi.model.enums;

public enum RoleEnum {
	ADMIN("ADMIN"), USER("USER");

	private String role;

	RoleEnum(String role) {
		this.role = role;
	}

	public String toRole() {
		return role;
	}
}
