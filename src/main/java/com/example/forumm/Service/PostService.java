package com.example.forumm.Service;

import com.example.forumm.Entity.Post;
import com.example.forumm.Entity.Topic;
import com.example.forumm.Entity.User;
import com.example.forumm.Repository.PostRepository;
import com.example.forumm.Repository.TopicRepository;
import com.example.forumm.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class PostService implements IPostService{

    @Autowired
    PostRepository postRepo;
    @Autowired
    UserRepository userRepo;
    @Autowired
    TopicRepository topicRepo;


    @Override
    public List<Post> getAllPosts() {
        return (List<Post>) postRepo.findAll();
    }

    @Override
    public Post getPost(Long postId) {
        return postRepo.findById(postId).orElse(null);
    }

    @Override
    public Post addPost(Post post, Long idUser, Long idTopic) {
        User user = userRepo.findById(idUser).orElse(null);
        Topic topic = topicRepo.findById(idTopic).orElse(null);
        post.setUser(user);
        post.setTopic(topic);

        post.setCreationDate(new Date());
        post.setLikes(0);
        post.setModified(false);


        return postRepo.save(post);
    }

    @Override
    public Post updatePost(Post post) {
        if (postRepo.existsById(post.getIdPost())) {
            Post postExist = postRepo.findById(post.getIdPost()).orElse(null);
            if (postExist != null) {
                postExist.setContent(post.getContent());
                postExist.setCreationDate(new Date());
                postExist.setModified(true);

                return postRepo.save(postExist);
            }
        }
        return null;
    }

    @Override
    public void deletePost(Long postId) {

        postRepo.deleteById(postId);
    }

    @Override
    public Post likePost(Long postId) {
        Post post = postRepo.findById(postId).orElse(null);

        if (post != null) {

            post.setLikes(post.getLikes() + 1);
            return postRepo.save(post);
        }

        return null;
    }

    @Override
    public Post dislikePost(Long postId) {
        Post post = postRepo.findById(postId).orElse(null);

        if (post != null) {

            post.setLikes(post.getLikes() - 1);
            return postRepo.save(post);
        }

        return null;
    }

    @Override
    public List<Post> getPostsByTopicId(Long idTopic) {

        return (List<Post>) postRepo.findByTopicId(idTopic);
    }

    @Override
    public Long countCommentsByIdPost(Long idPost) {
        return postRepo.countCommentsByIdPost(idPost);
    }

}
