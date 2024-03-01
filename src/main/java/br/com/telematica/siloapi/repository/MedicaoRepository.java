package br.com.telematica.siloapi.repository;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.telematica.siloapi.model.entity.MedicaoEntity;

public interface MedicaoRepository extends JpaRepository<MedicaoEntity, Date> {

    void deleteByMsidth(Date msidth);

    MedicaoEntity findByMsidth(Date msidth);

}
