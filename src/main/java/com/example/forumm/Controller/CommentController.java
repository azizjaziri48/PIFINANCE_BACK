package com.example.forumm.Controller;


import com.example.forumm.Entity.Comment;
import com.example.forumm.Service.ICommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/Comment")
public class CommentController {

    @Autowired
    ICommentService commentService;

    @PostMapping("/addComment/{idUser}/{idPost}")
    @ResponseBody
    public Comment addComment(@RequestBody Comment comment,
                        @PathVariable("idUser") Long idUser,
                        @PathVariable("idPost") Long idPost){

        return commentService.addComment(comment, idUser, idPost);
    }

    @PutMapping("/updateComment/{commentId}")
    @ResponseBody
    public Comment updateComment(@PathVariable Long commentId,
                           @RequestBody Comment comment) {
        comment.setIdComment(commentId);
        return commentService.updateComment(comment);
    }

    @DeleteMapping("/deleteComment/{commentId}")
    @ResponseBody
    public void deleteComment(@PathVariable("commentId") Long commentId) {

        commentService.deleteComment(commentId);
    }

    @GetMapping("/getAllComments")
    @ResponseBody
    public List<Comment> getAllComments() {
        List<Comment> listComments = commentService.getAllComments();
        return listComments;
    }

    @GetMapping("/getComment/{commentId}")
    @ResponseBody
    public Comment getComment(@PathVariable("commentId") Long commentId) {
        return commentService.getComment(commentId);
    }

    @PutMapping("/likeComment/{commentId}")
    @ResponseBody
    public Comment likeComment(@PathVariable Long commentId) {

        return commentService.likeComment(commentId);
    }

    @PutMapping("/dislikeComment/{commentId}")
    @ResponseBody
    public Comment dislikeComment(@PathVariable Long commentId) {

        return commentService.dislikeComment(commentId);
    }

    @GetMapping("/byPost/{postId}")
    @ResponseBody
    public List<Comment> getCommentsByPostId(@PathVariable Long postId) {
        List<Comment> listComments = commentService.getCommentsByPostId(postId);
        return listComments;
    }

}
