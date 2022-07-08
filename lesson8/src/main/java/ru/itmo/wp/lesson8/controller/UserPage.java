package ru.itmo.wp.lesson8.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.itmo.wp.lesson8.service.UserService;

import javax.servlet.http.HttpSession;

@Controller
public class UserPage extends Page {
    private final UserService userService;

    public UserPage(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user/{id}")
    public String user(@PathVariable String id, Model model, HttpSession session) {
        try {
            final long userId = Long.parseLong(id);

            model.addAttribute("currentUser", userService.findById(userId));
            return "UserPage";
        } catch (NumberFormatException ignored) {
            setMessage(session, "Invalid user id");
            return "IndexPage";
        }
    }
}
