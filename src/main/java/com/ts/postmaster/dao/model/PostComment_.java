package com.ts.postmaster.dao.model;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import java.time.LocalDateTime;

/**
 * @author toyewole
 */


@StaticMetamodel(PostComment.class)
public class PostComment_ extends BaseEntity_ {

    public static volatile SingularAttribute<PostComment, String> text;
    public static volatile SingularAttribute<PostComment, LocalDateTime> createdAt;
    public static volatile SingularAttribute<PostComment, LocalDateTime> updatedAt;
    public static volatile SingularAttribute<PostComment, String> commenter;
    public static volatile SingularAttribute<PostComment, BlogPost> blogPost;

    // You can add static string variables for the attribute names, if needed.
    public static final String TEXT = "text";
    public static final String CREATED_AT = "createdAt";
    public static final String UPDATED_AT = "updatedAt";
    public static final String COMMENTER = "commenter";
    public static final String BLOG_POST = "blogPost";
}
