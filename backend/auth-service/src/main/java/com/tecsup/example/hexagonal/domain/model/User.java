package com.tecsup.example.hexagonal.domain.model;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class User {

    private Long id;
    private String name;
    private String email;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private Integer edad;
    private String dni;
    private String telefono;
    private String password;
    private boolean enabled;
    private Role role;


    public boolean hasValidEmail(){
        return email != null &&
               email.contains("@") &&
               email.contains(".") &&
               email.length()> 5;

    }

    @Override
    public String toString() {
        return "User {" +
                "id=" + id +
                ", name='" + name + "'" +
                ", email='" + email + "'" +
                ", apellidoPaterno='" + apellidoPaterno + "'" +
                ", apellidoMaterno='" + apellidoMaterno + "'" +
                ", edad=" + edad +
                ", dni='" + dni + "'" +
                ", telefono='" + telefono + "'" +
                '}';
    }


    public boolean hasValidName() {
        return name!=null && !name.trim().isEmpty() && name.length()>=2;
    }
}
