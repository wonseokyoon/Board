package Board.Post.Dto;

import Board.Comment.CommentResponse;
import Board.Post.Post;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class PostDto {
    private int id;
    private String title;
    private String content;
    private String author;
    private int likes;
    private int disLikes;
    private List<CommentResponse.CommentDetails> commentList;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd HH:mm")
    private LocalDateTime createTime;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd HH:mm")
    private LocalDateTime modifyTime;

    public PostDto(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.author =post.getAuthor().getUsername();
        this.likes=post.getLikes().size();
        this.disLikes=post.getDislikes().size();
        this.commentList=post.getComments().stream()
                .map(CommentResponse.CommentDetails::new)
                .collect(Collectors.toList());
        this.createTime = post.getCreateTime();
        this.modifyTime=post.getModifyTime();
    }
}
