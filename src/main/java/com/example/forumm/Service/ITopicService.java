package com.example.forumm.Service;

import com.example.forumm.Entity.Topic;

import java.util.List;

public interface ITopicService {
    Topic addTopic (Topic topic, Long idUser);
    void deleteTopic(Long topicId);
    List<Topic> getAllTopics();
    Topic getTopic(Long topicId);
    Topic likeTopic(Long topicId);
    Topic dislikeTopic(Long topicId);
    Long countPostsByIdTopic(Long idTopic);
}
