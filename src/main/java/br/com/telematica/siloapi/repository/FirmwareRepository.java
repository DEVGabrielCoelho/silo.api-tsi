package br.com.telematica.siloapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.telematica.siloapi.model.entity.Firmware;

public interface FirmwareRepository extends JpaRepository<Firmware, Long> {

}
