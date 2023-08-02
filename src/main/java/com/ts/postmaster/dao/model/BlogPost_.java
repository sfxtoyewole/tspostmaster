package com.ts.postmaster.dao.model;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * @author toyewole
 */
@StaticMetamodel(BlogPost.class)
public class BlogPost_ extends BaseEntity_ {

    public static volatile SingularAttribute<BlogPost, String> content;
    public static volatile SingularAttribute<BlogPost, String> title;
    public static volatile SingularAttribute<BlogPost, String> author;

    // You can add static string variables for the attribute names, if needed.
    public static final String CONTENT = "content";
    public static final String TITLE = "title";
    public static final String AUTHOR = "author";
}
