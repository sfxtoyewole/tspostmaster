package com.ts.postmaster.dto;

import com.ts.postmaster.dao.model.PostComment;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author toyewole
 */
@Data
public class CommentDto {

    private Long postId;
    private Long commentId;

    @NotBlank(message = "Comment cannot be blank")
    private String content;


    public PostComment transformToCommentEntity(){
        var comment = new PostComment();
        comment.setText(content);

        return comment;
    }
}
