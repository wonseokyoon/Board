package Board.Comment;

import Board.Post.Post;
import Board.User.SiteUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment,Integer> {
    List<Comment> findByAuthor(SiteUser author);

    List<Comment> findByPost(Post post);
}
