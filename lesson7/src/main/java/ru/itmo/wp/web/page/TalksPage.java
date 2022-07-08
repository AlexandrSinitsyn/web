package ru.itmo.wp.web.page;

import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@SuppressWarnings({"unused", "RedundantSuppression"})
public class TalksPage extends Page {

    @Override
    protected void action(HttpServletRequest request, Map<String, Object> view) {
        checkUserAuthenticated(request);

        view.put("users", userService.findAll());
        view.put("talks", talkService.findAll());
    }

    private void sendMessage(HttpServletRequest request, Map<String, Object> view) throws ValidationException {
        String selectedUser = request.getParameter("selectedUser");

        final String prefix = "user_id_";
        if (!selectedUser.startsWith(prefix)) {
            throw new ValidationException("Invalid user id");
        }

        selectedUser = selectedUser.split(prefix)[1];
        long targetUserId = Long.parseLong(selectedUser);
        String text = request.getParameter("text");

        talkService.save((User) request.getSession().getAttribute("user"), targetUserId, text);

        setMessage(request, "Your message was sent");

        throw new RedirectException("/talks");
    }
}
