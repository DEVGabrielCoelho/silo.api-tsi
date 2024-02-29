package br.com.telematica.siloapi.model.enums;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

public enum RoleColectionEnum {

	USER(Collections.emptySet()), ADMIN(Collections.emptySet());

	private final Set<Permission> permission;

	RoleColectionEnum(Set<Permission> permissions) {
		this.permission = permissions;
	}

	public Set<Permission> getPermission() {
		return permission;
	}

	public List<SimpleGrantedAuthority> getAuthorities() {
		var authorities = getPermission().stream().map(permission -> new SimpleGrantedAuthority(permission.getPermission())).collect(Collectors.toList());
		authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
		return authorities;
	}
}
