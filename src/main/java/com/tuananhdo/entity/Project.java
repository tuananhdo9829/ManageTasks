package com.tuananhdo.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
    @NotBlank(message = "")
    @Size(min = 2,message = "Should have at least 2 characters")
    private String name;

    @Column(nullable = false, length = 200)
    @NotBlank(message = "")
    @Size(min = 2,message = "Should have at least 2 characters")
    private String description;

    @Column(name = "created_time")
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private Date createdTime;

    @Column(name = "updated_time")
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private Date updatedTime;

    @Enumerated(EnumType.STRING)
    private ProjectStatus projectStatus;

    @Column(name = "time_start")
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private Date timeStart;

    @Column(name = "time_end")
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private Date timeEnd;

    @Column(name = "created_by", length = 35)
    private String createdBy;

    @Column(name = "updated_by", length = 35)
    private String updatedBy;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "participants_project", joinColumns = @JoinColumn(name = "project_id", referencedColumnName = "id")
            , inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id")
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


    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", createdTime=" + createdTime +
                ", updatedTime=" + updatedTime +
                ", projectStatus=" + projectStatus +
                ", timeStart=" + timeStart +
                ", timeEnd=" + timeEnd +
                ", createdBy='" + createdBy + '\'' +
                ", updatedBy='" + updatedBy + '\'' +
                ", users=" + users +
                '}';
    }

    public Project(Integer id) {
        this.id = id;
    }

}
