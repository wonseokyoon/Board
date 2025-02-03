package Board.Comment;

import Board.Exception.BaseException;
import Board.Exception.ErrorCode;
import Board.Post.Post;
import Board.Post.PostRepository;
import Board.Post.PostService;
import Board.User.SiteUser;
import com.zaxxer.hikari.metrics.PoolStats;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private PostService postService;


    public Comment create(Comment comment, SiteUser user,Post post) {
        comment.setCreateTime(LocalDateTime.now());
        comment.setAuthor(user);
        comment.setPost(post);
        return commentRepository.save(comment);
    }
}
