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

    @Column(length = 30, unique = true)
    private String username;

    @Column(length = 30,nullable = false)
    private String email;

    @Column(name = "first_name", length = 10)
    private String firstName;

    @Column(name = "last_name", length = 10)
    private String lastName;

    @Column(nullable = false, length = 64)
    private String password;

    @Column(length = 64)
    private String photos;

    private boolean enabled;

    @Column(name = "verification_code",length = 64,updatable = false)
    private String verificationCode;


    public User() {

    }

    public User(String username, String email, String firstName, String lastName, String password) {
        this.username = username;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
    }

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles", joinColumns = @JoinColumn(name = "users_id", referencedColumnName = "id")
            , inverseJoinColumns = @JoinColumn(name = "roles_id", referencedColumnName = "id")
    )
    private Set<Role> roles = new HashSet<>();


    @ManyToOne(cascade = CascadeType.MERGE)
    private Team team;


    public User(Integer id) {
        this.id = id;
    }


    public void addRole(Role role) {
        this.roles.add(role);
    }


    @Override
    public String toString() {

        return this.getFullName();
    }

    public String getFullName() {
        return this.firstName + this.lastName;
    }


    @Transient
    public String getPhotosImagePath() {
        if (id == null || photos == null) return "/admin/img/avatar-female-3.jpg";
        return "/user-photos/" + this.id + "/" + this.photos;
    }

}
