package ru.itmo.wp.form;

import org.springframework.lang.Nullable;
import ru.itmo.wp.domain.Post;
import ru.itmo.wp.domain.Tag;
import ru.itmo.wp.service.TagService;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

/** @noinspection unused*/
public class PostForm {

    private TagService tagService;

    @NotNull
    @NotBlank
    @Size(min = 1, max = 60)
    private String title;

    @NotNull
    @NotBlank
    @Size(min = 1, max = 65000)
    @Lob
    private String text;

    @Pattern(regexp = "[a-z ]+")
    private String tags;

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

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }


    public void setTagService(TagService tagService) {
        this.tagService = tagService;
    }

    public Post parseToPost() {
        final Post res = new Post();

        res.setTitle(this.title);
        res.setText(this.text);
        if (tags == null || tags.isBlank()) {
            res.setTags(Set.of());
        } else {
            res.setTags(Arrays.stream(this.tags.split(" ")).map(name -> {
                Tag tag = tagService.findByName(name);

                if (tag == null) {
                    if (!name.isBlank()) {
                        tag = new Tag(name);

                        tagService.save(tag);
                    }
                }

                return tag;
            }).collect(Collectors.toSet()));
        }

        return res;
    }
}
