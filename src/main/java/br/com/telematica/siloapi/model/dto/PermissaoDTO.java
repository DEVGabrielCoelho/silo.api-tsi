package br.com.telematica.siloapi.model.dto;

import br.com.telematica.siloapi.model.entity.PermissaoEntity;
import br.com.telematica.siloapi.model.enums.MapaURLEnum;

public class PermissaoDTO extends Codigo {
	private Long usuario;
	private Long perfil;
	private MapaURLEnum descricao;
	private Integer get;
	private Integer post;
	private Integer put;
	private Integer delete;

	public Long getUsuario() {
		return usuario;
	}

	public void setUsuario(Long usuario) {
		this.usuario = usuario;
	}

	public Long getPerfil() {
		return perfil;
	}

	public void setPerfil(Long perfil) {
		this.perfil = perfil;
	}

	public MapaURLEnum getDescricao() {
		return descricao;
	}

	public void setDescricao(MapaURLEnum descricao) {
		this.descricao = descricao;
	}

	public Integer getGet() {
		return get;
	}

	public void setGet(Integer get) {
		this.get = get;
	}

	public Integer getPost() {
		return post;
	}

	public void setPost(Integer post) {
		this.post = post;
	}

	public Integer getPut() {
		return put;
	}

	public void setPut(Integer put) {
		this.put = put;
	}

	public Integer getDelete() {
		return delete;
	}

	public void setDelete(Integer delete) {
		this.delete = delete;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PermissaoDTO [");
		if (usuario != null) {
			builder.append("usuario=").append(usuario).append(", ");
		}
		if (perfil != null) {
			builder.append("perfil=").append(perfil).append(", ");
		}
		if (descricao != null) {
			builder.append("descricao=").append(descricao).append(", ");
		}
		if (get != null) {
			builder.append("get=").append(get).append(", ");
		}
		if (post != null) {
			builder.append("post=").append(post).append(", ");
		}
		if (put != null) {
			builder.append("put=").append(put).append(", ");
		}
		if (delete != null) {
			builder.append("delete=").append(delete);
		}
		builder.append("]");
		return builder.toString();
	}

	public PermissaoDTO(Long codigo, Long usuario, Long perfil, MapaURLEnum descricao, Integer get, Integer post, Integer put, Integer delete) {
		super(codigo);
		this.usuario = usuario;
		this.perfil = perfil;
		this.descricao = descricao;
		this.get = get;
		this.post = post;
		this.put = put;
		this.delete = delete;
	}

	public PermissaoDTO(PermissaoEntity perm) {
		super();

		MapaURLEnum enumURL = MapaURLEnum.valueOf(perm.getPemdes());

		this.setCodigo(perm.getPemcod());
		this.usuario = perm.getUsucod();
		this.perfil = perm.getPercod();
		this.descricao = enumURL;
		this.get = perm.getPemget();
		this.post = perm.getPempos();
		this.put = perm.getPemput();
		this.delete = perm.getPemdlt();
	}

	public PermissaoDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
}
