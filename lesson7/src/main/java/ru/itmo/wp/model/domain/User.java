package ru.itmo.wp.model.domain;

import java.io.Serializable;
import java.util.Date;

public class User extends ModelObject implements Serializable {

    private String login;
    private String email;
    private boolean admin;


    public String getLogin() {
        return login;
    }

    public String getEmail() {
        return email;
    }

    public boolean getAdmin() {
        return admin;
    }


    public void setLogin(String login) {
        this.login = login;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }
}
