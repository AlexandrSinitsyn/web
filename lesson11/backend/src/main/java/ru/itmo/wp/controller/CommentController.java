package ru.itmo.wp.controller;

import org.springframework.web.bind.annotation.*;
import ru.itmo.wp.domain.Comment;
import ru.itmo.wp.service.JwtService;
import ru.itmo.wp.service.CommentService;

import java.util.List;

@RestController
@RequestMapping("/api/1")
public class CommentController {
    private final CommentService commentService;
    private final JwtService jwtService;

    public CommentController(CommentService commentService, JwtService jwtService) {
        this.commentService = commentService;
        this.jwtService = jwtService;
    }

    @GetMapping("comments")
    public List<Comment> findComments() {
        return commentService.findAll();
    }
}
