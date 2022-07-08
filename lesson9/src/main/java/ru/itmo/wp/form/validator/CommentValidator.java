package ru.itmo.wp.form.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.itmo.wp.domain.Comment;
import ru.itmo.wp.form.UserCredentials;
import ru.itmo.wp.service.CommentService;
import ru.itmo.wp.service.UserService;

@Component
public class CommentValidator implements Validator {

    public boolean supports(Class<?> clazz) {
        return Comment.class.equals(clazz);
    }

    public void validate(Object target, Errors errors) {
        if (!errors.hasErrors()) {
            Comment comment = (Comment) target;
            if (comment.getText().isBlank()) {
                errors.rejectValue("text", "text.is-empty", "text can not be empty");
            }
        }
    }
}
