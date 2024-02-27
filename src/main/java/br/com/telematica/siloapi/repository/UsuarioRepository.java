package br.com.telematica.siloapi.repository;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.telematica.siloapi.model.enttity.UsuarioEntity;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioEntity, BigInteger>{
    UsuarioEntity findByUsulog(String login);
}
