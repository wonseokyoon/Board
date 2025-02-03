package Board.Like;

import Board.Comment.Comment;
import Board.User.SiteUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentLikeRepository extends JpaRepository<CommentLikes,Integer> {
    Optional<CommentLikes> findByCommentAndAuthor(Comment comment, SiteUser user);
}
