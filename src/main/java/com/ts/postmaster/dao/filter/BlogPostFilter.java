package com.ts.postmaster.dao.filter;

import com.ts.postmaster.dao.model.BlogPost;
import com.ts.postmaster.dao.model.BlogPost_;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

/**
 * @author toyewole
 */
@UtilityClass
public class BlogPostFilter {


    public static Specification<BlogPost> likeTitle(String title) {
        if (StringUtils.isBlank(title)) {
            return null;
        }
        return (root, query, cb) -> cb.like(root.get(BlogPost_.title), "%" + title + "%");
    }


}
