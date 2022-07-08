package ru.itmo.wp.model.domain;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable {
    private long id;
    private String login;
    private String email;
    private Date creationTime;


    public long getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getEmail() {
        return email;
    }

    public Date getCreationTime() {
        return creationTime;
    }


    public void setId(long id) {
        this.id = id;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }
}