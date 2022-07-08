package ru.itmo.wp.model.domain;

import ru.itmo.wp.model.service.UserService;

import java.io.Serializable;
import java.util.Date;

public class Talk extends ModelObject implements Serializable {

    private long sourceUserId;
    private long targetUserId;
    private String text;


    public long getSourceUserId() {
        return sourceUserId;
    }

    public String getSourceUserLogin() {
        return new UserService().findById(sourceUserId).getLogin();
    }

    public long getTargetUserId() {
        return targetUserId;
    }

    public String getTargetUserLogin() {
        return new UserService().findById(targetUserId).getLogin();
    }

    public String getText() {
        return text;
    }


    public void setSourceUserId(long sourceUserId) {
        this.sourceUserId = sourceUserId;
    }

    public void setTargetUserId(long targetUserId) {
        this.targetUserId = targetUserId;
    }

    public void setText(String text) {
        this.text = text;
    }
}
