package com.ts.postmaster.dao.model;

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
@Table(name = "POST_COMMENT")
@Entity
public class PostComment extends BaseEntity{

    @Lob
    @Column(nullable = false)
    private String text;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private Long postId;

}
