package ru.itmo.wp.web.page;

import ru.itmo.wp.model.domain.Article;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.web.annotation.Json;
import ru.itmo.wp.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@SuppressWarnings({"unused", "RedundantSuppression"})
public class MyArticlesPage extends Page {

    @Override
    protected void action(HttpServletRequest request, Map<String, Object> view) {
        checkUserAuthenticated(request);

        final long userId = getUser(request).getId();
        view.put("articles", articleService.findByUserId(userId));
    }

    @Json
    private void findAll(HttpServletRequest request, Map<String, Object> view) {
        view.put("articles", articleService.findAll());
    }

    @Json
    private void setHidden(HttpServletRequest request, Map<String, Object> view) throws ValidationException {
        try {
            final long articleId = Long.parseLong(request.getParameter("articleId"));
            final boolean hidden = Boolean.parseBoolean(request.getParameter("hidden"));

            final Article article = articleService.find(articleId);

            articleService.validateUpdate(article, getUser(request));
            articleService.updateHidden(article, hidden);

//            setMessage(request, "Hidden was changed");
//            throw new RedirectException("/myArticles");
        } catch (NumberFormatException ignored) {
            setMessage(request, "Invalid article id");
            throw new RedirectException("/myArticles");
        }
    }
}
