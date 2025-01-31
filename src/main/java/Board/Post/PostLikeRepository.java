package Board.Post;

import Board.Post.Entity.Post;
import Board.Post.Entity.PostLikes;
import Board.User.SiteUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostLikeRepository extends JpaRepository<PostLikes,Integer> {

    Optional<PostLikes> findByPostAndUser(Post post, SiteUser user);
}
