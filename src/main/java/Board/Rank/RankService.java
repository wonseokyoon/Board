package Board.Rank;

import Board.Comment.CommentRepository;
import Board.Post.Post;
import Board.Post.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RankService {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private CommentRepository commentRepository;

    public List<Post> byLike(List<Post> postList) {
        postList.sort((post1,post2) ->
                Integer.compare(post2.getLikes().size(), post1.getLikes().size()));
        return postList;
    }
}
