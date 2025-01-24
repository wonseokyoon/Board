package Board.Post;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class UpdateRequest {
    private String title;
    private String content;
}
