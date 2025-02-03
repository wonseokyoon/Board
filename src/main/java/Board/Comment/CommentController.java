package Board.Comment;

import Board.Exception.BaseException;
import Board.Exception.ErrorCode;
import Board.Post.Post;
import Board.Post.PostService;
import Board.User.SiteUser;
import Board.User.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;
    @Autowired
    private UserService userService;
    @Autowired
    private PostService postService;
    @PostMapping("/create")
    public ResponseEntity<?> createComment(@RequestParam Integer postId, @RequestBody Comment comment, Principal principal) throws BaseException {
        if(principal==null){
            throw new BaseException(ErrorCode.UNAUTHORIZED_ACCESS);
        }
        SiteUser user= userService.getUser(principal.getName());
        Optional<Post> post=postService.findById(postId);
        if(post.isEmpty()) throw new BaseException(ErrorCode.POST_NOT_FOUND);

        Comment createdComment=commentService.create(comment,user, post.get());
        CommentResponse dto=new CommentResponse(createdComment);

        return ResponseEntity.ok(dto);
    }

    // 유저 기반 검색
    @GetMapping("/search/user")
    public ResponseEntity<?> searchUser(@RequestParam String name) throws BaseException {
        SiteUser user= userService.getUser(name);
        List<Comment> commentList=commentService.findByUser(user);
        if(commentList.isEmpty()){
            throw new BaseException(ErrorCode.COMMENT_NOT_FOUND);
        }
        List<CommentResponse> commentResponses=commentList.stream()
                .map(CommentResponse::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(commentResponses);

    }

    //게시글 기반 검색
    @GetMapping("search/post")
    public ResponseEntity<?> searchPost(@RequestParam Integer postId) throws BaseException {
        Optional<Post> post=postService.findById(postId);
        if(post.isEmpty()) throw new BaseException(ErrorCode.POST_NOT_FOUND);

        List<Comment> commentList=commentService.findByPost(post.get());
        if (commentList.isEmpty()) throw new BaseException(ErrorCode.COMMENT_NOT_FOUND);

        List<CommentResponse.CommentDetails> response=commentList.stream()
                .map(CommentResponse.CommentDetails::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    // 따봉
    @PostMapping("/like/{id}")
    public ResponseEntity<?> likeComment(@PathVariable("id") Integer id,Principal principal) throws BaseException {
        if(principal==null){
            throw new BaseException(ErrorCode.UNAUTHORIZED_ACCESS);
        }
        SiteUser user= userService.getUser(principal.getName());
        Comment comment=commentService.findById(id);

        Comment likedComment=commentService.addLike(comment,user);
        CommentResponse response=new CommentResponse(likedComment);
        return ResponseEntity.ok(response);
    }

    // 싫어요
    @PostMapping("/dislike/{id}")
    public ResponseEntity<?> disLikeComment(@PathVariable("id") Integer id,Principal principal) throws BaseException {
        if(principal==null){
            throw new BaseException(ErrorCode.UNAUTHORIZED_ACCESS);
        }
        SiteUser user= userService.getUser(principal.getName());
        Comment comment= commentService.findById(id);

        Comment disLikedComment=commentService.addDisLike(comment,user);
        CommentResponse response=new CommentResponse(disLikedComment);
        return ResponseEntity.ok(response);

    }




}
