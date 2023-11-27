package com.example.forumm.Service;

import com.example.forumm.Entity.Post;
import com.example.forumm.Entity.Topic;

import java.util.List;

public interface IPostService {
    Post addPost (Post post, Long idUser, Long idTopic);
    Post updatePost (Post post);
    void deletePost(Long postId);
    List<Post> getAllPosts();
    Post getPost(Long postId);
    Post likePost(Long postId);
    Post dislikePost(Long postId);
    List<Post> getPostsByTopicId(Long topicId);
    Long countCommentsByIdPost(Long idPost);
}
