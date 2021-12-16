package com.tuananhdo.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Setter
@Getter
@Entity(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 35, unique = true)
    @NotEmpty(message = "")
    @Size(min = 6,message = "Should have at least 6 characters")
    private String name;

    @Column(nullable = false, length = 150)
    @NotEmpty(message = "")
    @Size(min = 6,message = "Should have at least 6 characters")
    private String description;

    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    @Column(name = "created_time")
    @DateTimeFormat(pattern = "yyyy-dd-mm")
    private Date createdTime;

    @Column(name = "time_start")
    @DateTimeFormat(pattern = "yyyy-dd-mm")
    private Date timeStart;

    @Column(name = "time_end")
    @DateTimeFormat(pattern = "yyyy-dd-mm")
    private Date timeEnd;

    @Column(name = "updated_time")
    @DateTimeFormat(pattern = "yyyy-dd-mm")
    private Date updatedTime;

    @Column(name = "created_by", length = 35)
    private String createdBy;

    @Column(name = "updated_by", length = 35)
    private String updateBy;

    @Column(name = "assign_to", length = 35)
    private String assignTo;

    @NotNull(message = "")
    @Valid
    @ManyToOne
    @JoinColumn(name = "projects_id")
    private Project project;

    @NotNull(message = "")
    @ManyToOne
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

    public Task(Integer id, String description) {
        this.id = id;
        this.description = description;
    }


    @Transient
    public String getDateTimeStart() {
        DateFormat dateFormat = getDateFormat();
        return dateFormat.format(this.timeStart);
    }


    public void setDateTimeStart(String dateString) throws ParseException {
        DateFormat dateFormat = getDateFormat();

        this.timeStart = dateFormat.parse(dateString);
    }


    @Transient
    public String getDateTimeEnd() {
        DateFormat dateFormat = getDateFormat();
        return dateFormat.format(this.timeEnd);
    }

    public void setDateTimeEnd(String dateString) throws ParseException {
        DateFormat dateFormat = getDateFormat();
        this.timeEnd = dateFormat.parse(dateString);
    }


    private DateFormat getDateFormat() {
        return new SimpleDateFormat("yyyy-MM-dd");
    }

    public Date getTimeStart() {

        return timeStart;
    }

    public void setTimeStart(Date timeStart) {
        this.timeStart = timeStart;
    }

    @Override
    public String toString() {
        return this.user.getEmail();
    }

}
