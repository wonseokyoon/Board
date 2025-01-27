package Board.Post;

import lombok.Getter;
import lombok.Setter;

@Getter
public class PostResponse {
    private final int id;
    private final String title;
    private final String content;
    private final String author;
    private final String createTime;
    private String modifyTime;

    public PostResponse(int id, String title, String content, String author, String createTime,String modifyTime) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.author = author;
        this.createTime = createTime;
        this.modifyTime=modifyTime;
    }
}
