package com.tuananhdo.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
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

    @Column(nullable = false, length = 25, name = "name")
    private String name;

    @Column(length = 150)
    private String slogan;

    @OneToMany(fetch =FetchType.EAGER,cascade =CascadeType.MERGE)
    @JoinColumn(name = "team_id")
    public Set<User> users = new HashSet<>();

}
