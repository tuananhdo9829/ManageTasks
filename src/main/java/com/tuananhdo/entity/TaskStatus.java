package com.tuananhdo.entity;

public enum TaskStatus {

    TODO("To do"), IN_PROGRESS("In Progress"), DONE("Done");

    private final String name;

    TaskStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
