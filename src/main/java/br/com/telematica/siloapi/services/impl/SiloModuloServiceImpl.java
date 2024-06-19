package br.com.telematica.siloapi.services.impl;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.telematica.siloapi.services.SiloModuloServInterface;

@Service
public class SiloModuloServiceImpl implements SiloModuloServInterface {

//	private static Logger logger = (Logger) LoggerFactory.getLogger(SiloModuloServiceImpl.class);
//	@Autowired
//	private SiloModuloRepository siloModuloRepository;

	@Override
	public ResponseEntity<Object> save(Object object) {
		return null;

	}

	@Override
	public ResponseEntity<Object> deleteByPlacod(Long codigo) {
		return null;
	}

	@Override
	public ResponseEntity<Object> update(Long codigo, Object object) {
		return null;
	}

	@Override
	public ResponseEntity<List<Object>> findAllSiloDTO() {
		return null;
	}

	@Override
	public ResponseEntity<Object> findById(Long codigo) {
		return null;
	}

}
