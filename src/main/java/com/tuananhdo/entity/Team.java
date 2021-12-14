package com.tuananhdo.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@Entity
@Table(name = "teams")
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Enter your team name")
    @Size(min = 3 , message = "The team name should have at least 3 characters")
    @Column(nullable = false, length = 25)
    private String name;

    @Column(length = 150)
    @NotBlank(message = "Enter your team slogan")
    @Size(min = 3 , message = "The team slogan should have at least 3 characters")
    private String slogan;

    @OneToMany(fetch =FetchType.EAGER,cascade =CascadeType.MERGE)
    @JoinColumn(name = "team_id")
    public Set<User> users = new HashSet<>();


}
