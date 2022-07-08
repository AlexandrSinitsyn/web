package ru.itmo.wp.web.page;

import com.google.common.base.Strings;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.service.EventService;
import ru.itmo.wp.model.service.TalkService;
import ru.itmo.wp.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@SuppressWarnings({"unused", "RedundantSuppression"})
public abstract class Page {

    protected final UserService userService = new UserService();
    protected final EventService eventService = new EventService();
    protected final TalkService talkService = new TalkService();

    protected void before(HttpServletRequest request, Map<String, Object> view) {
        User user = getUser(request);
        if (user != null) {
            view.put("user", user);
        }

        view.put("userCount", new UserService().findCount());

        String message = (String) request.getSession().getAttribute("message");
        if (!Strings.isNullOrEmpty(message)) {
            view.put("message", message);
            request.getSession().removeAttribute("message");
        }
    }

    protected void action(HttpServletRequest request, Map<String, Object> view) {
        // No operations.
    }

    protected void after(HttpServletRequest request, Map<String, Object> view) {}


    protected void setMessage(HttpServletRequest request, String message) {
        request.getSession().setAttribute("message", message);
    }

    protected void setUser(HttpServletRequest request, User user) {
        request.getSession().setAttribute("user", user);
    }

    protected User getUser(HttpServletRequest request) {
        return (User) request.getSession().getAttribute("user");
    }
}
