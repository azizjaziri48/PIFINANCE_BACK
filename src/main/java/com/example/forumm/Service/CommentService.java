package com.example.forumm.Service;

import com.example.forumm.Entity.Comment;
import com.example.forumm.Entity.Post;
import com.example.forumm.Entity.User;
import com.example.forumm.Repository.CommentRepository;
import com.example.forumm.Repository.PostRepository;
import com.example.forumm.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CommentService implements ICommentService{

    @Autowired
    CommentRepository commentRepo;
    @Autowired
    PostRepository postRepo;
    @Autowired
    UserRepository userRepo;


    @Override
    public Comment addComment(Comment comment, Long idUser, Long idPost) {
        User user = userRepo.findById(idUser).orElse(null);
        Post post = postRepo.findById(idPost).orElse(null);
        comment.setUser(user);
        comment.setPost(post);

        comment.setCreationDate(new Date());
        comment.setLikes(0);
        comment.setModified(false);
        return commentRepo.save(comment);
    }

    @Override
    public Comment updateComment(Comment comment) {
        if (commentRepo.existsById(comment.getIdComment())) {
            Comment commentExist = commentRepo.findById(comment.getIdComment()).orElse(null);
            if (commentExist != null) {
                commentExist.setContent(comment.getContent());
                commentExist.setCreationDate(new Date());
                commentExist.setModified(true);

                return commentRepo.save(commentExist);
            }
        }
        return null;
    }

    @Override
    public void deleteComment(Long commentId) {
        commentRepo.deleteById(commentId);

    }

    @Override
    public List<Comment> getAllComments() {
        return (List<Comment>) commentRepo.findAll();
    }

    @Override
    public Comment getComment(Long commentId) {
        return commentRepo.findById(commentId).orElse(null);
    }

    @Override
    public Comment likeComment(Long commentId) {
        Comment comment = commentRepo.findById(commentId).orElse(null);

        if (comment != null) {

            comment.setLikes(comment.getLikes() + 1);
            return commentRepo.save(comment);
        }

        return null;
    }

    @Override
    public Comment dislikeComment(Long commentId) {
        Comment comment = commentRepo.findById(commentId).orElse(null);

        if (comment != null) {

            comment.setLikes(comment.getLikes() - 1);
            return commentRepo.save(comment);
        }

        return null;
    }

    @Override
    public List<Comment> getCommentsByPostId(Long postId) {
        return (List<Comment>) commentRepo.findByPostId(postId);
    }
}
