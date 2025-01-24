package Board.Post;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@Controller
@Transactional
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {

    private final PostService postService;

    //작성
    @PostMapping("/create")
    public ResponseEntity<Post> createPost(@RequestBody Post post){
        Post createPost=postService.create(post);
        return ResponseEntity.ok(createPost);
    }


}
