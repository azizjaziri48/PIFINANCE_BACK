package com.example.forumm.Controller;

import com.example.forumm.Entity.Topic;
import com.example.forumm.Service.ITopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/Topic")
public class TopicController {

    @Autowired
    ITopicService topicService;

    @PostMapping("/addTopic/{idUser}")
    @ResponseBody
    public Topic addTopic(@RequestBody Topic topic,
                          @PathVariable("idUser") Long idUser){

        return topicService.addTopic(topic, idUser);
    }

    @DeleteMapping("/deleteTopic/{topicId}")
    @ResponseBody
    public void deleteTopic(@PathVariable("topicId") Long topicId) {

        topicService.deleteTopic(topicId);
    }

    @GetMapping("/getAllTopics")
    @ResponseBody
    public List<Topic> getAllTopics() {
        List<Topic> listTopics = topicService.getAllTopics();
        return listTopics;
    }

    @GetMapping("/getTopic/{topicId}")
    @ResponseBody
    public Topic getTopic(@PathVariable("topicId") Long topicId) {
        return topicService.getTopic(topicId);
    }

    @PutMapping("/likeTopic/{topicId}")
    @ResponseBody
    public Topic likeTopic(@PathVariable Long topicId) {
        return topicService.likeTopic(topicId);
    }

    @PutMapping("/dislikeTopic/{topicId}")
    @ResponseBody
    public Topic dislikeTopic(@PathVariable Long topicId) {
        return topicService.dislikeTopic(topicId);
    }

    @GetMapping("/postCount/{idTopic}")
    public Long countPostsByIdTopic(@PathVariable Long idTopic) {
        return topicService.countPostsByIdTopic(idTopic);
    }

}
