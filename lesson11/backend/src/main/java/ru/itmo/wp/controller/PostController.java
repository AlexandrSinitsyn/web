package ru.itmo.wp.controller;

import org.springframework.web.bind.annotation.*;
import ru.itmo.wp.domain.Post;
import ru.itmo.wp.service.JwtService;
import ru.itmo.wp.service.PostService;

import java.util.List;

@RestController
@RequestMapping("/api/1")
public class PostController {
    private final PostService postService;
    private final JwtService jwtService;

    public PostController(PostService postService, JwtService jwtService) {
        this.postService = postService;
        this.jwtService = jwtService;
    }

    @GetMapping("posts")
    public List<Post> findPosts() {
        return postService.findAll();
    }
}
