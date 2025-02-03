package Board.Rank;

import Board.Post.Dto.PostDto;
import Board.Post.Post;
import Board.Post.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@Transactional
@RequiredArgsConstructor
@RequestMapping("/rank")
public class RankController {

    private final RankService rankService;
    private final PostService postService;
    // 좋아요 정렬
    @GetMapping("like")
    ResponseEntity<?> rankByLike(){
        List<Post> postList=postService.list();
        List<Post> sortedList=rankService.byLike(postList);
        List<RankResponse> response=sortedList.stream()
                .map(post->new RankResponse(sortedList.indexOf(post)+1,post))
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }



}
