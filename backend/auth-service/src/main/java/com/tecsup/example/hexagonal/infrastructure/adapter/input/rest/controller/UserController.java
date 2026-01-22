package com.tecsup.example.hexagonal.infrastructure.adapter.input.rest.controller;

import com.tecsup.example.hexagonal.application.port.input.UserService;
import com.tecsup.example.hexagonal.domain.exception.InvalidUserDataException;
import com.tecsup.example.hexagonal.domain.exception.UserNotFoundException;
import com.tecsup.example.hexagonal.domain.exception.UserNotFoundExceptionByDni;
import com.tecsup.example.hexagonal.domain.exception.UserNotFoundExceptionMenorEdad;
import com.tecsup.example.hexagonal.domain.model.User;
import com.tecsup.example.hexagonal.infrastructure.adapter.input.rest.dto.UserRequest;
import com.tecsup.example.hexagonal.infrastructure.adapter.input.rest.dto.UserResponse;
import com.tecsup.example.hexagonal.infrastructure.adapter.output.persistence.mapper.UserMapper;
import com.tecsup.example.hexagonal.infrastructure.adapter.output.persistence.repository.UserRepositoryAdapter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
@Slf4j
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;
    private static final Logger log = LoggerFactory.getLogger(UserController.class);


    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    //public ResponseEntity createUser(@RequestBody UserRequest request){
     public ResponseEntity<UserResponse> createUser(@RequestBody UserRequest request){

        try{
            if(request ==null){
                log.warn("Received null UserRequest");
                return ResponseEntity.badRequest().build();
            }

            log.info("User Request: {} ",request);
            log.info("Creating user with name: {} and email :{}",request.getName(),request.getEmail());
            User newUser = this.userMapper.toDomain(request);
            log.info("Mapped User entity {}",newUser);
            User createUser=this.userService.createUser(newUser);
            UserResponse response = this.userMapper.toResponse(createUser);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
            //return response;
        }catch (InvalidUserDataException e){
            log.warn("Invalid user data {}",e.getMessage());
            return ResponseEntity.badRequest().build();
        }catch (Exception e){
            log.error("Error creating user:{}",e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping("/{id}")
    //public UserResponse getUser(@PathVariable Long id){
    public ResponseEntity<UserResponse> getUser(@PathVariable Long id){
        try{
            User  user = this.userService.findUser(id);
            UserResponse response=this.userMapper.toResponse(user);
            return ResponseEntity.ok(response);
            //return  response;
        }catch (UserNotFoundException e){
            log.error("Error fetching user with :{}",id,e);
            //throw e;
            return  ResponseEntity.notFound().build();
        }catch (Exception e){

            log.error("Error  fetching user with ID:{}",id,e);
            return ResponseEntity.badRequest().build();
        }

    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/lastname/{apellidoPaterno}")
    public ResponseEntity<List<UserResponse>> getUserApellidoPaterno(@PathVariable String apellidoPaterno){
        try{
            List<User> user = this.userService.findUserLastName(apellidoPaterno);

            List<UserResponse> response  = user
                    .stream()
                    .map(this.userMapper::toResponse)
                    .toList();

            return ResponseEntity.ok(response);

        }catch (UserNotFoundException e){
            log.error("Error fetching user with :{}",apellidoPaterno,e);
            //throw e;
            return  ResponseEntity.notFound().build();
        }catch (Exception e){

            log.error("Error  fetching user with ID:{}",apellidoPaterno,e);
            return ResponseEntity.badRequest().build();
        }

    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/dni/{dni}")
    //public UserResponse getUser(@PathVariable Long id){
    public ResponseEntity<UserResponse> getUserDni(@PathVariable String dni){
        try{
            User  user = this.userService.findUserDni(dni);
            UserResponse response=this.userMapper.toResponse(user);
            return ResponseEntity.ok(response);
            //return  response;
        }catch (UserNotFoundExceptionByDni e){
            log.error("Error fetching user with :{}",dni,e);
            //throw e;
            return  ResponseEntity.notFound().build();
        }catch (Exception e){

            log.error("Error  fetching user with ID:{}",dni,e);
            return ResponseEntity.badRequest().build();
        }

    }

    @PreAuthorize("hasRole('MONITOR')")
    @GetMapping("/")
    public ResponseEntity<List<UserResponse>> getUserMenorEdad(){
        try{
            List<User> user = this.userService.findUserMenorEdad(18);

            List<UserResponse> response  = user
                    .stream()
                    .map(this.userMapper::toResponse)
                    .toList();

            return ResponseEntity.ok(response);

        }catch (UserNotFoundExceptionMenorEdad e){
            log.error("Error No minor users found.");
            //throw e;
            return  ResponseEntity.notFound().build();
        }catch (Exception e){

            log.error("Error No minor users found.");
            return ResponseEntity.badRequest().build();
        }

    }



}
