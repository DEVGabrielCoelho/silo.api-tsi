package br.com.telematica.siloapi.model.dto;

import br.com.telematica.siloapi.model.entity.UsuarioEntity;
import br.com.telematica.siloapi.model.enums.RoleColectionEnum;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * RegistryDTO
 */
@Schema(description = "RegistryDTO")
public class RegistryDTO {

	@Schema(description = "Login", example = "user")
	private String user;
	@Schema(description = "Password", example = "password")
	private String password;
	@Schema(description = "Name", example = "User Name")
	private String name;
	@Schema(description = "Email", example = "example@example.com", format = "email")
	private String email;
	@Schema(description = "Role", example = "ADMIN")
	private RoleColectionEnum role;

	public RegistryDTO() {
	}

	public RegistryDTO(String user, String password, String name, String email, RoleColectionEnum role) {
		this.user = user;
		this.password = password;
		this.name = name;
		this.email = email;
		this.role = role;
	}

	public RegistryDTO(UsuarioEntity user) {
		this.user = user.getUsulog();
		this.password = user.getPassword();
		this.name = user.getUsunom();
		this.email = user.getUsuema();
		this.role = user.getUsurol();
	}

	// getters and setters

	public String getUser() {
		return user;
	}

	public void setUser(String login) {
		this.user = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public RoleColectionEnum getRole() {
		return role;
	}

	public void setRole(RoleColectionEnum role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "RegistryDTO [user=" + user + ", password=" + password + ", name=" + name + ", email=" + email + ", role=" + role + "]";
	}

}