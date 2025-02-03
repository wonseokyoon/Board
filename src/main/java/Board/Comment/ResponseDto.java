package Board.Comment;


import Board.Post.Post;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ResponseDto {
//    private String postTitle;
//    private String postContent;
    private PostDetails post;
    private int id;
    private String content;
    private String author;
    //like,dislike

    private LocalDateTime createTime;
    private LocalDateTime modifyTime;

    public ResponseDto(Comment comment){
        this.id=comment.getId();
        this.content= comment.getContent();
        this.author= comment.getAuthor().getUsername();
        this.createTime= comment.getCreateTime();
        this.modifyTime= comment.getModifyTime();
        this.post=new PostDetails(comment.getPost());
//        this.postTitle= comment.getPost().getTitle();
//        this.postContent= comment.getPost().getContent();
//        this.likes=post.getLikes().size();
//        this.disLikes=post.getDislikes().size();

    }
    @Data
    public static class PostDetails{
        private String postTitle;
        private String postContent;
        public PostDetails(Post post){
            this.postTitle=post.getTitle();
            this.postContent=post.getContent();
        }
    }

}
