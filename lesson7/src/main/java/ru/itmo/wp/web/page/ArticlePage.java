package ru.itmo.wp.web.page;

import ru.itmo.wp.model.domain.ModelObject;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.Comparator;
import java.util.Map;

@SuppressWarnings({"unused", "RedundantSuppression"})
public class ArticlePage extends Page {

    @Override
    protected void action(HttpServletRequest request, Map<String, Object> view) {
        if (request.getSession().getAttribute("user") == null) {
            setMessage(request, "You must log in before creating any articles");

            throw new RedirectException("/index");
        }

        view.put("users", userService.findAll());
        view.put("articles", articleService.findAll().stream().sorted(Comparator.comparing(ModelObject::getCreationTime)));
    }

    private void createArticle(HttpServletRequest request, Map<String, Object> view) {
        String title = request.getParameter("title");
        String text = request.getParameter("text");

        articleService.save((User) request.getSession().getAttribute("user"), title, text, true);

        setMessage(request, "Your article was created");

        throw new RedirectException("/article");
    }
}
