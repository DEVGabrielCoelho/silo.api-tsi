package br.com.telematica.siloapi.model.enums;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import static br.com.telematica.siloapi.model.enums.Permission.USER_1;
import static br.com.telematica.siloapi.model.enums.Permission.USER_2;
import static br.com.telematica.siloapi.model.enums.Permission.USER_3;
import static br.com.telematica.siloapi.model.enums.Permission.USER_4;

public enum RoleColectionEnum {

	USER(Set.of(USER_1, USER_2, USER_3, USER_4)), ADMIN(Collections.emptySet());

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
