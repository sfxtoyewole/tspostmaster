package com.ts.postmaster.dto;

import com.ts.postmaster.dao.model.BlogPost;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author toyewole
 */
@Data
public class PostDto {
    private Long id;

    @NotBlank(message = "Content cannot be blank")
    private String content;

    @NotBlank(message = "Title cannot be blank")
    private String title;


    public BlogPost transformToBlogPost() {
        var blogpost = new BlogPost();
        blogpost.setContent(content);
        blogpost.setTitle(title);
        blogpost.setId(id);
        return blogpost;
    }


}
