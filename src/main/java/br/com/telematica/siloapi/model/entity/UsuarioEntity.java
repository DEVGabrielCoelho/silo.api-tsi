package br.com.telematica.siloapi.model.entity;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "usuario")
public class UsuarioEntity implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false)
	private Long usucod;
	@Column(nullable = false)
	private String usulog;
	@Column(nullable = false)
	private String ususen;
	private String usuema;
	@ManyToOne
	@JoinColumn(name = "percod", nullable = false)
	private PerfilEntity perfil;

	public Long getUsucod() {
		return usucod;
	}

	public void setUsucod(Long usucod) {
		this.usucod = usucod;
	}

	public String getUsulog() {
		return usulog;
	}

	public void setUsulog(String usulog) {
		this.usulog = usulog;
	}

	public String getUsusen() {
		return ususen;
	}

	public void setUsusen(String ususen) {
		this.ususen = ususen;
	}

	public String getUsuema() {
		return usuema;
	}

	public void setUsuema(String usuema) {
		this.usuema = usuema;
	}

	public PerfilEntity getPerfil() {
		return perfil;
	}

	public void setPerfil(PerfilEntity perfil) {
		this.perfil = perfil;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Usuario [usucod=");
		builder.append(usucod);
		builder.append(", usulog=");
		builder.append(usulog);
		builder.append(", ususen=");
		builder.append(ususen);
		builder.append(", usuema=");
		builder.append(usuema);
		builder.append(", perfil=");
		builder.append(perfil);
		builder.append("]");
		return builder.toString();
	}

	public UsuarioEntity(Long usucod, String usulog, String ususen, String usuema, PerfilEntity perfil) {
		super();
		this.usucod = usucod;
		this.usulog = usulog;
		this.ususen = ususen;
		this.usuema = usuema;
		this.perfil = perfil;
	}

	public UsuarioEntity(UsuarioEntity user) {
		super();
		this.usucod = user.getUsucod();
		this.usulog = user.getUsulog();
		this.ususen = user.getUsusen();
		this.usuema = user.getUsuema();
		this.perfil = user.getPerfil();

	}

	public UsuarioEntity() {
		super();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Collections.singleton(new SimpleGrantedAuthority(perfil.getPerdes()));
	}

	@Override
	public String getPassword() {
		return this.getUsusen();
	}

	@Override
	public String getUsername() {
		return this.getUsulog();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
		// throw new UnsupportedOperationException("Unimplemented method
		// 'isAccountNonExpired'");
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
		// throw new UnsupportedOperationException("Unimplemented method
		// 'isAccountNonLocked'");
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
		// throw new UnsupportedOperationException("Unimplemented method
		// 'isCredentialsNonExpired'");
	}

	@Override
	public boolean isEnabled() {
		return true;
		// throw new UnsupportedOperationException("Unimplemented method 'isEnabled'");
	}
}