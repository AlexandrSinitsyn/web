package ru.itmo.wp.model.domain;

import java.io.Serializable;
import java.util.Date;

public class Event extends ModelObject implements Serializable {

    private long userId;
    private EventType type;


    public long getUserId() {
        return userId;
    }

    public EventType getType() {
        return type;
    }


    public void setUserId(long userId) {
        this.userId = userId;
    }

    public void setType(EventType type) {
        this.type = type;
    }


    public enum EventType {
        ENTER, LOGOUT
    }
}
