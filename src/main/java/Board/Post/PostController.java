package Board.Post;


import Board.Exception.BaseException;
import Board.Exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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

    //전체 조회
    @GetMapping
    public ResponseEntity<List<Post>> listPost(){
        List<Post> postList=postService.list();
        return ResponseEntity.ok(postList);
    }


}
