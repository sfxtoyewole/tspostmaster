package com.ts.postmaster.dto;

import com.ts.postmaster.dao.model.PostComment;
import lombok.Data;

/**
 * @author toyewole
 */
@Data
public class CommentDto {

    private Long postId;
    private Long commentId;
    private String content;


    public PostComment transformToCommentEntity(){
        var comment = new PostComment();
        comment.setText(content);

        return comment;
    }
}
