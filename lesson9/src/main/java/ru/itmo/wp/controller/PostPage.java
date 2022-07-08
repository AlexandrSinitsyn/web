package ru.itmo.wp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import ru.itmo.wp.domain.Comment;
import ru.itmo.wp.domain.Post;
import ru.itmo.wp.domain.Role;
import ru.itmo.wp.form.validator.CommentValidator;
import ru.itmo.wp.security.AnyRole;
import ru.itmo.wp.security.Guest;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class PostPage extends Page {

    private CommentValidator commentValidator;

    public PostPage(CommentValidator commentValidator) {
        this.commentValidator = commentValidator;
    }

    @InitBinder("comment")
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(commentValidator);
    }

    @Guest
    @GetMapping("/post/{id}")
    public String postGet(@PathVariable String id, Model model, HttpSession session) {
        model.addAttribute("comment", new Comment());

        try {
            final long postId = Long.parseLong(id);

            model.addAttribute("currentPost", postService.findById(postId));

            return "PostPage";
        } catch (NumberFormatException ignored) {
            putMessage(session, "Invalid post id");
            return "redirect:/";
        }
    }

    @PostMapping("/post/{id}")
    public String postPost(@PathVariable String id,
                           Model model,
                           @Valid @ModelAttribute("comment") Comment comment,
                           BindingResult bindingResult,
                           HttpSession session) {
        try {
            final long postId = Long.parseLong(id);

            final Post post = postService.findById(postId);

            model.addAttribute("currentPost", post);

            if (bindingResult.hasErrors()) {
                return "PostPage";
            }

            postService.writeComment(getUser(session), post, comment);

            putMessage(session, "You have commented this post");

            return "redirect:/post/" + id;
        } catch (NumberFormatException ignored) {
            putMessage(session, "Invalid post id");
            return "redirect:/";
        }
    }
}
