package com.example.forumm.Service;

import com.example.forumm.Entity.Comment;

import java.util.List;

public interface ICommentService {

    Comment addComment (Comment comment, Long idUser, Long idPost);
    Comment updateComment (Comment comment);
    void deleteComment(Long commentId);
    List<Comment> getAllComments();
    Comment getComment(Long commentId);
    Comment likeComment(Long commentId);
    Comment dislikeComment(Long commentId);
    List<Comment> getCommentsByPostId(Long postId);

}
