package com.tecsup.example.hexagonal.application.port.input;

import com.tecsup.example.hexagonal.domain.model.User;
import org.springframework.stereotype.Service;

import java.util.List;


public interface UserService {

    User createUser(User newUser);
    User findUser(Long id);
    List<User> findUserLastName(String apellidoPaterno);
    User findUserDni(String dni);
    List<User> findUserMenorEdad(Integer edad);

}
