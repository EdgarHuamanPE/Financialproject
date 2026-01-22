package com.tecsup.example.hexagonal.infrastructure.adapter.output.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 150)
    private String email;

    private String password;

    @Column(name = "enabled ", nullable = false)
    private boolean enabled;

    @Column(name = "apellido_Paterno",nullable = false, length = 100)
    private String apellidoPaterno;

    @Column(name = "apellido_materno",nullable = false, length = 100)
    private String apellidoMaterno;

    private Integer edad;

    @Column(nullable = false, length = 8)
    private String dni;

    @Column(nullable = false, length = 12)
    private String telefono;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", nullable = false)
    private RoleEntity role;

}
