package com.tuananhdo.entity;

public enum ProjectStatus {

    NEW("New"), REVIEW("Review"), COMPLETED("Completed");

    private final String name;

    ProjectStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

}
