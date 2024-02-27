package br.com.telematica.siloapi.model.enttity;

import java.math.BigInteger;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import br.com.telematica.siloapi.model.enums.RoleColectionEnum;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
	private BigInteger usucod;
	private String usulog;
	private String ususen;
	private String usunom;
	private String usuema;
	@Enumerated(EnumType.STRING)
	private RoleColectionEnum usurol;

	public UsuarioEntity() {
	}

	public UsuarioEntity(String usulog, String ususen, String usunom, String usuema, RoleColectionEnum usurol) {
		this.usulog = usulog;
		this.ususen = ususen;
		this.usunom = usunom;
		this.usuema = usuema;
		this.usurol = usurol;
	}

	public BigInteger getUsucod() {
		return usucod;
	}

	public void setUsucod(BigInteger usucod) {
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

	public String getUsunom() {
		return usunom;
	}

	public void setUsunom(String usunom) {
		this.usunom = usunom;
	}

	public String getUsuema() {
		return usuema;
	}

	public void setUsuema(String usuema) {
		this.usuema = usuema;
	}

	public RoleColectionEnum getUsurol() {
		return usurol;
	}

	public void setUsurol(RoleColectionEnum usurol) {
		this.usurol = usurol;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		try {
			return usurol.getAuthorities();
		} catch (Exception e) {
			throw new UnsupportedOperationException("Unimplemented method 'getAuthorities'");
		}
	}

	@Override
	public String getPassword() {
		try {
			return this.ususen;
		} catch (Exception e) {
			throw new UnsupportedOperationException("Unimplemented method 'getPassword'");
		}
	}

	@Override
	public String getUsername() {
		try {
			return this.usulog;
		} catch (Exception e) {
			throw new UnsupportedOperationException("Unimplemented method 'getUsername'");
		}
	}

	@Override
	public boolean isAccountNonExpired() {
		try {
			return true;
		} catch (Exception e) {
			throw new UnsupportedOperationException("Unimplemented method 'isAccountNonExpired'");
		}
	}

	@Override
	public boolean isAccountNonLocked() {
		try {
			return true;
		} catch (Exception e) {
			throw new UnsupportedOperationException("Unimplemented method 'isAccountNonLocked'");
		}
	}

	@Override
	public boolean isCredentialsNonExpired() {
		try {
			return true;
		} catch (Exception e) {
			throw new UnsupportedOperationException("Unimplemented method 'isCredentialsNonExpired'");
		}
	}

	@Override
	public boolean isEnabled() {
		try {
			return true;
		} catch (Exception e) {
			throw new UnsupportedOperationException("Unimplemented method 'isEnabled'");
		}
	}

}
