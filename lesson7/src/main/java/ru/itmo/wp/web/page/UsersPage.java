package ru.itmo.wp.web.page;

import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.exception.RepositoryException;
import ru.itmo.wp.web.annotation.Json;
import ru.itmo.wp.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/** @noinspection unused*/
public class UsersPage extends Page {

    @Json
    private void findAll(HttpServletRequest request, Map<String, Object> view) {
        view.put("users", userService.findAll());
    }

    @Json
    private void findUser(HttpServletRequest request, Map<String, Object> view) {
        view.put("user",
                userService.findById(Long.parseLong(request.getParameter("userId"))));
    }

    @Json
    private void setAdmin(HttpServletRequest request, Map<String, Object> view) {
        try {
            final long onClickUserId = Long.parseLong(request.getParameter("onClickUserId"));

            final User onClicked = userService.findById(onClickUserId);

            if (!getUser(request).getAdmin()) {
                setMessage(request, "Current user is not an admin");
                throw new RedirectException("/users");
            }

            userService.updateUserAdminRoot(onClicked, !onClicked.getAdmin());

            setMessage(request, "Admin root was changed");
            throw new RedirectException("/users");
        } catch (NumberFormatException | RepositoryException e) {
            setMessage(request, "Invalid user id");
            throw new RedirectException("/users");
        }
    }
}
