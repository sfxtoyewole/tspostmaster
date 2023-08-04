package com.ts.postmaster.dao.repository;

import com.ts.postmaster.dao.model.BlogPost;
import com.ts.postmaster.dao.view.IPostView;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author toyewole
 */
@Repository
public interface IBlogPostRepository extends JpaRepository<BlogPost, Long> , JpaSpecificationExecutor<BlogPost> {

    @Transactional
    @Query("SELECT b " +
            "FROM BlogPost b " +
            "WHERE (:title is null OR b.title LIKE %:title%) " +
            "ORDER BY b.createdAt desc")
    List<IPostView> findAllBlogPost(String title, Pageable pageable);



}
