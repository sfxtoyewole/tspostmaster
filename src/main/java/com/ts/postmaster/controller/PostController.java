package com.ts.postmaster.controller;

import com.ts.postmaster.dao.model.BlogPost;
import com.ts.postmaster.dao.view.IPostView;
import com.ts.postmaster.dto.ApiResp;
import com.ts.postmaster.dto.DataTableResp;
import com.ts.postmaster.dto.PostDto;
import com.ts.postmaster.service.PostService;
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

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

/**
 * @author toyewole
 */

@RequiredArgsConstructor
@RestController
@RequestMapping("/post")
public class PostController {

    private final PostService postService;
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ApiResp<Long> createPost(@RequestBody @Valid PostDto postDto) {
        return postService.createPost(postDto);
    }

    @GetMapping()
    public ApiResp<DataTableResp<IPostView>> getPosts(@RequestParam Optional<String> title, @RequestParam int size, @RequestParam int index) {
        return postService.getPosts(title, index, size);
    }
    @GetMapping("/{id}")
    public ApiResp<PostDto> getSinglePost(@PathVariable @RequestBody Long id) {
        return postService.getPostById(id);
    }


}
