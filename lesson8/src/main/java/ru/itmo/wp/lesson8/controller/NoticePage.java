package ru.itmo.wp.lesson8.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.itmo.wp.lesson8.domain.Notice;
import ru.itmo.wp.lesson8.form.validator.NoticeValidator;
import ru.itmo.wp.lesson8.service.NoticeService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class NoticePage extends Page {

    private final NoticeService noticeService;

    public NoticePage(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @GetMapping("/notice")
    public String noticeGet(Model model) {
        model.addAttribute("newNotice", new Notice());
        return "NoticePage";
    }

    @PostMapping("/notice")
    public String noticePost(@Valid @ModelAttribute("newNotice") Notice notice,
                               BindingResult bindingResult,
                               HttpSession httpSession) {
        if (bindingResult.hasErrors()) {
            return "NoticePage";
        }

        noticeService.create(notice);
        setMessage(httpSession, "Your notice was saved!");

        //noinspection SpringMVCViewInspection
        return "redirect:/";
    }
}
