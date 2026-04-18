package com.laboratory.management.system.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "user")
public class User extends Auditable<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "full_name", nullable = false, length = 150)
    private String fullName;

    @Column(name = "email", nullable = false, length = 150, unique = true)
    private String email;

    @Column(name = "username", nullable = false, length = 80, unique = true)
    private String username;

    @Column(name = "password", nullable = false, length = 255)
    private String password;

    @Column(name = "role", nullable = false, length = 50)
    private String role;

    @Column(name = "department", length = 100)
    private String department;

    @Column(name = "status", nullable = false, length = 20)
    private String status;
}
