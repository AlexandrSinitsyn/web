package ru.itmo.wp.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class CommentForm {

    @NotEmpty
    @Size(min = 1, max = 10000)
    private String text;

    private String userJwt;

    private long postId;

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

    public long getPostId() {
        return postId;
    }

    public void setPostId(long postId) {
        this.postId = postId;
    }
}
