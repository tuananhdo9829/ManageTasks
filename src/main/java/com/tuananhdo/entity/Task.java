package com.tuananhdo.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
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
    private String name;

    @Column(nullable = false, length = 200)
    private String description;

    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    @Column(name = "created_time")
    private Date createdTime;

    @Column(name = "time_start")
    private Date timeStart;

    @Column(name = "time_end")
    private Date timeEnd;

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

    @Transient
    public boolean isTodo() {
        return status.equals(TaskStatus.TODO);
    }

    @Transient
    public boolean isInProgress() {
        return hasStatus(TaskStatus.IN_PROGRESS);
    }

    @Transient
    public boolean isDone() {
        return hasStatus(TaskStatus.DONE);
    }


    public boolean hasStatus(TaskStatus status) {
        return getStatus().equals(status);
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
