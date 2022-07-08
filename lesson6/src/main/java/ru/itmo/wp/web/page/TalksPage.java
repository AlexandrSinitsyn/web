package ru.itmo.wp.web.page;

import ru.itmo.wp.model.domain.Talk;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.Comparator;
import java.util.Date;
import java.util.Map;
import java.util.stream.Collectors;

@SuppressWarnings({"unused", "RedundantSuppression"})
public class TalksPage extends Page {

    public static final boolean DEBUG = false;
    public static final int ALEX_SIN_ID = 3;

    @Override
    protected void action(HttpServletRequest request, Map<String, Object> view) {
        if (DEBUG) {
            request.getSession().setAttribute("user", userService.findById(ALEX_SIN_ID));
        }

        final User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            setMessage(request, "You must log in before sending any messages");

            throw new RedirectException("/index");
        }

        view.put("users", userService.findAll());
        view.put("talks", talkService.findAll().stream().
                filter(t -> t.getTargetUserId() == user.getId() || t.getSourceUserId() == user.getId()).
                sorted(Comparator.comparing(Talk::getCreationTime)).
                collect(Collectors.toList()));
    }

    private void sendMessage(HttpServletRequest request, Map<String, Object> view) throws ValidationException {
        String selectedUser = request.getParameter("selectedUser");

        final String prefix = "user_id_";
        if (!selectedUser.startsWith(prefix)) {
            throw new ValidationException("Invalid user id");
        }

        selectedUser = selectedUser.split(prefix)[1];
        try {

            long targetUserId = Long.parseLong(selectedUser);
            if (userService.findAll().stream().noneMatch(t -> t.getId() == targetUserId)) {
                setMessage(request, "Invalid target user id");
                throw new RedirectException("/talks");
            }

            String text = request.getParameter("text");

            if (text.isBlank()) {
                setMessage(request, "You can not send empty message");
                throw new RedirectException("/talks");
            }

            final User user = (User) request.getSession().getAttribute("user");
            System.out.println(">>> " + user);

            if (user == null) {
                setMessage(request, "You must be logged in");
                throw new RedirectException("/index");
            }

            talkService.save(user, targetUserId, text);

            setMessage(request, "Your message was sent");

            throw new RedirectException("/talks");
        } catch (NumberFormatException e) {
            setMessage(request, "Invalid target user id");
            throw new RedirectException("/talks");
        }
    }
}
