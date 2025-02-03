package Board.Like;

import Board.Comment.Comment;
import Board.User.SiteUser;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@Data
@RequiredArgsConstructor
@Table(name="comment_likes")
public class CommentLikes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JsonBackReference
    private Comment comment;

    @ManyToOne
    private SiteUser author;

    public CommentLikes(Comment comment,SiteUser author){
        this.comment=comment;
        this.author=author;
    }

}
