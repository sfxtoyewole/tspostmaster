package com.ts.postmaster.dto;

import com.ts.postmaster.dao.model.BlogPost;
import com.ts.postmaster.utility.CommonLogic;
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

    private String imageBase64;


    public BlogPost transformToBlogPost() {
        var blogpost = new BlogPost();
        blogpost.setContent(content);
        blogpost.setTitle(title);
        blogpost.setId(id);
        blogpost.setImg(CommonLogic.getByteArray(imageBase64));
        return blogpost;
    }


}
