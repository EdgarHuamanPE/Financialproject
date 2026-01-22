package com.tecsup.example.hexagonal.application.port.output;

import com.tecsup.example.hexagonal.domain.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    User save(User user);

    Optional<User> findById(Long id);

    List<User> findByApellidoPaterno(String apellidoPaterno);
    Optional<User> findByDni(String id);

    List<User> findByMenorEdad(Integer edad);
    Optional<User> findByEmail(String email);
}
