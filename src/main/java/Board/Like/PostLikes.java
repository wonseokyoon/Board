package Board.Like;

import Board.Post.Post;
import Board.User.SiteUser;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@Data
@RequiredArgsConstructor
@Table(name="post_likes")
public class PostLikes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JsonBackReference(value = "postLikes")
    private Post post;

    @ManyToOne
    private SiteUser user;

    public PostLikes(Post post,SiteUser user){
        this.post=post;
        this.user=user;
    }

}
