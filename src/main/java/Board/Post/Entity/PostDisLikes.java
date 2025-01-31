package Board.Post.Entity;

import Board.User.SiteUser;
import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@Data
@RequiredArgsConstructor
@Table(name="post_Dislikes")
public class PostDisLikes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private Post post;

    @ManyToOne
    private SiteUser user;

    public PostDisLikes(Post post, SiteUser user) {
        this.post=post;
        this.user=user;
    }
}
