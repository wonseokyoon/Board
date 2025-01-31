package Board.Post;

import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;

@Data
public class PostDto {
    private int id;
    private String title;
    private String content;
    private String author;
    private int likes;
    private int disLikes;
    private LocalDateTime createTime;
    private LocalDateTime modifyTime;

    public PostDto(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.author =post.getAuthor().getUsername();
        this.likes=post.getLikes().size();
        this.disLikes=post.getDislikes().size();
        this.createTime = post.getCreateTime();
        this.modifyTime=post.getModifyTime();
    }
}
