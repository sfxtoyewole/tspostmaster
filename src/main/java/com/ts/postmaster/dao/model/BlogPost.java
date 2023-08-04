package com.ts.postmaster.dao.model;

import com.ts.postmaster.dao.view.IPostView;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

/**
 * @author toyewole
 */

@Getter
@Setter
@Table(name = "BLOG_POST")
@Entity
public class BlogPost extends BaseEntity {

    @Column(columnDefinition = "TEXT",name = "CONTENT", nullable = false)
    private String content;

    @Column(name = "TITLE", nullable = false)
    private String title;

    @Column(name = "AUTHOR", nullable = false)
    private String author;

    @Column(name = "likes")
    private Integer likes= 0;

    @Lob
    @Column(name= "img")
    private byte[] img;
}
