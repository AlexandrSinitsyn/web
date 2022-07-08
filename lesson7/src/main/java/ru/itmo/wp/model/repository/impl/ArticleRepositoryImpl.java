package ru.itmo.wp.model.repository.impl;

import ru.itmo.wp.model.domain.Article;
import ru.itmo.wp.model.domain.Event;
import ru.itmo.wp.model.exception.RepositoryException;
import ru.itmo.wp.model.repository.ArticleRepository;
import ru.itmo.wp.model.repository.EventRepository;

import java.sql.*;

public class ArticleRepositoryImpl extends BasicRepositoryImpl<Article> implements ArticleRepository {

    public ArticleRepositoryImpl() {
        super("Article");
    }


    @Override
    public void save(Article article, long userId, String title, String text, boolean hidden) {
        save(article, statement -> {
            statement.setLong(1, userId);
            statement.setString(2, title);
            statement.setString(3, text);
            statement.setBoolean(4, hidden);
        }, "userId", "title", "text", "hidden");
    }

    @Override
    public void updateHidden(long id, boolean hidden) {
        try (Connection connection = DATA_SOURCE.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("UPDATE `Article` SET hidden=? WHERE id=?")) {
                statement.setBoolean(1, hidden);
                statement.setLong(2, id);

                statement.executeQuery();
            }
        } catch (SQLException e) {
            throw new RepositoryException("Can't find " + tableName + ".", e);
        }
    }


    @Override
    protected Article parse(ResultSetMetaData metaData, ResultSet resultSet) throws SQLException {
        if (!resultSet.next()) {
            return null;
        }

        Article article = new Article();
        for (int i = 1; i <= metaData.getColumnCount(); i++) {
            switch (metaData.getColumnName(i)) {
                case "id":
                    article.setId(resultSet.getLong(i));
                    break;
                case "userId":
                    article.setUserId(resultSet.getLong(i));
                    break;
                case "title":
                    article.setTitle(resultSet.getString(i));
                    break;
                case "text":
                    article.setText(resultSet.getString(i));
                    break;
                case "hidden":
                    article.setHidden(resultSet.getBoolean(i));
                    break;
                case "creationTime":
                    article.setCreationTime(resultSet.getTimestamp(i));
                    break;
                default:
                    // No operations.
            }
        }

        return article;
    }
}
