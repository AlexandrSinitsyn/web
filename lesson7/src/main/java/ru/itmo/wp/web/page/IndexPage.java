package ru.itmo.wp.web.page;

import ru.itmo.wp.web.annotation.Json;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@SuppressWarnings({"unused", "RedundantSuppression"})
public class IndexPage extends Page {

    @Override
    protected void action(HttpServletRequest request, Map<String, Object> view) {
        view.put("articles", articleService.findAll());
    }

    @Json
    private void findAll(HttpServletRequest request, Map<String, Object> view) {
        view.put("articles", articleService.findAll());
    }
}
