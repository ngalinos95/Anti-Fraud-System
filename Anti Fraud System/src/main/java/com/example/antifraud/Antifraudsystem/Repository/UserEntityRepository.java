package com.example.antifraud.Antifraudsystem.Repository;

import com.example.antifraud.Antifraudsystem.Model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@EnableJpaRepositories
public interface UserEntityRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findUserEntityByUsernameIgnoreCase(String username);

    List<UserEntity> findAll();
}