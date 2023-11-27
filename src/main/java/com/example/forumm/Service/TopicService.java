package com.example.forumm.Service;

import com.example.forumm.Entity.Topic;
import com.example.forumm.Entity.User;
import com.example.forumm.Repository.TopicRepository;
import com.example.forumm.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class TopicService implements ITopicService{

    @Autowired
    TopicRepository topicRepo;
    @Autowired
    UserRepository userRepo;

    @Override
    public List<Topic> getAllTopics() {
        return (List<Topic>) topicRepo.findAll();
    }

    @Override
    public Topic addTopic(Topic topic, Long idUser) {
        User user = userRepo.findById(idUser).orElse(null);
        topic.setUser(user);

        topic.setCreationDate(new Date());
        topic.setLikes(0);

        return topicRepo.save(topic);
    }

    @Override
    public void deleteTopic(Long topicId) {
        topicRepo.deleteById(topicId);
    }

    @Override
    public Topic getTopic(Long topicId) {
        return topicRepo.findById(topicId).orElse(null);
    }

    @Override
    public Topic likeTopic(Long topicId) {
        Topic topic = topicRepo.findById(topicId).orElse(null);

        if (topic != null) {

            topic.setLikes(topic.getLikes() + 1);
            return topicRepo.save(topic);
        }

        return null;
    }

    @Override
    public Topic dislikeTopic(Long topicId) {
        Topic topic = topicRepo.findById(topicId).orElse(null);

        if (topic != null) {

            topic.setLikes(topic.getLikes() - 1);
            return topicRepo.save(topic);
        }

        return null;
    }

    @Override
    public Long countPostsByIdTopic(Long idTopic) {
        return topicRepo.countPostsByIdTopic(idTopic);
    }



}
