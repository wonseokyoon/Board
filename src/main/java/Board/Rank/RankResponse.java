package Board.Rank;

import Board.Post.Post;
import lombok.Data;

import java.util.List;

@Data
public class RankResponse {
    private int id;
    private PostDetails post;
    private int likes;

    public RankResponse(int id,Post post){
        this.id=id;
        this.post=new PostDetails(post);
        this.likes=post.getLikes().size();
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
