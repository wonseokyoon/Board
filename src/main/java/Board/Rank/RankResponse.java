package Board.Rank;

import Board.Post.Post;
import lombok.Data;

import java.util.List;

@Data
public class RankResponse {
    private int rank;
    private PostDetails post;
    private int likes;
    private int dislikes;

    public RankResponse(int rank,Post post){
        this.rank=rank;
        this.post=new PostDetails(post);
        this.likes=post.getLikes().size();
        this.dislikes=post.getDislikes().size();
    }

    @Data
    public static class PostDetails{
        private int postId;
        private String postTitle;
        private String postContent;
        private String author;
        public PostDetails(Post post){
            this.postId=post.getId();
            this.postTitle=post.getTitle();
            this.postContent=post.getContent();
            this.author=post.getAuthor().getUsername();
        }
    }

}
