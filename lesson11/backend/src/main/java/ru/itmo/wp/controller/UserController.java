package ru.itmo.wp.controller;

import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import ru.itmo.wp.domain.Comment;
import ru.itmo.wp.domain.Post;
import ru.itmo.wp.domain.User;
import ru.itmo.wp.exception.ValidationException;
import ru.itmo.wp.form.CommentForm;
import ru.itmo.wp.form.PostForm;
import ru.itmo.wp.form.RegisterForm;
import ru.itmo.wp.service.CommentService;
import ru.itmo.wp.service.JwtService;
import ru.itmo.wp.service.PostService;
import ru.itmo.wp.service.UserService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/1")
public class UserController {
    private final UserService userService;
    private final PostService postService;
    private final CommentService commentService;
    private final JwtService jwtService;

    public UserController(UserService userService, PostService postService, CommentService commentService, JwtService jwtService) {
        this.userService = userService;
        this.postService = postService;
        this.commentService = commentService;
        this.jwtService = jwtService;
    }

    @GetMapping("users/auth")
    public User findUserByJwt(@RequestParam String jwt) {
        return jwtService.find(jwt);
    }

    @GetMapping("users")
    public List<User> findUsers() {
        return userService.findAll();
    }

    @PostMapping("register")
    public String register(@RequestBody @Valid RegisterForm registerForm, BindingResult bindingResult) {
        if (userService.findByLogin(registerForm.getLogin()) != null) {
            bindingResult.addError(new ObjectError("invalid-login", "login is already occupied"));
        }

        if (bindingResult.hasErrors()) {
            throw new ValidationException(bindingResult);
        }
        User user = userService.register(registerForm);
        return jwtService.create(user);
    }

    @PostMapping("writePost")
    public String writePost(@RequestBody @Valid PostForm postForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationException(bindingResult);
        }

        Post post = new Post();
        post.setText(postForm.getText());
        post.setTitle(postForm.getTitle());

        final User user = jwtService.find(postForm.getUserJwt());

        post.setUser(user);
        user.addPost(post);
        postService.save(post);
//        userService.writePost(user, post);
        return "success";
    }

    @PostMapping("commentPost")
    public String commentPost(@RequestBody @Valid CommentForm commentForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationException(bindingResult);
        }

        Comment comment = new Comment();
        comment.setText(commentForm.getText());

        final User user = jwtService.find(commentForm.getUserJwt());

        final Post post = postService.findById(commentForm.getPostId());

        comment.setUser(user);
        comment.setPost(post);
        user.addComment(comment);
        post.addComment(comment);
        commentService.save(comment);
//        userService.writeComment(user, post, comment);
        return "success";
    }
}
