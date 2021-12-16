package com.tuananhdo.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
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
    @NotBlank(message = "Enter your username")
    @Size(min = 6 , message = "The username should have at least 6 characters")
    private String username;

    @Column(length = 30,nullable = false)
    @NotBlank(message = "Enter your email")
    @Email(message = "Enter a valid email address")
    private String email;

    @Column(name = "first_name", length = 10)
    @NotBlank(message = "Enter your first name")
    @Size(min = 2 , message = "The first name should have at least 2 characters")
    private String firstName;

    @Column(name = "last_name", length = 10)
    @NotBlank(message = "Enter your last name")
    @Size(min = 2 , message = "The last name should have at least 2 characters")
    private String lastName;

    @Column(nullable = false, length = 64)
    @NotBlank(message = "Enter your password")
    @Length(min = 8,message = "Password must be at least 8 characters")
    private String password;

    @Column(length = 64)
    @NotBlank
    private String photos;

    @AssertTrue
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

    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinTable(
            name = "users_roles", joinColumns = @JoinColumn(name = "users_id", referencedColumnName = "id")
            , inverseJoinColumns = @JoinColumn(name = "roles_id", referencedColumnName = "id")
    )
    private Set<Role> roles = new HashSet<>();


    @ManyToOne(fetch = FetchType.EAGER)
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
