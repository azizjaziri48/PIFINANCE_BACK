package com.example.forumm.Controller;


import com.example.forumm.Entity.Post;
import com.example.forumm.Service.IPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/Post")
public class PostController {

    @Autowired
    IPostService postService;

    @PostMapping("/addPost/{idUser}/{idTopic}")
    @ResponseBody
    public Post addPost(@RequestBody Post post,
                        @PathVariable("idUser") Long idUser,
                        @PathVariable("idTopic") Long idTopic){

        return postService.addPost(post, idUser, idTopic);
    }

    @PutMapping("/updatePost/{postId}")
    @ResponseBody
    public Post updatePost(@PathVariable Long postId,
                               @RequestBody Post post) {
        post.setIdPost(postId);
        return postService.updatePost(post);
    }

    @DeleteMapping("/deletePost/{postId}")
    @ResponseBody
    public void deletePost(@PathVariable("postId") Long postId) {

        postService.deletePost(postId);
    }

    @GetMapping("/getAllPosts")
    @ResponseBody
    public List<Post> getAllPosts() {
        List<Post> listPosts = postService.getAllPosts();
        return listPosts;
    }

    @GetMapping("/getPost/{postId}")
    @ResponseBody
    public Post getPost(@PathVariable("postId") Long postId) {

        return postService.getPost(postId);
    }

    @PutMapping("/likePost/{postId}")
    @ResponseBody
    public Post likePost(@PathVariable Long postId) {

        return postService.likePost(postId);
    }

    @PutMapping("/dislikePost/{postId}")
    @ResponseBody
    public Post dislikePost(@PathVariable Long postId) {

        return postService.dislikePost(postId);
    }

    @GetMapping("/byTopic/{topicId}")
    @ResponseBody
    public List<Post> getPostsByTopicId(@PathVariable Long topicId) {
        List<Post> listPosts = postService.getPostsByTopicId(topicId);
        return listPosts;
    }

    @GetMapping("/commentCount/{idPost}")
    public Long countCommentsByIdPost(@PathVariable Long idPost) {
        return postService.countCommentsByIdPost(idPost);
    }


}
