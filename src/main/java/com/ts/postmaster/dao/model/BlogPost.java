package com.ts.postmaster.dao.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author toyewole
 */

@Getter
@Setter
@Table(name = "BLOG_POST")
@Entity
public class BlogPost extends BaseEntity{

    @Lob
    @Column(nullable = false)
    private String content;

    private String title;

    private String author;


}
