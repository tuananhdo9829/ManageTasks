package com.tuananhdo.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Setter
@Getter
@Entity(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 35, unique = true)
    private String name;

    @Column(nullable = false, length = 200)
    private String description;

    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    @Column(name = "created_time")
    private Date createdTime;

    @Column(name = "updated_time")
    private Date updatedTime;

    @Column(name = "created_by", length = 35)
    private String createdBy;

    @Column(name = "updated_by", length = 35)
    private String updateBy;

    @Column(name = "assign_to", length = 35)
    private String assignTo;

    @ManyToOne
    @JoinColumn(name = "projects_id")
    private Project project;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "users_id")
    private User user;

    public Task() {

    }

    public Task(Integer id, String name, String description, TaskStatus status, Project project, User user) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status;
        this.project = project;
        this.user = user;
    }

    public Task(Integer id, String name, String description, TaskStatus status, Date createdTime, Project project, User user) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status;
        this.createdTime = createdTime;
        this.project = project;
        this.user = user;
    }

    public Task(Integer id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public Task(Integer id,String description) {
        this.id = id;
        this.description = description;
    }




    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", createdTime=" + createdTime +
                ", updatedTime=" + updatedTime +
                ", createdBy='" + createdBy + '\'' +
                ", updateBy='" + updateBy + '\'' +
                ", assignTo='" + assignTo + '\'' +
                ", project=" + project.getName() +
                ", user=" + user.getUsername() +
                '}';
    }
}
