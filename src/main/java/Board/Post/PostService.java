package Board.Post;

import Board.Comment.Comment;
import Board.Like.PostLikeRepository;
import Board.Post.Dto.UpdateRequest;
import Board.User.SiteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;
    private final PostLikeRepository postLikeRepository;

    public Post create(Post post, SiteUser user) {
        post.setCreateTime(LocalDateTime.now());
        post.setAuthor(user);
        return postRepository.save(post);
    }

//    public List<Post> list() {
//        List<Post> postList=postRepository.findAll();
//        return postList;
//    }

    public Page<Post> list(Pageable pageable){
        Page<Post> postList=postRepository.findAll(pageable);
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

    public List<Post> findByTitle(String title) {
        List<Post> postList=postRepository.findByTitleContaining(title);
        return postList;
    }

    public Page<Post> findByTitle(String title,Pageable pageable) {
        Page<Post> postList=postRepository.findByTitleContaining(title,pageable);
        return postList;
    }

    public List<Post> findByContent(String content) {
        List<Post> postList=postRepository.findByContentContaining(content);
        return postList;
    }

    public List<Post> findByTitleOrContent(String word) {
        List<Post> postListByTitle=postRepository.findByTitleContaining(word);
        List<Post> postListByContent=postRepository.findByContentContaining(word);

        Set<Post> postSet=new LinkedHashSet<>(postListByTitle);
        postSet.addAll(postListByContent);

        return postSet.stream()
                .sorted(Comparator.comparing(Post::getId))
                .collect(Collectors.toList());
    }

    public List<Post> findByUser(SiteUser user) {
        List<Post> postList=postRepository.findByAuthor(user);
        return postList;

    }

}
