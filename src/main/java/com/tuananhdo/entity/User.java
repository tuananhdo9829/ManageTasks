package com.tuananhdo.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@Entity
@Table(name = "users")
public class User {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "username",nullable = false,length = 30)
    private String username;

    @Column(name = "email",nullable = false,length = 30)
    private String email;

    @Column(name = "first_name",nullable = false,length = 10)
    private String firstName;

    @Column(name = "last_name",nullable = false,length = 10)
    private String lastName;

    @Column(name = "password",nullable = false,length = 64)
    private String password;
    private boolean enabled;

    @ManyToMany
    @JoinTable(
            name = "users_roles",joinColumns = @JoinColumn(name = "users_id")
            ,inverseJoinColumns = @JoinColumn(name = "roles_id")
    )
    private Set<Role> roles = new HashSet<>();
}
