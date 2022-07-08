package ru.itmo.wp.lesson8.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@SuppressWarnings("unused")
public class UserForm {
    @NotNull
    @NotEmpty
    @Size(min = 2, max = 16)
    @Pattern(regexp = "[a-z]+", message = "Only lowercase latin letters expected")
    private String login;

    private boolean disabled;


    public String getLogin() {
        return login;
    }

    public boolean getDisabled() {
        return disabled;
    }


    public void setLogin(String login) {
        this.login = login;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }
}
