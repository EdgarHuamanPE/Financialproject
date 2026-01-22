package com.tecsup.example.hexagonal.application.service;

import com.tecsup.example.hexagonal.application.port.output.UserRepository;
import com.tecsup.example.hexagonal.domain.exception.UserNotFoundException;
import com.tecsup.example.hexagonal.domain.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImpTest {

    @Mock
    private UserRepository userRepository;


    private UserServiceImp userService;

    @BeforeEach
    void setup() {
        userService = new UserServiceImp(userRepository);
    }

    @Test
    void createUser() {
        Long ID = 50L;
        String NAME = "Juana";
        String EMAIL = "juana@demo.com";

        // Initial Condition
        User newUser = User.builder()
                .name(NAME)
                .email(EMAIL)
                .build();  // new User(NAME, EMAIL);

        User savedUser = User.builder()
                .id(ID)
                .name(NAME)
                .email(EMAIL)
                .build(); // new User(ID, NAME, EMAIL);

        // Mocking the repository behavior
        when(userRepository.save(newUser)).thenReturn(savedUser);

        // Execute the service method
        User realUser = userService.createUser(newUser);

        // Validate the results
        assertNotNull(realUser);
        assertEquals(ID, realUser.getId());
        assertEquals(NAME, realUser.getName());
        assertEquals(EMAIL, realUser.getEmail());
    }

    @Test
    void findUser() {

        Long ID = 100L;
        String NAME = "Jaime";
        String EMAIL = "jaime@demo.com";

        // Inicial condicional
        User existingUser =  User.builder()
                .id(ID)
                .name(NAME)
                .email(EMAIL)
                .build();

        //Action
         when (userRepository.findById(100L)).thenReturn(Optional.of(existingUser));

        //Execute the servicio method
        User realUser = userService.findUser(100L);

        //Validate the result
        //hope value - real valor
        assertNotNull(realUser);
        assertEquals(ID, realUser.getId());
        assertEquals(NAME, realUser.getName());
        assertEquals(EMAIL, realUser.getEmail());




    }

    @Test
    public void findUser_NotFound(){
        Long ID_UNKNOW = 999L;

        // Mocking the repository behavior to return empty
        when(userRepository.findById(ID_UNKNOW)).thenReturn(Optional.empty());

        // Execute the service method and expect an exception
        assertThrows(UserNotFoundException.class,
                () -> userService.findUser(ID_UNKNOW));

    }

    @Test
    void findUserLastName() {
        Long ID = 100L;
        String NAME = "Jaime";
        String EMAIL = "jaime@demo.com";
        String APELLIDOPATERNO="Huaman";
        String APELLIDOMATERNO="pumachapi";
        Integer EDAD=29;
        String DNI="67543267";
        String TELEFONO="999999999";

        // Inicial condicional
        User existingUserA =  User.builder()
                .id(ID)
                .name(NAME)
                .email(EMAIL)
                .apellidoPaterno(APELLIDOPATERNO)
                .apellidoMaterno(APELLIDOMATERNO)
                .edad(EDAD)
                .dni(DNI)
                .build();

        User existingUserB =  User.builder()
                .id(ID)
                .name(NAME)
                .email(EMAIL)
                .apellidoPaterno(APELLIDOPATERNO)
                .apellidoMaterno(APELLIDOMATERNO)
                .edad(EDAD)
                .dni(DNI)
                .build();

        //Action
        when (userRepository.findByApellidoPaterno("Huaman")).thenReturn(List.of(existingUserA,existingUserB));

        //Execute the servicio method
        List<User> realUser = userService.findUserLastName("Huaman");

        //Validate the result
        //hope value - real valor
        assertNotNull(realUser);
        assertEquals(ID, realUser.get(0).getId());
        assertEquals(NAME, realUser.get(0).getName());
        assertEquals(EMAIL, realUser.get(0).getEmail());
        assertEquals(APELLIDOPATERNO, realUser.get(0).getApellidoPaterno());
        assertEquals(APELLIDOMATERNO, realUser.get(0).getApellidoMaterno());
        assertEquals(EDAD, realUser.get(0).getEdad());
        assertEquals(DNI, realUser.get(0).getDni());

    }

    @Test
    void findUserDni() {
        Long ID = 100L;
        String NAME = "Jaime";
        String EMAIL = "jaime@demo.com";
        String APELLIDOPATERNO="Huaman";
        String APELLIDOMATERNO="pumachapi";
        Integer EDAD=29;
        String DNI="67543267";
        String TELEFONO="999999999";

        // Inicial condicional
        User existingUserA =  User.builder()
                .id(ID)
                .name(NAME)
                .email(EMAIL)
                .apellidoPaterno(APELLIDOPATERNO)
                .apellidoMaterno(APELLIDOMATERNO)
                .edad(EDAD)
                .dni(DNI)
                .build();

        //Action
        when (userRepository.findByDni ("67543267")).thenReturn(Optional.of(existingUserA));

        //Execute the servicio method
        User realUser = userService.findUserDni ("67543267");

        //Validate the result
        //hope value - real valor
        assertNotNull(realUser);
        assertEquals(ID, realUser.getId());
        assertEquals(NAME, realUser.getName());
        assertEquals(EMAIL, realUser.getEmail());
        assertEquals(APELLIDOPATERNO, realUser.getApellidoPaterno());
        assertEquals(APELLIDOMATERNO, realUser.getApellidoMaterno());
        assertEquals(EDAD, realUser.getEdad());
        assertEquals(DNI, realUser.getDni());

    }@Test
    void findUserMayorEdad() {
        Long ID = 100L;
        String NAME = "Jaime";
        String EMAIL = "jaime@demo.com";
        String APELLIDOPATERNO="Huaman";
        String APELLIDOMATERNO="pumachapi";
        Integer EDAD=29;
        String DNI="67543267";
        String TELEFONO="999999999";

        // Inicial condicional
        User existingUserA =  User.builder()
                .id(ID)
                .name(NAME)
                .email(EMAIL)
                .apellidoPaterno(APELLIDOPATERNO)
                .apellidoMaterno(APELLIDOMATERNO)
                .edad(EDAD)
                .dni(DNI)
                .build();

        User existingUserB =  User.builder()
                .id(ID)
                .name(NAME)
                .email(EMAIL)
                .apellidoPaterno(APELLIDOPATERNO)
                .apellidoMaterno(APELLIDOMATERNO)
                .edad(EDAD)
                .dni(DNI)
                .build();


        //Action
        when (userRepository.findByMenorEdad (18)).thenReturn(List.of(existingUserA,existingUserB));

        //Execute the servicio method
        List<User> realUser = userService.findUserMenorEdad(18);

        //Validate the result
        //hope value - real valor
        assertNotNull(realUser);
        assertEquals(ID, realUser.get(0).getId());
        assertEquals(NAME, realUser.get(0).getName());
        assertEquals(EMAIL, realUser.get(0).getEmail());
        assertEquals(APELLIDOPATERNO, realUser.get(0).getApellidoPaterno());
        assertEquals(APELLIDOMATERNO, realUser.get(0).getApellidoMaterno());
        assertEquals(EDAD, realUser.get(0).getEdad());
        assertEquals(DNI, realUser.get(0).getDni());

    }
}