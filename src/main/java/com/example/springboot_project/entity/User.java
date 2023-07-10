package com.example.springboot_project.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.NaturalId;

import java.util.Collection;

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    @NaturalId(mutable = true)
    private String email;
    private String password;
    private boolean isEnabled = false;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id" ),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id" ))
    private Collection<Role> roles;
}
