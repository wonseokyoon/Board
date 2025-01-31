package Board.Post.Entity;

import Board.User.SiteUser;
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
    private Post post;

    @ManyToOne
    private SiteUser user;

    public PostLikes(Post post,SiteUser user){
        this.post=post;
        this.user=user;
    }

}
