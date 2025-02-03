package Board.Comment;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ResponseDto {
    private String postTitle;
    private String postContent;
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

        this.postTitle= comment.getPost().getTitle();
        this.postContent= comment.getPost().getContent();
//        this.likes=post.getLikes().size();
//        this.disLikes=post.getDislikes().size();

    }

}
