package Board.Post;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post,Integer> {

    List<Post> findByTitleContaining(String title);

    List<Post> findByContentContaining(String content);

}
