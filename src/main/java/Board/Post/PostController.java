package Board.Post;


import Board.Exception.BaseException;
import Board.Exception.ErrorCode;
import Board.Like.PostLikeService;
import Board.Post.Dto.PageResponse;
import Board.Post.Dto.PostDto;
import Board.Post.Dto.UpdateRequest;
import Board.User.SiteUser;
import Board.User.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@Transactional
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {

    private final PostService postService;
    private final UserService userService;
    private final PostLikeService postLikeService;
    //작성

    @PostMapping("/create")
    public ResponseEntity<PostDto> createPost(@RequestBody Post post, Principal principal) throws BaseException {
        SiteUser user=userService.getUser(principal.getName());
        Post createPost=postService.create(post,user);
        PostDto postDto=new PostDto(createPost);
        return ResponseEntity.ok(postDto);
    }

//    전체 조회
    @GetMapping
    public ResponseEntity<PageResponse<PostDto>> listPost(@RequestParam(defaultValue = "0") int page,
                                                          @RequestParam(defaultValue = "10") int size){
        Pageable pageable=PageRequest.of(page,size);
        Page<Post> postPage=postService.list(pageable);
        List<PostDto> postDtos=postPage.stream()
                .map(PostDto::new)
                .collect(Collectors.toList());
        PageResponse<PostDto> response=new PageResponse(postDtos,postPage.getNumber(),postPage.getTotalPages());
        return ResponseEntity.ok(response);
    }

    // ID 기반 검색
    @GetMapping("/{id}")
    public ResponseEntity<?> findPost(@PathVariable("id") Integer id) throws BaseException {
        Optional<Post> post=postService.findById(id);
        if(post.isPresent()){
            PostDto postDto=new PostDto(post.get());
            return ResponseEntity.ok(postDto);
        }else{
            throw new BaseException(ErrorCode.POST_NOT_FOUND);
        }
    }

    // 수정
    @PutMapping("/{id}")
    public ResponseEntity<?> modifyPost(@PathVariable("id") Integer id
    , @RequestBody UpdateRequest updateRequest, Principal principal) throws BaseException {
        if (principal == null) {
            throw new BaseException(ErrorCode.UNAUTHORIZED_ACCESS);
        }

        Optional<Post> post=postService.findById(id);
        if(post.isPresent()){
            SiteUser user=post.get().getAuthor();
            SiteUser currentUser=userService.getUser(principal.getName());

            if(!user.getId().equals(currentUser.getId())){
                throw new BaseException(ErrorCode.UNAUTHORIZED_ACCESS);
            }
            Post modified=postService.modify(post.get(),updateRequest);
            PostDto postDto=new PostDto(modified);
            return  ResponseEntity.ok(postDto);
        }else{
            throw new BaseException(ErrorCode.POST_NOT_FOUND);
        }
    }

    // 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePost(@PathVariable("id") Integer id,Principal principal) throws BaseException {
        Optional<Post> post=postService.findById(id);
        if (principal == null) {
            throw new BaseException(ErrorCode.UNAUTHORIZED_ACCESS);
        }
        if(post.isPresent()){
            SiteUser user=post.get().getAuthor();
            SiteUser currentUser=userService.getUser(principal.getName());
            if(!user.getId().equals(currentUser.getId())){
                throw new BaseException(ErrorCode.UNAUTHORIZED_ACCESS);
            }
            postService.delete(post.get());
            return ResponseEntity.ok("게시물이 삭제되었습니다.");
        }else{
            throw new BaseException(ErrorCode.POST_NOT_FOUND);
        }
    }

    // 제목 기반 검색(포함하면 반환)
//    @GetMapping("/search/title")
//    public ResponseEntity<?> searchTitle(@RequestParam String title) throws BaseException {
//        List<Post> postList=postService.findByTitle(title);
//        if(postList.isEmpty()){
//            throw new BaseException(ErrorCode.POST_NOT_FOUND);
//        }
//        List<PostDto> postDtos=postList.stream()
//                .map(PostDto::new)
//                .collect(Collectors.toList());
//        return ResponseEntity.ok(postDtos);
//    }


    @GetMapping("/search/title")
    public ResponseEntity<PageResponse<PostDto>> searchTitle(@RequestParam String title
            ,@RequestParam(defaultValue = "0") int page
            ,@RequestParam(defaultValue = "10") int size) throws BaseException {
        Pageable pageable=PageRequest.of(page,size);
        Page<Post> postPage=postService.findByTitle(title,pageable);
        if(postPage.isEmpty()){
            throw new BaseException(ErrorCode.POST_NOT_FOUND);
        }
        List<PostDto> postDtos=postPage.stream()
                .map(PostDto::new)
                .collect(Collectors.toList());
        PageResponse<PostDto> response=new PageResponse<>(postDtos,postPage.getNumber(),postPage.getTotalPages());
        return ResponseEntity.ok(response);
    }

    // 내용 기반 검색(포함하면 반환)
    @GetMapping("/search/content")
    public ResponseEntity<?> searchContent(@RequestParam String content) throws BaseException {
        List<Post> postList=postService.findByContent(content);
        if(postList.isEmpty()){
            throw new BaseException(ErrorCode.POST_NOT_FOUND);
        }
        List<PostDto> postDtos=postList.stream()
                .map(PostDto::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(postDtos);
    }

    //제목 + 내용 같이 검색
    @GetMapping("/search/all")
    public ResponseEntity<?> searchAll(@RequestParam String word) throws BaseException {
        List<Post> postList=postService.findByTitleOrContent(word);
        if(postList.isEmpty()){
            throw new BaseException(ErrorCode.POST_NOT_FOUND);
        }else{
            List<PostDto> postDtos=postList.stream()
                    .map(PostDto::new)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(postDtos);
        }
    }

    // 작성자 기반 검색
    @GetMapping("/search/user")
    public ResponseEntity<?> searchUser(@RequestParam String name) throws BaseException {
        SiteUser user= userService.getUser(name);
        List<Post> postList=postService.findByUser(user);
        if(postList.isEmpty()){
            throw new BaseException(ErrorCode.POST_NOT_FOUND);
        }else{
            List<PostDto> postDtos=postList.stream()
                    .map(PostDto::new)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(postDtos);
        }
    }


    //좋아요
    @PostMapping("/like/{id}")
    public ResponseEntity<?> likePost(@PathVariable("id") Integer id,Principal principal) throws BaseException {
        if(principal==null){
            throw new BaseException(ErrorCode.UNAUTHORIZED_ACCESS);
        }
        SiteUser user= userService.getUser(principal.getName());
        Optional<Post> post=postService.findById(id);
        if(post.isEmpty()){
            throw new BaseException(ErrorCode.POST_NOT_FOUND);
        }else{
            Post likedPost=postLikeService.addLike(post.get(),user);
            PostDto postDto=new PostDto(likedPost);
            return ResponseEntity.ok(postDto);
        }
    }

    //싫어요
    @PostMapping("/dislike/{id}")
    public ResponseEntity<?> disLikePost(@PathVariable("id") Integer id,Principal principal) throws BaseException {
        if(principal==null){
            throw new BaseException(ErrorCode.UNAUTHORIZED_ACCESS);
        }
        SiteUser user= userService.getUser(principal.getName());
        Optional<Post> post=postService.findById(id);

        if(post.isEmpty()){
            throw new BaseException(ErrorCode.POST_NOT_FOUND);
        }else{
            Post dislikedPost=postLikeService.addDislike(post.get(),user);
            PostDto postDto=new PostDto(dislikedPost);
            return ResponseEntity.ok(postDto);
        }
    }

    // 댓글

}
