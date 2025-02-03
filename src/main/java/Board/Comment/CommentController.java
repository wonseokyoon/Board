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
import java.util.Optional;

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



}
