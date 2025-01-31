package Board.Post;

import Board.User.SiteUser;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 200)
    private String title;
    @Column(columnDefinition = "TEXT")
    private String content;

    @ManyToOne
    private SiteUser author;

    @OneToMany
    private Set<SiteUser> likes=new HashSet<>();

    @OneToMany
    private Set<SiteUser> dislikes=new HashSet<>();

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd HH:mm")
    private LocalDateTime createTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd HH:mm")
    private LocalDateTime modifyTime;

    public void addLike(SiteUser user) {
        likes.add(user);
    }
    public void subLike(SiteUser user) {
        likes.remove(user);
    }
    public void addDislike(SiteUser user) {
        dislikes.add(user);
    }
    public void subDislike(SiteUser user) {
        dislikes.remove(user);
    }

}
