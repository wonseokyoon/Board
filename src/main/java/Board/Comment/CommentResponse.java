package Board.Comment;


import Board.Post.Post;
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
        //like,dislike

        private LocalDateTime createTime;
        private LocalDateTime modifyTime;

        public CommentDetails(Comment comment){
            this.id=comment.getId();
            this.content= comment.getContent();
            this.author= comment.getAuthor().getUsername();
            this.createTime= comment.getCreateTime();
            this.modifyTime= comment.getModifyTime();

//            this.likes=post.getLikes().size();
//        this.disLikes=post.getDislikes().size();
        }
    }

}
