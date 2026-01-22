package com.tecsup.example.hexagonal.infrastructure.adapter.output.persistence.repository;

import aj.org.objectweb.asm.commons.Remapper;
import com.tecsup.example.hexagonal.infrastructure.adapter.output.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface UserJpaRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByEmail(String email);
    List<UserEntity> findByApellidoPaterno(String apellidoPaterno);
    Optional<UserEntity> findByDni(String dni);
    List<UserEntity> findByEdadLessThan(Integer edad);
}
