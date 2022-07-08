package ru.itmo.wp.model.domain;

import java.io.Serializable;
import java.util.Date;

public abstract class ModelObject implements Serializable {

    protected long id;
    protected Date creationTime;


    public long getId() {
        return id;
    }

    public Date getCreationTime() {
        return creationTime;
    }


    public void setId(long id) {
        this.id = id;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }
}
