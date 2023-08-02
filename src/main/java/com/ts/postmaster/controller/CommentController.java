package com.ts.postmaster.controller;

import com.ts.postmaster.dao.model.PostComment;
import com.ts.postmaster.dto.ApiResp;
import com.ts.postmaster.dto.CommentDto;
import com.ts.postmaster.dto.DataTableResp;
import com.ts.postmaster.dto.PostDto;
import com.ts.postmaster.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author toyewole
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("{postId}/")
public class CommentController {

    private final CommentService commentService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ApiResp<Long> postComment(@PathVariable Long postId, @RequestBody CommentDto commentDto) {
        return commentService.createComment(commentDto, postId);
    }

    @GetMapping()
    public ApiResp<DataTableResp<PostComment>> getPosts(@PathVariable Long postId, @RequestParam int size, @RequestParam int index) {
        return commentService.getComments(postId ,index, size);
    }

    @GetMapping("/{id}")
    public ApiResp<PostComment> getPostById(@PathVariable @RequestBody Long id) {
        return commentService.getCommentById(id);
    }

}
