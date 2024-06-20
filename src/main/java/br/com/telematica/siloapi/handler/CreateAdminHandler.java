package br.com.telematica.siloapi.handler;

import java.text.ParseException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.telematica.siloapi.model.EmpresaModel;
import br.com.telematica.siloapi.model.PermissaoModel;
import br.com.telematica.siloapi.model.RecursoModel;
import br.com.telematica.siloapi.model.UsuarioModel;
import br.com.telematica.siloapi.model.entity.Abrangencia;
import br.com.telematica.siloapi.model.entity.AbrangenciaDetalhes;
import br.com.telematica.siloapi.model.entity.Perfil;
import br.com.telematica.siloapi.model.entity.Recurso;
import br.com.telematica.siloapi.model.enums.RecursoMapEnum;
import br.com.telematica.siloapi.services.impl.AbrangenciaServiceImpl;
import br.com.telematica.siloapi.services.impl.EmpresaServiceImpl;
import br.com.telematica.siloapi.services.impl.PerfilPermissaoServiceImpl;
import br.com.telematica.siloapi.services.impl.RecursoServiceImpl;
import br.com.telematica.siloapi.services.impl.UsuarioServiceImpl;
import br.com.telematica.siloapi.utils.JsonNodeConverter;
import jakarta.annotation.PostConstruct;

@Configuration
public class CreateAdminHandler {

	private static final Logger logs = LoggerFactory.getLogger(CreateAdminHandler.class);

	@Autowired
	private UsuarioServiceImpl usuarioService;
	@Autowired
	private PerfilPermissaoServiceImpl perfilPermissaoService;
	@Autowired
	private RecursoServiceImpl recursoService;
	@Autowired
	private AbrangenciaServiceImpl abrangenciaService;
	@Autowired
	private EmpresaServiceImpl empresaService;

	private static Long cnpjTSI = Long.valueOf("44772937000150");

	private static String[] listaRecursos = { "PENDENCIA", "FIRMWARE", "LOGGER", "USUARIO", "PERFIL", "RECURSO", "ABRANGENCIA", "EMPRESA", "PLANTA", "MEDICAO", "SILO", "TIPOSILO" };
	private static String[] listaAbrangencia = { "EMPRESA", "PLANTA", "SILO", "TIPOSILO" };

	@PostConstruct
	public void init() {
		createAdminHandler();
	}

	public void createAdminHandler() {
		try {
			logs.debug("CreateAdminHandler Start... ");
			var user = usuarioService.findLoginEntity("ADMIN");
			if (user == null) {
				createEmpresa();
				createRecurso();
				createPerfilPermissao();
				createPerfilPermissaoDevice();
				createAbrangencia();
				createAbrangenciaDevice();
				createUsuario();
				createUsuarioDevice();
			}
		} catch (Exception e) {
			logs.error("CreateAdminHandler: ", e);
		}
	}

	public void createEmpresa() throws ParseException {
		try {
			logs.debug("createEmpresa Start... ");
			var empresa = empresaService.empresaFindByCnpjEntity(cnpjTSI);

			EmpresaModel empresaModel = new EmpresaModel(cnpjTSI, "Telemática - Sistemas Inteligentes", "TSI", "(99)99999-9999");
			if (empresa == null)
				empresaService.empresaSave(empresaModel);

		} catch (Exception e) {
			logs.error("createEmpresa: ", e);
		}
	}

	public void createPerfilPermissao() {
		try {
			logs.debug("createPerfil Start... ");
			var perfil = perfilPermissaoService.findByIdPerfilEntity("ADMIN");
			if (perfil == null)
				perfil = perfilPermissaoService.createUpdatePerfil(new Perfil(null, "ADMIN", "Perfil do Administrador."));
			else
				perfil = perfilPermissaoService.createUpdatePerfil(new Perfil(perfil.getPercod(), "ADMIN", "Perfil do Administrador."));
			int listItem = listaRecursos.length;
			for (int i = 0; i < listItem; i++) {
				RecursoMapEnum recursoEnum = RecursoMapEnum.valueOf(listaRecursos[i]);
				var valueRecurso = perfilPermissaoService.findByPerfilAndRecurso(perfil, recursoService.findByIdEntity(recursoEnum.getNome()));
				PermissaoModel permissao = new PermissaoModel(recursoEnum, 1, 1, 1, 1, 1);
				if (valueRecurso == null)
					perfilPermissaoService.saveEntityPermissao(perfil, permissao);
			}

		} catch (Exception e) {
			logs.error("createPerfil: ", e);
		}
	}

	public void createPerfilPermissaoDevice() {
		try {
			logs.debug("createPerfilDevice Start... ");
			var perfil = perfilPermissaoService.findByIdPerfilEntity("DEVICE");
			if (perfil == null)
				perfil = perfilPermissaoService.createUpdatePerfil(new Perfil(null, "DEVICE", "Perfil do DEVICE."));
			else
				perfil = perfilPermissaoService.createUpdatePerfil(new Perfil(perfil.getPercod(), "DEVICE", "Perfil do DEVICE."));
			int listItem = listaRecursos.length;
			for (int i = 0; i < listItem; i++) {
				RecursoMapEnum recursoEnum = RecursoMapEnum.valueOf(listaRecursos[i]);
				var valueRecurso = perfilPermissaoService.findByPerfilAndRecurso(perfil, recursoService.findByIdEntity(recursoEnum.getNome()));
				PermissaoModel permissao = new PermissaoModel(RecursoMapEnum.valueOf(listaRecursos[i]), 1, 1, 1, 1, 1);
				if (valueRecurso == null)
					perfilPermissaoService.saveEntityPermissao(perfil, permissao);
			}

		} catch (Exception e) {
			logs.error("createPerfilDevice: ", e);
		}
	}

	public void createRecurso() {
		try {
			logs.debug("createRecurso Start... ");
			int listItem = listaRecursos.length;
			for (int i = 0; i < listItem; i++) {
				RecursoMapEnum recursoEnum = RecursoMapEnum.valueOf(listaRecursos[i]);
				var valueRecurso = recursoService.findByIdEntity(recursoEnum.getNome());
				RecursoModel recurso = new RecursoModel(recursoEnum, "Descrição do Recurso " + listaRecursos[i]);
				if (valueRecurso == null)
					recursoService.saveEntity(recurso);
			}
		} catch (Exception e) {
			logs.error("createRecurso: ", e);
		}
	}

	public void createAbrangencia() throws Exception {
	    try {
	        logs.debug("createAbrangencia Start...");
	        
	        Abrangencia abrangencia = abrangenciaService.findByIdEntity("ADMIN");
	        
	        if (abrangencia == null) {
	            abrangencia = abrangenciaService.createUpdateAbrangencia(new Abrangencia(null, "ADMIN", "Descrição Abrangencia ADMIN"));
	        } else {
	            abrangencia = abrangenciaService.createUpdateAbrangencia(new Abrangencia(abrangencia.getAbrcod(), "ADMIN", "Descrição Abrangencia ADMIN"));
	        }

	        int listItem = listaAbrangencia.length;
	        for (int i = 0; i < listItem; i++) {
	            RecursoMapEnum recursoEnum = RecursoMapEnum.valueOf(listaAbrangencia[i]);
	            Recurso recurso = recursoService.findByIdEntity(recursoEnum.getNome());
	            
	            JsonNodeConverter jsonNode = new JsonNodeConverter();
	            String data = jsonNode.convertToDatabaseColumn(new ObjectMapper().createArrayNode());

	            abrangenciaService.saveOrUpdateAbrangenciaDetalhes(abrangencia, new AbrangenciaDetalhes(
	                null,
	                abrangencia,
	                recurso,
	                0, 
	                data
	            ));
	        }
	    } catch (Exception e) {
	        logs.error("createAbrangencia: ", e);
	    }
	}



	public void createAbrangenciaDevice() throws Exception {
	    try {
	        logs.debug("createAbrangenciaDevice Start...");
	        
	        Abrangencia abrangencia = abrangenciaService.findByIdEntity("DEVICE");
	        
	        if (abrangencia == null) {
	            abrangencia = abrangenciaService.createUpdateAbrangencia(new Abrangencia(null, "DEVICE", "Descrição Abrangencia DEVICE"));
	        } else {
	            abrangencia = abrangenciaService.createUpdateAbrangencia(new Abrangencia(abrangencia.getAbrcod(), "DEVICE", "Descrição Abrangencia DEVICE"));
	        }
	    } catch (Exception e) {
	        logs.error("createAbrangenciaDevice: ", e);
	    }
	}


	public void createUsuario() throws ParseException {
		try {
			logs.debug("createUsuario Start... ");
			var empresa = empresaService.empresaFindByCnpjEntity(cnpjTSI);
			var perfil = perfilPermissaoService.findByIdPerfilEntity("ADMIN");
			var abrangencia = abrangenciaService.findByIdEntity("ADMIN");
			UsuarioModel usuario = new UsuarioModel("ADMIN", Long.valueOf(0), "admin", "admin", "admin@admin.com", empresa.getEmpcod(), perfil.getPercod(), abrangencia.getAbrcod());
			var userCheck = usuarioService.findLoginEntity("admin");
			if (userCheck == null)
				usuarioService.saveUpdateEntity(usuario);
		} catch (Exception e) {
			logs.error("createUsuario: ", e);
		}
	}

	public void createUsuarioDevice() throws ParseException {
		try {
			logs.debug("createUsuarioDevice Start... ");
			var empresa = empresaService.empresaFindByCnpjEntity(cnpjTSI);
			var perfil = perfilPermissaoService.findByIdPerfilEntity("DEVICE");
			var abrangencia = abrangenciaService.findByIdEntity("DEVICE");
			UsuarioModel usuario = new UsuarioModel("DEVICE", Long.valueOf(0), "device", "device", "DEVICE@DEVICE.com", empresa.getEmpcod(), perfil.getPercod(), abrangencia.getAbrcod());
			var userCheck = usuarioService.findLoginEntity("device");
			if (userCheck == null)
				usuarioService.saveUpdateEntity(usuario);
		} catch (Exception e) {
			logs.error("createUsuarioDevice: ", e);
		}
	}
}
