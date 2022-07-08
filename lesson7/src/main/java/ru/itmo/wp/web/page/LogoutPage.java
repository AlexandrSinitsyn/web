package ru.itmo.wp.web.page;

import ru.itmo.wp.model.domain.Event;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@SuppressWarnings({"unused", "RedundantSuppression"})
public class LogoutPage extends Page {

    @Override
    protected void action(HttpServletRequest request, Map<String, Object> view) {
        eventService.save((User) request.getSession().getAttribute("user"), Event.EventType.LOGOUT);

        request.getSession().removeAttribute("user");

        setMessage(request, "Good bye. Hope to see you soon!");
        throw new RedirectException("/index");
    }
}
