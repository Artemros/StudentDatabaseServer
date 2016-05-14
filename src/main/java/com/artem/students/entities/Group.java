package com.artem.students.entities;

/**
 * Created by denis on 13.03.2016.
 */
public class Group {
    public Long id;

    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
