package com.ts.postmaster.dao.repository;

import com.ts.postmaster.dao.model.PostComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author toyewole
 */
@Repository
public interface ICommentRepository extends JpaRepository<PostComment, Long> {


}
