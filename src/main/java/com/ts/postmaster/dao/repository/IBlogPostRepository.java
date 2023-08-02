package com.ts.postmaster.dao.repository;

import com.ts.postmaster.dao.model.BlogPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author toyewole
 */
@Repository
public interface IBlogPostRepository extends JpaRepository<BlogPost, Long> {


}
