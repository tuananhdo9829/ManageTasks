package com.tuananhdo.entity;


import lombok.*;

import javax.persistence.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

    @Column(name = "created_time")
    private Date createdTime;

    @Column(name = "updated_time")
    private Date updatedTime;

    @Enumerated(EnumType.STRING)
    private ProjectStatus projectStatus;

    @Column(name = "time_start")
    private Date timeStart;

    @Column(name = "time_end")
    private Date timeEnd;

    @Column(name = "created_by", length = 35)
    private String createdBy;

    @Column(name = "updated_by", length = 35)
    private String updatedBy;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "participants_project", joinColumns = @JoinColumn(name = "projects_id", referencedColumnName = "id")
            , inverseJoinColumns = @JoinColumn(name = "users_id", referencedColumnName = "id")
    )
    public Set<User> users = new HashSet<>();


    @Transient
    public String getTimeStartOfProject() {
        DateFormat dateFormat = getDateFormat();
        return dateFormat.format(this.timeStart);
    }

    public void setTimeStartOfProject(String dateString) throws ParseException {
        DateFormat dateFormat = getDateFormat();
        this.timeStart = dateFormat.parse(dateString);
    }


    @Transient
    public String getTimeEndOfProject() {
        DateFormat dateFormat = getDateFormat();
        return dateFormat.format(this.timeEnd);
    }

    public void setTimeEndOfProject(String dateString) throws ParseException {
        DateFormat dateFormat = getDateFormat();
        this.timeEnd = dateFormat.parse(dateString);
    }

    private DateFormat getDateFormat() {
        return new SimpleDateFormat("yyyy-MM-dd");
    }

//    public boolean isHasChildren() {
//        return this.hasChildren;
//    }
//
//    public void setHasChildren(boolean hasChildren) {
//        this.hasChildren = hasChildren;
//    }
//
//    @Transient
//    private boolean hasChildren;


    public Project(Integer id) {
        this.id = id;
    }

}
