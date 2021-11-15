package com.tuananhdo.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;

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
