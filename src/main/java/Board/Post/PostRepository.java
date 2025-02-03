package Board.Post;

import Board.User.SiteUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post,Integer> {

    List<Post> findByTitleContaining(String title);
    Page<Post> findByTitleContaining(String title, Pageable pageable);

    List<Post> findByContentContaining(String content);

    List<Post> findByAuthor(SiteUser author);
}
