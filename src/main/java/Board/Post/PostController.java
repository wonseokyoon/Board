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

    // ID 기반 검색
    @GetMapping("/{id}")
    public ResponseEntity<?> findPost(@PathVariable("id") Integer id) throws BaseException {
        Optional<Post> post=postService.findById(id);
        if(post.isPresent()){
            return ResponseEntity.ok(post.get());
        }else{
            throw new BaseException(ErrorCode.POST_NOT_FOUND);
        }
    }

    // 수정
    @PostMapping("/update/{id}")
    public ResponseEntity<?> modifyPost(@PathVariable("id") Integer id
    ,@RequestBody UpdateRequest updateRequest) throws BaseException {
        Optional<Post> post=postService.findById(id);
        if(post.isPresent()){
            Post modified=postService.modify(post.get(),updateRequest);
            return  ResponseEntity.ok(modified);
        }else{
            throw new BaseException(ErrorCode.POST_NOT_FOUND);
        }
    }

    // 삭제
    @PostMapping("/delete/{id}")
    public ResponseEntity<?> deletePost(@PathVariable("id") Integer id) throws BaseException {
        Optional<Post> post=postService.findById(id);
        if(post.isPresent()){
            postService.delete(post.get());
            return ResponseEntity.ok("게시물이 삭제되었습니다.");
        }else{
            throw new BaseException(ErrorCode.POST_NOT_FOUND);
        }
    }

}
