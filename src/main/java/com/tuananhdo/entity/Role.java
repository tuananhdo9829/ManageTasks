package com.tuananhdo.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Setter
@Getter
@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @NotBlank(message = "Enter your role name")
    @Size(min = 3 , message = "The role should have at least 3 characters")
    private String name;

    @NotBlank(message = "Enter your role description")
    @Size(min = 6 , message = "The role should have at least 6 characters")
    private String description;


    public Role(){

    }

    public Role(Integer id){
        this.id=id;
    }

    public Role(String name){
        this.name=name;
    }

    public Role(Integer id , String name){
        this.id = id;
        this.name=name;
    }



    @Override
    public String toString() {
        return this.name;
    }
}
