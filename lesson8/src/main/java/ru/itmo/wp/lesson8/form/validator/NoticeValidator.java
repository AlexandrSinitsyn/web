package ru.itmo.wp.lesson8.form.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.itmo.wp.lesson8.domain.Notice;
import ru.itmo.wp.lesson8.form.UserCredentials;
import ru.itmo.wp.lesson8.service.NoticeService;
import ru.itmo.wp.lesson8.service.UserService;

@Component
public class NoticeValidator implements Validator {
    
    private final NoticeService noticeService;

    public NoticeValidator(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    public boolean supports(Class<?> clazz) {
        return Notice.class.equals(clazz);
    }

    public void validate(Object target, Errors errors) {
        if (!errors.hasErrors()) {
//            Notice notice = (Notice) target;
//            if (noticeService.findByContent(notice.getContent()) == null) {
//                errors.rejectValue("content", "content.invalid-content", "invalid content. It can not be null, empty or blank");
//            }
            // do nothing
        }
    }
}
