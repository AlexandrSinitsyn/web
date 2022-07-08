package ru.itmo.wp.lesson8.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.itmo.wp.lesson8.form.UserForm;
import ru.itmo.wp.lesson8.service.UserService;
import ru.itmo.wp.lesson8.domain.User;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class UsersPage extends Page {

    private final UserService userService;

    public UsersPage(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users/all")
    public String usersGet(Model model) {
        model.addAttribute("users", userService.findAll());
        return "UsersPage";
    }

    @PostMapping("/users/all")
    public String usersPost(@Valid UserForm userForm, HttpSession httpSession) {//, HttpServletRequest request) {
        final String login = userForm.getLogin();//request.getParameter("login");
        final boolean disabled = userForm.getDisabled();//Boolean.parseBoolean(request.getParameter("disabled"));

        final User clickedUser = userService.findByLogin(login);

        if (clickedUser == null) {
            setMessage(httpSession, "User is unknown");
            return "UsersPage";
        }

        userService.updateDisabled(clickedUser, disabled);

        setMessage(httpSession, "User's status was changed");

        return "redirect:/users/all";
    }
}
