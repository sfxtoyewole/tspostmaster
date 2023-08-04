package com.ts.postmaster.dao.view;

/**
 * @author toyewole
 */
 public interface IPostView {
     Long getId();
     String getContent();

     String getTitle() ;

     String getAuthor();

     Integer getLikes();

}
