package Board.Comment;


import Board.Post.Post;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentResponse {
//    private String postTitle;
//    private String postContent;
    private PostDetails post;
    private CommentDetails comment;
    public CommentResponse(Comment comment){
        this.post=new PostDetails(comment.getPost());
        this.comment=new CommentDetails(comment);
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

    @Data
    public static class CommentDetails{
        private int id;
        private String content;
        private String author;
        private int likes;
        private int disLikes;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd HH:mm")
        private LocalDateTime createTime;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd HH:mm")
        private LocalDateTime modifyTime;


        public CommentDetails(Comment comment){
            this.id=comment.getId();
            this.content= comment.getContent();
            this.author= comment.getAuthor().getUsername();
            this.createTime= comment.getCreateTime();
            this.modifyTime= comment.getModifyTime();
            this.likes=comment.getLikes().size();
            this.disLikes=comment.getDislikes().size();
        }
    }

}
