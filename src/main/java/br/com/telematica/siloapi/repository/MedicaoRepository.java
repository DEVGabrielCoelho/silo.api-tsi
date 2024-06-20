package br.com.telematica.siloapi.repository;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import br.com.telematica.siloapi.model.entity.Medicao;

public interface MedicaoRepository extends JpaRepository<Medicao, Date>, JpaSpecificationExecutor<Medicao> {

	void deleteByMsidth(Date msidth);

	Medicao findByMsidth(Date msidth);

}
