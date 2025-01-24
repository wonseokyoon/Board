package Board.Post;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;

    public Post create(Post post) {
        post.setCreateTime(LocalDateTime.now());
        return postRepository.save(post);
    }

    public List<Post> list() {
        List<Post> postList=postRepository.findAll();
        return postList;
    }

    public Optional<Post> findById(Integer id) {
        Optional<Post> post=postRepository.findById(id);
        return post;
    }

    public Post modify(Post post, UpdateRequest updateRequest) {
        post.setModifyTime(LocalDateTime.now());
        post.setTitle(updateRequest.getTitle());
        post.setContent(updateRequest.getContent());
        return postRepository.save(post);
    }

    public void delete(Post post) {
        Integer id=post.getId();
        postRepository.deleteById(id);
    }


}
