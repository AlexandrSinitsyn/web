package ru.itmo.wp.form;

import ru.itmo.wp.domain.User;

import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class PostForm {

    @NotEmpty
    @Size(min = 1, max = 100)
    private String title;

    @NotEmpty
    @Size(min = 1, max = 10000)
    private String text;

    private String userJwt;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUserJwt() {
        return userJwt;
    }

    public void setUserJwt(String userJwt) {
        this.userJwt = userJwt;
    }
}
