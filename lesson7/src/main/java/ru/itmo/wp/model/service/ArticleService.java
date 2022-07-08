package ru.itmo.wp.model.service;

import ru.itmo.wp.model.domain.Article;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.model.repository.ArticleRepository;
import ru.itmo.wp.model.repository.impl.ArticleRepositoryImpl;

import javax.annotation.Nullable;
import java.util.List;

public class ArticleService {

    private final ArticleRepository articleRepository = new ArticleRepositoryImpl();

    public void save(User user, String title, String text, boolean hidden) {
        final Article article = new Article();
        articleRepository.save(article, user.getId(), title, text, hidden);
    }

    public List<Article> findAll() {
        return articleRepository.findAll();
    }

    public Article find(long id) {
        return articleRepository.find(id);
    }

    public List<Article> findByUserId(long userId) {
        return articleRepository.findByUserId(userId);
    }

    public void updateHidden(Article article, boolean hidden) {
        articleRepository.updateHidden(article.getId(), hidden);
    }

    public void validateUpdate(@Nullable Article article, @Nullable User user) throws ValidationException {
        if (article == null) {
            throw new ValidationException("No such article");
        }

        if (user == null) {
            throw new ValidationException("No authenticated user");
        }

        if (article.getUserId() != user.getId()) {
            throw new ValidationException("Only author can change an article");
        }

        if (article.getText() == null || article.getText().isBlank()) {
            throw new ValidationException("Text can not be null or blank");
        }
    }
}
