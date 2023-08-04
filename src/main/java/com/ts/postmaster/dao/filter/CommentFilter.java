package com.ts.postmaster.dao.filter;

import com.ts.postmaster.dao.model.PostComment;
import lombok.experimental.UtilityClass;
import org.springframework.data.jpa.domain.Specification;

/**
 * @author toyewole
 */
@UtilityClass
public class CommentFilter {
    public static Specification<PostComment> equalPostId(Long postId) {
        if (postId == null ) {
            return null;
        }
        return (root, query, cb) -> cb.equal(root.get("postId"), postId);
    }
}
