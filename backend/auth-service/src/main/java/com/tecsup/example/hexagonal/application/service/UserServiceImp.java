package com.tecsup.example.hexagonal.application.service;

import com.tecsup.example.hexagonal.application.port.input.UserService;
import com.tecsup.example.hexagonal.application.port.output.UserRepository;
import com.tecsup.example.hexagonal.domain.exception.InvalidUserDataException;
import com.tecsup.example.hexagonal.domain.exception.UserNotFoundException;
import com.tecsup.example.hexagonal.domain.exception.UserNotFoundExceptionByDni;
import com.tecsup.example.hexagonal.domain.exception.UserNotFoundExceptionMenorEdad;
import com.tecsup.example.hexagonal.domain.model.Role;
import com.tecsup.example.hexagonal.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


@RequiredArgsConstructor
public class UserServiceImp implements UserService {

    private static final Logger log = LoggerFactory.getLogger(UserServiceImp.class);
    private final UserRepository userRepository;

    @Override
    public User createUser(User newUser) {
        validateUserInput(newUser);

        //user.setName("Margot");

        //set default value
        log.info("Valor del rol {}:",newUser.getRole());
        if(newUser.getRole()==null){
           newUser.setRole(Role.USER);
        }

        User user = this.userRepository.save(newUser);
        return user;

    }



    @Override
    public User findUser(Long id) {

        if( id == null || id <= 0){
            throw  new IllegalArgumentException("Invalid user");
        }
        User user = this.userRepository.findById(id).orElseThrow(

                //()->new IllegalArgumentException("User not found")
                ()->new UserNotFoundException(id)
        );

        return user;
    }

    @Override
    public List<User> findUserLastName(String apellidoPaterno) {
        if( apellidoPaterno == null || apellidoPaterno.isEmpty()){
            throw  new IllegalArgumentException("Invalid user");
        }
        List<User> user = this.userRepository.findByApellidoPaterno(apellidoPaterno);
        if(user.isEmpty()){
            throw new UserNotFoundException(apellidoPaterno);
        }



        return user;
    }

    @Override
    public User findUserDni(String dni) {
        if( (dni==null) || dni.isEmpty()){
            throw  new IllegalArgumentException("Invalid user");
        }

        User user = this.userRepository.findByDni(dni).orElseThrow(

                //()->new IllegalArgumentException("User not found")
                ()->new UserNotFoundExceptionByDni(dni)
        );

        return user;
    }

    @Override
    public List<User> findUserMenorEdad(Integer edad) {

        List<User> user = this.userRepository.findByMenorEdad(edad);
        if(user.isEmpty()){
            throw new UserNotFoundExceptionMenorEdad(edad);
        }

        return user;
    }

    private void validateUserInput(User newUser) {


        if(!newUser.hasValidName()){
            throw new InvalidUserDataException("Invalid Name");
        }

        if(!newUser.hasValidEmail()){
            throw new IllegalArgumentException("Invalid Email");
        }

    }
}
