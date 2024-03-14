package br.com.telematica.siloapi.model.enums;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

public enum RoleColectionEnum {

	USER("USER"), ADMIN("ADMIN");

	private final String permission;

	RoleColectionEnum(String permission) {
		this.permission = permission;
	}

	public String getPermission() {
		return permission;
	}

	public List<SimpleGrantedAuthority> getAuthorities() {
		List<SimpleGrantedAuthority> authorities = Stream.of(permission).map(SimpleGrantedAuthority::new).collect(Collectors.toList());

		authorities.add(new SimpleGrantedAuthority(this.name()));
		return authorities;
	}
}
