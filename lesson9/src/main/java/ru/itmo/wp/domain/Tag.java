package ru.itmo.wp.domain;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(
        uniqueConstraints = @UniqueConstraint(columnNames = "name")
)
public class Tag {
    @Id
    @GeneratedValue
    private long id;

    @NotNull
    @NotBlank
    @Size(min = 1, max = 255)
    @Pattern(regexp = "[a-z]+")
    private String name;

    /** @noinspection unused*/
    public Tag() {
    }

    @CreationTimestamp
    public Date creationTime;

    public Tag(@NotNull String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }
}
