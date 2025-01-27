package Board.Post;


import Board.Exception.BaseException;
import Board.Exception.ErrorCode;
import Board.User.SiteUser;
import Board.User.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@Transactional
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {

    private final PostService postService;
    private final UserService userService;

    //작성

    @PostMapping("/create")
    public ResponseEntity<PostResponse> createPost(@RequestBody Post post, Principal principal) throws BaseException {
        SiteUser user=userService.getUser(principal.getName());
        Post createPost=postService.create(post,user);
        PostResponse response=new PostResponse(
                createPost.getId(),
                createPost.getTitle(),
                createPost.getContent(),
                createPost.getAuthor().getUsername(),
                createPost.getCreateTime().toString(),
                null
        );

        return ResponseEntity.ok(response);
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
    @PutMapping("/{id}")
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
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePost(@PathVariable("id") Integer id) throws BaseException {
        Optional<Post> post=postService.findById(id);
        if(post.isPresent()){
            postService.delete(post.get());
            return ResponseEntity.ok("게시물이 삭제되었습니다.");
        }else{
            throw new BaseException(ErrorCode.POST_NOT_FOUND);
        }
    }

    // 제목 기반 검색(포함하면 반환)
    @GetMapping("/search/title")
    public ResponseEntity<?> searchTitle(@RequestParam String title) throws BaseException {
        List<Post> postList=postService.findByTitle(title);
        if(postList.isEmpty()){
            throw new BaseException(ErrorCode.POST_NOT_FOUND);
        }
        return ResponseEntity.ok(postList);
    }

    // 내용 기반 검색(포함하면 반환)
    @GetMapping("/search/content")
    public ResponseEntity<?> searchContent(@RequestParam String content) throws BaseException {
        List<Post> postList=postService.findByContent(content);
        if(postList.isEmpty()){
            throw new BaseException(ErrorCode.POST_NOT_FOUND);
        }
        return ResponseEntity.ok(postList);
    }

    //제목 + 내용 같이 검색
    @GetMapping("/search/all")
    public ResponseEntity<?> searchAll(@RequestParam String word) throws BaseException {
        List<Post> postList=postService.findByTitleOrContent(word);
        if(postList.isEmpty()){
            throw new BaseException(ErrorCode.POST_NOT_FOUND);
        }else{
            return ResponseEntity.ok(postList);
        }
    }

    // 작성자 기반 검색


    // 페이징 처리
}
