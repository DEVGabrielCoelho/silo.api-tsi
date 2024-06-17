package br.com.telematica.siloapi.repository;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.telematica.siloapi.model.entity.Medicao;

public interface MedicaoRepository extends JpaRepository<Medicao, Date> {

	void deleteByMsidth(Date msidth);

	Medicao findByMsidth(Date msidth);

}
