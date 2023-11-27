package com.example.forumm.Repository;

import com.example.forumm.Entity.Comment;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends CrudRepository<Comment, Long> {

    @Query("SELECT c FROM Comment c WHERE c.post.idPost= :idPost")
    List<Comment> findByPostId(@Param("idPost") Long idPost);
}
