package Board.Post;

import Board.Post.Entity.Post;
import Board.Post.Entity.PostDisLikes;
import Board.User.SiteUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostDislikeRepository extends JpaRepository<PostDisLikes,Integer> {
    Optional<PostDisLikes> findByPostAndUser(Post post, SiteUser user);
}
