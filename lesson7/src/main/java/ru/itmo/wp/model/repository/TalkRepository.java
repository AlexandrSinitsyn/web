package ru.itmo.wp.model.repository;

import ru.itmo.wp.model.domain.Talk;

import java.util.List;

public interface TalkRepository {
    void save(Talk talk, long sourceUserId, long targetUserId, String text);
    List<Talk> findAll();
    Talk find(long id);
}
