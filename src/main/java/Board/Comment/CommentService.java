package Board.Comment;

import Board.Exception.BaseException;
import Board.Exception.ErrorCode;
import Board.Like.CommentDisLikeRepository;
import Board.Like.CommentDisLikes;
import Board.Like.CommentLikes;
import Board.Like.CommentLikeRepository;
import Board.Post.Post;
import Board.User.SiteUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private CommentLikeRepository commentLikeRepository;
    @Autowired
    private CommentDisLikeRepository commentDisLikeRepository;

    public Comment create(Comment comment, SiteUser user,Post post) {
        comment.setCreateTime(LocalDateTime.now());
        comment.setAuthor(user);
        comment.setPost(post);
        return commentRepository.save(comment);
    }

    public List<Comment> findByUser(SiteUser user) {
        List<Comment> commentList=commentRepository.findByAuthor(user);
        return commentList;
    }

    public List<Comment> findByPost(Post post) {
        List<Comment> commentList=commentRepository.findByPost(post);
        return commentList;
    }

    public Comment findById(Integer id) throws BaseException {
        Optional<Comment> comment=commentRepository.findById(id);
        if(comment.isEmpty()) throw new BaseException(ErrorCode.COMMENT_NOT_FOUND);
        return comment.get();
    }

    public Comment addLike(Comment comment, SiteUser user) {
        // 이미 따봉 누름
        if(commentLikeRepository.findByCommentAndAuthor(comment,user).isPresent()){
            return sublike(comment,user);
        }
        // 싫어요 취소
        if(commentDisLikeRepository.findByCommentAndAuthor(comment, user).isPresent()){
            subDisLike(comment,user);
        }
        CommentLikes likes=new CommentLikes(comment,user);
        commentLikeRepository.save(likes);
        return comment;
    }

    private Comment sublike(Comment comment, SiteUser user){
        Optional<CommentLikes> likes=commentLikeRepository.findByCommentAndAuthor(comment, user);
        if(likes.isPresent()){
            commentLikeRepository.delete(likes.get());
            commentLikeRepository.flush();
        }
        return comment;
    }

    public Comment addDisLike(Comment comment,SiteUser user) {
        //이미 싫어요
        if(commentDisLikeRepository.findByCommentAndAuthor(comment,user).isPresent()){
            return subDisLike(comment,user);
        }
        // 좋아요 취소
        if(commentLikeRepository.findByCommentAndAuthor(comment, user).isPresent()){
            sublike(comment,user);
        }
        CommentDisLikes disLikes=new CommentDisLikes(comment,user);
        commentDisLikeRepository.save(disLikes);
        return comment;
    }

    private Comment subDisLike(Comment comment, SiteUser user) {
        Optional<CommentDisLikes> disLikes=commentDisLikeRepository.findByCommentAndAuthor(comment, user);
        if(disLikes.isPresent()){
            commentDisLikeRepository.delete(disLikes.get());
            commentDisLikeRepository.flush();
        }
        return comment;
    }
}
