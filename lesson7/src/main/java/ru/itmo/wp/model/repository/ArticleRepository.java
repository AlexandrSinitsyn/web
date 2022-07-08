package ru.itmo.wp.model.repository;

import ru.itmo.wp.model.domain.Article;
import ru.itmo.wp.model.domain.Event;

import java.util.List;

public interface ArticleRepository {
    void save(Article event, long userId, String title, String text, boolean hidden);
    List<Article> findAll();
    List<Article> findByUserId(long id);
    Article find(long id);

    void updateHidden(long id, boolean hidden);
}
