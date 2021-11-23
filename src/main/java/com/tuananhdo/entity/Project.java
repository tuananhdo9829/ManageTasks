package com.tuananhdo.entity;


import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

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
            name = "participants_project", joinColumns = @JoinColumn(name = "users_id", referencedColumnName = "id")
            , inverseJoinColumns = @JoinColumn(name = "projects_id", referencedColumnName = "id")
    )
    private Set<User> users = new HashSet<>();


    private void getUserByProject(User user) {
         user.getUsername();
    }


    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                ", createdTime=" + createdTime +
                ", updatedTime=" + updatedTime +
                ", createdBy='" + createdBy + '\'' +
                ", updatedBy='" + updatedBy + '\'' +
                ", users=" + users +
                '}';
    }
}
