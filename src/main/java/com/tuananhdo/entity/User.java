package com.tuananhdo.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Setter
@Getter
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 30, unique = true)
    private String username;

    @Column(length = 30,nullable = false)
    @NotBlank(message = "")
    @Email(message = "Enter a valid email address")
    private String email;

    @Column(name = "first_name", length = 10)
    @NotBlank(message = "")
    @Size(min = 2 , message = "The first name should have at least 2 characters")
    private String firstName;

    @Column(name = "last_name", length = 10)
    @NotBlank(message = "")
    @Size(min = 2 , message = "The last name should have at least 2 characters")
    private String lastName;

    @Column(nullable = false, length = 64)
    @NotBlank(message = "")
    @Length(min = 8,message = "Password must be at least 8 characters")
    private String password;

    @Column(length = 64)
    private String photos;

    private boolean enabled;

    @Column(name = "verification_code",length = 64,updatable = false)
    private String verificationCode;

    @Enumerated(EnumType.STRING)
    @Column(name = "authentication_type",length = 10 )
    private AuthenticationType authenticationType;

    public User() {

    }

    public User(String username, String email, String firstName, String lastName, String password,boolean enabled) {
        this.username = username;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.enabled=enabled;
    }


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles", joinColumns = @JoinColumn(name = "user_id")
            , inverseJoinColumns = @JoinColumn(name = "role_id")
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id.equals(user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
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
