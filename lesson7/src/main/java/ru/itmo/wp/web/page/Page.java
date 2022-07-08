package ru.itmo.wp.web.page;

import com.google.common.base.Strings;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.service.ArticleService;
import ru.itmo.wp.model.service.EventService;
import ru.itmo.wp.model.service.TalkService;
import ru.itmo.wp.model.service.UserService;
import ru.itmo.wp.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@SuppressWarnings({"unused", "RedundantSuppression"})
public abstract class Page {

    public static final boolean DEBUG = true;
    public static final int ALEX_SIN_ID = 3;

    protected final UserService userService = new UserService();
    protected final EventService eventService = new EventService();
    protected final TalkService talkService = new TalkService();
    protected final ArticleService articleService = new ArticleService();

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

        if (DEBUG) {
            request.getSession().setAttribute("user", userService.findById(ALEX_SIN_ID));
        }
    }

    protected void action(HttpServletRequest request, Map<String, Object> view) {
        // No operations.
    }

    protected void after(HttpServletRequest request, Map<String, Object> view) {}


    protected void setMessage(HttpServletRequest request, String message) {
        request.getSession().setAttribute("message", message);
    }

    protected void checkUserAuthenticated(HttpServletRequest request) {
        if (request.getSession().getAttribute("user") == null) {
            setMessage(request, "You must logged in to process the query");

            throw new RedirectException("/index");
        }
    }

    protected void setUser(HttpServletRequest request, User user) {
        request.getSession().setAttribute("user", user);
    }

    protected User getUser(HttpServletRequest request) {
        return (User) request.getSession().getAttribute("user");
    }
}
