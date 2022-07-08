package ru.itmo.wp.service;

import org.springframework.stereotype.Service;
import ru.itmo.wp.domain.Tag;
import ru.itmo.wp.domain.User;
import ru.itmo.wp.form.UserCredentials;
import ru.itmo.wp.repository.TagRepository;

import java.util.List;

@Service
public class TagService {

    private final TagRepository tagRepository;

    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public void save(Tag tag) {
        tagRepository.save(tag);
    }

    public List<Tag> findAll() {
        return tagRepository.findAllByOrderByCreationTimeDesc();
    }

    public Tag findById(Long id) {
        return id == null ? null : tagRepository.findById(id).orElse(null);
    }

    public Tag findByName(String name) {
        return name == null ? null : tagRepository.findAllByName(name).orElse(null);
    }
}
