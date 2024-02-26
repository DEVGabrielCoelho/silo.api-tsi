package br.com.telematica.ciloapi.repository;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.telematica.ciloapi.model.enttity.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, BigInteger>{
    UserEntity findByUsulog(String login);
}
