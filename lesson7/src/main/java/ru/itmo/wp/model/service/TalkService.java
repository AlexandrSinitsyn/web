package ru.itmo.wp.model.service;

import ru.itmo.wp.model.domain.Talk;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.repository.TalkRepository;
import ru.itmo.wp.model.repository.impl.TalkRepositoryImpl;

import java.util.List;
import java.util.Optional;

public class TalkService {

    private final TalkRepository talkRepository = new TalkRepositoryImpl();


    public void save(User sourceUser, long targetUserId, String text) {
        final Talk talk = new Talk();
        talkRepository.save(talk, sourceUser.getId(), targetUserId, text);
    }

    public List<Talk> findAll() {
        return talkRepository.findAll();
    }


    public Optional<Talk> findByUsersId(long sourceUserId, long targetUserId) {
        return findAll().stream().filter((e) -> e.getSourceUserId() == sourceUserId &&
                e.getTargetUserId() == targetUserId).findFirst();
    }
}
