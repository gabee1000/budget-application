package com.example.gabor.mybudget.Model.Entities;

/**
 * Created by Gabor on 2017. 05. 20..
 */

public class User {
    private long id;
    private final String name;
    private final String password;

    public User(long id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }
}
