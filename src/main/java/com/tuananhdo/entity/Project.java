package com.tuananhdo.entity;


import lombok.*;

import javax.persistence.*;
import java.util.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(
        name = "projects"
)
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 35, unique = true)
    private String name;

    @Column(nullable = false, length = 200)
    private String description;

    @Column(length = 20)
    private String status;

    @Column(name = "created_time")
    private Date createdTime;

    @Column(name = "updated_time")
    private Date updatedTime;

    @Column(name = "created_by", length = 35)
    private String createdBy;

    @Column(name = "updated_by", length = 35)
    private String updatedBy;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "participants_project", joinColumns = @JoinColumn(name = "projects_id", referencedColumnName = "id")
            , inverseJoinColumns = @JoinColumn(name = "users_id", referencedColumnName = "id")
    )
    private Set<User> users = new HashSet<>();


}
