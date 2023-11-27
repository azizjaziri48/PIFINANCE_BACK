package com.example.forumm.Repository;

import com.example.forumm.Entity.Topic;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TopicRepository extends CrudRepository<Topic, Long>  {

    @Query("SELECT COUNT(p) FROM Post p WHERE p.topic.idTopic = :idTopic")
    Long countPostsByIdTopic(@Param("idTopic") Long idTopic);

}
