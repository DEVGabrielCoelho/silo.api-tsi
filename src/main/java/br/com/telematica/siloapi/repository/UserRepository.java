package br.com.telematica.siloapi.repository;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.telematica.siloapi.model.enttity.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, BigInteger>{
    UserEntity findByUsulog(String login);
}
