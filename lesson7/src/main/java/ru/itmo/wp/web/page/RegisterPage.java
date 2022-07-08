package ru.itmo.wp.web.page;

import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.model.service.UserService;
import ru.itmo.wp.web.annotation.Json;
import ru.itmo.wp.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/** @noinspection unused*/
public class RegisterPage extends Page {

    @Json
    private void register(HttpServletRequest request, Map<String, Object> view) throws ValidationException {
        User user = new User();
        user.setLogin(request.getParameter("login"));
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String passwordConfirmation = request.getParameter("password-confirmation");

        userService.validateRegistration(user, email, password, passwordConfirmation);
        userService.register(user, email, password);

        setMessage(request, "You are successfully registered!");
        throw new RedirectException("/index");
    }
}
